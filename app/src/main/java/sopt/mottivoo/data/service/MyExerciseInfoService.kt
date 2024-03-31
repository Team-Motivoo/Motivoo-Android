package sopt.mottivoo.data.service

import retrofit2.http.GET
import sopt.mottivoo.data.model.response.ResponseMyExerciseInfoDto

interface MyExerciseInfoService {
    @GET("/user/exercise")
    suspend fun getMyExerciseInfo(): ResponseMyExerciseInfoDto
}
