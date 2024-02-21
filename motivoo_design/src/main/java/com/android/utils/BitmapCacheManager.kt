package com.android.utils

import android.graphics.Bitmap
import android.util.LruCache
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BitmapCacheManager(
    private val dispatcher: CoroutineDispatcher,
) {
    private val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
    private val cacheSize = maxMemory / 8

    private val bitmapCache = object : LruCache<String?, Bitmap?>(cacheSize) {
        override fun sizeOf(key: String?, value: Bitmap?): Int {
            return value?.byteCount?.div(1024) ?: 0
        }
    }

    suspend fun addBitmapToMemoryCache(key: String, bitmap: Bitmap) {
        withContext(dispatcher) {
            if (getBitmapFromMemoryCache(key) == null) {
                bitmapCache.put(key, bitmap)
            }
        }
    }

    suspend fun getBitmapFromMemoryCache(key: String): Bitmap? = withContext(dispatcher) {
        bitmapCache.get(key)
    }

    companion object {
        const val MY_IMAGE = "MY_IMAGE"
        const val OTHER_IMAGE = "OTHER_IMAGE"

        @Volatile
        private var instance: BitmapCacheManager? = null

        fun getInstance(dispatcher: CoroutineDispatcher = Dispatchers.IO) =
            instance ?: synchronized(this) {
                instance ?: BitmapCacheManager(dispatcher).also { instance = it }
            }
    }
}