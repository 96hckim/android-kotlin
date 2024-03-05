package com.hocheol.data.datasource

import android.content.Context
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.hocheol.data.deserializer.BaseModelDeserializer
import com.hocheol.domain.model.BaseModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun getProducts(): Flow<List<BaseModel>> = flow {
        val inputStream = context.assets.open("product_list.json")
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        val type = object : TypeToken<List<BaseModel>>() {}.type

        emit(
            GsonBuilder()
                .registerTypeAdapter(BaseModel::class.java, BaseModelDeserializer())
                .create()
                .fromJson(jsonString, type)
        )
    }
}