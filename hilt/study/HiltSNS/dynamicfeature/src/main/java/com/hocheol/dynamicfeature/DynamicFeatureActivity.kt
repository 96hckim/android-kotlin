package com.hocheol.dynamicfeature

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.hocheol.data.UserDataStore
import com.hocheol.hiltsns.FooEntryPoint
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class DynamicFeatureActivity : ComponentActivity() {

    @Inject
    lateinit var userDataStore: UserDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fooEntryPoint = EntryPointAccessors.fromApplication(applicationContext, FooEntryPoint::class.java)
        val foo = fooEntryPoint.foo()

        val component = DaggerDynamicFeatureComponent.builder()
            .fooEntryPoint(fooEntryPoint)
            .build()

        component.inject(this)

        Log.d(TAG, "foo: $foo")
        Log.d(TAG, "userDataStore is initialized: ${::userDataStore.isInitialized}")
    }

    companion object {
        private const val TAG = "DynamicFeatureActivity"
    }
}