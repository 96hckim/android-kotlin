package com.hocheol.chattingapp.chatroom

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.hocheol.chattingapp.Key
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

        val currentUserId = Firebase.auth.currentUser?.uid ?: return
        val chatRoomsDB = Firebase.database.reference.child(Key.DB_CHAT_ROOMS).child(currentUserId)

        chatRoomsDB.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val chatRoomList = snapshot.children.map {
                    it.getValue(ChatRoomItem::class.java)
                }
                chatRoomAdapter.submitList(chatRoomList)
            }

            override fun onCancelled(error: DatabaseError) {
                error.toException().printStackTrace()
            }
        })
    }
}