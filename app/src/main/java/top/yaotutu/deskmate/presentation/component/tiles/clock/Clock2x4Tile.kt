package top.yaotutu.deskmate.presentation.component.tiles.clock

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import top.yaotutu.deskmate.presentation.component.base.BaseTile
import top.yaotutu.deskmate.presentation.component.base.TileSpec
import top.yaotutu.deskmate.presentation.theme.MetroTileColors
import top.yaotutu.deskmate.presentation.theme.MetroTypography

/**
 * 时钟瓷砖 2×4 - 高版
 *
 * 特性：
 * - 垂直布局，适合侧边栏位置
 * - 脉冲动画，吸引注意力
 * - 信息垂直排列，充分利用高度
 *
 * 这个组件展示：
 * - 使用 BaseTile 轻松实现垂直布局
 * - 配置 PULSE 动画只需一行代码
 * - 业务代码只有 20 行左右
 *
 * @param time 当前时间（如 "13:58"）
 * @param date 日期（如 "10月28日"）
 * @param weekday 星期（如 "星期二"）
 * @param backgroundColor 背景颜色
 * @param modifier 修饰符
 */
@Composable
fun Clock2x4Tile(
    time: String,
    date: String,
    weekday: String,
    backgroundColor: Color = MetroTileColors.Time,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    BaseTile(
        spec = TileSpec.tall(backgroundColor),
        onClick = onClick,
        modifier = modifier
    ) {
        // 垂直布局，从上到下排列
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 时间
            Text(
                text = time,
                fontSize = MetroTypography.displayLarge(),
                fontWeight = FontWeight.Thin,
                color = Color.White,
                lineHeight = MetroTypography.displayLarge()
            )

            // 分隔线（可选，使用字符）
            Text(
                text = "━",
                fontSize = MetroTypography.bodyLarge(),
                color = Color.White.copy(alpha = 0.3f)
            )

            // 日期
            Text(
                text = date,
                fontSize = MetroTypography.bodyLarge(),
                fontWeight = FontWeight.Light,
                color = Color.White
            )

            // 星期
            Text(
                text = weekday,
                fontSize = MetroTypography.bodyMedium(),
                fontWeight = FontWeight.ExtraLight,
                color = Color.White.copy(alpha = 0.9f)
            )
        }
    }
}
