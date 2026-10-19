package com.example.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.screens.CategoriesScreen
import com.example.ui.screens.ExploreScreen
import com.example.ui.screens.FavoritesScreen
import com.example.ui.screens.UploadScreen
import com.example.ui.screens.WallpaperDetailSheet

@Composable
fun MainScreen(
    viewModel: WallpaperViewModel,
    modifier: Modifier = Modifier
) {
    val currentTab by viewModel.currentTab.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val sortOption by viewModel.sortOption.collectAsStateWithLifecycle()
    val filteredWallpapers by viewModel.filteredWallpapers.collectAsStateWithLifecycle()
    val dailyFeatured by viewModel.dailyFeatured.collectAsStateWithLifecycle()
    val favoriteIds by viewModel.favoriteIds.collectAsStateWithLifecycle()
    val favoriteWallpapers by viewModel.favoriteWallpapers.collectAsStateWithLifecycle()
    val userUploads by viewModel.userUploads.collectAsStateWithLifecycle()
    val selectedWallpaper by viewModel.selectedWallpaper.collectAsStateWithLifecycle()
    val mockupType by viewModel.mockupType.collectAsStateWithLifecycle()
    val actionState by viewModel.actionState.collectAsStateWithLifecycle()
    val isDarkTheme by viewModel.isDarkTheme.collectAsStateWithLifecycle()

    // Upload state
    val uploadUri by viewModel.uploadImageUri.collectAsStateWithLifecycle()
    val uploadTitle by viewModel.uploadTitle.collectAsStateWithLifecycle()
    val uploadCategory by viewModel.uploadCategory.collectAsStateWithLifecycle()
    val uploadAuthor by viewModel.uploadAuthor.collectAsStateWithLifecycle()
    val uploadTags by viewModel.uploadTags.collectAsStateWithLifecycle()
    val uploadColorHex by viewModel.uploadColorHex.collectAsStateWithLifecycle()
    val isUploading by viewModel.isUploading.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    // Handle back press
    BackHandler(enabled = currentTab != "explore" && selectedWallpaper == null) {
        viewModel.setTab("explore")
    }

    // React to action results with Snackbar
    LaunchedEffect(actionState) {
        when (val state = actionState) {
            is ActionState.Success -> {
                snackbarHostState.showSnackbar(state.message)
                viewModel.clearActionState()
            }
            is ActionState.Error -> {
                snackbarHostState.showSnackbar(state.message)
                viewModel.clearActionState()
            }
            else -> {}
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            bottomBar = {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 8.dp,
                    modifier = Modifier.testTag("bottom_nav_bar")
                ) {
                    NavigationBarItem(
                        selected = currentTab == "explore",
                        onClick = { viewModel.setTab("explore") },
                        icon = {
                            Icon(
                                imageVector = if (currentTab == "explore") Icons.Filled.Explore else Icons.Outlined.Explore,
                                contentDescription = "Explore"
                            )
                        },
                        label = { Text("Explore", fontSize = 11.sp, fontWeight = FontWeight.Medium) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                        ),
                        modifier = Modifier.testTag("nav_explore")
                    )

                    NavigationBarItem(
                        selected = currentTab == "categories",
                        onClick = { viewModel.setTab("categories") },
                        icon = {
                            Icon(
                                imageVector = if (currentTab == "categories") Icons.Filled.Category else Icons.Outlined.Category,
                                contentDescription = "Categories"
                            )
                        },
                        label = { Text("Categories", fontSize = 11.sp, fontWeight = FontWeight.Medium) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                        ),
                        modifier = Modifier.testTag("nav_categories")
                    )

                    NavigationBarItem(
                        selected = currentTab == "upload",
                        onClick = { viewModel.setTab("upload") },
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.AddCircle,
                                contentDescription = "Upload",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        label = { Text("Upload", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                        ),
                        modifier = Modifier.testTag("nav_upload")
                    )

                    NavigationBarItem(
                        selected = currentTab == "favorites",
                        onClick = { viewModel.setTab("favorites") },
                        icon = {
                            Icon(
                                imageVector = if (currentTab == "favorites") Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                contentDescription = "Favorites",
                                tint = if (currentTab == "favorites") Color(0xFFEF4444) else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        },
                        label = { Text("Collection", fontSize = 11.sp, fontWeight = FontWeight.Medium) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFFEF4444),
                            indicatorColor = Color(0xFFEF4444).copy(alpha = 0.15f)
                        ),
                        modifier = Modifier.testTag("nav_favorites")
                    )
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (currentTab) {
                    "explore" -> {
                        ExploreScreen(
                            wallpapers = filteredWallpapers,
                            dailyFeatured = dailyFeatured,
                            favoriteIds = favoriteIds,
                            searchQuery = searchQuery,
                            onSearchQueryChange = { viewModel.searchQuery.value = it },
                            selectedCategory = selectedCategory,
                            onSelectCategory = { viewModel.selectedCategory.value = it },
                            sortOption = sortOption,
                            onSelectSortOption = { viewModel.sortOption.value = it },
                            onWallpaperClick = { viewModel.openWallpaperDetail(it) },
                            onFavoriteClick = { viewModel.toggleFavorite(it) },
                            onDownload = { viewModel.downloadHighRes(it) },
                            onShare = { viewModel.shareWallpaper(it) },
                            isDarkTheme = isDarkTheme,
                            onToggleTheme = { viewModel.isDarkTheme.value = !isDarkTheme },
                            onResetFilters = { viewModel.clearFilters() }
                        )
                    }

                    "categories" -> {
                        CategoriesScreen(
                            categories = viewModel.categories,
                            onSelectCategory = { viewModel.selectCategory(it) }
                        )
                    }

                    "upload" -> {
                        UploadScreen(
                            imageUri = uploadUri,
                            onImagePicked = { viewModel.onImagePicked(it) },
                            title = uploadTitle,
                            onTitleChange = { viewModel.uploadTitle.value = it },
                            category = uploadCategory,
                            onCategoryChange = { viewModel.uploadCategory.value = it },
                            author = uploadAuthor,
                            onAuthorChange = { viewModel.uploadAuthor.value = it },
                            tags = uploadTags,
                            onTagsChange = { viewModel.uploadTags.value = it },
                            selectedColorHex = uploadColorHex,
                            onColorHexChange = { viewModel.uploadColorHex.value = it },
                            isUploading = isUploading,
                            onSubmit = { onSuccess -> viewModel.submitUpload(onSuccess) },
                            onUploadSuccess = { item ->
                                viewModel.setTab("favorites")
                                viewModel.openWallpaperDetail(item)
                            }
                        )
                    }

                    "favorites" -> {
                        FavoritesScreen(
                            favorites = favoriteWallpapers,
                            userUploads = userUploads,
                            favoriteIds = favoriteIds,
                            onWallpaperClick = { viewModel.openWallpaperDetail(it) },
                            onFavoriteClick = { viewModel.toggleFavorite(it) },
                            onNavigateToExplore = { viewModel.setTab("explore") },
                            onNavigateToUpload = { viewModel.setTab("upload") }
                        )
                    }
                }
            }
        }

        // Fullscreen Wallpaper Detail Viewer Overlay
        AnimatedVisibility(
            visible = selectedWallpaper != null,
            enter = fadeIn() + slideInVertically(initialOffsetY = { it }),
            exit = fadeOut() + slideOutVertically(targetOffsetY = { it })
        ) {
            selectedWallpaper?.let { wallpaper ->
                WallpaperDetailSheet(
                    wallpaper = wallpaper,
                    isFavorite = favoriteIds.contains(wallpaper.id),
                    mockupType = mockupType,
                    onMockupTypeChange = { viewModel.mockupType.value = it },
                    onClose = { viewModel.closeWallpaperDetail() },
                    onToggleFavorite = { viewModel.toggleFavorite(wallpaper) },
                    onDownload = { viewModel.downloadHighRes(wallpaper) },
                    onApply = { target -> viewModel.applyWallpaper(wallpaper, target) },
                    onShare = { viewModel.shareWallpaper(wallpaper) },
                    onDelete = if (wallpaper.isUserUploaded) {
                        { viewModel.deleteUpload(wallpaper) }
                    } else null,
                    onTagClick = { tag ->
                        viewModel.searchQuery.value = tag.removePrefix("#")
                    }
                )
            }
        }

        // Loading overlay for download/apply operations
        if (actionState is ActionState.Loading) {
            Dialog(onDismissRequest = {}) {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = MaterialTheme.colorScheme.surface,
                    tonalElevation = 8.dp,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Box(
                        modifier = Modifier.padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        androidx.compose.foundation.layout.Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(16.dp)
                        ) {
                            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                            Text(
                                text = (actionState as ActionState.Loading).message,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }
    }
}
