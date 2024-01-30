package com.hocheol.chattingapp.chatdetail

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hocheol.chattingapp.databinding.ItemChatDetailBinding
import com.hocheol.chattingapp.userlist.UserItem

class ChatDetailAdapter : ListAdapter<ChatDetailItem, ChatDetailAdapter.ViewHolder>(diffCallback) {

    var otherUserItem: UserItem? = null

    inner class ViewHolder(private val binding: ItemChatDetailBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ChatDetailItem) {
            val isOtherUser = item.userId == otherUserItem?.userId

            binding.nicknameTextView.isVisible = isOtherUser
            if (isOtherUser) {
                binding.nicknameTextView.text = otherUserItem?.username
            }

            binding.messageTextView.text = item.message
            binding.messageTextView.gravity = if (isOtherUser) Gravity.START else Gravity.END
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemChatDetailBinding.inflate(
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
        val diffCallback = object : DiffUtil.ItemCallback<ChatDetailItem>() {
            override fun areItemsTheSame(oldItem: ChatDetailItem, newItem: ChatDetailItem): Boolean {
                return oldItem.chatId == newItem.chatId
            }

            override fun areContentsTheSame(oldItem: ChatDetailItem, newItem: ChatDetailItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}