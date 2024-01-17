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

    override var stepCount: Int
        get() = pref.getInt(STEP_COUNT, 0)
        set(value) = pref.edit { putInt(STEP_COUNT, value) }

    override var nickName: String
        get() = pref.getString(NAME, "") ?: ""
        set(value) = pref.edit { putString(NAME, value) }

    override var userId: Long
        set(value) = pref.edit { putLong(USER_ID, value) }
        get() = pref.getLong(USER_ID, 0L)

    override var accessToken: String
        set(value) = pref.edit { putString(ACCESS_TOKEN, value) }
        get() = pref.getString(
            ACCESS_TOKEN,
            "",
        ) ?: ""

    override var refreshToken: String
        set(value) = pref.edit { putString(REFRESH_TOKEN, value) }
        get() = pref.getString(
            REFRESH_TOKEN,
            "",
        ) ?: ""

    override var inviteCode: String
        set(value) = pref.edit { putString(INVITE_CODE, value) }
        get() = pref.getString(
            INVITE_CODE,
            "",
        ) ?: ""

    override var isUserLoggedIn: Boolean
        set(value) = pref.edit { putBoolean(IS_LOGIN, value) }
        get() = pref.getBoolean(IS_LOGIN, false)

    override var isUserMatched: Boolean
        set(value) = pref.edit { putBoolean(IS_MATCHED, value) }
        get() = pref.getBoolean(IS_MATCHED, false)

    override var isFinishedOnboarding: Boolean
        set(value) = pref.edit { putBoolean(IS_ONBOARDING_FINISHED, value) }
        get() = pref.getBoolean(IS_ONBOARDING_FINISHED, false)

    override fun clear() {
        pref.edit {
            clear()
        }
    }

    companion object {
        private const val FILE_NAME = "MtDataStore"
        private const val NAME = "name"
        private const val STEP_COUNT = "step_count"
        const val ACCESS_TOKEN = "accessToken"
        const val REFRESH_TOKEN = "refreshToken"
        private const val IS_LOGIN = "isLogin"
        private const val IS_MATCHED = "isMatched"
        private const val USER_ID = "userId"
        private const val INVITE_CODE = "inviteCode"
        private const val IS_ONBOARDING_FINISHED = "isOnboardingFinished"
    }
}
