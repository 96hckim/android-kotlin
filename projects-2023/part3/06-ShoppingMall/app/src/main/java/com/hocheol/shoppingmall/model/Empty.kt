package com.hocheol.shoppingmall.model

class Empty : ListItem {
    override val viewType: ViewType
        get() = ViewType.EMPTY
}