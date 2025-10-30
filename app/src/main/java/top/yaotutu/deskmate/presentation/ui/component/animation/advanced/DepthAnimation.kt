package top.yaotutu.deskmate.presentation.ui.component.animation.advanced

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import top.yaotutu.deskmate.presentation.ui.theme.MetroDuration
import top.yaotutu.deskmate.presentation.ui.theme.MetroEasing

/**
 * Windows Phone 风格的深度/透视动画（Depth Animation）
 *
 * 通过缩放 + 阴影变化模拟 Z 轴深度感。
 * 瓷砖周期性地"靠近"和"远离"，产生 3D 透视效果。
 *
 * 动画原理：
 * - 使用 scale 模拟 Z 轴移动（放大 = 靠近，缩小 = 远离）
 * - 使用 shadow 强化深度感（靠近时阴影增大）
 * - 可选的轻微 Y 轴偏移增强立体感
 *
 * 效果类似 Windows Phone 8.1 的 Parallax 主题效果。
 *
 * 使用场景：
 * - 照片瓷砖的 3D 展示
 * - 音乐播放器的专辑封面动画
 * - 强调性内容的视觉吸引
 * - 视差滚动效果
 *
 * 使用示例：
 * ```
 * // 照片瓷砖的深度效果
 * DepthTileAnimation(
 *     enabled = true,
 *     scaleRange = 0.95f to 1.05f,  // 缩放 95% ~ 105%
 *     shadowRange = 2f to 8f,        // 阴影 2dp ~ 8dp
 *     depthDuration = 3000           // 3秒一个周期
 * ) {
 *     Box(
 *         Modifier
 *             .fillMaxSize()
 *             .background(Color(0xFF0078D7))
 *             .padding(16.dp),
 *         contentAlignment = Alignment.Center
 *     ) {
 *         Text("📷", fontSize = 64.sp)
 *         Text("照片", fontSize = 20.sp, fontWeight = FontWeight.Light, color = Color.White)
 *     }
 * }
 * ```
 *
 * @param enabled 是否启用动画（默认 true）
 * @param scaleRange 缩放范围（最小值 to 最大值，默认 0.95f to 1.05f）
 * @param shadowRange 阴影范围（最小值 to 最大值，默认 2f to 8f，单位 dp）
 * @param translateYRange Y轴偏移范围（可选，默认 -4f to 4f，单位 dp）
 * @param depthDuration 动画周期时长（毫秒，默认 3000ms）
 * @param modifier 修饰符
 * @param content 内容
 */
@Composable
fun DepthTileAnimation(
    enabled: Boolean = true,
    scaleRange: Pair<Float, Float> = 0.95f to 1.05f,
    shadowRange: Pair<Float, Float> = 2f to 8f,
    translateYRange: Pair<Float, Float> = -4f to 4f,
    depthDuration: Int = 3000,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "depth_transition")

    // 缩放动画（模拟 Z 轴移动）
    val scale by infiniteTransition.animateFloat(
        initialValue = scaleRange.first,
        targetValue = scaleRange.second,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = depthDuration / 2,
                easing = MetroEasing.EaseInOut
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "depth_scale"
    )

    // 阴影大小动画（强化深度感）
    val shadowElevation by infiniteTransition.animateFloat(
        initialValue = shadowRange.first,
        targetValue = shadowRange.second,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = depthDuration / 2,
                easing = MetroEasing.EaseInOut
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "depth_shadow"
    )

    // Y 轴偏移动画（可选，增强立体感）
    val translateY by infiniteTransition.animateFloat(
        initialValue = translateYRange.first,
        targetValue = translateYRange.second,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = depthDuration / 2,
                easing = MetroEasing.EaseInOut
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "depth_translateY"
    )

    Box(
        modifier = modifier
            .shadow(
                elevation = if (enabled) shadowElevation.dp else shadowRange.first.dp,
                spotColor = androidx.compose.ui.graphics.Color.Black.copy(alpha = 0.3f)
            )
            .graphicsLayer {
                if (enabled) {
                    scaleX = scale
                    scaleY = scale
                    translationY = translateY
                }
            }
    ) {
        content()
    }
}
