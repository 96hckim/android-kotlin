package com.hocheol.mediasearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.hocheol.mediasearch.databinding.FragmentFavoritesBinding
import com.hocheol.mediasearch.list.ListAdapter

class FavoritesFragment : Fragment() {

    private var binding: FragmentFavoritesBinding? = null

    private val adapter by lazy { ListAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentFavoritesBinding.inflate(inflater, container, false).apply {
            binding = this
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.let {
            it.recyclerView.adapter = adapter
        }
    }

    override fun onResume() {
        super.onResume()
        binding?.apply {
            val isEmpty = Common.favoritesList.isEmpty()
            emptyTextView.isVisible = isEmpty
            recyclerView.isVisible = isEmpty.not()
        }
        adapter.submitList(Common.favoritesList.sortedBy { it.dateTime })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}