package top.yaotutu.deskmate.presentation.ui.component.animation.special

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import top.yaotutu.deskmate.presentation.ui.theme.MetroDuration
import top.yaotutu.deskmate.presentation.ui.theme.MetroEasing

/**
 * 数字滚动动画
 *
 * @param targetValue 目标数值
 * @param durationMillis 动画持续时间
 * @param modifier 修饰符
 */
@Composable
fun CounterAnimation(
    targetValue: Int,
    durationMillis: Int = MetroDuration.SLOW,
    modifier: Modifier = Modifier,
    content: @Composable (Int) -> Unit
) {
    // 使用 Metro 标准缓动曲线
    val animatedValue by animateIntAsState(
        targetValue = targetValue,
        animationSpec = tween(
            durationMillis = durationMillis,
            easing = MetroEasing.Standard
        ),
        label = "counter_value"
    )

    Box(modifier = modifier) {
        content(animatedValue)
    }
}
