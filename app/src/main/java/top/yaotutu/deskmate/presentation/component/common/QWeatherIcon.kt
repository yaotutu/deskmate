package top.yaotutu.deskmate.presentation.component.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest

/**
 * 和风天气图标组件
 *
 * 从本地 assets 目录加载和风天气官方 SVG 图标。
 * 使用 Coil 库进行图片加载，支持 SVG 渲染和着色。
 *
 * 图标来源：和风天气官方图标库（线条版本）
 * 存放位置：app/src/main/assets/weather_icons/
 * 官方文档：https://icons.qweather.com/
 *
 * @param iconCode 和风天气图标代码（如 "100" = 晴天，"101" = 多云，"104" = 阴天）
 * @param size 图标尺寸（默认 64.dp）
 * @param contentDescription 无障碍描述（默认为图标代码）
 * @param modifier 修饰符
 *
 * 使用示例：
 * ```kotlin
 * QWeatherIcon(
 *     iconCode = weatherData.iconCode,
 *     size = 80.dp,
 *     contentDescription = weatherData.condition
 * )
 * ```
 */
@Composable
fun QWeatherIcon(
    iconCode: String,
    size: Dp = 64.dp,
    contentDescription: String? = iconCode,
    modifier: Modifier = Modifier
) {
    // 使用本地 assets 目录的 SVG 文件
    val iconPath = "file:///android_asset/weather_icons/$iconCode.svg"

    // 调试日志
    timber.log.Timber.d("QWeatherIcon - 加载本地图标: $iconPath")

    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = iconPath,  // 从 assets 加载
            contentDescription = contentDescription,
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(Color.White),  // 给SVG着色为白色
            modifier = Modifier.size(size),
            onError = { error ->
                timber.log.Timber.e("QWeatherIcon - 加载失败: ${error.result.throwable?.message}")
            },
            onSuccess = {
                timber.log.Timber.d("QWeatherIcon - 加载成功: $iconCode")
            }
        )
    }
}
