package sopt.motivoo.data.service

import retrofit2.http.GET
import sopt.motivoo.data.model.response.ResponseMyExerciseInfoDto

interface MyExerciseInfoService {
    @GET("/user/exercise")
    suspend fun getMyExerciseInfo(): ResponseMyExerciseInfoDto
}
