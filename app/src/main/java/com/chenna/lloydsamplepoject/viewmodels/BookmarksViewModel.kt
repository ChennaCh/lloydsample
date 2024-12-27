package com.chenna.lloydsamplepoject.viewmodels

import androidx.lifecycle.viewModelScope
import com.chenna.domain.models.toShowModel
import com.chenna.domain.usecase.ShowsUseCase
import com.chenna.lloydsamplepoject.models.BookmarksActionEvent
import com.chenna.lloydsamplepoject.models.BookmarksStateModel
import com.chenna.lloydsamplepoject.models.UiState
import com.chenna.lloydsamplepoject.config.Constants
import com.chenna.lloydsamplepoject.util.NavigationEvent
import com.chenna.lloydsamplepoject.viewmodels.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Chenna Rao on 17/12/24.
 * <p>
 * Frost Interactive
 */
@HiltViewModel
class BookmarksViewModel @Inject constructor(
    private val useCase: ShowsUseCase,
) : BaseViewModel() {

    private val _resultState = MutableStateFlow(UiState<BookmarksStateModel>(isLoading = true))
    val resultState: StateFlow<UiState<BookmarksStateModel>> = _resultState

    fun onActionEvent(actionEvent: BookmarksActionEvent) {
        when (actionEvent) {
            is BookmarksActionEvent.RedirectToShowDetails -> redirectToShowDetails(actionEvent)
            BookmarksActionEvent.GetBookmarks -> getBookMarks()
        }
    }

    private fun redirectToShowDetails(actionEvent: BookmarksActionEvent.RedirectToShowDetails) {
        viewModelScope.launch {
            _navigationEvent.emit(
                NavigationEvent(
                    route = Constants.AppRoute.SHOW_DETAILS,
                    any = actionEvent.entity.toShowModel()
                )
            )
        }
    }

    private fun getBookMarks() {
        viewModelScope.launch {
            val bookMarks = BookmarksStateModel(useCase.getAllBookmarks())
            _resultState.value = UiState(data = bookMarks)
        }
    }

}