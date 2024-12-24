package com.chenna.lloydsamplepoject.viewmodels

import androidx.lifecycle.viewModelScope
import com.chenna.domain.model.toShowModel
import com.chenna.domain.usecase.ShowsUseCase
import com.chenna.lloydsamplepoject.models.BookmarksActionEvent
import com.chenna.lloydsamplepoject.models.ResultActionStateModel
import com.chenna.lloydsamplepoject.models.UiState
import com.chenna.lloydsamplepoject.util.Constants
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

    private val _resultState =
        MutableStateFlow(UiState<ResultActionStateModel>(isLoading = true))
    val resultState: StateFlow<UiState<ResultActionStateModel>> = _resultState

    init {
        getBookMarks()
    }

    fun onActionEvent(actionEvent: BookmarksActionEvent) {
        when (actionEvent) {

            is BookmarksActionEvent.RedirectToShowDetails -> {
                viewModelScope.launch {
                    _navigationEvent.emit(
                        NavigationEvent(
                            route = Constants.AppRoute.SHOW_DETAILS,
                            any = actionEvent.entity.toShowModel()
                        )
                    )
                }
            }

            BookmarksActionEvent.GetBookmarks -> getBookMarks()

            else -> {}
        }
    }

    fun getBookMarks() {
        viewModelScope.launch {
            val bookMark = ResultActionStateModel(
                bookMarks = useCase.getAllBookmarks()
            )

            _resultState.value = resultState.value.copy(
                isLoading = false,
                error = null,
                data = resultState.value.data?.copy(
                    bookMarks = useCase.getAllBookmarks()
                ) ?: bookMark,
            )
        }
    }

}