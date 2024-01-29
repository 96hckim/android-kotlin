package com.hocheol.chattingapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hocheol.chattingapp.chatroom.ChatRoomFragment
import com.hocheol.chattingapp.databinding.ActivityMainBinding
import com.hocheol.chattingapp.mypage.MyPageFragment
import com.hocheol.chattingapp.userlist.UserFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val userFragment = UserFragment()
    private val chatRoomFragment = ChatRoomFragment()
    private val myPageFragment = MyPageFragment()

    override fun onStart() {
        super.onStart()
        checkLogin()
    }

    private fun checkLogin() {
        val currentUser = Firebase.auth.currentUser
        if (currentUser == null) {
            // 로그인 필요
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.setOnItemSelectedListener {
            val fragment: Fragment? = when (it.itemId) {
                R.id.userList -> {
                    userFragment
                }

                R.id.chatRoomList -> {
                    chatRoomFragment
                }

                R.id.myPage -> {
                    myPageFragment
                }

                else -> null
            }

            if (fragment != null) {
                replaceFragment(fragment)
                true
            } else {
                false
            }
        }

        binding.bottomNavigationView.selectedItemId = R.id.userList
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .apply {
                replace(R.id.frameLayout, fragment)
                commit()
            }
    }
}