package sopt.teammotivoo.data.service

import retrofit2.http.GET
import sopt.teammotivoo.data.model.response.ResponseMyExerciseInfoDto

interface MyExerciseInfoService {
    @GET("/user/exercise")
    suspend fun getMyExerciseInfo(): ResponseMyExerciseInfoDto
}
