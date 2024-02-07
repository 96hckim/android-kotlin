package com.hocheol.starbucks

import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonParseException
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

fun <T> Context.readData(fileName: String, classT: Class<T>): T? {
    return try {
        this.resources.assets.open(fileName).use { inputStream ->
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            Gson().fromJson(bufferedReader, classT)
        }
    } catch (e: IOException) {
        e.printStackTrace()
        null
    } catch (e: JsonParseException) {
        e.printStackTrace()
        null
    }
}