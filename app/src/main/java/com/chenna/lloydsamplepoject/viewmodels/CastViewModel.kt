package com.chenna.lloydsamplepoject.viewmodels

import androidx.lifecycle.viewModelScope
import com.chenna.domain.model.CastModel
import com.chenna.domain.usecase.ShowsUseCase
import com.chenna.domain.utils.Error
import com.chenna.domain.utils.Work
import com.chenna.lloydsamplepoject.models.CastActionEvent
import com.chenna.lloydsamplepoject.models.CastStateModel
import com.chenna.lloydsamplepoject.models.UiState
import com.chenna.lloydsamplepoject.util.Constants
import com.chenna.lloydsamplepoject.viewmodels.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Chenna Rao on 25/12/24.
 * <p>
 * Frost Interactive
 */
@HiltViewModel
class CastViewModel @Inject constructor(private val useCase: ShowsUseCase) : BaseViewModel() {

    private val _resultState = MutableStateFlow(UiState<CastStateModel>(isLoading = true))
    val resultState: StateFlow<UiState<CastStateModel>> = _resultState

    fun onActionEvent(actionEvent: CastActionEvent) {
        when (actionEvent) {
            CastActionEvent.FetchCasts -> fetchCasts()
        }
    }

    private fun fetchCasts() {
        if (resultState.value.data?.list?.isNotEmpty() == true) {
            return
        }

        _resultState.value = _resultState.value.copy(isLoading = true)
        viewModelScope.launch {
            _resultState.value = when (val work = useCase.fetchCasts()) {
                is Work.Result -> handleResult(work)
                is Work.Stop -> handleStop(work)
                else -> handleConnectionError()
            }
        }
    }

    private fun handleResult(work: Work.Result<List<CastModel>>): UiState<CastStateModel> {
        return if (work.data.isNotEmpty()) {
            UiState(data = CastStateModel(work.data))
        } else {
            UiState(
                error = Error(
                    title = "No casts",
                    description = Constants.Errors.TV_SHOW_CASTS
                )
            )
        }
    }

    private fun handleStop(work: Work.Stop): UiState<CastStateModel> {
        pushMessage(work.message)
        return UiState(
            error = Error(
                title = Constants.Errors.ERROR,
                description = work.message.message
            )
        )
    }

    private fun handleConnectionError(): UiState<CastStateModel> = UiState(
        error = Error(
            title = Constants.Errors.CONNECTION_ERROR,
            description = Constants.Errors.TV_SHOWS
        )
    )

}

