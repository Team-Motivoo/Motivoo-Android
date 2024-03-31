package sopt.mottivoo.data.service

import retrofit2.http.GET
import sopt.mottivoo.data.model.response.ResponseExerciseDto

interface ExerciseService {
    @GET("/mission")
    suspend fun getExerciseHistoryInfo(): ResponseExerciseDto
}
