package com.example.data.repository

import android.content.Context
import android.net.Uri
import com.example.data.local.AppDatabase
import com.example.data.local.FavoriteEntity
import com.example.data.local.UserWallpaperEntity
import com.example.data.model.CategoryItem
import com.example.data.model.WallpaperItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

class WallpaperRepository(private val context: Context) {

    private val db = AppDatabase.getDatabase(context)
    private val dao = db.wallpaperDao()

    val categories: List<CategoryItem> = CuratedWallpapers.categories

    val allWallpapers: Flow<List<WallpaperItem>> = dao.getAllUserWallpapers().map { userUploads ->
        val convertedUploads = userUploads.map { entity ->
            WallpaperItem(
                id = entity.id,
                title = entity.title,
                category = entity.category,
                imageUrl = entity.imageUri,
                highResUrl = entity.imageUri,
                author = entity.author.ifBlank { "You" },
                authorBio = "Community Creator Showcase",
                tags = entity.tags.split(",").map { it.trim() }.filter { it.isNotEmpty() },
                colorHex = entity.colorHex,
                resolution = entity.resolution,
                fileSize = "Custom Upload",
                isDailyFeatured = false,
                views = 150,
                likes = 12,
                downloads = 5,
                isUserUploaded = true,
                timestamp = entity.createdAt
            )
        }
        // User uploads appear at top of list
        convertedUploads + CuratedWallpapers.items
    }

    val favoriteIds: Flow<Set<String>> = dao.getAllFavoriteIds().map { it.toSet() }

    val favoriteWallpapers: Flow<List<WallpaperItem>> = combine(allWallpapers, favoriteIds) { wallpapers, favIds ->
        wallpapers.filter { favIds.contains(it.id) }
    }

    val userUploadedWallpapers: Flow<List<WallpaperItem>> = allWallpapers.map { wallpapers ->
        wallpapers.filter { it.isUserUploaded }
    }

    suspend fun toggleFavorite(wallpaperId: String, isCurrentlyFavorite: Boolean) {
        if (isCurrentlyFavorite) {
            dao.removeFavorite(wallpaperId)
        } else {
            dao.addFavorite(FavoriteEntity(wallpaperId))
        }
    }

    suspend fun saveUserUpload(
        sourceUri: Uri,
        title: String,
        category: String,
        author: String,
        tags: List<String>,
        colorHex: String
    ): Result<WallpaperItem> = withContext(Dispatchers.IO) {
        try {
            val id = "user_" + UUID.randomUUID().toString().take(8)
            val wallpapersDir = File(context.filesDir, "wallpapers")
            if (!wallpapersDir.exists()) wallpapersDir.mkdirs()

            val targetFile = File(wallpapersDir, "$id.jpg")

            context.contentResolver.openInputStream(sourceUri)?.use { input ->
                FileOutputStream(targetFile).use { output ->
                    input.copyTo(output)
                }
            }

            val savedUri = Uri.fromFile(targetFile).toString()
            val entity = UserWallpaperEntity(
                id = id,
                title = title.ifBlank { "Untitled Wallpaper" },
                category = category,
                imageUri = savedUri,
                author = author.ifBlank { "Aesthetic Creator" },
                tags = tags.joinToString(","),
                colorHex = colorHex,
                resolution = "High Definition (Custom)"
            )

            dao.insertUserWallpaper(entity)

            val item = WallpaperItem(
                id = id,
                title = entity.title,
                category = entity.category,
                imageUrl = savedUri,
                highResUrl = savedUri,
                author = entity.author,
                authorBio = "Community Creator Showcase",
                tags = tags,
                colorHex = colorHex,
                resolution = "High Definition (Custom)",
                fileSize = "${(targetFile.length() / 1024 / 1024).coerceAtLeast(1)} MB",
                isDailyFeatured = false,
                views = 1,
                likes = 1,
                downloads = 0,
                isUserUploaded = true,
                timestamp = System.currentTimeMillis()
            )

            Result.success(item)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun deleteUpload(id: String) = withContext(Dispatchers.IO) {
        dao.deleteUserWallpaper(id)
        val file = File(context.filesDir, "wallpapers/$id.jpg")
        if (file.exists()) file.delete()
    }
}
