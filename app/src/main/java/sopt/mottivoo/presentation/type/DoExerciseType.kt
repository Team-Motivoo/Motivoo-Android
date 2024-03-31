package sopt.mottivoo.presentation.type

import androidx.annotation.StringRes
import sopt.mottivoo.R

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
