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

    override var isUserLoggedIn: Boolean
        set(value) = pref.edit { putBoolean(IS_LOGIN, value) }
        get() = pref.getBoolean(IS_LOGIN, false)

    override var isUserMatched: Boolean
        set(value) = pref.edit { putBoolean(IS_MATCHED, value) }
        get() = pref.getBoolean(IS_MATCHED, false)

    override var isFinishedOnboarding: Boolean
        set(value) = pref.edit { putBoolean(IS_ONBOARDING_FINISHED, value) }
        get() = pref.getBoolean(IS_ONBOARDING_FINISHED, false)

    override var isFinishedPermission: Boolean
        set(value) = pref.edit { putBoolean(IS_PERMISSION_FINISHED, value) }
        get() = pref.getBoolean(IS_PERMISSION_FINISHED, false)

    override var isFinishedTermsOfUse: Boolean
        set(value) = pref.edit { putBoolean(IS_TERMS_FINISHED, value) }
        get() = pref.getBoolean(IS_TERMS_FINISHED, false)

    override var myGoalStepCount: Int
        get() = pref.getInt(MY_GOAL_STEP_COUNT, 0)
        set(value) = pref.edit { putInt(MY_GOAL_STEP_COUNT, value) }

    override var otherGoalStepCount: Int
        get() = pref.getInt(OTHER_GOAL_STEP_COUNT, 0)
        set(value) = pref.edit { putInt(OTHER_GOAL_STEP_COUNT, value) }

    override fun clear() {
        pref.edit {
            clear()
        }
    }

    override fun logout() {
        pref.edit {
            remove(USER_ID)
            remove(ACCESS_TOKEN)
            remove(REFRESH_TOKEN)
            remove(IS_LOGIN)
        }
    }

    companion object {
        private const val FILE_NAME = "MtDataStore"
        private const val NAME = "name"
        const val ACCESS_TOKEN = "accessToken"
        const val REFRESH_TOKEN = "refreshToken"
        private const val IS_LOGIN = "isLogin"
        private const val IS_MATCHED = "isMatched"
        private const val USER_ID = "userId"
        private const val MY_GOAL_STEP_COUNT = "my_goal_step_count"
        private const val OTHER_GOAL_STEP_COUNT = "other_goal_step_count"
        private const val IS_ONBOARDING_FINISHED = "isOnboardingFinished"
        private const val IS_PERMISSION_FINISHED = "isPermissionFinished"
        private const val IS_TERMS_FINISHED = "isTermsFinished"
    }
}
