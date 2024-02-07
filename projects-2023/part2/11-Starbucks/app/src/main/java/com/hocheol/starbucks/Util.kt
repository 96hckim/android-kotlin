package com.hocheol.starbucks

import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonParseException
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

fun Context.readData(): Home? {
    return try {
        this.resources.assets.open("home.json").use { inputStream ->
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            Gson().fromJson(bufferedReader, Home::class.java)
        }
    } catch (e: IOException) {
        e.printStackTrace()
        null
    } catch (e: JsonParseException) {
        e.printStackTrace()
        null
    }
}