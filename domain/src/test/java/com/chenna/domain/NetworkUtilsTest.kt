package com.chenna.domain

import android.content.Context
import android.net.ConnectivityManager
import com.chenna.domain.utils.NetworkUtils
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test

/**
 * Created by Chenna Rao on 23/12/24.
 * <p>
 * Frost Interactive
 */
class NetworkUtilsTest {

    private lateinit var mockContext: Context
    private lateinit var connectivityManager: ConnectivityManager

    @Before
    fun setUp() {
        mockContext = mockk(relaxed = true)
        connectivityManager = mockk(relaxed = true)
    }

    @Test
    fun isInternetAvailable() {

        every { mockContext.getSystemService(Context.CONNECTIVITY_SERVICE) } returns connectivityManager
        every { connectivityManager.activeNetwork } returns null
        every { connectivityManager.getNetworkCapabilities(any()) } returns null

        val result = NetworkUtils.isInternetAvailable(mockContext)
        assertFalse(result)

    }
}