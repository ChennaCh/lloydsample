package com.chenna.lloydsamplepoject.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.chenna.domain.entities.ShowEntity
import com.chenna.domain.utils.Constants
import com.chenna.lloydsamplepoject.R
import com.chenna.lloydsamplepoject.models.BookmarksActionEvent
import com.chenna.lloydsamplepoject.models.BookmarksStateModel
import com.chenna.lloydsamplepoject.models.UiState
import com.chenna.lloydsamplepoject.util.NavigationEvent
import com.chenna.lloydsamplepoject.viewmodels.BookmarksViewModel
import kotlinx.coroutines.flow.collectLatest

/**
 * Created by Chenna Rao on 17/12/24.
 * <p>
 * Frost Interactive
 */
@Composable
fun BookmarkScreen(
    viewModel: BookmarksViewModel = hiltViewModel(),
    navigate: (NavigationEvent) -> Unit,
) {

    val uiState = remember { mutableStateOf(UiState<BookmarksStateModel>()) }

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collectLatest {
            navigate(it)
        }
    }

    LaunchedEffect(viewModel) {
        viewModel.resultState.collectLatest {
            uiState.value = it
        }
    }

    LaunchedEffect(Unit) {
        viewModel.onActionEvent(BookmarksActionEvent.GetBookmarks)
    }

    BookmarkTVShowListContent(
        uiState.value
    ) { show ->
        viewModel.onActionEvent(actionEvent = BookmarksActionEvent.RedirectToShowDetails(show))
    }
}

@Composable
fun BookmarkTVShowListContent(
    uiState: UiState<BookmarksStateModel>,
    onShowClick: (ShowEntity) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.data?.bookMarks?.isEmpty() == true) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.no_bookmarks),
                    contentDescription = "no_bookmarks",
                    modifier = Modifier.size(200.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = Constants.NO_BOOKMARKS,
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily.Default,
                    fontSize = 16.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            }
        } else
            uiState.data?.bookMarks?.let {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                ) {
                    items(it) { show ->
                        TvShowItem(
                            show = show,
                            onClick = { onShowClick(show) }
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
    }
}

@Composable
fun TvShowItem(
    show: ShowEntity,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 0.dp)
            .clickable { onClick() }
            .background(
                color = Color.Transparent, // Optional background if needed
                shape = RoundedCornerShape(4.dp)
            )
    ) {
        Row(
            modifier = Modifier.padding(0.dp),
            verticalAlignment = Alignment.Top,
        ) {
            show.image.let { image ->
                AsyncImage(
                    model = image.medium,
                    contentDescription = show.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(90.dp)
                        .height(120.dp)
                        .clip(RoundedCornerShape(4.dp))
                )
                Spacer(modifier = Modifier.width(16.dp))
            }

            Column {
                show.name.let {
                    Text(
                        text = it,
                        fontWeight = FontWeight.Medium,
                        fontFamily = FontFamily.Default,
                        fontSize = 16.sp,
                        color = Color.Black,
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    for (i in 1..3) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            tint = Color(0xFFFFC700),
                            modifier = Modifier
                                .width(14.dp)
                                .height(14.dp)
                        )
                    }
                    Text(
                        text = "${show.rating.average}",
                        fontWeight = FontWeight.Medium,
                        fontFamily = FontFamily.Default,
                        fontSize = 12.sp,
                        color = Color.Gray,
                    )
                }
                show.genres.joinToString(", ").let {
                    Text(
                        text = it,
                        color = Color.Gray,
                        fontWeight = FontWeight.Normal,
                        fontFamily = FontFamily.Default,
                        fontSize = 12.sp,
                    )
                }
            }
        }
    }
}

