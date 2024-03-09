package sopt.motivoo.data.datasource.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import sopt.motivoo.di.PreferencesKeys
import javax.inject.Inject

class UserLocalDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) {
    val userId: Flow<Int> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.USER_ID] ?: -1
    }

    suspend fun setUserId(userId: Int) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_ID] = userId
        }
    }
}
