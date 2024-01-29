package com.hocheol.chattingapp.chatroom

data class ChatRoomItem(
    val chatRoomId: String,
    val otherUserName: String,
    val lastMessage: String
)