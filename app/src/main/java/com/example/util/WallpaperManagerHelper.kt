package com.example.util

import android.app.WallpaperManager
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import com.example.data.model.WallpaperItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

object WallpaperManagerHelper {

    enum class TargetScreen {
        HOME,
        LOCK,
        BOTH
    }

    suspend fun fetchBitmap(context: Context, imageUrl: String): Bitmap? = withContext(Dispatchers.IO) {
        try {
            when {
                imageUrl.startsWith("content://") || imageUrl.startsWith("file://") || imageUrl.startsWith("android.resource://") -> {
                    val uri = Uri.parse(imageUrl)
                    context.contentResolver.openInputStream(uri)?.use { stream ->
                        BitmapFactory.decodeStream(stream)
                    }
                }
                imageUrl.startsWith("drawable://") -> {
                    val resName = imageUrl.removePrefix("drawable://")
                    val resId = context.resources.getIdentifier(resName, "drawable", context.packageName)
                    if (resId != 0) {
                        BitmapFactory.decodeResource(context.resources, resId)
                    } else null
                }
                else -> {
                    val url = URL(imageUrl)
                    val connection = url.openConnection() as HttpURLConnection
                    connection.doInput = true
                    connection.connectTimeout = 15000
                    connection.readTimeout = 15000
                    connection.connect()
                    val input: InputStream = connection.inputStream
                    val bitmap = BitmapFactory.decodeStream(input)
                    input.close()
                    bitmap
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun saveToGallery(context: Context, wallpaper: WallpaperItem): Result<Uri> = withContext(Dispatchers.IO) {
        try {
            val bitmap = fetchBitmap(context, wallpaper.highResUrl)
                ?: return@withContext Result.failure(Exception("Could not load high resolution image"))

            val filename = "WorldWallpaper_${wallpaper.id}_${System.currentTimeMillis()}.jpg"
            val resolver = context.contentResolver

            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/WorldWallpaper")
                    put(MediaStore.MediaColumns.IS_PENDING, 1)
                }
            }

            val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                ?: return@withContext Result.failure(Exception("Failed to create MediaStore entry"))

            resolver.openOutputStream(imageUri)?.use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 95, out)
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                contentValues.clear()
                contentValues.put(MediaStore.MediaColumns.IS_PENDING, 0)
                resolver.update(imageUri, contentValues, null, null)
            }

            Result.success(imageUri)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun applyWallpaper(
        context: Context,
        wallpaper: WallpaperItem,
        target: TargetScreen
    ): Result<Boolean> = withContext(Dispatchers.IO) {
        try {
            val bitmap = fetchBitmap(context, wallpaper.highResUrl)
                ?: return@withContext Result.failure(Exception("Failed to decode wallpaper bitmap"))

            val wallpaperManager = WallpaperManager.getInstance(context)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                val whichFlag = when (target) {
                    TargetScreen.HOME -> WallpaperManager.FLAG_SYSTEM
                    TargetScreen.LOCK -> WallpaperManager.FLAG_LOCK
                    TargetScreen.BOTH -> WallpaperManager.FLAG_SYSTEM or WallpaperManager.FLAG_LOCK
                }
                wallpaperManager.setBitmap(bitmap, null, true, whichFlag)
            } else {
                wallpaperManager.setBitmap(bitmap)
            }
            Result.success(true)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun shareWallpaper(
        context: Context,
        wallpaper: WallpaperItem
    ) = withContext(Dispatchers.IO) {
        try {
            val bitmap = fetchBitmap(context, wallpaper.imageUrl)
            val shareFolder = File(context.cacheDir, "shared_wallpapers")
            if (!shareFolder.exists()) shareFolder.mkdirs()

            val file = File(shareFolder, "wallpaper_${wallpaper.id}.jpg")
            val outputStream = FileOutputStream(file)
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
            outputStream.flush()
            outputStream.close()

            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                file
            )

            val shareText = "✨ Discovering '${wallpaper.title}' by ${wallpaper.author} on WorldWallpaper!\n" +
                    "Category: ${wallpaper.category} | ${wallpaper.resolution}\n" +
                    "${wallpaper.tags.joinToString(" ")}"

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "image/jpeg"
                putExtra(Intent.EXTRA_STREAM, uri)
                putExtra(Intent.EXTRA_TEXT, shareText)
                putExtra(Intent.EXTRA_TITLE, wallpaper.title)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }

            val chooser = Intent.createChooser(shareIntent, "Share Wallpaper via").apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(chooser)
        } catch (e: Exception) {
            e.printStackTrace()
            // Fallback to text share
            val textIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, "✨ Check out this 4K wallpaper '${wallpaper.title}' on WorldWallpaper!")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(Intent.createChooser(textIntent, "Share Wallpaper").apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            })
        }
    }
}
