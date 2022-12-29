package com.bsu.iot.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoreSettings(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")
        private val API_URL = stringPreferencesKey("api_url")
    }

    val getApiUrl: Flow<String?> = context.dataStore.data.map { it[API_URL] }

    suspend fun saveApiUrl(newUrl: String) {
        context.dataStore.edit { it[API_URL] = newUrl }
    }
}