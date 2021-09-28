package com.hocheol.sweettracker.presentation.trackinghistory

import com.hocheol.sweettracker.data.entity.TrackingInformation
import com.hocheol.sweettracker.data.entity.TrackingItem
import com.hocheol.sweettracker.presentation.BasePresenter
import com.hocheol.sweettracker.presentation.BaseView

class TrackingHistoryContract {

    interface View : BaseView<Presenter> {

        fun showLoadingIndicator()

        fun hideLoadingIndicator()

        fun showTrackingItemInformation(trackingItem: TrackingItem, trackingInformation: TrackingInformation)

        fun finish()

    }

    interface Presenter : BasePresenter {

        fun refresh()

        fun deleteTrackingItem()

    }

}
