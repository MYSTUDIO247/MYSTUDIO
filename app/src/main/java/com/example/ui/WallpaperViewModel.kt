package com.example.ui

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.CategoryItem
import com.example.data.model.WallpaperItem
import com.example.data.repository.WallpaperRepository
import com.example.util.WallpaperManagerHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class SortOption(val label: String) {
    POPULAR("Most Popular"),
    LATEST("Newest Added"),
    DOWNLOADS("Top Downloads")
}

enum class MockupType {
    NONE,
    LOCKSCREEN,
    HOMESCREEN
}

sealed class ActionState {
    object Idle : ActionState()
    data class Loading(val message: String) : ActionState()
    data class Success(val message: String) : ActionState()
    data class Error(val message: String) : ActionState()
}

class WallpaperViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = WallpaperRepository(application)

    // Navigation state
    val currentTab = MutableStateFlow("explore") // "explore", "categories", "upload", "favorites"

    // Search and filter state
    val searchQuery = MutableStateFlow("")
    val selectedCategory = MutableStateFlow("All")
    val selectedColorHex = MutableStateFlow<String?>(null)
    val sortOption = MutableStateFlow(SortOption.POPULAR)

    // Wallpaper detail view state
    val selectedWallpaper = MutableStateFlow<WallpaperItem?>(null)
    val mockupType = MutableStateFlow(MockupType.NONE)

    // Theme toggle
    val isDarkTheme = MutableStateFlow(true)

    // Action execution state (Download / Apply)
    val actionState = MutableStateFlow<ActionState>(ActionState.Idle)

    // Upload state
    val uploadImageUri = MutableStateFlow<Uri?>(null)
    val uploadTitle = MutableStateFlow("")
    val uploadCategory = MutableStateFlow("Nature")
    val uploadAuthor = MutableStateFlow("")
    val uploadTags = MutableStateFlow("")
    val uploadColorHex = MutableStateFlow("#6366F1")
    val isUploading = MutableStateFlow(false)

    val categories: List<CategoryItem> = repository.categories

    val favoriteIds: StateFlow<Set<String>> = repository.favoriteIds
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptySet())

    val userUploads: StateFlow<List<WallpaperItem>> = repository.userUploadedWallpapers
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val favoriteWallpapers: StateFlow<List<WallpaperItem>> = repository.favoriteWallpapers
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Filtered wallpaper list
    val filteredWallpapers: StateFlow<List<WallpaperItem>> = combine(
        repository.allWallpapers,
        searchQuery,
        selectedCategory,
        selectedColorHex,
        sortOption
    ) { all, query, category, color, sort ->
        var list = all

        if (category != "All") {
            list = list.filter { it.category.equals(category, ignoreCase = true) }
        }

        if (query.isNotBlank()) {
            val q = query.trim().lowercase()
            list = list.filter { item ->
                item.title.lowercase().contains(q) ||
                        item.category.lowercase().contains(q) ||
                        item.author.lowercase().contains(q) ||
                        item.tags.any { it.lowercase().contains(q) }
            }
        }

        if (color != null) {
            list = list.filter { it.colorHex.equals(color, ignoreCase = true) }
        }

        when (sort) {
            SortOption.POPULAR -> list.sortedByDescending { it.likes }
            SortOption.LATEST -> list.sortedByDescending { it.timestamp }
            SortOption.DOWNLOADS -> list.sortedByDescending { it.downloads }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val dailyFeatured: StateFlow<WallpaperItem?> = repository.allWallpapers.combine(
        MutableStateFlow(Unit)
    ) { list, _ ->
        list.firstOrNull { it.isDailyFeatured } ?: list.firstOrNull()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun setTab(tab: String) {
        currentTab.value = tab
    }

    fun selectCategory(category: String) {
        selectedCategory.value = category
        currentTab.value = "explore"
    }

    fun clearFilters() {
        searchQuery.value = ""
        selectedCategory.value = "All"
        selectedColorHex.value = null
    }

    fun openWallpaperDetail(item: WallpaperItem) {
        selectedWallpaper.value = item
        mockupType.value = MockupType.NONE
    }

    fun closeWallpaperDetail() {
        selectedWallpaper.value = null
        mockupType.value = MockupType.NONE
    }

    fun toggleFavorite(item: WallpaperItem) {
        viewModelScope.launch {
            val isFav = favoriteIds.value.contains(item.id)
            repository.toggleFavorite(item.id, isFav)
        }
    }

    fun downloadHighRes(item: WallpaperItem) {
        viewModelScope.launch {
            actionState.value = ActionState.Loading("Downloading 4K Ultra HD wallpaper to Pictures...")
            val result = WallpaperManagerHelper.saveToGallery(getApplication(), item)
            result.onSuccess {
                actionState.value = ActionState.Success("High-Res wallpaper saved to Pictures/WorldWallpaper!")
            }.onFailure { e ->
                actionState.value = ActionState.Error("Download failed: ${e.localizedMessage ?: "Unknown error"}")
            }
        }
    }

    fun applyWallpaper(item: WallpaperItem, target: WallpaperManagerHelper.TargetScreen) {
        viewModelScope.launch {
            val targetName = when (target) {
                WallpaperManagerHelper.TargetScreen.HOME -> "Home Screen"
                WallpaperManagerHelper.TargetScreen.LOCK -> "Lock Screen"
                WallpaperManagerHelper.TargetScreen.BOTH -> "Home & Lock Screen"
            }
            actionState.value = ActionState.Loading("Applying to $targetName...")
            val result = WallpaperManagerHelper.applyWallpaper(getApplication(), item, target)
            result.onSuccess {
                actionState.value = ActionState.Success("Wallpaper applied successfully to $targetName!")
            }.onFailure { e ->
                actionState.value = ActionState.Error("Failed to set wallpaper: ${e.localizedMessage ?: "Error"}")
            }
        }
    }

    fun shareWallpaper(item: WallpaperItem) {
        viewModelScope.launch {
            WallpaperManagerHelper.shareWallpaper(getApplication(), item)
        }
    }

    fun clearActionState() {
        actionState.value = ActionState.Idle
    }

    fun onImagePicked(uri: Uri) {
        uploadImageUri.value = uri
    }

    fun submitUpload(onSuccess: (WallpaperItem) -> Unit) {
        val uri = uploadImageUri.value ?: return
        viewModelScope.launch {
            isUploading.value = true
            val tagsList = uploadTags.value
                .split(",")
                .map { it.trim() }
                .map { if (it.startsWith("#")) it else "#$it" }
                .filter { it.length > 1 }

            val result = repository.saveUserUpload(
                sourceUri = uri,
                title = uploadTitle.value.ifBlank { "Aesthetic Masterpiece" },
                category = uploadCategory.value,
                author = uploadAuthor.value.ifBlank { "You" },
                tags = if (tagsList.isEmpty()) listOf("#aesthetic", "#custom", "#wallpaper") else tagsList,
                colorHex = uploadColorHex.value
            )

            isUploading.value = false
            result.onSuccess { item ->
                // Reset form
                uploadImageUri.value = null
                uploadTitle.value = ""
                uploadTags.value = ""
                uploadAuthor.value = ""
                actionState.value = ActionState.Success("Uploaded '${item.title}' successfully!")
                onSuccess(item)
            }.onFailure { err ->
                actionState.value = ActionState.Error("Upload failed: ${err.localizedMessage}")
            }
        }
    }

    fun deleteUpload(item: WallpaperItem) {
        viewModelScope.launch {
            repository.deleteUpload(item.id)
            if (selectedWallpaper.value?.id == item.id) {
                selectedWallpaper.value = null
            }
            actionState.value = ActionState.Success("Wallpaper deleted from your collection.")
        }
    }
}
