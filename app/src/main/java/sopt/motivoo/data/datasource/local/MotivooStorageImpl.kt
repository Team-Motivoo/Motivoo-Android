package sopt.motivoo.data.datasource.local

import android.content.Context
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.google.firebase.BuildConfig
import dagger.hilt.android.qualifiers.ApplicationContext
import sopt.motivoo.domain.entity.MotivooStorage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MotivooStorageImpl @Inject constructor(@ApplicationContext context: Context) :
    MotivooStorage {
    private val masterKey = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val pref by lazy {
        if (BuildConfig.DEBUG) {
            context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        } else {
            EncryptedSharedPreferences.create(
                context,
                FILE_NAME,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
            )
        }
    }

    override var name: String
        get() = pref.getString(NAME, "") ?: ""
        set(value) = pref.edit { putString(NAME, value) }

    override var email: String
        get() = pref.getString(EMAIL, "") ?: ""
        set(value) = pref.edit { putString(EMAIL, value) }

    override var stepCount: Int
        get() = pref.getInt(STEP_COUNT, 0)
        set(value) = pref.edit { putInt(STEP_COUNT, value) }

    companion object {
        private const val FILE_NAME = "MtDataStore"
        private const val NAME = "name"
        private const val EMAIL = "email"
        private const val STEP_COUNT = "step_count"
    }
}
