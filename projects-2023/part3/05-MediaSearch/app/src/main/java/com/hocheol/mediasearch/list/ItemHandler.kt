package com.hocheol.mediasearch.list

import com.hocheol.mediasearch.model.ListItem

interface ItemHandler {
    fun onClickFavorite(item: ListItem)
}