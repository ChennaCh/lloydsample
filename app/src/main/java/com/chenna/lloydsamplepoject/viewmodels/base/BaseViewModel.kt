package com.chenna.lloydsamplepoject.viewmodels.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chenna.domain.models.Message
import com.chenna.domain.models.MessageType
import com.chenna.lloydsamplepoject.util.NavigationEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

/**
 * Created by Chenna Rao on 17/12/24.
 * <p>
 * Frost Interactive
 */
abstract class BaseViewModel : ViewModel() {
    private val _messageEvent: MutableSharedFlow<Message> = MutableSharedFlow<Message>()
    val messageEvent: SharedFlow<Message> = _messageEvent

    protected val _navigationEvent = MutableSharedFlow<NavigationEvent>()
    val navigationEvent: SharedFlow<NavigationEvent> = _navigationEvent

    fun pushMessage(message: Message) {
        viewModelScope.launch {
            _messageEvent.emit(message)
        }
    }

    fun pushMessage(messageType: MessageType = MessageType.TOAST, message: String) {
        viewModelScope.launch {
            _messageEvent.emit(Message(message = message, messageType = messageType))
        }
    }
}