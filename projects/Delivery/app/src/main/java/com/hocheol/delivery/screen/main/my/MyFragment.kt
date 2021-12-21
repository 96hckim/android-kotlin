package com.hocheol.delivery.screen.main.my

import android.app.Activity
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.hocheol.delivery.R
import com.hocheol.delivery.databinding.FragmentMyBinding
import com.hocheol.delivery.extensions.load
import com.hocheol.delivery.screen.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyFragment : BaseFragment<MyViewModel, FragmentMyBinding>() {

    override val viewModel by viewModel<MyViewModel>()

    override fun getViewBinding(): FragmentMyBinding = FragmentMyBinding.inflate(layoutInflater)

    private val gso: GoogleSignInOptions by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }

    private val gsc by lazy { GoogleSignIn.getClient(requireContext(), gso) }

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

    private val loginLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                task.getResult(ApiException::class.java)?.let { account ->
                    viewModel.saveToken(account.idToken ?: throw Exception())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun initViews() = with(binding) {
        loginButton.setOnClickListener {
            signInGoogle()
        }

        logoutButton.setOnClickListener {
            firebaseAuth.signOut()
            viewModel.signOut()
        }
    }

    private fun signInGoogle() {
        val signInIntent = gsc.signInIntent
        loginLauncher.launch(signInIntent)
    }

    override fun observeData() = viewModel.myStateLiveData.observe(viewLifecycleOwner) {
        when (it) {
            is MyState.Loading -> handleLoading()
            is MyState.Success -> handleSuccess(it)
            is MyState.Login -> handleLogin(it)
            is MyState.Error -> handleError(it)
            else -> Unit
        }
    }

    private fun handleLoading() = with(binding) {
        loginRequiredGroup.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    private fun handleSuccess(state: MyState.Success) = with(binding) {
        progressBar.visibility = View.GONE
        when (state) {
            is MyState.Success.Registered -> {
                handleRegistered(state)
            }
            is MyState.Success.NotRegistered -> {
                profileGroup.visibility = View.GONE
                loginRequiredGroup.visibility = View.VISIBLE
            }
        }
    }

    private fun handleRegistered(state: MyState.Success.Registered) = with(binding) {
        profileGroup.visibility = View.VISIBLE
        loginRequiredGroup.visibility = View.GONE
        profileImageView.load(state.profileImageUri.toString(), 60f)
        userNameTextView.text = state.userName
        Toast.makeText(requireContext(), state.orderList.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun handleLogin(state: MyState.Login) {
        binding.progressBar.visibility = View.VISIBLE
        val credential = GoogleAuthProvider.getCredential(state.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    viewModel.setUserInfo(user)
                } else {
                    firebaseAuth.signOut()
                    viewModel.setUserInfo(null)
                }
            }
    }

    private fun handleError(state: MyState.Error) {
        Toast.makeText(requireContext(), getString(state.messageId, state.e), Toast.LENGTH_SHORT).show()
    }

    companion object {

        fun newInstance() = MyFragment()

        const val TAG = "MyFragment"

    }

}