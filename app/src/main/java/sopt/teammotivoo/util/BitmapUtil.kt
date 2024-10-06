package sopt.teammotivoo.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.provider.MediaStore

class BitmapUtil(private val context: Context) {
    private fun loadOrientation(uri: Uri): Int {
        var orientation = 0
        val stream = context.contentResolver.openInputStream(uri) ?: return 0
        try {
            val exifInterface = ExifInterface(stream)
            orientation = exifInterface.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )
        } catch (e: Exception) {
            e.message
        } finally {
            stream.close()
        }
        return orientation
    }

    /**
     * @param bounds : if ture, assign bitmap in memory. if false, no assign bitmap in memory
     * @param size : return image ratio
     */
    private fun decodeUriToBitmap(uri: Uri, bounds: Boolean = false, size: Int = 1): Bitmap? {
        var bitmap: Bitmap? = null
        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = bounds
            inSampleSize = size
        }

        val stream = context.contentResolver.openInputStream(uri)
        try {
            bitmap = BitmapFactory.decodeStream(stream, null, options)
        } catch (e: Exception) {
            e.message
        } finally {
            stream?.close()
        }

        return bitmap
    }

    private fun rotateBitmap(orientation: Int, bitmap: Bitmap?): Bitmap? = when (orientation) {
        ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(bitmap, 90f)
        ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(bitmap, 180f)
        ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(bitmap, 270f)
        else -> bitmap
    }

    private fun rotateImage(bitmap: Bitmap?, angle: Float): Bitmap? {
        val matrix = Matrix().apply { postRotate(angle) }
        return bitmap?.let {
            Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        }
    }

    /**
     * Use BitmapFactory Bitmap Resize
     */
    fun createUriToBitmap(uri: Uri, bounds: Boolean = false, size: Int = 1): Bitmap? {
        val orientation = loadOrientation(uri)
        val bitmap = decodeUriToBitmap(uri, bounds, size)

        return rotateBitmap(orientation, bitmap)
    }

    /**
     * Use ImageDecoder Bitmap Resize, easy convert uri to bitmap
     */
    fun createUriToBitmap(uri: Uri): Bitmap =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source =
                ImageDecoder.createSource(context.contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        } else {
            MediaStore.Images.Media.getBitmap(
                context.contentResolver,
                uri
            )
        }
}
