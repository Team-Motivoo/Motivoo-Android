package sopt.motivoo.data.repository

import android.graphics.Bitmap
import android.icu.text.UnicodeSetSpanner
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import okhttp3.RequestBody.Companion.toRequestBody
import sopt.motivoo.data.datasource.remote.HomeDataSource
import sopt.motivoo.data.model.request.home.RequestHomeDto
import sopt.motivoo.data.model.request.home.RequestMissionImageDto
import sopt.motivoo.data.model.request.home.RequestMissionTodayDto
import sopt.motivoo.domain.entity.MotivooStorage
import sopt.motivoo.domain.entity.home.HomeData
import sopt.motivoo.domain.entity.home.MissionChoiceData
import sopt.motivoo.domain.entity.home.MissionImageData
import sopt.motivoo.domain.repository.HomeRepository
import sopt.motivoo.util.Constants.USERS
import timber.log.Timber
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val firebaseRealtimeDB: FirebaseDatabase,
    private val homeDataSource: HomeDataSource,
) : HomeRepository {
    @Inject
    lateinit var pref: MotivooStorage

    override fun getMyStepCount(uid: String, isInitStepCount: (Boolean) -> Unit) {
        firebaseRealtimeDB.reference.child(USERS).child(uid)
            .addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.value == 0) {
                        pref.myStepCount = 0
                        isInitStepCount(true)
                    }
                }

                override fun onCancelled(error: DatabaseError) {}
            })
    }

    override fun getOtherStepCount(otherUid: String, onStepCountAction: (Long) -> Unit) {
        firebaseRealtimeDB.reference.child(USERS).child(otherUid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.value != null) {
                        onStepCountAction(snapshot.value as Long)
                    }
                }

                override fun onCancelled(error: DatabaseError) {}
            })
    }

    override fun setMyStepCount(uid: String) {
        firebaseRealtimeDB.reference.child(USERS).child(uid).get()
            .addOnSuccessListener {
                if (it.value == null) {
                    Firebase.database.reference.child(USERS).child(uid).setValue(pref.myStepCount)
                }
            }
    }

    override suspend fun patchHome(myStepCount: Int, otherStepCount: Int): Result<HomeData> =
        runCatching {
            homeDataSource.patchHome(RequestHomeDto(myStepCount, otherStepCount)).toHomeData()
        }

    override suspend fun postMissionTodayChoice(): Result<MissionChoiceData> =
        runCatching { homeDataSource.postMissionTodayChoice().toMissionChoiceData() }

    override suspend fun postMissionToday(requestMissionTodayDto: RequestMissionTodayDto): Result<Unit> =
        runCatching { homeDataSource.postMissionToday(requestMissionTodayDto) }

    override suspend fun patchMissionImage(requestMissionImageDto: RequestMissionImageDto): Result<MissionImageData> =
        runCatching {
            homeDataSource.patchMissionImage(requestMissionImageDto).toMissionImageData()
        }

    override suspend fun uploadPhoto(url: String, bitmap: Bitmap): Result<Unit> {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, outputStream)
        val requestBody = outputStream.toByteArray().toRequestBody()
        return runCatching { homeDataSource.uploadPhoto(url, requestBody) }
    }
}
