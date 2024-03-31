package sopt.motivoo.presentation.type

import androidx.annotation.StringRes
import sopt.motivoo.R

enum class TimeType(
    @StringRes val titleRes: Int,
) {
    UNDERTHIRTY(
        R.string.time_under_thirty
    ),
    THIRTYTOONE(
        R.string.time_thirty_to_one
    ),
    ONETOTWO(
        R.string.time_one_to_two
    ),
    OVERTWO(
        R.string.time_over_two
    )
}
