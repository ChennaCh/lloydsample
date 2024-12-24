package com.chenna.lloydsamplepoject.viewmodels

import androidx.lifecycle.viewModelScope
import com.chenna.domain.usecase.ShowsUseCase
import com.chenna.lloydsamplepoject.models.TVShowDetailsActionEvent
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
class TvShowDetailsViewModel @Inject constructor(
    private val useCase: ShowsUseCase,
) : BaseViewModel() {

    private val _isBookmarked = MutableStateFlow(false)
    val isBookmarked: StateFlow<Boolean> = _isBookmarked

    fun onActionEvent(actionEvent: TVShowDetailsActionEvent) {
        when (actionEvent) {

            is TVShowDetailsActionEvent.SaveBookMark -> viewModelScope.launch {
                useCase.saveBookmark(
                    actionEvent.entity
                )
            }

            is TVShowDetailsActionEvent.RemoveBookMark -> viewModelScope.launch {
                useCase.removeBookmark(
                    actionEvent.id
                )
            }

            is TVShowDetailsActionEvent.IsShowBookmarked ->
                viewModelScope.launch {
                    _isBookmarked.value = useCase.isShowBookmarked(actionEvent.showId)
                }

            else -> {}
        }
    }
}