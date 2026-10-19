package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_wallpapers")
data class UserWallpaperEntity(
    @PrimaryKey val id: String,
    val title: String,
    val category: String,
    val imageUri: String,
    val author: String,
    val tags: String, // comma-separated
    val colorHex: String,
    val resolution: String,
    val createdAt: Long = System.currentTimeMillis()
)
