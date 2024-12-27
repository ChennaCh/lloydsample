package com.chenna.lloydsamplepoject.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.chenna.domain.models.ShowModel
import com.chenna.lloydsamplepoject.components.DetailsRow
import com.chenna.lloydsamplepoject.components.GenresSection
import com.chenna.lloydsamplepoject.components.ImageSection
import com.chenna.lloydsamplepoject.components.RatingSection
import com.chenna.lloydsamplepoject.components.SummarySection
import com.chenna.lloydsamplepoject.components.TitleSection
import com.chenna.lloydsamplepoject.models.TvShowDetailsActionEvent
import com.chenna.lloydsamplepoject.viewmodels.TvShowDetailsViewModel

/**
 * Created by Chenna Rao on 18/12/24.
 * <p>
 * Frost Interactive
 */
@Composable
fun ShowDetailsScreen(
    showModel: ShowModel,
    viewModel: TvShowDetailsViewModel = hiltViewModel(),
) {

    val isBookmarked = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        showModel.let {
            viewModel.onActionEvent(TvShowDetailsActionEvent.IsShowBookmarked(showId = it.id))
        }
    }

    LaunchedEffect(viewModel) {
        viewModel.isBookmarked.collect {
            isBookmarked.value = it
        }
    }

    ShowDetailsContent(
        showModel,
        isBookmarked = isBookmarked
    ) {
        viewModel.onActionEvent(it)
    }
}

@Composable
fun ShowDetailsContent(
    showModel: ShowModel,
    isBookmarked: MutableState<Boolean>,
    action: (TvShowDetailsActionEvent) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.White)
    ) {

        ImageSection(showModel, isBookmarked, action)
        Spacer(modifier = Modifier.height(16.dp))
        TitleSection(showModel)
        Spacer(modifier = Modifier.height(6.dp))
        DetailsRow(showModel)
        Spacer(modifier = Modifier.height(8.dp))
        GenresSection(showModel.genres)
        RatingSection(showModel.rating?.average)
        Spacer(modifier = Modifier.height(8.dp))
        SummarySection(showModel.summary)
    }
}


