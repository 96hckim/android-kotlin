package com.hocheol.chattingapp.mypage

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.hocheol.chattingapp.Key
import com.hocheol.chattingapp.LoginActivity
import com.hocheol.chattingapp.R
import com.hocheol.chattingapp.databinding.FragmentMypageBinding
import com.hocheol.chattingapp.userlist.UserItem

class MyPageFragment : Fragment(R.layout.fragment_mypage) {

    private lateinit var binding: FragmentMypageBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMypageBinding.bind(view)

        val currentUserId = Firebase.auth.currentUser?.uid ?: ""
        val currentUserDB = Firebase.database.reference.child(Key.DB_USERS).child(currentUserId)

        currentUserDB.get().addOnSuccessListener { snapshot ->
            val currentUserItem = snapshot.getValue(UserItem::class.java) ?: return@addOnSuccessListener

            binding.nicknameEditView.setText(currentUserItem.username)
            binding.descriptionEditText.setText(currentUserItem.description)
        }

        binding.applyButton.setOnClickListener {
            val nickname = binding.nicknameEditView.text.toString()
            val description = binding.descriptionEditText.text.toString()

            if (nickname.isEmpty()) {
                Toast.makeText(context, "닉네임은 빈 값으로 두실 수 없습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = mutableMapOf<String, Any>(
                "username" to nickname,
                "description" to description
            )

            currentUserDB.updateChildren(user)
        }

        binding.signOutButton.setOnClickListener {
            Firebase.auth.signOut()
            startActivity(Intent(context, LoginActivity::class.java))
            activity?.finish()
        }
    }
}