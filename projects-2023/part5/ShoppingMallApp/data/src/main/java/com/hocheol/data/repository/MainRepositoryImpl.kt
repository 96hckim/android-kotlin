package com.hocheol.data.repository

import android.content.Context
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.hocheol.domain.model.Product
import com.hocheol.domain.repository.MainRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : MainRepository {

    override fun getProductList(): List<Product> {
        val inputStream = context.assets.open("product_list.json")
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        val type = object : TypeToken<List<Product>>() {}.type

        return GsonBuilder().create().fromJson(jsonString, type)
    }
}