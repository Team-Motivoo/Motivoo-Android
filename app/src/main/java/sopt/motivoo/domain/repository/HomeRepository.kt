package sopt.motivoo.domain.repository

import android.graphics.Bitmap
import sopt.motivoo.data.model.request.home.RequestMissionImageDto
import sopt.motivoo.data.model.request.home.RequestMissionTodayDto
import sopt.motivoo.domain.entity.home.HomeData
import sopt.motivoo.domain.entity.home.MissionChoiceData
import sopt.motivoo.domain.entity.home.MissionImageData

interface HomeRepository {
    fun getMyStepCount(myUid: Long, myStepCount: (Long) -> Unit)
    fun getEventOtherStepCount(otherUid: Long, otherStepCount: (Long) -> Unit)
    fun setMyStepCount(myUid: Long, stepCount: Int)
    suspend fun patchHome(myStepCount: Int, otherStepCount: Int): Result<HomeData>
    suspend fun postMissionTodayChoice(): Result<MissionChoiceData>
    suspend fun postMissionToday(requestMissionTodayDto: RequestMissionTodayDto): Result<Unit>
    suspend fun patchMissionImage(requestMissionImageDto: RequestMissionImageDto): Result<MissionImageData>
    suspend fun uploadPhoto(url: String, bitmap: Bitmap): Result<Unit>
}
