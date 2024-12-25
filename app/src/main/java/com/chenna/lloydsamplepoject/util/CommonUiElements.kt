package com.chenna.lloydsamplepoject.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chenna.lloydsamplepoject.R

/**
 * Created by Chenna Rao on 18/12/24.
 * <p>
 * Frost Interactive
 */

@Composable
fun ProgressBarCompose(color: Color = Color.Red) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = color)
    }
}

@Composable
fun NoInternetContent() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        item {
            Image(
                painter = painterResource(id = R.drawable.ic_wifi_signal),
                contentDescription = "No Internet",
                modifier = Modifier.size(180.dp)
            )
        }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item {
            Text(
                text = Constants.CONNECTION_ERROR,
                color = Color.Black.copy(alpha = 0.6f),
                fontFamily = FontFamily.Default,
                fontSize = 18.sp,
                fontWeight = FontWeight.W500,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }

    }
}