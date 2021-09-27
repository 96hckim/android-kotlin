package com.hocheol.subway.presentation.stations

import com.hocheol.subway.domain.Station
import com.hocheol.subway.presentation.BasePresenter
import com.hocheol.subway.presentation.BaseView

interface StationsContract {

    interface View : BaseView<Presenter> {

        fun showLoadingIndicator()

        fun hideLoadingIndicator()

        fun showStations(stations: List<Station>)

    }

    interface Presenter : BasePresenter {

        fun filterStations(query: String)

    }

}