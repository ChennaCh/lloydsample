package com.chenna.lloydsamplepoject.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chenna.domain.model.ShowModel
import com.chenna.lloydsamplepoject.util.Utility

/**
 * Created by Chenna Rao on 25/12/24.
 * <p>
 * Frost Interactive
 */
@Composable
fun DetailsRow(showModel: ShowModel) {
    Column {
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            DetailText("${showModel.runtime ?: "N/A"} mins")
            DetailText("|")
            showModel.language?.let { DetailText(it) }
            DetailText("|")
            showModel.type?.let { DetailText(it) }
            DetailText("|")
            DetailText(showModel.network?.country?.name ?: "N/A")
        }
    }
}

@Composable
fun DetailText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.body1,
        modifier = Modifier.padding(horizontal = 2.dp),
        color = Color.Gray.copy(alpha = 0.9f),
        fontFamily = FontFamily.Default,
        fontSize = 12.sp
    )
}

@Preview(showBackground = true)
@Composable
fun DetailsRowPreviewCard() {
    DetailsRow(showModel = Utility.getTvShowDummyData())
}

