package sopt.motivoo.presentation.home

import android.graphics.Bitmap

sealed class HomePictureState {
    object Idle : HomePictureState()
    data class SuccessMissionData(
        val imgPresignedUrl: String,
        val fileName: String,
        val pictureBitmap: Bitmap,
    ) : HomePictureState()

    data class UploadFile(
        val fileName: String,
        val pictureBitmap: Bitmap,
    ) : HomePictureState()

    data class SuccessImageUpload(
        val pictureBitmap: Bitmap,
    ) : HomePictureState()
}
