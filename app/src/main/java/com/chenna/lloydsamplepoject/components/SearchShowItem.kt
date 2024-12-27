package com.chenna.lloydsamplepoject.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import coil.compose.AsyncImage
import com.chenna.domain.models.ShowModel

/**
 * Created by Chenna Rao on 27/12/24.
 * <p>
 * Frost Interactive
 */
@Composable
fun SearchShowItem(
    show: ShowModel,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 0.dp)
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
                    model = image?.medium,
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
                show.name?.let {
                    Text(
                        text = it,
                        fontWeight = FontWeight.Medium,
                        fontFamily = FontFamily.Default,
                        fontSize = 16.sp,
                        color = Color.Black,
                    )
                }

                if (show.genres != null)
                    show.genres?.joinToString(", ").let {
                        it?.let { it1 ->
                            Text(
                                text = it1,
                                color = Color.Black.copy(alpha = 0.8f),
                                fontWeight = FontWeight.W600,
                                fontFamily = FontFamily.Default,
                                fontSize = 12.sp,
                            )
                        }
                    }

                Spacer(modifier = Modifier.height(4.dp))
                show.summary?.let {
                    Text(
                        text = HtmlCompat.fromHtml(it, HtmlCompat.FROM_HTML_MODE_LEGACY).toString(),
                        color = Color.Gray,
                        fontWeight = FontWeight.Normal,
                        fontFamily = FontFamily.Default,
                        fontSize = 12.sp,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(
                            lineHeight = 20.sp
                        )
                    )
                }
            }
        }
    }
}