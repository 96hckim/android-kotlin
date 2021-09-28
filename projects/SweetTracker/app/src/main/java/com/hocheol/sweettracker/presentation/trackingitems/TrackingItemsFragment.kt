package com.hocheol.sweettracker.presentation.trackingitems

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hocheol.sweettracker.R
import com.hocheol.sweettracker.data.entity.TrackingInformation
import com.hocheol.sweettracker.data.entity.TrackingItem
import com.hocheol.sweettracker.databinding.FragmentTrackingItemsBinding
import com.hocheol.sweettracker.extension.toGone
import com.hocheol.sweettracker.extension.toInvisible
import com.hocheol.sweettracker.extension.toVisible
import org.koin.android.scope.ScopeFragment

class TrackingItemsFragment : ScopeFragment(), TrackingItemsContract.View {

    override val presenter: TrackingItemsContract.Presenter by inject()

    private var binding: FragmentTrackingItemsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentTrackingItemsBinding.inflate(inflater)
        .also { binding = it }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        bindView()
        presenter.onViewCreated()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        presenter.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter.onDestroy()
    }

    override fun showLoadingIndicator() {
        binding?.progressBar?.toVisible()
    }

    override fun hideLoadingIndicator() {
        binding?.progressBar?.toGone()
        binding?.refreshLayout?.isRefreshing = false
    }

    override fun showNoDataDescription() {
        binding?.refreshLayout?.toInvisible()
        binding?.noDataContainer?.toVisible()
    }

    override fun showTrackingItemInformation(trackingItemInformation: List<Pair<TrackingItem, TrackingInformation>>) {
        binding?.refreshLayout?.toVisible()
        binding?.noDataContainer?.toGone()
        (binding?.recyclerView?.adapter as? TrackingItemsAdapter)?.apply {
            this.data = trackingItemInformation
            notifyDataSetChanged()
        }
    }

    private fun initViews() {
        binding?.recyclerView?.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = TrackingItemsAdapter()
        }
    }

    private fun bindView() {
        binding?.refreshLayout?.setOnRefreshListener {
            presenter.refresh()
        }

        binding?.addTrackingItemButton?.setOnClickListener {
            findNavController().navigate(R.id.action_tracking_items_dest_to_add_tracking_item_dest)
        }

        binding?.addTrackingItemFloatingActionButton?.setOnClickListener { _ ->
            findNavController().navigate(R.id.action_tracking_items_dest_to_add_tracking_item_dest)
        }

        (binding?.recyclerView?.adapter as? TrackingItemsAdapter)?.onClickItemListener = { item, information ->
//            findNavController()
//                .navigate(TrackingItemsFragmentDirections.toTrackingHistory(item, information))
        }
    }

}
