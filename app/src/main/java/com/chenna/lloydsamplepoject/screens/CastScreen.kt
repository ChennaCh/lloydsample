package com.chenna.lloydsamplepoject.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.chenna.lloydsamplepoject.models.CastActionEvent
import com.chenna.lloydsamplepoject.models.CastStateModel
import com.chenna.lloydsamplepoject.models.UiState
import com.chenna.lloydsamplepoject.util.NavigationEvent
import com.chenna.lloydsamplepoject.util.ProgressBarCompose
import com.chenna.lloydsamplepoject.util.UiUtils
import com.chenna.lloydsamplepoject.viewmodels.CastViewModel
import kotlinx.coroutines.flow.collectLatest

/**
 * Created by Chenna Rao on 25/12/24.
 * <p>
 * Frost Interactive
 */

@Composable
fun CastScreen(
    viewModel: CastViewModel = hiltViewModel(),
    navigate: (NavigationEvent) -> Unit,
) {

    val uiState = remember { mutableStateOf(UiState<CastStateModel>()) }
    val context = LocalContext.current


    LaunchedEffect(Unit) {
        viewModel.messageEvent.collectLatest {
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

    CastsListContent(uiState.value)

}

@Composable
fun CastsListContent(
    uiState: UiState<CastStateModel>,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {

        if (uiState.isLoading) {
            ProgressBarCompose(color = colorResource(id = R.color.purple_700))
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

                    CastItem(cast)

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