package com.hocheol.chattingapp.chatroom

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.hocheol.chattingapp.R
import com.hocheol.chattingapp.databinding.FragmentChatroomBinding

class ChatRoomFragment : Fragment(R.layout.fragment_chatroom) {

    private lateinit var binding: FragmentChatroomBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentChatroomBinding.bind(view)

        val chatRoomAdapter = ChatRoomAdapter()

        binding.chatRoomRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = chatRoomAdapter
        }

        chatRoomAdapter.submitList(
            mutableListOf(
                ChatRoomItem(
                    chatRoomId = "1",
                    otherUserName = "1",
                    lastMessage = "1.."
                ),
                ChatRoomItem(
                    chatRoomId = "2",
                    otherUserName = "2",
                    lastMessage = "2.."
                )
            )
        )
    }
}