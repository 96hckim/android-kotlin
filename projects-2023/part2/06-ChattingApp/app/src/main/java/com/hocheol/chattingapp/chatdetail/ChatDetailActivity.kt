package com.hocheol.chattingapp.chatdetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.hocheol.chattingapp.Key
import com.hocheol.chattingapp.databinding.ActivityChatDetailBinding
import com.hocheol.chattingapp.userlist.UserItem

class ChatDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatDetailBinding

    private var chatRoomId: String = ""
    private var otherUserId: String = ""
    private var myUserId: String = ""

    private val chatDetailItemList = mutableListOf<ChatDetailItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val chatDetailAdapter = ChatDetailAdapter()

        chatRoomId = intent.getStringExtra("chatRoomId") ?: return
        otherUserId = intent.getStringExtra("otherUserId") ?: return
        myUserId = Firebase.auth.currentUser?.uid ?: ""

        Firebase.database.reference.child(Key.DB_USERS).child(myUserId).get().addOnSuccessListener {
            val myUserItem = it.getValue(UserItem::class.java)
            val myUserName = myUserItem?.username
        }
        Firebase.database.reference.child(Key.DB_USERS).child(otherUserId).get().addOnSuccessListener {
            val otherUserItem = it.getValue(UserItem::class.java)
            chatDetailAdapter.otherUserItem = otherUserItem
        }

        Firebase.database.reference.child(Key.DB_CHATS).child(chatRoomId).addChildEventListener(
            object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    val chatDetailItem = snapshot.getValue(ChatDetailItem::class.java) ?: return

                    chatDetailItemList.add(chatDetailItem)
                    chatDetailAdapter.submitList(chatDetailItemList)
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) = Unit

                override fun onChildRemoved(snapshot: DataSnapshot) = Unit

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) = Unit

                override fun onCancelled(error: DatabaseError) {
                    error.toException().printStackTrace()
                }
            }
        )

        binding.chatRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = chatDetailAdapter
        }
    }
}