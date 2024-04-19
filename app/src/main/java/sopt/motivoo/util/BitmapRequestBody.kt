package sopt.motivoo.util

import android.graphics.Bitmap
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream

class BitmapRequestBody(private val bitmap: Bitmap) {

    /**
     * byte size = output.toByteArray().size
     */
    fun create(quality: Int = 100): RequestBody {
        val output = ByteArrayOutputStream()
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, output)
        } catch (e: Exception) {
            e.message
        } finally {
            output.close()
        }
        return output.toByteArray().toRequestBody()
    }
}
