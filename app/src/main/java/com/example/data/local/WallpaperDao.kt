package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WallpaperDao {
    @Query("SELECT wallpaperId FROM favorites ORDER BY addedAt DESC")
    fun getAllFavoriteIds(): Flow<List<String>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE wallpaperId = :id)")
    fun isFavorite(id: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(favorite: FavoriteEntity)

    @Query("DELETE FROM favorites WHERE wallpaperId = :id")
    suspend fun removeFavorite(id: String)

    @Query("SELECT * FROM user_wallpapers ORDER BY createdAt DESC")
    fun getAllUserWallpapers(): Flow<List<UserWallpaperEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserWallpaper(wallpaper: UserWallpaperEntity)

    @Query("DELETE FROM user_wallpapers WHERE id = :id")
    suspend fun deleteUserWallpaper(id: String)
}
