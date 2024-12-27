package com.chenna.lloydsamplepoject.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.chenna.lloydsamplepoject.R
import com.chenna.lloydsamplepoject.components.CastItem
import com.chenna.lloydsamplepoject.config.Constants
import com.chenna.lloydsamplepoject.models.CastActionEvent
import com.chenna.lloydsamplepoject.models.CastStateModel
import com.chenna.lloydsamplepoject.models.UiState
import com.chenna.lloydsamplepoject.util.NavigationEvent
import com.chenna.lloydsamplepoject.util.NoInternetContent
import com.chenna.lloydsamplepoject.util.ProgressBarCompose
import com.chenna.lloydsamplepoject.util.UiUtils
import com.chenna.lloydsamplepoject.viewmodels.CastViewModel
import kotlinx.coroutines.flow.collectLatest

/**
 * Created by Chenna Rao on 25/12/24.
 * <p>
 * Frost Interactive
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CastScreen(
    viewModel: CastViewModel = hiltViewModel(),
    navigate: (NavigationEvent) -> Unit,
) {

    val uiState = remember { mutableStateOf(UiState<CastStateModel>()) }
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
        viewModel.onActionEvent(CastActionEvent.FetchCasts)
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
        onRefresh = { viewModel.onActionEvent(CastActionEvent.Retry) },
    ) {
        CastsListContent(uiState.value, context) {
            viewModel.onActionEvent(it)
        }
    }

}

@Composable
fun CastsListContent(
    uiState: UiState<CastStateModel>,
    context: Context,
    action: (CastActionEvent) -> Unit,
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
            LazyColumn(
                modifier = Modifier
            ) {
                itemsIndexed(it) { index, cast ->

                    // Add more padding if the item is the first one
                    if (index == 0) {
                        Spacer(modifier = Modifier.height(16.dp))
                    } else {
                        Spacer(modifier = Modifier.height(6.dp))
                    }

                    CastItem(
                        cast,
                        onClick = {
                            action(
                                CastActionEvent.RedirectToWeb(
                                    url = it,
                                    context = context
                                )
                            )
                        })

                    // Add extra bottom padding if the item is the last one
                    if (index == it.size - 1) {
                        Spacer(modifier = Modifier.height(16.dp))
                    } else {
                        Spacer(modifier = Modifier.height(6.dp))
                    }
                }
            }
        }

    }

}
