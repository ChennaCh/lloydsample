package com.chenna.domain.models

/**
 * Created by Chenna Rao on 17/12/24.
 * <p>
 * Frost Interactive
 */
data class Message(
    val messageType: MessageType = MessageType.TOAST,
    val messageStatus: MessageStatus = MessageStatus.NORMAL,
    val message: String,
    val fieldId: Any? = null,
)

enum class MessageStatus {
    NORMAL
}

enum class MessageType {
    TOAST,
}