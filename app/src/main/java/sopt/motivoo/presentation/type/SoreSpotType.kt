package sopt.motivoo.presentation.type

import androidx.annotation.StringRes
import sopt.motivoo.R

enum class SoreSpotType(
    @StringRes val titleRes: Int,
) {
    NECK(
        R.string.sore_spot_neck
    ),
    SHOULDER(
        R.string.sore_spot_shoulder
    ),
    WAIST(
        R.string.sore_spot_waist
    ),
    KNEE(
        R.string.sore_spot_knee
    ),
    WRIST(
        R.string.sore_spot_wrist
    ),
    ANKLE(
        R.string.sore_spot_ankle
    )
}
