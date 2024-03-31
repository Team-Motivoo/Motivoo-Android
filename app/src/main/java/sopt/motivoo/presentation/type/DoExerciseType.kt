package sopt.motivoo.presentation.type

import androidx.annotation.StringRes
import sopt.motivoo.R

enum class DoExerciseType(
    @StringRes val titleRes: Int,
) {
    YES(
        R.string.do_exercise_yes
    ),
    NO(
        R.string.do_exercise_no
    )
}
