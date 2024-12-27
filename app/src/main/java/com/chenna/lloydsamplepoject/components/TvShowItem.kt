package com.chenna.lloydsamplepoject.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.chenna.domain.models.ShowModel
import com.chenna.lloydsamplepoject.util.Utility

/**
 * Created by Chenna Rao on 25/12/24.
 * <p>
 * Frost Interactive
 */
@Composable
fun TvShowCard(
    model: ShowModel,
    onClick: () -> Unit,
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .background(
                color = Color.Transparent
            )
            .clickable {
                onClick()
            }
    ) {
        model.image?.let {
            AsyncImage(
                model = it.medium,
                contentDescription = model.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .aspectRatio(0.75f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(color = Color.Gray.copy(alpha = 0.1f))
            )
            Spacer(modifier = Modifier.height(2.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TvShowPreviewCard() {
    TvShowCard(model = Utility.getTvShowDummyData()) {}
}