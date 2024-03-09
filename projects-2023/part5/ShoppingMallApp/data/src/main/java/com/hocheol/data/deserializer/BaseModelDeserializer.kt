package com.hocheol.data.deserializer

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.hocheol.domain.model.Banner
import com.hocheol.domain.model.BannerList
import com.hocheol.domain.model.BaseModel
import com.hocheol.domain.model.Carousel
import com.hocheol.domain.model.ModelType
import com.hocheol.domain.model.Product
import com.hocheol.domain.model.Ranking
import java.lang.reflect.Type

class BaseModelDeserializer : JsonDeserializer<BaseModel> {

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): BaseModel {
        val root = json?.asJsonObject
        val gson = GsonBuilder().create()

        val typeString = root?.get(TYPE)?.asString ?: ""

        return when (ModelType.valueOf(typeString)) {
            ModelType.PRODUCT -> gson.fromJson(root, Product::class.java)
            ModelType.BANNER -> gson.fromJson(root, Banner::class.java)
            ModelType.BANNER_LIST -> gson.fromJson(root, BannerList::class.java)
            ModelType.CAROUSEL -> gson.fromJson(root, Carousel::class.java)
            ModelType.RANKING -> gson.fromJson(root, Ranking::class.java)
        }
    }

    companion object {
        private const val TYPE = "type"
    }
}