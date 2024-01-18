package sopt.motivoo.domain.entity

data class MyExerciseInfo(
    val isExercise: Boolean,
    val exerciseType: String,
    val exerciseFrequency: String,
    val exerciseTime: String,
    val healthNotes: List<String>,
)
