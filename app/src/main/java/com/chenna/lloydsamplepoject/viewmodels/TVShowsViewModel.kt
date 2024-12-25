package com.chenna.lloydsamplepoject.viewmodels

import androidx.lifecycle.viewModelScope
import com.chenna.domain.model.ShowModel
import com.chenna.domain.usecase.ShowsUseCase
import com.chenna.domain.utils.Error
import com.chenna.domain.utils.Work
import com.chenna.lloydsamplepoject.models.TvShowActionEvent
import com.chenna.lloydsamplepoject.models.TvShowStateModel
import com.chenna.lloydsamplepoject.models.UiState
import com.chenna.lloydsamplepoject.util.Constants
import com.chenna.lloydsamplepoject.util.NavigationEvent
import com.chenna.lloydsamplepoject.viewmodels.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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
class TVShowsViewModel @Inject constructor(private val useCase: ShowsUseCase) : BaseViewModel() {

    private val _resultState = MutableStateFlow(UiState<TvShowStateModel>(isLoading = true))
    val resultState: StateFlow<UiState<TvShowStateModel>> = _resultState

    val isRefreshing = MutableStateFlow(false)

    fun onActionEvent(actionEvent: TvShowActionEvent) {
        when (actionEvent) {
            is TvShowActionEvent.FetchTvShows -> fetchTVShows()
            is TvShowActionEvent.RedirectToShowDetails -> redirectToTVShowDetails(actionEvent)
            is TvShowActionEvent.Retry -> {
                fetchTVShows()
                isRefreshing.value = true
            }
        }
    }

    private fun redirectToTVShowDetails(actionEvent: TvShowActionEvent.RedirectToShowDetails) {
        viewModelScope.launch {
            _navigationEvent.emit(
                NavigationEvent(
                    route = Constants.AppRoute.SHOW_DETAILS,
                    any = actionEvent.model
                )
            )
        }
    }

    private fun fetchTVShows() {
        if (resultState.value.data?.list?.isNotEmpty() == true) {
            viewModelScope.launch {
                delay(100)
                isRefreshing.value = false
            }
            return
        }

        _resultState.value = _resultState.value.copy(isLoading = true)
        viewModelScope.launch {
            _resultState.value = when (val work = useCase.getListOfShows()) {
                is Work.Result -> handleResult(work)
                is Work.Stop -> handleStop(work)
                else -> {
                    handleConnectionError()
                }
            }
            isRefreshing.value = false
        }
    }

    private fun handleResult(work: Work.Result<List<ShowModel>>): UiState<TvShowStateModel> {
        return if (work.data.isNotEmpty()) {
            UiState(data = TvShowStateModel(work.data))
        } else {
            UiState(
                error = Error(
                    title = "No shows",
                    description = Constants.Errors.TV_SHOWS
                )
            )
        }
    }

    private fun handleStop(work: Work.Stop): UiState<TvShowStateModel> {
        pushMessage(work.message)
        return UiState(
            error = Error(
                title = work.message.message,
                description = work.message.message
            )
        )
    }

    private fun handleConnectionError(): UiState<TvShowStateModel> {
        return UiState(
            error = Error(
                title = Constants.Errors.CONNECTION_ERROR,
                description = Constants.Errors.TV_SHOWS
            )
        )
    }
}