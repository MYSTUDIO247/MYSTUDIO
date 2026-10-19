package com.example.data.repository

import com.example.data.model.CategoryItem
import com.example.data.model.WallpaperItem

object CuratedWallpapers {

    val categories = listOf(
        CategoryItem(
            id = "nature",
            name = "Nature",
            count = 1420,
            previewUrl = "https://images.unsplash.com/photo-1531366936337-7c912a4589a7?auto=format&fit=crop&w=1080&q=80",
            iconName = "nature",
            description = "Fjords, aurora skies, mist valleys, & alpine peaks"
        ),
        CategoryItem(
            id = "transport",
            name = "Transport",
            count = 980,
            previewUrl = "https://images.unsplash.com/photo-1617788138017-80ad40651399?auto=format&fit=crop&w=1080&q=80",
            iconName = "transport",
            description = "Exotic hypercars, supersonic jets, & neon speed machines"
        ),
        CategoryItem(
            id = "abstract",
            name = "Abstract Art",
            count = 1650,
            previewUrl = "https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?auto=format&fit=crop&w=1080&q=80",
            iconName = "abstract",
            description = "Fluid holographic prisms, liquid waves, & geometry"
        ),
        CategoryItem(
            id = "space",
            name = "Space",
            count = 840,
            previewUrl = "https://images.unsplash.com/photo-1451187580459-43490279c0fa?auto=format&fit=crop&w=1080&q=80",
            iconName = "space",
            description = "Cosmic nebulas, planetary rings, & stellar galaxies"
        ),
        CategoryItem(
            id = "amoled",
            name = "AMOLED",
            count = 1120,
            previewUrl = "https://images.unsplash.com/photo-1507608869274-d3177c8bb4c7?auto=format&fit=crop&w=1080&q=80",
            iconName = "amoled",
            description = "True pitch black backgrounds with vivid accent lights"
        ),
        CategoryItem(
            id = "minimalist",
            name = "Minimalist",
            count = 760,
            previewUrl = "https://images.unsplash.com/photo-1513542789411-b6a5d4f31634?auto=format&fit=crop&w=1080&q=80",
            iconName = "minimalist",
            description = "Clean aesthetic balance, calming tones, & zen forms"
        ),
        CategoryItem(
            id = "gods",
            name = "God & Spiritual",
            count = 2500,
            previewUrl = "android.resource://com.aistudio.worldwallpaper.vpxrn/drawable/img_god_shiva",
            iconName = "spiritual",
            description = "Lord Shiva, Vishnu, Ram, Hanuman & divine sacred art"
        ),
        CategoryItem(
            id = "architecture",
            name = "Architecture",
            count = 890,
            previewUrl = "https://images.unsplash.com/photo-1503899036084-c55cdd92da26?auto=format&fit=crop&w=1080&q=80",
            iconName = "architecture",
            description = "Cyberpunk cityscapes, brutalist structures, & modern glass"
        )
    )

    val items = listOf(
        // God & Spiritual Wallpapers
        WallpaperItem(
            id = "god_shiva_01",
            title = "Lord Shiva Mahadev in Meditation",
            category = "God & Spiritual",
            imageUrl = "android.resource://com.aistudio.worldwallpaper.vpxrn/drawable/img_god_shiva",
            highResUrl = "android.resource://com.aistudio.worldwallpaper.vpxrn/drawable/img_god_shiva",
            author = "Vedic Sacred Art",
            authorBio = "Himalayan sacred masterwork & Kailash meditation",
            tags = listOf("#shiva", "#mahadev", "#bholenath", "#god", "#spiritual", "#harharmahadev", "#kailash"),
            colorHex = "#06B6D4",
            resolution = "3840 x 2160 (4K UHD Divine)",
            fileSize = "5.1 MB",
            views = 68400,
            likes = 29400,
            downloads = 21200
        ),
        WallpaperItem(
            id = "god_vishnu_01",
            title = "Lord Vishnu Narayana Cosmic Ocean",
            category = "God & Spiritual",
            imageUrl = "android.resource://com.aistudio.worldwallpaper.vpxrn/drawable/img_god_vishnu",
            highResUrl = "android.resource://com.aistudio.worldwallpaper.vpxrn/drawable/img_god_vishnu",
            author = "Vedic Sacred Art",
            authorBio = "Kshira Sagara cosmic preserver & Sheshnaag divine throne",
            tags = listOf("#vishnu", "#narayana", "#sheshnaag", "#god", "#devotional", "#spiritual", "#omnamonarayana"),
            colorHex = "#F59E0B",
            resolution = "3840 x 2160 (4K UHD Divine)",
            fileSize = "5.4 MB",
            views = 59100,
            likes = 24300,
            downloads = 17800
        ),
        WallpaperItem(
            id = "god_ram_01",
            title = "Lord Ram Maryada Purushottam Ayodhya",
            category = "God & Spiritual",
            imageUrl = "android.resource://com.aistudio.worldwallpaper.vpxrn/drawable/img_god_ram",
            highResUrl = "android.resource://com.aistudio.worldwallpaper.vpxrn/drawable/img_god_ram",
            author = "Vedic Sacred Art",
            authorBio = "Ayodhya golden royal temple & Kodanda divine archer",
            tags = listOf("#ram", "#shriram", "#ayodhya", "#jaishriram", "#god", "#spiritual", "#ramji"),
            colorHex = "#F59E0B",
            resolution = "3840 x 2160 (4K UHD Divine)",
            fileSize = "5.2 MB",
            views = 84200,
            likes = 37800,
            downloads = 28900
        ),
        WallpaperItem(
            id = "god_hanuman_01",
            title = "Lord Hanuman Sankat Mochan Mahabali",
            category = "God & Spiritual",
            imageUrl = "android.resource://com.aistudio.worldwallpaper.vpxrn/drawable/img_god_hanuman",
            highResUrl = "android.resource://com.aistudio.worldwallpaper.vpxrn/drawable/img_god_hanuman",
            author = "Vedic Sacred Art",
            authorBio = "Sanjeevani mountain lifter & eternal devotee of Shri Ram",
            tags = listOf("#hanuman", "#bajrangbali", "#sankatmochan", "#god", "#spiritual", "#jaihanuman", "#mahabali"),
            colorHex = "#F97316",
            resolution = "3840 x 2160 (4K UHD Divine)",
            fileSize = "5.3 MB",
            views = 91400,
            likes = 42100,
            downloads = 32400
        ),

        // Daily Featured
        WallpaperItem(
            id = "daily_featured_01",
            title = "Aurora Over Nordic Fjords",
            category = "Nature",
            imageUrl = "https://images.unsplash.com/photo-1531366936337-7c912a4589a7?auto=format&fit=crop&w=1440&q=85",
            highResUrl = "https://images.unsplash.com/photo-1531366936337-7c912a4589a7?auto=format&fit=crop&w=3840&q=95",
            author = "Elena Lindqvist",
            authorBio = "Arctic wilderness photographer based in Tromsø, Norway",
            tags = listOf("#aurora", "#nordic", "#nature", "#4k", "#mountains"),
            colorHex = "#06B6D4",
            resolution = "3840 x 2160 (4K UHD)",
            fileSize = "6.2 MB",
            isDailyFeatured = true,
            views = 48290,
            likes = 12450,
            downloads = 8390
        ),
        // Nature
        WallpaperItem(
            id = "nature_01",
            title = "Alps Crimson Sunset Peak",
            category = "Nature",
            imageUrl = "https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?auto=format&fit=crop&w=1440&q=85",
            highResUrl = "https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?auto=format&fit=crop&w=3840&q=95",
            author = "Marco Bellini",
            authorBio = "High alpine mountaineer and landscape capturer",
            tags = listOf("#sunset", "#alps", "#snow", "#epic", "#hiking"),
            colorHex = "#F59E0B",
            resolution = "4096 x 2160 (Cinema 4K)",
            fileSize = "5.8 MB",
            views = 28100,
            likes = 7830,
            downloads = 4120
        ),
        WallpaperItem(
            id = "nature_02",
            title = "Emerald Cascade in Rainforest",
            category = "Nature",
            imageUrl = "https://images.unsplash.com/photo-1432405972618-c60b0225b8f9?auto=format&fit=crop&w=1440&q=85",
            highResUrl = "https://images.unsplash.com/photo-1432405972618-c60b0225b8f9?auto=format&fit=crop&w=3840&q=95",
            author = "Kai Tanaka",
            authorBio = "Tropical forest eco-documentary photographer",
            tags = listOf("#waterfall", "#jungle", "#emerald", "#calm"),
            colorHex = "#10B981",
            resolution = "3840 x 2160 (4K)",
            fileSize = "4.9 MB",
            views = 19400,
            likes = 5420,
            downloads = 2950
        ),
        WallpaperItem(
            id = "nature_03",
            title = "Sahara Golden Dunes Geometry",
            category = "Nature",
            imageUrl = "https://images.unsplash.com/photo-1509316975850-ff9c5deb0cd9?auto=format&fit=crop&w=1440&q=85",
            highResUrl = "https://images.unsplash.com/photo-1509316975850-ff9c5deb0cd9?auto=format&fit=crop&w=3840&q=95",
            author = "Amir Mansour",
            authorBio = "Desert aerial and golden light explorer",
            tags = listOf("#desert", "#dunes", "#gold", "#minimalist"),
            colorHex = "#D97706",
            resolution = "3840 x 2160 (4K)",
            fileSize = "4.1 MB",
            views = 15200,
            likes = 4310,
            downloads = 2100
        ),
        WallpaperItem(
            id = "nature_04",
            title = "Turquoise Coral Atoll",
            category = "Nature",
            imageUrl = "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?auto=format&fit=crop&w=1440&q=85",
            highResUrl = "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?auto=format&fit=crop&w=3840&q=95",
            author = "Chloe Martin",
            authorBio = "Ocean marine conservationist & drone pilot",
            tags = listOf("#ocean", "#beach", "#blue", "#summer"),
            colorHex = "#0EA5E9",
            resolution = "3840 x 2160 (4K)",
            fileSize = "5.2 MB",
            views = 31200,
            likes = 8900,
            downloads = 5400
        ),

        // Transport
        WallpaperItem(
            id = "transport_01",
            title = "Neon Cyberpunk Hypercar GT",
            category = "Transport",
            imageUrl = "https://images.unsplash.com/photo-1617788138017-80ad40651399?auto=format&fit=crop&w=1440&q=85",
            highResUrl = "https://images.unsplash.com/photo-1617788138017-80ad40651399?auto=format&fit=crop&w=3840&q=95",
            author = "Alexander Voss",
            authorBio = "Supercar photographer and motorsport visualist",
            tags = listOf("#supercar", "#neon", "#speed", "#cyberpunk", "#fast"),
            colorHex = "#6366F1",
            resolution = "3840 x 2160 (4K)",
            fileSize = "6.5 MB",
            views = 39800,
            likes = 11200,
            downloads = 7600
        ),
        WallpaperItem(
            id = "transport_02",
            title = "Shinkansen Bullet Train Tokyo Night",
            category = "Transport",
            imageUrl = "https://images.unsplash.com/photo-1509749837427-ac94a2553d0e?auto=format&fit=crop&w=1440&q=85",
            highResUrl = "https://images.unsplash.com/photo-1509749837427-ac94a2553d0e?auto=format&fit=crop&w=3840&q=95",
            author = "Kenji Sato",
            authorBio = "Rail enthusiast and urban transit photographer",
            tags = listOf("#train", "#japan", "#tokyo", "#speed", "#railway"),
            colorHex = "#3B82F6",
            resolution = "3840 x 2160 (4K)",
            fileSize = "5.1 MB",
            views = 24600,
            likes = 6720,
            downloads = 3890
        ),
        WallpaperItem(
            id = "transport_03",
            title = "Vintage Café Racer by Sunset Coast",
            category = "Transport",
            imageUrl = "https://images.unsplash.com/photo-1558981403-c5f9899a28bc?auto=format&fit=crop&w=1440&q=85",
            highResUrl = "https://images.unsplash.com/photo-1558981403-c5f9899a28bc?auto=format&fit=crop&w=3840&q=95",
            author = "David Brooks",
            authorBio = "Custom motorcycle builder & road trip storyteller",
            tags = listOf("#motorcycle", "#vintage", "#biker", "#classic"),
            colorHex = "#EC4899",
            resolution = "3840 x 2160 (4K)",
            fileSize = "4.7 MB",
            views = 18900,
            likes = 4920,
            downloads = 2650
        ),
        WallpaperItem(
            id = "transport_04",
            title = "Stratojet Above Sunset Sea of Clouds",
            category = "Transport",
            imageUrl = "https://images.unsplash.com/photo-1436491865332-7a61a109cc05?auto=format&fit=crop&w=1440&q=85",
            highResUrl = "https://images.unsplash.com/photo-1436491865332-7a61a109cc05?auto=format&fit=crop&w=3840&q=95",
            author = "Captain James Rivera",
            authorBio = "Commercial pilot with a passion for sky photography",
            tags = listOf("#aviation", "#airplane", "#clouds", "#sunset", "#wing"),
            colorHex = "#F59E0B",
            resolution = "3840 x 2160 (4K)",
            fileSize = "5.3 MB",
            views = 27400,
            likes = 7300,
            downloads = 4500
        ),

        // Abstract Art
        WallpaperItem(
            id = "abstract_01",
            title = "Iridescent Holographic Waves",
            category = "Abstract Art",
            imageUrl = "https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?auto=format&fit=crop&w=1440&q=85",
            highResUrl = "https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?auto=format&fit=crop&w=3840&q=95",
            author = "Soren Vane",
            authorBio = "3D generative digital artist and shader programmer",
            tags = listOf("#abstract", "#3d", "#iridescent", "#fluid", "#waves"),
            colorHex = "#8B5CF6",
            resolution = "3840 x 2160 (4K UHD)",
            fileSize = "6.8 MB",
            views = 42100,
            likes = 13900,
            downloads = 9100
        ),
        WallpaperItem(
            id = "abstract_02",
            title = "Cosmic Fluid Swirls in Turquoise",
            category = "Abstract Art",
            imageUrl = "https://images.unsplash.com/photo-1541701494587-cb58502866ab?auto=format&fit=crop&w=1440&q=85",
            highResUrl = "https://images.unsplash.com/photo-1541701494587-cb58502866ab?auto=format&fit=crop&w=3840&q=95",
            author = "Astrid Lindau",
            authorBio = "Experimental fluid artist blending alcohol inks",
            tags = listOf("#fluid", "#acrylic", "#turquoise", "#colorful"),
            colorHex = "#06B6D4",
            resolution = "3840 x 2160 (4K)",
            fileSize = "5.9 MB",
            views = 33800,
            likes = 9420,
            downloads = 5820
        ),
        WallpaperItem(
            id = "abstract_03",
            title = "Prism Light Refraction Spectrum",
            category = "Abstract Art",
            imageUrl = "https://images.unsplash.com/photo-1550684848-fac1c5b4e853?auto=format&fit=crop&w=1440&q=85",
            highResUrl = "https://images.unsplash.com/photo-1550684848-fac1c5b4e853?auto=format&fit=crop&w=3840&q=95",
            author = "Noah Thorne",
            authorBio = "Optics researcher and light-art sculptor",
            tags = listOf("#prism", "#spectrum", "#rainbow", "#modern"),
            colorHex = "#EC4899",
            resolution = "3840 x 2160 (4K)",
            fileSize = "4.9 MB",
            views = 21700,
            likes = 6180,
            downloads = 3400
        ),

        // Space
        WallpaperItem(
            id = "space_01",
            title = "Orion Nebula Deep Starfield",
            category = "Space",
            imageUrl = "https://images.unsplash.com/photo-1451187580459-43490279c0fa?auto=format&fit=crop&w=1440&q=85",
            highResUrl = "https://images.unsplash.com/photo-1451187580459-43490279c0fa?auto=format&fit=crop&w=3840&q=95",
            author = "NASA Hubble Archive",
            authorBio = "Deep field astronomical observatory data",
            tags = listOf("#space", "#nebula", "#stars", "#cosmic", "#galaxy"),
            colorHex = "#3B82F6",
            resolution = "3840 x 2160 (4K)",
            fileSize = "6.1 MB",
            views = 36700,
            likes = 10890,
            downloads = 7200
        ),
        WallpaperItem(
            id = "space_02",
            title = "Milky Way Over Silhouetted Mountain",
            category = "Space",
            imageUrl = "https://images.unsplash.com/photo-1506703719100-a0f3a48c0f86?auto=format&fit=crop&w=1440&q=85",
            highResUrl = "https://images.unsplash.com/photo-1506703719100-a0f3a48c0f86?auto=format&fit=crop&w=3840&q=95",
            author = "Benjamin Davis",
            authorBio = "Nightscape astrophotographer chasing dark skies",
            tags = listOf("#milkyway", "#stargazing", "#night", "#sky"),
            colorHex = "#8B5CF6",
            resolution = "3840 x 2160 (4K)",
            fileSize = "5.6 MB",
            views = 29400,
            likes = 8120,
            downloads = 5100
        ),

        // AMOLED
        WallpaperItem(
            id = "amoled_01",
            title = "Midnight Eclipse Corona",
            category = "AMOLED",
            imageUrl = "https://images.unsplash.com/photo-1507608869274-d3177c8bb4c7?auto=format&fit=crop&w=1440&q=85",
            highResUrl = "https://images.unsplash.com/photo-1507608869274-d3177c8bb4c7?auto=format&fit=crop&w=3840&q=95",
            author = "Lucas Croft",
            authorBio = "Minimalist dark mode wallpaper enthusiast",
            tags = listOf("#amoled", "#dark", "#pureblack", "#minimal", "#oled"),
            colorHex = "#10B981",
            resolution = "3840 x 2160 (True OLED 4K)",
            fileSize = "3.8 MB",
            views = 41200,
            likes = 12700,
            downloads = 8900
        ),

        // Minimalist
        WallpaperItem(
            id = "minimalist_01",
            title = "Zen Geometry & Pastel Shadows",
            category = "Minimalist",
            imageUrl = "https://images.unsplash.com/photo-1513542789411-b6a5d4f31634?auto=format&fit=crop&w=1440&q=85",
            highResUrl = "https://images.unsplash.com/photo-1513542789411-b6a5d4f31634?auto=format&fit=crop&w=3840&q=95",
            author = "Yuki Morita",
            authorBio = "Architectural minimalist focusing on calm spaces",
            tags = listOf("#minimal", "#pastel", "#zen", "#calm", "#clean"),
            colorHex = "#EC4899",
            resolution = "3840 x 2160 (4K)",
            fileSize = "3.4 MB",
            views = 22100,
            likes = 5900,
            downloads = 3200
        ),

        // Architecture
        WallpaperItem(
            id = "architecture_01",
            title = "Tokyo Shinjuku Cyber Rain",
            category = "Architecture",
            imageUrl = "https://images.unsplash.com/photo-1503899036084-c55cdd92da26?auto=format&fit=crop&w=1440&q=85",
            highResUrl = "https://images.unsplash.com/photo-1503899036084-c55cdd92da26?auto=format&fit=crop&w=3840&q=95",
            author = "Tatsuya Endo",
            authorBio = "Street & neon night photographer in Tokyo",
            tags = listOf("#tokyo", "#city", "#neon", "#cyberpunk", "#rain"),
            colorHex = "#EC4899",
            resolution = "3840 x 2160 (4K)",
            fileSize = "5.7 MB",
            views = 35600,
            likes = 10100,
            downloads = 6800
        )
    )
}
