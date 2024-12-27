package com.chenna.lloydsamplepoject.viewmodels

import androidx.lifecycle.viewModelScope
import com.chenna.domain.models.Error
import com.chenna.domain.models.SearchShowModel
import com.chenna.domain.models.Work
import com.chenna.domain.usecase.ShowsUseCase
import com.chenna.lloydsamplepoject.config.Constants
import com.chenna.lloydsamplepoject.models.SearchActionEvent
import com.chenna.lloydsamplepoject.models.SearchStateModel
import com.chenna.lloydsamplepoject.models.SearchUiState
import com.chenna.lloydsamplepoject.util.NavigationEvent
import com.chenna.lloydsamplepoject.viewmodels.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Chenna Rao on 27/12/24.
 * <p>
 * Frost Interactive
 */
@HiltViewModel
class SearchViewModel @Inject constructor(private val useCase: ShowsUseCase) : BaseViewModel() {

    private val _resultState = MutableStateFlow(SearchUiState<SearchStateModel>())
    val resultState: StateFlow<SearchUiState<SearchStateModel>> = _resultState

    private var job: Job? = null

    // Check if the job is active
    fun isJobActive(): Boolean = job?.isActive == true

    // Handle different action events
    fun onActionEvent(actionEvent: SearchActionEvent) {
        when (actionEvent) {
            is SearchActionEvent.FetchSearchTvShow -> {
                updateQuery(actionEvent.text) // Update the query before fetching results
                getSearchList(actionEvent.text)
            }

            is SearchActionEvent.RedirectToShowDetails -> redirectToTVShowDetails(actionEvent)
        }
    }

    // Cancel the active job
    fun cancelJob() {
        job?.takeIf { it.isActive }?.apply {
            cancel()
            println("Job canceled successfully.")
        }
    }

    // Redirect to TV show details
    private fun redirectToTVShowDetails(actionEvent: SearchActionEvent.RedirectToShowDetails) {
        viewModelScope.launch {
            _navigationEvent.emit(
                NavigationEvent(
                    route = Constants.AppRoute.SHOW_DETAILS,
                    any = actionEvent.model
                )
            )
        }
    }

    // Fetch the search list
    private fun getSearchList(text: String) {
        if (text.isEmpty()) {
            _resultState.value = _resultState.value.copy(
                showSearchMessage = true,
                query = ""
            )
            return
        }

        _resultState.value = _resultState.value.copy(isLoading = true)

        job = viewModelScope.launch {
            _resultState.value = when (val work = useCase.getSearchList(text.trim())) {
                is Work.Result -> handleResult(work)
                is Work.Stop -> handleStop(work)
                else -> {
                    handleConnectionError()
                }
            }
        }
    }

    // Update the query
    private fun updateQuery(newQuery: String) {
        println("Updating query to: $newQuery")
        _resultState.value = _resultState.value.copy(
            query = newQuery
        )
    }

    // Handle the search result
    private fun handleResult(
        work: Work.Result<List<SearchShowModel>>,
    ): SearchUiState<SearchStateModel> {
        return _resultState.value.copy(
            showSearchMessage = false,
            data = SearchStateModel(work.data),
            isLoading = false
        )
    }

    // Handle when there are no results
    private fun handleStop(work: Work.Stop): SearchUiState<SearchStateModel> {
        pushMessage(work.message)
        return _resultState.value.copy(
            showSearchMessage = false,
            error = Error(
                title = work.message.message,
                description = work.message.message
            ),
            isLoading = false
        )
    }

    // Handle connection error
    private fun handleConnectionError(): SearchUiState<SearchStateModel> {
        return _resultState.value.copy(
            showSearchMessage = false,
            error = Error(
                title = Constants.Errors.CONNECTION_ERROR,
                description = Constants.Errors.NO_SHOWS_FOUND
            ),
            isLoading = false
        )
    }
}


