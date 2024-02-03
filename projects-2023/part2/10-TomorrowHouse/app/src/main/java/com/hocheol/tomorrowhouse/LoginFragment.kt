package com.hocheol.tomorrowhouse

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hocheol.tomorrowhouse.databinding.FragmentLoginBinding

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        setupSignUpButton()
        setupSignInOutButton()
    }

    override fun onStart() {
        super.onStart()

        if (Firebase.auth.currentUser == null) {
            initSignOutState()
        } else {
            initSignInState()
        }
    }

    private fun setupSignUpButton() {
        binding.signUpButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Snackbar.make(binding.root, "이메일 또는 패스워드를 입력해주세요.", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            signUp(email, password)
        }
    }

    private fun setupSignInOutButton() {
        binding.signInOutButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (Firebase.auth.currentUser == null) {
                if (email.isEmpty() || password.isEmpty()) {
                    Snackbar.make(binding.root, "이메일 또는 패스워드를 입력해주세요.", Snackbar.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                signIn(email, password)
            } else {
                signOut()
            }
        }
    }

    private fun signUp(email: String, password: String) {
        Firebase.auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    showSnackbar("회원가입에 성공했습니다.")
                    initSignInState()
                } else {
                    showSnackbar("회원가입에 실패했습니다.")
                }
            }
    }

    private fun signIn(email: String, password: String) {
        Firebase.auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    initSignInState()
                } else {
                    showSnackbar("로그인에 실패했습니다. 이메일 또는 패스워드를 확인해주세요.")
                }
            }
    }

    private fun signOut() {
        Firebase.auth.signOut()
        initSignOutState()
    }

    private fun initSignInState() {
        with(binding) {
            emailEditText.setText(Firebase.auth.currentUser?.email)
            emailEditText.isEnabled = false
            passwordEditText.isVisible = false
            signInOutButton.text = getString(R.string.sign_out)
            signUpButton.isEnabled = false
        }
    }

    private fun initSignOutState() {
        with(binding) {
            emailEditText.text.clear()
            emailEditText.isEnabled = true
            passwordEditText.text.clear()
            passwordEditText.isVisible = true
            signInOutButton.text = getString(R.string.sign_in)
            signUpButton.isEnabled = true
        }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }
}