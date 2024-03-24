package sopt.motivoo.data.datasource.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import sopt.motivoo.di.PreferencesKeys
import javax.inject.Inject

class StepCountLocalDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) {
    val myStepCount: Flow<Int> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.STEP_COUNT] ?: -1
    }

    suspend fun addMyStepCount(stepCount: (Int) -> Unit) {
        dataStore.edit { preferences ->
            val currentStepCount = preferences[PreferencesKeys.STEP_COUNT] ?: 0
            preferences[PreferencesKeys.STEP_COUNT] = currentStepCount + 1
            stepCount(preferences[PreferencesKeys.STEP_COUNT] ?: 0)
        }
    }

    suspend fun setMyStepCount(stepCount: Int) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.STEP_COUNT] = stepCount
        }
    }

    suspend fun removeMyStepCount() {
        dataStore.edit { preferences ->
            preferences.remove(PreferencesKeys.STEP_COUNT)
        }
    }
}
