package com.hocheol.hiltsns

import androidx.test.core.app.ApplicationProvider
import com.hocheol.data.UserDataStore
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import javax.inject.Inject

@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
@Config(application = HiltTestApplication::class)
class RobolectricWithHiltTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var userDataStore: UserDataStore

    @Test
    fun 주입_테스트() {
        hiltRule.inject()
        Assert.assertNotNull(userDataStore)
    }

    @Test
    fun 애플리케이션_테스트() {
        val application = ApplicationProvider.getApplicationContext<HiltTestApplication>()
        Assert.assertNotNull(application)
    }
}