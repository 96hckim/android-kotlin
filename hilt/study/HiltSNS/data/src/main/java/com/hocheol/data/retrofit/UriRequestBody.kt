package com.hocheol.data.retrofit

import com.hocheol.domain.usecase.file.GetInputStreamUseCase
import okhttp3.MediaType
import okhttp3.RequestBody
import okio.BufferedSink
import okio.FileNotFoundException
import okio.source
import okio.use

class UriRequestBody(
    val contentUri: String,
    val contentType: MediaType? = null,
    val contentLength: Long,
    val getInputStreamUseCase: GetInputStreamUseCase
) : RequestBody() {

    override fun contentType(): MediaType? {
        return contentType
    }

    override fun contentLength(): Long {
        return contentLength
    }

    override fun writeTo(sink: BufferedSink) {
        try {
            getInputStreamUseCase(contentUri).getOrThrow()
                .use { inputStream ->
                    sink.writeAll(inputStream.source())
                }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
    }
}