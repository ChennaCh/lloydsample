package com.chenna.lloydsamplepoject.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
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
import coil.compose.rememberAsyncImagePainter
import com.chenna.domain.model.CastModel
import com.chenna.lloydsamplepoject.util.Utility

/**
 * Created by Chenna Rao on 25/12/24.
 * <p>
 * Frost Interactive
 */
@Composable
fun CastItem(model: CastModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        elevation = 5.dp,
        shape = RoundedCornerShape(5.dp),
        backgroundColor = Color.White
    ) {
        Box(
            modifier = Modifier.padding(8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(8.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = model.person.image.medium),
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.Gray.copy(alpha = 0.2f), CircleShape),
                    contentScale = ContentScale.Crop
                )

                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(start = 12.dp)
                ) {

                    Text(
                        text = model.person.name,
                        fontFamily = FontFamily.Default,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                    )

                    model.person.country.name?.let {
                        Text(
                            text = it,
                            fontSize = 14.sp,
                            fontFamily = FontFamily.Default,
                            color = Color.Black.copy(alpha = 0.7f),
                            modifier = Modifier.padding(0.dp)
                        )
                    }

                    model.person.birthday?.let {
                        Text(
                            text = Utility.formatDateToReadable(it),
                            fontSize = 14.sp,
                            fontFamily = FontFamily.Default,
                            color = Color.Black.copy(alpha = 0.7f),
                            modifier = Modifier.padding(0.dp)
                        )
                    }
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun CastItemPreviewCard() {

    CastItem(model = Utility.getCastData())
}