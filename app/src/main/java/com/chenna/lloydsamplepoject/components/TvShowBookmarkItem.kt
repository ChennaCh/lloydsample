package com.chenna.lloydsamplepoject.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.chenna.domain.entities.ShowEntity
import com.chenna.domain.model.toShowEntity
import com.chenna.lloydsamplepoject.util.Utility

/**
 * Created by Chenna Rao on 25/12/24.
 * <p>
 * Frost Interactive
 */
@Composable
fun TvShowBookmarkItem(
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
                        .background(color = Color.Gray.copy(alpha = 0.1f))
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

@Preview(showBackground = true)
@Composable
fun TvShowBookmarkPreviewCard() {
    TvShowBookmarkItem(show = Utility.getTvShowDummyData().toShowEntity()) {}
}