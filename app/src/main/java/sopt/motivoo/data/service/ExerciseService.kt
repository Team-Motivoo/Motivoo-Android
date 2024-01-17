package sopt.motivoo.data.service

import retrofit2.http.GET
import sopt.motivoo.data.model.response.ResponseExerciseDto

interface ExerciseService {
    @GET("/mission")
    suspend fun getExerciseHistoryInfo(): ResponseExerciseDto
}
