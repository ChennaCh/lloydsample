package com.chenna.lloydsamplepoject.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.chenna.domain.models.ShowModel
import com.chenna.lloydsamplepoject.R
import com.chenna.lloydsamplepoject.components.TvShowCard
import com.chenna.lloydsamplepoject.models.TvShowActionEvent
import com.chenna.lloydsamplepoject.models.TvShowStateModel
import com.chenna.lloydsamplepoject.models.UiState
import com.chenna.lloydsamplepoject.config.Constants
import com.chenna.lloydsamplepoject.util.NavigationEvent
import com.chenna.lloydsamplepoject.util.NoInternetContent
import com.chenna.lloydsamplepoject.util.ProgressBarCompose
import com.chenna.lloydsamplepoject.util.UiUtils
import com.chenna.lloydsamplepoject.viewmodels.TvShowsViewModel
import kotlinx.coroutines.flow.collectLatest

/**
 * Created by Chenna Rao on 17/12/24.
 * <p>
 * Frost Interactive
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowsScreen(
    viewModel: TvShowsViewModel = hiltViewModel(),
    navigate: (NavigationEvent) -> Unit,
) {

    val uiState = remember { mutableStateOf(UiState<TvShowStateModel>()) }
    val context = LocalContext.current
    val refreshState = remember { mutableStateOf(false) }
    val pullToRefreshState = rememberPullToRefreshState()


    LaunchedEffect(Unit) {
        viewModel.messageEvent.collectLatest {
            if (it.message != Constants.CONNECTION_ERROR)
                UiUtils.buildUIMessage(context = context, it = it)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collectLatest {
            navigate(it)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.onActionEvent(TvShowActionEvent.FetchTvShows)
    }

    LaunchedEffect(viewModel) {
        viewModel.resultState.collectLatest {
            uiState.value = it
        }
    }

    LaunchedEffect(viewModel) {
        viewModel.isRefreshing.collectLatest {
            refreshState.value = it
            if (!it) {
                pullToRefreshState.animateToHidden()
            }
        }
    }

    PullToRefreshBox(
        isRefreshing = refreshState.value,
        state = pullToRefreshState,
        onRefresh = { viewModel.onActionEvent(TvShowActionEvent.Retry) },
    ) {
        TvShowsListContent(uiState.value) { show ->
            viewModel.onActionEvent(actionEvent = TvShowActionEvent.RedirectToShowDetails(show))
        }
    }
}

@Composable
fun TvShowsListContent(
    uiState: UiState<TvShowStateModel>,
    onShowClick: (ShowModel) -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {

        if (uiState.isLoading) {
            ProgressBarCompose(color = colorResource(id = R.color.purple_700))
        }

        if (uiState.error?.title == Constants.CONNECTION_ERROR) {
            NoInternetContent()
        }

        uiState.data?.list?.let {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(4.dp),
                modifier = Modifier.fillMaxSize(),
            ) {
                items(it) { show ->
                    TvShowCard(show, onClick = { onShowClick(show) })
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGridItem() {

//    TvShowsListContent(uiState = UiUtils.staticTVShowsList()) {}
}
