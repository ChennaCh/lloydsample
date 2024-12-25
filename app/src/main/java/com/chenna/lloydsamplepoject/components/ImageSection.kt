package com.chenna.lloydsamplepoject.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.chenna.domain.model.ShowModel
import com.chenna.domain.model.toShowEntity
import com.chenna.lloydsamplepoject.models.TvShowDetailsActionEvent
import com.chenna.lloydsamplepoject.util.Utility

/**
 * Created by Chenna Rao on 25/12/24.
 * <p>
 * Frost Interactive
 */
@Composable
fun ImageSection(
    showModel: ShowModel,
    isBookmarked: MutableState<Boolean>,
    action: (TvShowDetailsActionEvent) -> Unit,
) {

    var isOriginLoaded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()

    ) {
        // Display medium image initially
        AsyncImage(
            model = showModel.image?.medium,
            contentDescription = showModel.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(600.dp),
            onSuccess = {
                isOriginLoaded = true
            } // Mark origin image loading success
        )

        // Load original image after the medium image
        if (isOriginLoaded && showModel.image?.original != null) {
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
                val showModelObject = showModel.toShowEntity()
                if (isBookmarked.value) {
                    action(
                        TvShowDetailsActionEvent.RemoveBookMark(
                            showModelObject.id
                        )
                    )
                } else {
                    action(TvShowDetailsActionEvent.SaveBookMark(showModelObject))
                }
                isBookmarked.value = !isBookmarked.value
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
}

@Preview(showBackground = true)
@Composable
fun ImageSectionPreviewCard() {

    val isBookmarked = remember { mutableStateOf(false) }
    ImageSection(showModel = Utility.getTvShowDummyData(), isBookmarked) {}
}