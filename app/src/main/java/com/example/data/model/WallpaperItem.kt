package com.example.data.model

data class WallpaperItem(
    val id: String,
    val title: String,
    val category: String,
    val imageUrl: String,
    val highResUrl: String = imageUrl,
    val author: String = "WorldWallpaper Curator",
    val authorBio: String = "Digital visual artist & landscape creator",
    val tags: List<String> = emptyList(),
    val colorHex: String = "#6366F1",
    val resolution: String = "3840 x 2160 (4K UHD)",
    val fileSize: String = "4.8 MB",
    val isDailyFeatured: Boolean = false,
    val views: Int = 12400,
    val likes: Int = 3890,
    val downloads: Int = 1820,
    val isUserUploaded: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)

data class CategoryItem(
    val id: String,
    val name: String,
    val count: Int,
    val previewUrl: String,
    val iconName: String,
    val description: String
)
