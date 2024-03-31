package sopt.mottivoo.presentation.type

import androidx.annotation.StringRes
import sopt.mottivoo.R

enum class UserType(
    @StringRes val titleRes: Int,
) {
    PARENT(
        R.string.onboarding_parent
    ),
    CHILD(
        R.string.onboarding_chile
    )
}
