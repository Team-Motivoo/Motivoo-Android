package sopt.mottivoo.presentation.type

import androidx.annotation.StringRes
import sopt.mottivoo.R

enum class FrequencyType(
    @StringRes val titleRes: Int,
) {
    UNDERONE(
        R.string.frequency_under_one
    ),
    ONETOTHREE(
        R.string.frequency_one_to_three
    ),
    THREETOFIVE(
        R.string.frequency_three_to_five
    ),
    OVERFIVE(
        R.string.frequency_over_five
    )
}
