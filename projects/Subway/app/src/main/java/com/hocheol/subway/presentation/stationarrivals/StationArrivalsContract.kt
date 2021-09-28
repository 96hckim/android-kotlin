package com.hocheol.subway.presentation.stationarrivals

import com.hocheol.subway.domain.ArrivalInformation
import com.hocheol.subway.presentation.BasePresenter
import com.hocheol.subway.presentation.BaseView

interface StationArrivalsContract {

    interface View : BaseView<Presenter> {

        fun showLoadingIndicator()

        fun hideLoadingIndicator()

        fun showErrorDescription(message: String)

        fun showStationArrivals(arrivalInformation: List<ArrivalInformation>)

    }

    interface Presenter : BasePresenter {

        fun fetchStationArrivals()

        fun toggleStationFavorite()

    }

}
