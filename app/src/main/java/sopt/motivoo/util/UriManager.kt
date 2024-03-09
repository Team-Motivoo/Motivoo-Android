package sopt.motivoo.util

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import java.text.SimpleDateFormat

class UriManager(
    private val context: Context,
) {
    private val now = System.currentTimeMillis().let {
        SimpleDateFormat(FILE_NAME_FORMAT).format(it)
    }

    private fun getContentValues(): ContentValues =
        ContentValues().apply {
            put(
                MediaStore.Images.Media.DISPLAY_NAME,
                IMG_PREFIX + now + JPG_TYPE
            )
            put(MediaStore.Images.Media.MIME_TYPE, JPG_MIME_TYPE)
        }

    fun createImageUri(): Uri? = context.contentResolver.insert(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, getContentValues()
    )

    companion object {
        private const val FILE_NAME_FORMAT = "yyMMddHHmmss"
        private const val IMG_PREFIX = "motivoo_img_"
        private const val JPG_MIME_TYPE = "image/jpg"
        private const val JPG_TYPE = ".jpg"
    }
}