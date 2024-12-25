package com.chenna.lloydsamplepoject.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.chenna.domain.model.CastModel
import com.chenna.lloydsamplepoject.R
import com.chenna.lloydsamplepoject.util.Utility

/**
 * Created by Chenna Rao on 25/12/24.
 * <p>
 * Frost Interactive
 */
@Composable
fun CastItem(
    model: CastModel,
    onClick: (String) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        elevation = 5.dp,
        shape = RoundedCornerShape(5.dp),
        backgroundColor = Color.White
    ) {
        Box(modifier = Modifier
            .clickable {
                onClick(model.person.url ?: "")
            }
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = model.person.image.medium),
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .width(100.dp)
                        .height(120.dp)
                        .background(color = Color.Gray.copy(alpha = 0.1f)),
                    contentScale = ContentScale.Crop
                )

                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 12.dp)
                ) {
                    Text(
                        text = model.person.name,
                        fontFamily = FontFamily.Default,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                    )

                    model.person.birthday?.let {
                        Text(
                            text = Utility.formatDateToReadable(it).uppercase(),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            fontFamily = FontFamily.Default,
                            color = Color.Black.copy(alpha = 0.7f),
                        )
                    }

                    model.person.country.name?.let {
                        Text(
                            text = it,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.W400,
                            fontFamily = FontFamily.Default,
                            color = Color.Black.copy(alpha = 0.5f),
                        )
                    }
                }

                Image(
                    painter = painterResource(id = R.drawable.ic_forward_icon),
                    contentDescription = "",
                    modifier = Modifier
                        .size(40.dp)
                        .padding(end = 24.dp),
                    colorFilter = ColorFilter.tint(Color.Black.copy(alpha = 0.7f)),
                )

            }

        }
    }
}

@Preview(
    showBackground = true,
    device = Devices.PIXEL_2_XL
)
@Composable
fun CastItemPreviewCard() {

    CastItem(model = Utility.getCastData()) {}
}