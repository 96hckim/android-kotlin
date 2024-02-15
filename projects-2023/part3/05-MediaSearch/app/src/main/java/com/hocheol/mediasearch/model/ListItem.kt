package com.hocheol.mediasearch.model

import java.util.Date

interface ListItem {
    val thumbnailUrl: String
    val dateTime: Date
    var isFavorite: Boolean
}