package sopt.teammotivoo.data.service

import retrofit2.http.GET
import sopt.teammotivoo.data.model.response.ResponseExerciseDto

interface ExerciseService {
    @GET("/mission")
    suspend fun getExerciseHistoryInfo(): ResponseExerciseDto
}
