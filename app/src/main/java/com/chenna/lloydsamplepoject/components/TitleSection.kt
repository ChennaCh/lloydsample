package com.chenna.lloydsamplepoject.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
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
fun TitleSection(showModel: ShowModel) {
    Text(
        text = showModel.name.orEmpty(),
        style = MaterialTheme.typography.h4,
        fontFamily = FontFamily.Default,
        fontSize = 24.sp,
        color = Color.Black,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        textAlign = TextAlign.Center
    )
}

@Preview(showBackground = true)
@Composable
fun TitleSectionPreviewCard() {
    TitleSection(showModel = Utility.getTvShowDummyData())
}
