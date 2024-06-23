package com.hocheol.domain.model

@kotlinx.serialization.Serializable
data class Image(
    val uri: String,
    val name: String,
    val size: Long,
    val mimeType: String
) : java.io.Serializable