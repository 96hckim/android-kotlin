package com.hocheol.data

import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class UserDataStoreTest {

    @Test
    fun 토큰_저장_테스트() = runTest {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val userDataStore = UserDataStore(context)

        val expectedToken = "abc123"
        userDataStore.setToken(expectedToken)
        var actualToken = userDataStore.getToken()
        Assert.assertEquals(expectedToken, actualToken)

        userDataStore.clear()
        actualToken = userDataStore.getToken()
        Assert.assertNotEquals(expectedToken, actualToken)
        Assert.assertNull(null, actualToken)
    }
}