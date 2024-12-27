package com.chenna.lloydsamplepoject.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.chenna.lloydsamplepoject.R
import com.chenna.lloydsamplepoject.components.SearchShowItem
import com.chenna.lloydsamplepoject.components.StyledOutlinedTextField
import com.chenna.lloydsamplepoject.config.Constants
import com.chenna.lloydsamplepoject.models.SearchActionEvent
import com.chenna.lloydsamplepoject.models.SearchStateModel
import com.chenna.lloydsamplepoject.models.SearchUiState
import com.chenna.lloydsamplepoject.util.NavigationEvent
import com.chenna.lloydsamplepoject.util.NoInternetContent
import com.chenna.lloydsamplepoject.util.NoResultsView
import com.chenna.lloydsamplepoject.util.ProgressBarCompose
import com.chenna.lloydsamplepoject.util.UiUtils
import com.chenna.lloydsamplepoject.viewmodels.SearchViewModel
import kotlinx.coroutines.flow.collectLatest

/**
 * Created by Chenna Rao on 27/12/24.
 * <p>
 * Frost Interactive
 */
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    onBack: () -> Boolean,
    navigate: (NavigationEvent) -> Unit,
) {
    val uiState = remember { mutableStateOf(SearchUiState<SearchStateModel>()) }
    val context = LocalContext.current

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

    LaunchedEffect(viewModel) {
        viewModel.resultState.collectLatest {
            uiState.value = it
        }
    }

    SearchListContent(
        viewModel,
        uiState.value,
        onBack = onBack
    ) {
        viewModel.onActionEvent(it)
    }
}

@Composable
fun SearchListContent(
    viewModel: SearchViewModel,
    uiState: SearchUiState<SearchStateModel>,
    onBack: () -> Boolean,
    actionEvent: (SearchActionEvent) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    keyboardController?.hide() // Hides the keyboard
                })
            }
            .background(Color.White)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Search",
            modifier = Modifier.clickable {
                onBack()
            }
        )
        Spacer(modifier = Modifier.height(12.dp))
        StyledOutlinedTextField(
            value = uiState.query.toString(),
            onValueChange = {
                actionEvent(SearchActionEvent.FetchSearchTvShow(it))
            },
            onDoneAction = { enteredText ->
                println("Entered text: $enteredText")
                keyboardController?.hide()
            },
            onCloseAction = {
                viewModel.cancelJob()
            },
            placeholder = "Search a Tv Show",
            modifier = Modifier
                .fillMaxWidth()
        )

        if (uiState.showSearchMessage) {
            NoResultsView(
                imageRes = R.drawable.search_info,
                contentDescription = "search_info",
                message = Constants.SEARCH_INFORMATION,
                modifier = Modifier,
                enableBottomPadding = true
            )
        }

        if (uiState.isLoading) {
            ProgressBarCompose(color = colorResource(id = R.color.purple_700))
        }

        if (uiState.error?.title == Constants.CONNECTION_ERROR) {
            NoInternetContent(enableBottomPadding = true)
        }

        Spacer(modifier = Modifier.height(24.dp))
        if (uiState.data?.searchList?.isEmpty() == true) {
            NoResultsView(
                imageRes = R.drawable.no_search_shows,
                contentDescription = "no_shows",
                message = Constants.Errors.NO_SHOWS_FOUND,
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(100.dp))
        } else
            uiState.data?.searchList?.let {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                ) {
                    items(it) { tvShow ->
                        SearchShowItem(show = tvShow.show, onClick = {
                            actionEvent(
                                SearchActionEvent.RedirectToShowDetails(
                                    tvShow.show
                                )
                            )
                        })
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }

    }

}


