package sopt.motivoo.presentation.type

import androidx.annotation.StringRes
import sopt.motivoo.R

enum class WhatActivityType(
    @StringRes val titleRes: Int,
    @StringRes val desRes: Int,
) {
    HARD(
        R.string.hard_activity_title,
        R.string.hard_activity_description,
    ),
    MIDDLE(
        R.string.middle_activity_title,
        R.string.middle_activity_description,
    ),
    LOW(
        R.string.low_activity_title,
        R.string.low_activity_description,
    )
}
