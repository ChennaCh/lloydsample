package com.chenna.lloydsamplepoject.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chenna.lloydsamplepoject.util.Utility

/**
 * Created by Chenna Rao on 25/12/24.
 * <p>
 * Frost Interactive
 */
@Composable
fun GenresSection(genres: List<String>?) {
    genres?.let {
        LazyRow(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(it) { genre ->
                GenreChip(genre)
            }
        }
    }
}

@Composable
fun GenreChip(genre: String) {
    Surface(
        modifier = Modifier.padding(end = 8.dp),
        shape = MaterialTheme.shapes.small,
        color = Color(0xFFff6823)
    ) {
        Text(
            text = genre,
            fontFamily = FontFamily.Default,
            fontSize = 14.sp,
            color = Color.White,
            style = MaterialTheme.typography.body2,
            fontWeight = FontWeight.Light,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GenresSectionPreviewCard() {
    GenresSection(genres = Utility.getTvShowDummyData().genres)
}
