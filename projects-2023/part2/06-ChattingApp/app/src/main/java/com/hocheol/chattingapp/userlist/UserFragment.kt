package com.hocheol.chattingapp.userlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.hocheol.chattingapp.R
import com.hocheol.chattingapp.databinding.FragmentUserlistBinding

class UserFragment : Fragment(R.layout.fragment_userlist) {

    private lateinit var binding: FragmentUserlistBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserlistBinding.bind(view)

        val userListAdapter = UserAdapter()

        binding.userListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = userListAdapter
        }

        userListAdapter.submitList(
            mutableListOf(
                UserItem(
                    userId = "1",
                    username = "1",
                    description = "1.."
                ),
                UserItem(
                    userId = "2",
                    username = "2",
                    description = "2.."
                )
            )
        )
    }
}