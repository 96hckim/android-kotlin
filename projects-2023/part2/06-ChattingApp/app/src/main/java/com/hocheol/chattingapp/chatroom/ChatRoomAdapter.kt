package com.hocheol.chattingapp.chatroom

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hocheol.chattingapp.databinding.ItemChatroomBinding

class ChatRoomAdapter(private val onClick: (ChatRoomItem) -> Unit) : ListAdapter<ChatRoomItem, ChatRoomAdapter.ViewHolder>(diffCallback) {

    inner class ViewHolder(private val binding: ItemChatroomBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ChatRoomItem) {
            binding.root.setOnClickListener {
                onClick(item)
            }

            binding.nicknameTextView.text = item.otherUserName
            binding.lastMessageTextView.text = item.lastMessage
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemChatroomBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<ChatRoomItem>() {
            override fun areItemsTheSame(oldItem: ChatRoomItem, newItem: ChatRoomItem): Boolean {
                return oldItem.chatRoomId == newItem.chatRoomId
            }

            override fun areContentsTheSame(oldItem: ChatRoomItem, newItem: ChatRoomItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}