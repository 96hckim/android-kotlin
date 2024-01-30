package com.hocheol.chattingapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.hocheol.chattingapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        // 회원가입
        binding.signUpButton.setOnClickListener {
            signUp()
        }

        // 로그인
        binding.signInButton.setOnClickListener {
            signIn()
        }
    }

    private fun signUp() {
        val (email, password) = getAccount()
        if (!isValidAccount(email, password)) return

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")
                    showToast("회원가입에 성공했습니다. 로그인해주세요.")
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    showToast("회원가입에 실패했습니다.")
                }
            }
    }

    private fun signIn() {
        val (email, password) = getAccount()
        if (!isValidAccount(email, password)) return

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                val currentUser = auth.currentUser
                if (task.isSuccessful && currentUser != null) {
                    Log.d(TAG, "signInWithEmail:success")

                    val userId = currentUser.uid

                    Firebase.messaging.token.addOnCompleteListener {
                        val token = it.result
                        val user = mutableMapOf<String, Any>(
                            "userId" to userId,
                            "username" to email,
                            "fcmToken" to token
                        )

                        Firebase.database(Key.DB_URL).reference.child(Key.DB_USERS).child(userId).updateChildren(user)

                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    showToast("로그인에 실패했습니다.")
                }
            }
    }

    private fun getAccount(): Pair<String, String> {
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        return Pair(email, password)
    }

    private fun isValidAccount(email: String, password: String): Boolean {
        if (email.isEmpty() || password.isEmpty()) {
            showToast("이메일 또는 패스워드가 입력되지 않았습니다.")
            return false
        }
        return true
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val TAG = "LoginActivity"
    }
}