package com.chenna.lloydsamplepoject

import androidx.lifecycle.viewModelScope
import com.chenna.domain.utils.Constants
import com.chenna.domain.utils.Message
import com.chenna.domain.utils.MessageStatus
import com.chenna.domain.utils.MessageType
import com.chenna.domain.utils.NavigationEvent
import com.chenna.lloydsamplepoject.viewmodels.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Created by Chenna Rao on 22/12/24.
 * <p>
 * Frost Interactive
 */
@OptIn(ExperimentalCoroutinesApi::class)
class BaseViewModelTest {

    private lateinit var testViewModel: TestBaseViewModel
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        testViewModel = TestBaseViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `pushMessage emits correct Message object`() = testScope.runTest {
        val message = Message(messageType = MessageType.TOAST, message = "Test Message")

        // Act
        testViewModel.pushMessage(message)

        // Assert
        val emittedMessage = testViewModel.messageEvent.first()
        assertEquals(message, emittedMessage)
    }

    @Test
    fun `pushMessage with messageType and message emits correct Message`() = testScope.runTest {
        val messageText = "Test Toast Message"
        val messageType = MessageType.TOAST

        // Act
        testViewModel.pushMessage(messageType, messageText)

        // Assert
        val emittedMessage = testViewModel.messageEvent.first()
        assertEquals(messageType, emittedMessage.messageType)
        assertEquals(messageText, emittedMessage.message)
        assertEquals(MessageStatus.NORMAL, emittedMessage.messageStatus)
    }

    @Test
    fun `navigationEvent emits correct NavigationEvent`() = testScope.runTest {
        val navigationEvent = NavigationEvent(Constants.AppRoute.SHOW_DETAILS, any = null)

        // Act
        testViewModel.emitNavigationEvent(navigationEvent)

        // Assert
        val emittedEvent = testViewModel.navigationEvent.first()
        assertEquals(navigationEvent, emittedEvent)
    }
}

// Subclass for testing BaseViewModel
class TestBaseViewModel : BaseViewModel() {
    fun emitNavigationEvent(event: NavigationEvent) {
        viewModelScope.launch {
            _navigationEvent.emit(event)
        }
    }
}