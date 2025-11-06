package top.yaotutu.deskmate.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import top.yaotutu.deskmate.data.repository.LayoutConfigRepository
import top.yaotutu.deskmate.presentation.component.base.TileGrid

/**
 * 网格信息测试页面
 *
 * 显示当前设备的网格规格信息，帮助用户了解：
 * - 设备类型（平板/手机）
 * - 屏幕尺寸
 * - 网格行列数
 * - 基准尺寸（baseCellSize）
 * - 配置文件示例
 *
 * @author Deskmate Team
 * @since 2025-01-06
 */
@Composable
fun GridInfoScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val repository = remember { LayoutConfigRepository(context) }
    val isTablet = remember { repository.isTablet() }

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF1E1E1E))
            .padding(16.dp)
    ) {
        // 计算网格参数
        val screenWidth = maxWidth
        val screenHeight = maxHeight
        val baseCellSize = TileGrid.calculateBaseCellSize(screenWidth, screenHeight, isTablet)
        val columns = TileGrid.calculateColumns(screenWidth, baseCellSize)
        val rows = if (isTablet) TileGrid.TABLET_GRID_ROWS else TileGrid.PHONE_GRID_ROWS
        val gap = TileGrid.FIXED_GAP

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 标题
            Text(
                text = "网格系统信息",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Divider(color = Color.Gray, thickness = 1.dp)

            // 设备信息
            SectionTitle("设备信息")
            InfoRow("设备类型", if (isTablet) "平板（sw >= 600dp）" else "手机（sw < 600dp）")
            InfoRow("屏幕尺寸", "${screenWidth.value.toInt()} × ${screenHeight.value.toInt()} dp")
            InfoRow("最小宽度", "${context.resources.configuration.smallestScreenWidthDp} dp")

            Divider(color = Color.Gray.copy(alpha = 0.3f), thickness = 1.dp)

            // 网格规格
            SectionTitle("网格规格")
            InfoRow("网格行数", "$rows 行（固定）", highlight = true)
            InfoRow("网格列数", "$columns 列（动态计算）", highlight = true)
            InfoRow("基准尺寸", "${baseCellSize.value.toInt()} dp", highlight = true)
            InfoRow("瓷砖间距", "$gap dp（固定）")

            Divider(color = Color.Gray.copy(alpha = 0.3f), thickness = 1.dp)

            // 计算公式
            SectionTitle("计算公式")
            FormulaRow("基准尺寸", "min(宽, 高) / 行数")
            FormulaRow("列数", "宽度 / 基准尺寸")
            FormulaRow("", "= ${screenWidth.value.toInt()} / ${baseCellSize.value.toInt()} = $columns")

            Divider(color = Color.Gray.copy(alpha = 0.3f), thickness = 1.dp)

            // 瓷砖尺寸示例
            SectionTitle("瓷砖尺寸示例（含间距）")
            val cell = baseCellSize.value.toInt()
            TileSizeRow("1×1", "$cell × $cell dp")
            TileSizeRow("2×2", "${cell * 2 + gap} × ${cell * 2 + gap} dp")
            TileSizeRow("4×2", "${cell * 2 + gap} × ${cell * 4 + gap * 3} dp")
            TileSizeRow("2×4", "${cell * 4 + gap * 3} × ${cell * 2 + gap} dp")
            TileSizeRow("4×4", "${cell * 4 + gap * 3} × ${cell * 4 + gap * 3} dp")

            Divider(color = Color.Gray.copy(alpha = 0.3f), thickness = 1.dp)

            // 配置文件示例
            SectionTitle("配置文件示例")
            Text(
                text = "\"areas\": [",
                fontFamily = FontFamily.Monospace,
                fontSize = 14.sp,
                color = Color(0xFF4EC9B0)
            )

            // 生成示例网格
            repeat(rows) { row ->
                val line = (0 until columns).joinToString(" ") { "." }
                val comma = if (row < rows - 1) "," else ""
                Text(
                    text = "  \"$line\"$comma",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 14.sp,
                    color = Color(0xFFCE9178)
                )
            }

            Text(
                text = "]",
                fontFamily = FontFamily.Monospace,
                fontSize = 14.sp,
                color = Color(0xFF4EC9B0)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 提示信息
            Text(
                text = "💡 提示：复制上述 areas 模板到配置文件，然后替换 '.' 为瓷砖 ID",
                fontSize = 12.sp,
                color = Color.Gray,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF4EC9B0)
    )
}

@Composable
private fun InfoRow(label: String, value: String, highlight: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            color = Color.LightGray
        )
        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = if (highlight) FontWeight.Bold else FontWeight.Normal,
            color = if (highlight) Color(0xFFDCDCAA) else Color.White
        )
    }
}

@Composable
private fun FormulaRow(label: String, formula: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (label.isEmpty()) Arrangement.End else Arrangement.SpaceBetween
    ) {
        if (label.isNotEmpty()) {
            Text(
                text = label,
                fontSize = 14.sp,
                color = Color.LightGray
            )
        }
        Text(
            text = formula,
            fontSize = 14.sp,
            fontFamily = FontFamily.Monospace,
            color = Color(0xFF9CDCFE)
        )
    }
}

@Composable
private fun TileSizeRow(size: String, dimensions: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = size,
            fontSize = 14.sp,
            fontFamily = FontFamily.Monospace,
            color = Color(0xFFDCDCAA)
        )
        Text(
            text = dimensions,
            fontSize = 14.sp,
            fontFamily = FontFamily.Monospace,
            color = Color.LightGray
        )
    }
}
