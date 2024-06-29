package com.hocheol.hiltsns

import com.hocheol.data.UserDataStore
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class HiltExampleTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var userDataStore: UserDataStore

    @Test
    fun 주입_테스트() {
        hiltRule.inject()
        Assert.assertNotNull(userDataStore)
    }
}