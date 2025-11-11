package top.yaotutu.deskmate.presentation.component.tiles.calendar

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
import top.yaotutu.deskmate.presentation.component.base.LocalTileBaseUnit
import top.yaotutu.deskmate.presentation.component.base.TileSpec
import top.yaotutu.deskmate.presentation.theme.MetroTileColors
import top.yaotutu.deskmate.presentation.theme.MetroTypography
import top.yaotutu.deskmate.presentation.theme.MetroSpacing
import top.yaotutu.deskmate.presentation.theme.MetroPadding

/**
 * 4×4 黄历瓷砖 - 完整的中国传统黄历显示
 *
 * 特性：
 * - 显示公历、农历、干支、节气、节日
 * - 显示星座、宜忌事项
 * - 自定义布局，优化信息密度和可读性
 * - 完整展示传统黄历所有信息
 *
 * @param solarDate 公历日期（如 "2025年11月3日"）
 * @param lunarDate 农历日期（如 "乙巳年十月初三"）
 * @param ganZhi 干支（如 "乙巳年 丙戌月 庚午日"）
 * @param solarTerm 节气（如 "立冬"，可为空）
 * @param festival 节日（如 "国庆节"，可为空）
 * @param constellation 星座（如 "天蝎座"）
 * @param dayLucky 宜（如 "祭祀 祈福 出行"）
 * @param dayAvoid 忌（如 "动土 破土 嫁娶"）
 * @param backgroundColor 背景颜色（默认 Metro 绿色）
 * @param onClick 点击回调
 * @param modifier 修饰符
 */
@Composable
fun Calendar4x4Tile(
    solarDate: String,
    lunarDate: String,
    ganZhi: String,
    solarTerm: String?,
    festival: String?,
    constellation: String,
    dayLucky: String,
    dayAvoid: String,
    backgroundColor: Color = MetroTileColors.Calendar,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    BaseTile(
        spec = TileSpec.large(backgroundColor),
        onClick = onClick,
        modifier = modifier
    ) {
        single {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(MetroPadding.medium()),
                verticalArrangement = Arrangement.spacedBy(MetroSpacing.large())
            ) {
            // 标题：公历日期
            Text(
                text = solarDate,
                fontSize = MetroTypography.bodyMedium(),
                fontWeight = FontWeight.Light,
                color = Color.White,
                modifier = Modifier.fillMaxWidth()
            )

            // 农历信息
            InfoRow(label = "农历", value = lunarDate)

            // 干支
            InfoRow(label = "干支", value = ganZhi)

            // 节气和节日（如果有）
            if (festival != null) {
                InfoRow(label = "节日", value = festival, highlight = true)
            }
            if (solarTerm != null) {
                InfoRow(label = "节气", value = solarTerm, highlight = true)
            }

            // 星座
            InfoRow(label = "星座", value = constellation)

            // 宜忌（更紧凑的布局）
            Spacer(modifier = Modifier.height(MetroSpacing.small()))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "宜",
                    fontSize = MetroTypography.labelSmall(),
                    fontWeight = FontWeight.Light,
                    color = Color.White.copy(alpha = 0.7f),
                    modifier = Modifier.width(LocalTileBaseUnit.current * 0.12f)  // 标签宽度 (约12%)
                )
                Text(
                    text = dayLucky,
                    fontSize = MetroTypography.labelSmall(),
                    fontWeight = FontWeight.Light,
                    color = Color.White,
                    lineHeight = MetroTypography.bodyMedium(),
                    modifier = Modifier.weight(1f)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "忌",
                    fontSize = MetroTypography.labelSmall(),
                    fontWeight = FontWeight.Light,
                    color = Color.White.copy(alpha = 0.7f),
                    modifier = Modifier.width(LocalTileBaseUnit.current * 0.12f)  // 标签宽度 (约12%)
                )
                Text(
                    text = dayAvoid,
                    fontSize = MetroTypography.labelSmall(),
                    fontWeight = FontWeight.Light,
                    color = Color.White,
                    lineHeight = MetroTypography.bodyMedium(),
                    modifier = Modifier.weight(1f)
                )
            }
        }
        }
    }
}

/**
 * 信息行组件 - 标签+值的水平布局
 */
@Composable
private fun InfoRow(
    label: String,
    value: String,
    highlight: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = label,
            fontSize = MetroTypography.labelSmall(),
            fontWeight = FontWeight.Light,
            color = Color.White.copy(alpha = 0.7f),
            modifier = Modifier.width(LocalTileBaseUnit.current * 0.18f)  // 标签宽度 (约18%)
        )
        Text(
            text = value,
            fontSize = if (highlight) MetroTypography.bodyMedium() else MetroTypography.bodySmall(),
            fontWeight = if (highlight) FontWeight.Normal else FontWeight.Light,
            color = Color.White,
            lineHeight = MetroTypography.bodyMedium(),
            modifier = Modifier.weight(1f)
        )
    }
}
