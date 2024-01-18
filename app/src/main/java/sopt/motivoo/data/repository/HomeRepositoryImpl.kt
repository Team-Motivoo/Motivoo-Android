package sopt.motivoo.data.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
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
import java.io.File
import java.net.URLConnection
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val firebaseRealtimeDB: FirebaseDatabase,
    private val homeDataSource: HomeDataSource,
) : HomeRepository {
    @Inject
    lateinit var pref: MotivooStorage

    override fun getMyStepCount(myUid: Long, myStepCount: (Long) -> Unit) {
        firebaseRealtimeDB.reference.child(USERS)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.child(myUid.toString()).apply {
                        if (exists()) (value as Long).let { if (it == 0L) pref.myStepCount = 0 }
                    }
                }

                override fun onCancelled(error: DatabaseError) {}
            })
    }

    override fun getEventOtherStepCount(otherUid: Long, otherStepCount: (Long) -> Unit) {
        firebaseRealtimeDB.reference.child(USERS)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.child(otherUid.toString()).apply {
                        if (exists()) otherStepCount(value as Long)
                    }
                }

                override fun onCancelled(error: DatabaseError) {}
            })
    }

    override fun setMyStepCount(myUid: Long, stepCount: Int) {
        firebaseRealtimeDB.reference.child(USERS).child(myUid.toString()).setValue(stepCount)
    }

    override suspend fun patchHome(myStepCount: Int, otherStepCount: Int): Result<HomeData> =
        runCatching { homeDataSource.patchHome(RequestHomeDto(myStepCount, otherStepCount)).toHomeData() }

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
                URLConnection.guessContentTypeFromName(file.name).toMediaType()
            )
        )
        return runCatching { homeDataSource.uploadPhoto(url, fileBody) }
    }

    companion object {
        const val PHOTO = "photo"
    }
}
