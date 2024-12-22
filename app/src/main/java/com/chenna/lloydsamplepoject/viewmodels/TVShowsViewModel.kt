package com.chenna.lloydsamplepoject.viewmodels

import androidx.lifecycle.viewModelScope
import com.chenna.domain.usecase.ShowsUseCase
import com.chenna.domain.utils.Constants
import com.chenna.domain.utils.Error
import com.chenna.domain.utils.NavigationEvent
import com.chenna.domain.utils.Work
import com.chenna.lloydsamplepoject.components.ResultActionEvent
import com.chenna.lloydsamplepoject.components.UiState
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
class TVShowsViewModel @Inject constructor(
    private val useCase: ShowsUseCase,
) : BaseViewModel() {

    private val _resultState =
        MutableStateFlow(UiState<ResultActionEvent.ResultActionState>(isLoading = true))
    val resultState: StateFlow<UiState<ResultActionEvent.ResultActionState>> = _resultState

    private val _isBookmarked = MutableStateFlow(false)
    val isBookmarked: StateFlow<Boolean> = _isBookmarked

    fun onActionEvent(actionEvent: ResultActionEvent) {
        when (actionEvent) {

            is ResultActionEvent.RedirectToShowDetails -> {

                viewModelScope.launch {
                    _navigationEvent.emit(
                        NavigationEvent(
                            route = Constants.AppRoute.SHOW_DETAILS,
                            any = actionEvent.entity
                        )
                    )
                }
            }

            ResultActionEvent.GetBookmarks -> getBookMarks()
            is ResultActionEvent.RemoveBookMark -> viewModelScope.launch {
                useCase.saveBookmark(
                    actionEvent.entity
                )
            }

            is ResultActionEvent.SaveBookMark -> viewModelScope.launch {
                useCase.removeBookmark(
                    actionEvent.entity
                )
            }

            is ResultActionEvent.IsShowBookmarked ->
                viewModelScope.launch {
                    _isBookmarked.value = useCase.isShowBookmarked(actionEvent.showId)
                }

            ResultActionEvent.FetchTVShows -> fetchTvShows()
        }
    }

    private fun getBookMarks() {

        viewModelScope.launch {
            val bookMark = ResultActionEvent.ResultActionState(
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

    private fun fetchTvShows() {
        _resultState.value = _resultState.value.copy(isLoading = true)
        viewModelScope.launch {
            when (val work = useCase.getListOfShows()) {
                is Work.Result -> {

                    if (work.data.isNotEmpty()) {
                        val list = ResultActionEvent.ResultActionState(
                            list = work.data
                        )

                        _resultState.value = resultState.value.copy(
                            isLoading = false,
                            error = null,
                            data = resultState.value.data?.copy(
                                list = work.data
                            ) ?: list,
                        )
                    } else {
                        _resultState.value = resultState.value.copy(
                            isLoading = false,
                            error = Error(
                                title = "No shows",
                                description = Constants.Errors.TV_SHOWS
                            )
                        )
                    }
                }

                is Work.Stop -> {
                    _resultState.value = resultState.value.copy(
                        isLoading = false,
                        error = Error(
                            title = "Error",
                            description = work.message.message
                        )
                    )
                    pushMessage(work.message)
                }

                else -> {
                    _resultState.value = resultState.value.copy(
                        isLoading = false,
                        error = Error(
                            title = "Connection Error",
                            description = Constants.Errors.TV_SHOWS
                        )
                    )
                }
            }
        }

    }
}