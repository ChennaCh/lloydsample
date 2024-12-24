package com.chenna.lloydsamplepoject.util

import android.content.Context
import android.widget.Toast
import com.chenna.domain.utils.Message
import com.chenna.domain.utils.MessageType

/**
 * Created by Chenna Rao on 17/12/24.
 * <p>
 * Frost Interactive
 */
object UiUtils {

    fun buildUIMessage(context: Context, it: Message) {
        when (it.messageType) {
            MessageType.TOAST -> {
                buildToast(context.applicationContext, it.message)
            }

            else -> {

            }
        }
    }

    fun buildToast(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, message, duration).show()
    }


}