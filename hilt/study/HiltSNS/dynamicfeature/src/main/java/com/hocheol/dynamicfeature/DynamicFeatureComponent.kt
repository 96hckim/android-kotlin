package com.hocheol.dynamicfeature

import com.hocheol.hiltsns.FooEntryPoint
import dagger.Component

@Component(dependencies = [FooEntryPoint::class])
interface DynamicFeatureComponent {

    fun inject(target: DynamicFeatureActivity)
}