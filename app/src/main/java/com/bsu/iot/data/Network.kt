package com.bsu.iot.data

import com.bsu.iot.model.Station

class Network {
    companion object {
        fun getStations(): List<Station> {
            return Datasource().loadStations()
        }
    }
}