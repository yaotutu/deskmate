package top.yaotutu.deskmate.presentation.ui.component.tiles

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import top.yaotutu.deskmate.presentation.ui.component.LocalBaseCellSize
import top.yaotutu.deskmate.presentation.ui.component.LocalDynamicGap
import top.yaotutu.deskmate.presentation.ui.component.Tile
import top.yaotutu.deskmate.presentation.ui.component.TileGrid
import top.yaotutu.deskmate.presentation.ui.theme.MetroColors

/**
 * 错误类型枚举
 */
enum class TileErrorType {
    /** 未知的变体 */
    UNKNOWN_VARIANT,

    /** 尺寸不匹配 */
    SIZE_MISMATCH,

    /** 缺少 variant 字段 */
    MISSING_VARIANT
}

/**
 * 错误提示瓷砖
 *
 * 当配置错误时显示友好的错误提示
 *
 * @param columns 瓷砖列数
 * @param rows 瓷砖行数
 * @param errorType 错误类型
 * @param message 主要错误信息
 * @param details 详细信息（键值对）
 */
@Composable
fun ErrorTile(
    columns: Int,
    rows: Int,
    errorType: TileErrorType,
    message: String,
    details: Map<String, String> = emptyMap(),
    modifier: Modifier = Modifier
) {
    val baseCellSize = LocalBaseCellSize.current
    val dynamicGap = LocalDynamicGap.current

    // 计算瓷砖实际尺寸
    val tileWidth = TileGrid.calculateTileWidth(baseCellSize, columns, dynamicGap)
    val tileHeight = TileGrid.calculateTileHeight(baseCellSize, rows, dynamicGap)

    // 使用深红色背景表示错误
    val errorBackgroundColor = Color(0xFFB71C1C)

    Box(
        modifier = modifier
            .width(tileWidth)
            .height(tileHeight)
    ) {
        Tile(
            columns = columns,
            rows = rows,
            backgroundColor = errorBackgroundColor,
            baseCellSize = baseCellSize,
            dynamicGap = dynamicGap
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 警告图标
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = "错误",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // 主要错误信息
                Text(
                    text = message,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light,
                    lineHeight = 16.sp
                )

                // 详细信息
                if (details.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))

                    details.forEach { (key, value) ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "$key: ",
                                color = Color.White.copy(alpha = 0.7f),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraLight
                            )
                            Text(
                                text = value,
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Light
                            )
                        }
                    }
                }
            }
        }
    }
}
