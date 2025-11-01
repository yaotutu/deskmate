package top.yaotutu.deskmate.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

/**
 * Metro 主题系统
 *
 * 支持深色和浅色两种主题模式
 */

@Stable
data class MetroTheme(
    val isDark: Boolean,
    val background: Color,
    val surface: Color,
    val onSurface: Color,
    val primary: Color,
    val secondary: Color,
    val tertiary: Color
) {
    companion object {
        /**
         * 深色主题（默认）- 经典 Metro 风格
         */
        val Dark = MetroTheme(
            isDark = true,
            background = Color(0xFF000000),      // 纯黑背景
            surface = Color(0xFF1E1E1E),         // 深灰表面
            onSurface = Color(0xFFFFFFFF),       // 白色文本
            primary = MetroColors.Blue,          // 主色调
            secondary = MetroColors.Purple,      // 次要色
            tertiary = MetroColors.Orange        // 第三色
        )

        /**
         * 浅色主题 - 现代化改良
         */
        val Light = MetroTheme(
            isDark = false,
            background = Color(0xFFFFFFFF),      // 白色背景
            surface = Color(0xFFF5F5F5),         // 浅灰表面
            onSurface = Color(0xFF000000),       // 黑色文本
            primary = MetroColors.Blue,          // 主色调
            secondary = MetroColors.Purple,      // 次要色
            tertiary = MetroColors.Orange        // 第三色
        )

        /**
         * 高对比度深色主题 - 无障碍优化
         */
        val HighContrastDark = MetroTheme(
            isDark = true,
            background = Color(0xFF000000),
            surface = Color(0xFF000000),
            onSurface = Color(0xFFFFFFFF),
            primary = Color(0xFF00B7FF),         // 更亮的蓝色
            secondary = Color(0xFFD000FF),       // 更亮的紫色
            tertiary = Color(0xFFFFAA00)         // 更亮的橙色
        )

        /**
         * 高对比度浅色主题 - 无障碍优化
         */
        val HighContrastLight = MetroTheme(
            isDark = false,
            background = Color(0xFFFFFFFF),
            surface = Color(0xFFFFFFFF),
            onSurface = Color(0xFF000000),
            primary = Color(0xFF0050C8),         // 更深的蓝色
            secondary = Color(0xFF8000C8),       // 更深的紫色
            tertiary = Color(0xFFE67300)         // 更深的橙色
        )
    }

    /**
     * 切换主题
     */
    fun toggle(): MetroTheme {
        return if (isDark) Light else Dark
    }
}

/**
 * 主题配置选项
 */
enum class MetroThemeOption {
    DARK,              // 深色主题
    LIGHT,             // 浅色主题
    HIGH_CONTRAST_DARK,    // 高对比度深色
    HIGH_CONTRAST_LIGHT,   // 高对比度浅色
    SYSTEM             // 跟随系统
}

/**
 * 根据主题选项获取主题
 */
fun getMetroTheme(option: MetroThemeOption, isSystemDark: Boolean = true): MetroTheme {
    return when (option) {
        MetroThemeOption.DARK -> MetroTheme.Dark
        MetroThemeOption.LIGHT -> MetroTheme.Light
        MetroThemeOption.HIGH_CONTRAST_DARK -> MetroTheme.HighContrastDark
        MetroThemeOption.HIGH_CONTRAST_LIGHT -> MetroTheme.HighContrastLight
        MetroThemeOption.SYSTEM -> if (isSystemDark) MetroTheme.Dark else MetroTheme.Light
    }
}

/**
 * 瓷砖主题适配
 *
 * 根据主题调整瓷砖颜色的亮度和对比度
 */
object MetroTileTheme {
    /**
     * 为深色主题调整瓷砖颜色（保持高饱和度）
     */
    fun forDarkTheme(baseColor: Color): Color {
        return baseColor
    }

    /**
     * 为浅色主题调整瓷砖颜色（略微降低亮度）
     */
    fun forLightTheme(baseColor: Color): Color {
        // 对于浅色主题，稍微降低颜色亮度以确保可读性
        return baseColor.copy(
            red = baseColor.red * 0.85f,
            green = baseColor.green * 0.85f,
            blue = baseColor.blue * 0.85f
        )
    }

    /**
     * 根据主题自动调整颜色
     */
    fun adjust(baseColor: Color, theme: MetroTheme): Color {
        return if (theme.isDark) {
            forDarkTheme(baseColor)
        } else {
            forLightTheme(baseColor)
        }
    }
}

/**
 * Metro 瓷砖在不同主题下的配色
 */
@Stable
data class TileColorScheme(
    val background: Color,
    val content: Color,
    val accent: Color
) {
    companion object {
        /**
         * 为指定主题生成瓷砖配色方案
         */
        fun create(baseColor: Color, theme: MetroTheme): TileColorScheme {
            val adjustedBackground = MetroTileTheme.adjust(baseColor, theme)

            return TileColorScheme(
                background = adjustedBackground,
                content = if (theme.isDark) Color.White else Color.White,
                accent = if (theme.isDark) Color.White.copy(alpha = 0.9f) else Color.White.copy(alpha = 0.95f)
            )
        }
    }
}
