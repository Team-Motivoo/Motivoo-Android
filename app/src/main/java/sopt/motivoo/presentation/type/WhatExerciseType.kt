package sopt.motivoo.presentation.type

import androidx.annotation.StringRes
import sopt.motivoo.R

enum class WhatExerciseType(
    @StringRes val titleRes: Int,
    @StringRes val desRes: Int,
) {
    HARD(
        R.string.hard_exercise_title,
        R.string.hard_exercise_description,
    ),
    MIDDLE(
        R.string.middle_exercise_title,
        R.string.middle_exercise_description,
    ),
    LOW(
        R.string.low_exercise_title,
        R.string.low_exercise_description,
    )
}
