package com.chenna.lloydsamplepoject.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.chenna.domain.entities.ShowEntity
import com.chenna.lloydsamplepoject.R
import com.chenna.lloydsamplepoject.components.TvShowBookmarkItem
import com.chenna.lloydsamplepoject.config.Constants
import com.chenna.lloydsamplepoject.models.BookmarksActionEvent
import com.chenna.lloydsamplepoject.models.BookmarksStateModel
import com.chenna.lloydsamplepoject.models.UiState
import com.chenna.lloydsamplepoject.util.NavigationEvent
import com.chenna.lloydsamplepoject.util.NoResultsView
import com.chenna.lloydsamplepoject.viewmodels.BookmarksViewModel
import kotlinx.coroutines.flow.collectLatest

/**
 * Created by Chenna Rao on 17/12/24.
 * <p>
 * Frost Interactive
 */
@Composable
fun BookmarkScreen(
    viewModel: BookmarksViewModel = hiltViewModel(),
    navigate: (NavigationEvent) -> Unit,
) {

    val uiState = remember { mutableStateOf(UiState<BookmarksStateModel>()) }

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

    LaunchedEffect(Unit) {
        viewModel.onActionEvent(BookmarksActionEvent.GetBookmarks)
    }

    BookmarkTVShowListContent(
        uiState.value
    ) { show ->
        viewModel.onActionEvent(actionEvent = BookmarksActionEvent.RedirectToShowDetails(show))
    }
}

@Composable
fun BookmarkTVShowListContent(
    uiState: UiState<BookmarksStateModel>,
    onShowClick: (ShowEntity) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.data?.bookMarks?.isEmpty() == true) {
            NoResultsView(
                imageRes = R.drawable.no_bookmarks,
                contentDescription = "no_bookmarks",
                message = Constants.Errors.NO_BOOKMARKS,
                modifier = Modifier
            )
        } else
            uiState.data?.bookMarks?.let {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                ) {
                    items(it) { show ->
                        TvShowBookmarkItem(
                            show = show,
                            onClick = { onShowClick(show) }
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
    }
}

