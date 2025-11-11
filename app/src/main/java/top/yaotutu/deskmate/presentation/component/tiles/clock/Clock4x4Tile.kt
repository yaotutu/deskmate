package top.yaotutu.deskmate.presentation.component.tiles.clock

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import top.yaotutu.deskmate.presentation.component.base.BaseTile
import top.yaotutu.deskmate.presentation.component.base.TileSpec
import top.yaotutu.deskmate.presentation.theme.MetroTileColors
import top.yaotutu.deskmate.presentation.theme.MetroTypography
import top.yaotutu.deskmate.presentation.theme.MetroSpacing

/**
 * 时钟瓷砖 4×4 - 大型版
 *
 * 特性：
 * - 最大尺寸，信息最丰富
 * - 翻转动画：正面显示超大时间，背面显示完整日期信息
 * - 适用于主屏幕焦点位置
 *
 * 这个组件展示了新架构的优势：
 * - 只用 BaseTile 配置尺寸和动画
 * - 无需关心布局参数（LocalBaseCellSize、LocalDynamicGap）
 * - 代码极简，只关注内容排版
 *
 * @param time 当前时间（如 "13:58"）
 * @param date 日期（如 "2025年10月28日"）
 * @param weekday 星期（如 "星期二"）
 * @param lunarDate 农历（如 "农历腊月廿九"）
 * @param backgroundColor 背景颜色
 * @param modifier 修饰符
 */
@Composable
fun Clock4x4Tile(
    time: String,
    date: String,
    weekday: String,
    lunarDate: String,
    backgroundColor: Color = MetroTileColors.Time,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    BaseTile(
        spec = TileSpec.large(backgroundColor),
        onClick = onClick,
        modifier = modifier
    ) {
        flip(
            front = {
                // 正面：超大时间显示
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // 超大时间
                    Text(
                        text = time,
                        fontSize = MetroTypography.displayHuge(),
                        fontWeight = FontWeight.Thin,
                        color = Color.White,
                        lineHeight = MetroTypography.displayHuge()
                    )

                    Spacer(modifier = Modifier.height(MetroSpacing.large()))

                    // 星期
                    Text(
                        text = weekday,
                        fontSize = MetroTypography.bodyLarge(),
                        fontWeight = FontWeight.Light,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            },
            back = {
                // 背面：完整日期信息
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(MetroSpacing.medium(), Alignment.CenterVertically),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // 阳历日期
                    Text(
                        text = date,
                        fontSize = MetroTypography.titleLarge(),
                        fontWeight = FontWeight.Light,
                        color = Color.White
                    )

                    // 农历日期
                    Text(
                        text = lunarDate,
                        fontSize = MetroTypography.bodyLarge(),
                        fontWeight = FontWeight.ExtraLight,
                        color = Color.White.copy(alpha = 0.9f)
                    )

                    // 星期
                    Text(
                        text = weekday,
                        fontSize = MetroTypography.bodyLarge(),
                        fontWeight = FontWeight.ExtraLight,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
            }
        )
    }
}
