package com.chenna.lloydsamplepoject.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.chenna.domain.model.ShowModel
import com.chenna.domain.model.toShowEntity
import com.chenna.lloydsamplepoject.models.TVShowDetailsActionEvent
import com.chenna.lloydsamplepoject.viewmodels.TvShowDetailsViewModel

/**
 * Created by Chenna Rao on 18/12/24.
 * <p>
 * Frost Interactive
 */
@Composable
fun ShowDetailsScreen(
    showModel: ShowModel?,
    viewModel: TvShowDetailsViewModel = hiltViewModel(),
) {
    val scrollState = rememberScrollState()
    var isOriginLoaded by remember { mutableStateOf(false) }
    var isBookmarked = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        showModel?.let {
            viewModel.onActionEvent(TVShowDetailsActionEvent.IsShowBookmarked(showId = it.id))
        }
    }

    LaunchedEffect(viewModel) {
        viewModel.isBookmarked.collect {
            isBookmarked.value = it
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(Color.White)
    ) {
        // Image Section
        Box(
            modifier = Modifier
                .fillMaxWidth()

        ) {
            // Display medium image initially
            AsyncImage(
                model = showModel?.image?.medium,
                contentDescription = showModel?.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(600.dp),
                onSuccess = {
                    isOriginLoaded = true
                } // Mark origin image loading success
            )

            // Load original image after the medium image
            if (isOriginLoaded && showModel?.image?.original != null) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(showModel.image?.original)
                        .size(Size.ORIGINAL) // Load full resolution
                        .crossfade(true)
                        .build(),
                    contentDescription = showModel.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(600.dp),
                )
            }

            // Gradient overlay
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp) // Height of the gradient
                    .align(Alignment.BottomCenter)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.7f) // Gradient color
                            )
                        )
                    )
            )

            IconButton(
                onClick = {
                    val showModelObject = showModel?.toShowEntity()
                    if (isBookmarked.value) {
                        showModelObject?.let {
                            viewModel.onActionEvent(
                                TVShowDetailsActionEvent.RemoveBookMark(
                                    it.id
                                )
                            )
                        }
                    } else {
                        showModelObject?.let {
                            viewModel.onActionEvent(TVShowDetailsActionEvent.SaveBookMark(it)) // Remove bookmark
                        }
                    }
                    isBookmarked.value = !isBookmarked.value // Toggle bookmark state
                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = if (isBookmarked.value) Icons.Filled.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Bookmark",
                    tint = if (isBookmarked.value) Color(0xFFff6823) else Color.Yellow,
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Title Section
        Text(
            text = showModel?.name.orEmpty(),
            style = MaterialTheme.typography.h4,
            fontFamily = FontFamily.Default,
            fontSize = 24.sp,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = " ${showModel?.runtime ?: "N/A"} mins",
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .padding(horizontal = 2.dp),
                color = Color.Gray.copy(alpha = 0.9f),
                fontFamily = FontFamily.Default,
                fontSize = 12.sp
            )

            Text(
                text = " | ",
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .padding(horizontal = 2.dp),
                color = Color.Gray.copy(alpha = 0.9f),
                fontFamily = FontFamily.Default,
                fontSize = 12.sp
            )

            Text(
                text = " ${showModel?.language}",
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .padding(horizontal = 2.dp),
                color = Color.Gray.copy(alpha = 0.9f),
                fontFamily = FontFamily.Default,
                fontSize = 12.sp
            )

            Text(
                text = " | ",
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .padding(horizontal = 2.dp),
                color = Color.Gray.copy(alpha = 0.9f),
                fontFamily = FontFamily.Default,
                fontSize = 12.sp
            )

            Text(
                text = " ${showModel?.type}",
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .padding(horizontal = 2.dp),
                color = Color.Gray.copy(alpha = 0.9f),
                fontFamily = FontFamily.Default,
                fontSize = 12.sp
            )

            Text(
                text = " | ",
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .padding(horizontal = 2.dp),
                color = Color.Gray.copy(alpha = 0.9f),
                fontFamily = FontFamily.Default,
                fontSize = 12.sp
            )

            Text(
                text = " ${showModel?.network?.country?.name}",
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .padding(horizontal = 2.dp),
                color = Color.Gray.copy(alpha = 0.9f),
                fontFamily = FontFamily.Default,
                fontSize = 12.sp
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            showModel?.genres?.let { GenresList(genres = it) }
        }

        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            showModel?.rating?.average?.let {
                StarRatingBar(
                    maxStars = 5,
                )
            }
            Text(
                text = " ${showModel?.rating?.average}",
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .padding(horizontal = 2.dp),
                color = Color.Gray.copy(alpha = 0.9f),
                fontFamily = FontFamily.Default,
                fontSize = 12.sp
            )

        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.Top,
        ) {

            // Summary Section with HTML Parsing
            val parsedSummary =
                showModel?.summary?.let {
                    HtmlCompat.fromHtml(it, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
                }
            if (parsedSummary != null) {
                Text(
                    text = parsedSummary,
                    style = MaterialTheme.typography.body1,
                    color = Color.Gray.copy(alpha = 0.9f),
                    fontFamily = FontFamily.Default,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                )
            }

        }
    }
}

@Composable
fun StarRatingBar(
    maxStars: Int = 5,
) {
    val density = LocalDensity.current.density
    val starSpacing = (0.5f * density).dp

    Row(
        modifier = Modifier.selectableGroup(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 1..(maxStars)) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = Color(0xFFFFC700),
                modifier = Modifier
                    .width(16.dp)
                    .height(16.dp)
            )

            if (i < maxStars) {
                Spacer(modifier = Modifier.width(starSpacing))
            }
        }
    }
}

@Composable
fun GenresList(genres: List<String>) {
    LazyRow(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(genres) { genre ->
            GenreChip(genre = genre)
        }
    }
}

@Composable
fun GenreChip(genre: String) {
    Surface(
        modifier = Modifier
            .padding(end = 8.dp)
            .background(Color(0xFFff6823), shape = MaterialTheme.shapes.small)
            .padding(horizontal = 12.dp, vertical = 6.dp),
        shape = MaterialTheme.shapes.small,
        color = Color(0xFFff6823) // You can customize the background color as needed
    ) {
        Text(
            text = genre,
            fontFamily = FontFamily.Default,
            fontSize = 14.sp,
            color = Color.White,
            style = MaterialTheme.typography.body2,
            fontWeight = FontWeight.Light
        )
    }
}


