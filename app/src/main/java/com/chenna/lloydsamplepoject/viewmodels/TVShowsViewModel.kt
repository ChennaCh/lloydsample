package com.chenna.lloydsamplepoject.viewmodels

import androidx.lifecycle.viewModelScope
import com.chenna.domain.usecase.ShowsUseCase
import com.chenna.domain.utils.Error
import com.chenna.domain.utils.Work
import com.chenna.lloydsamplepoject.models.ResultActionStateModel
import com.chenna.lloydsamplepoject.models.TVShowActionEvent
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
class TVShowsViewModel @Inject constructor(
    private val useCase: ShowsUseCase,
) : BaseViewModel() {

    private val _resultState =
        MutableStateFlow(UiState<ResultActionStateModel>(isLoading = true))
    val resultState: StateFlow<UiState<ResultActionStateModel>> = _resultState

    init {
        fetchTvShows()
    }

    fun onActionEvent(actionEvent: TVShowActionEvent) {
        when (actionEvent) {
            is TVShowActionEvent.RedirectToShowDetails -> {
                viewModelScope.launch {
                    _navigationEvent.emit(
                        NavigationEvent(
                            route = Constants.AppRoute.SHOW_DETAILS,
                            any = actionEvent.model
                        )
                    )
                }
            }
        }
    }

    fun fetchTvShows() {
        _resultState.value = _resultState.value.copy(isLoading = true)
        viewModelScope.launch {
            when (val work = useCase.getListOfShows()) {
                is Work.Result -> {

                    if (work.data.isNotEmpty()) {
                        val list = ResultActionStateModel(
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
                            title = Constants.Errors.ERROR,
                            description = work.message.message
                        )
                    )
                    pushMessage(work.message)
                }

                else -> {
                    _resultState.value = resultState.value.copy(
                        isLoading = false,
                        error = Error(
                            title = Constants.Errors.CONNECTION_ERROR,
                            description = Constants.Errors.TV_SHOWS
                        )
                    )
                }
            }
        }

    }
}