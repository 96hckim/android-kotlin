package com.hocheol.data.model

import android.os.Parcelable
import com.hocheol.domain.model.Image
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class BoardParcel(
    val title: String,
    val content: String,
    val images: List<Image>
) : Parcelable