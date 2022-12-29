package com.bsu.iot.network

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import com.bsu.iot.data.StoreSettings
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url


private const val BASE_URL = ""

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface StationApiService {
    @GET
    suspend fun getStationsInfo(@Url url: String): String
}

object StationsApi {
    val retrofitService: StationApiService by lazy {
        retrofit.create(StationApiService::class.java)
    }
}

