package com.bsu.iot.data

import com.bsu.iot.model.Station

class Datasource {
    fun loadStations(): List<Station> {
        return listOf(
            Station("Station 1", 15),
            Station("Station 2", 25),
            Station("Station 3", 18),
            Station("Station 4", 30),
            Station("Station 5", -2),
        )
    }
}