package com.hocheol.sweettracker.presentation.trackinghistory

import com.hocheol.sweettracker.data.entity.TrackingInformation
import com.hocheol.sweettracker.data.entity.TrackingItem
import com.hocheol.sweettracker.data.repository.TrackingItemRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class TrackingHistoryPresenter(
    private val view: TrackingHistoryContract.View,
    private val trackerRepository: TrackingItemRepository,
    private val trackingItem: TrackingItem,
    private var trackingInformation: TrackingInformation
) : TrackingHistoryContract.Presenter {

    override val scope: CoroutineScope = MainScope()

    override fun onViewCreated() {
        view.showLoadingIndicator()
        view.showTrackingItemInformation(trackingItem, trackingInformation)
        view.hideLoadingIndicator()
    }

    override fun onDestroyView() {}

    override fun refresh() {
        scope.launch {
            try {
                view.showLoadingIndicator()
                val newTrackingInformation =
                    trackerRepository.getTrackingInformation(trackingItem.company.code, trackingItem.invoice)
                newTrackingInformation?.let {
                    trackingInformation = it
                    view.showTrackingItemInformation(trackingItem, trackingInformation)
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            } finally {
                view.hideLoadingIndicator()
            }
        }
    }

    override fun deleteTrackingItem() {
        scope.launch {
            try {
                trackerRepository.deleteTrackingItem(trackingItem)
                view.finish()
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
    }

}
