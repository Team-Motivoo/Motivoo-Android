package sopt.motivoo.data.service

import retrofit2.http.GET
import sopt.motivoo.domain.entity.ResponseMyPageDto

interface ExerciseService {
    @GET("/mission")
    suspend fun getExerciseHistoryInfo(): ResponseMyPageDto
}
