package top.yaotutu.deskmate.presentation.ui.component.tiles.clock

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import top.yaotutu.deskmate.presentation.ui.component.base.BaseTile
import top.yaotutu.deskmate.presentation.ui.component.base.TileSpec
import top.yaotutu.deskmate.presentation.ui.theme.MetroTileColors

/**
 * 紧凑时钟瓷砖 (2×1)
 *
 * 特性：
 * - 横条布局，高度只有一个单元格
 * - 适合作为快捷信息条
 * - 时间和日期横向排列
 *
 * 这个组件展示：
 * - BaseTile 支持任意尺寸配置
 * - 极简业务代码（< 15 行）
 * - 无动画的静态显示
 *
 * @param time 当前时间（如 "13:58"）
 * @param date 简短日期（如 "10/28"）
 * @param backgroundColor 背景颜色
 * @param modifier 修饰符
 */
@Composable
fun ClockCompactTile(
    time: String,
    date: String,
    backgroundColor: Color = MetroTileColors.Time,
    modifier: Modifier = Modifier
) {
    BaseTile(
        spec = TileSpec(2, 1, backgroundColor),  // 自定义尺寸 2×1
        modifier = modifier
    ) {
        // 横向排列
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 时间
            Text(
                text = time,
                fontSize = 32.sp,
                fontWeight = FontWeight.Thin,
                color = Color.White
            )

            // 分隔符
            Text(
                text = "|",
                fontSize = 24.sp,
                color = Color.White.copy(alpha = 0.4f)
            )

            // 日期
            Text(
                text = date,
                fontSize = 18.sp,
                fontWeight = FontWeight.Light,
                color = Color.White.copy(alpha = 0.9f)
            )
        }
    }
}
