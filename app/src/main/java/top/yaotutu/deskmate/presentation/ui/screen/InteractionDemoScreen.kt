package top.yaotutu.deskmate.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import top.yaotutu.deskmate.presentation.ui.component.base.TileGridContainer
import top.yaotutu.deskmate.presentation.ui.component.legacy.ProvideTileGrid
import top.yaotutu.deskmate.presentation.ui.component.interaction.TileClickEffect
import top.yaotutu.deskmate.presentation.ui.component.interaction.TileTapFlipTrigger
import top.yaotutu.deskmate.presentation.ui.component.base.Tile
import top.yaotutu.deskmate.presentation.ui.component.base.TileSize
import top.yaotutu.deskmate.presentation.ui.component.base.TileGrid

/**
 * 瓷砖交互动效演示页面
 *
 * 展示所有可用的点击动效类型
 */
@Composable
fun InteractionDemoScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF1E1E1E))
            .padding(8.dp)
    ) {
        TileGridContainer(modifier = Modifier.fillMaxSize()) { baseCellSize, dynamicGap, columns, _ ->
            ProvideTileGrid(baseCellSize = baseCellSize, dynamicGap = dynamicGap, columns = columns) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(dynamicGap)
                ) {
                    // 第一行：经典按压效果
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(dynamicGap)
                    ) {
                        DemoTile(
                            title = "按压缩放",
                            subtitle = "PRESS SCALE",
                            backgroundColor = Color(0xFF0078D7),
                            baseCellSize = baseCellSize,
                            dynamicGap = dynamicGap,
                            clickEffect = TileClickEffect.PRESS_SCALE
                        )

                        DemoTile(
                            title = "按压闪烁",
                            subtitle = "PRESS FLASH",
                            backgroundColor = Color(0xFFFF8C00),
                            baseCellSize = baseCellSize,
                            dynamicGap = dynamicGap,
                            clickEffect = TileClickEffect.PRESS_FLASH
                        )

                        DemoTile(
                            title = "点击弹跳",
                            subtitle = "TAP BOUNCE",
                            backgroundColor = Color(0xFF00A300),
                            baseCellSize = baseCellSize,
                            dynamicGap = dynamicGap,
                            clickEffect = TileClickEffect.TAP_BOUNCE
                        )
                    }

                    // 第二行：更多动效
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(dynamicGap)
                    ) {
                        DemoTile(
                            title = "点击脉冲",
                            subtitle = "TAP PULSE",
                            backgroundColor = Color(0xFFAA00FF),
                            baseCellSize = baseCellSize,
                            dynamicGap = dynamicGap,
                            clickEffect = TileClickEffect.TAP_PULSE
                        )

                        DemoTile(
                            title = "点击抖动",
                            subtitle = "TAP SHAKE",
                            backgroundColor = Color(0xFFE51400),
                            baseCellSize = baseCellSize,
                            dynamicGap = dynamicGap,
                            clickEffect = TileClickEffect.TAP_SHAKE
                        )

                        // 点击翻转效果（特殊处理）
                        FlipDemoTile(
                            baseCellSize = baseCellSize,
                            dynamicGap = dynamicGap
                        )
                    }
                }
            }
        }
    }
}

/**
 * 演示瓷砖组件
 */
@Composable
private fun DemoTile(
    title: String,
    subtitle: String,
    backgroundColor: Color,
    baseCellSize: Dp,
    dynamicGap: Dp,
    clickEffect: TileClickEffect,
    modifier: Modifier = Modifier
) {
    var clickCount by remember { mutableStateOf(0) }

    Tile(
        size = TileSize.MEDIUM,
        backgroundColor = backgroundColor,
        baseCellSize = baseCellSize,
        dynamicGap = dynamicGap,
        onClick = { clickCount++ },
        clickEffect = clickEffect,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraLight,
                    color = Color.White.copy(alpha = 0.8f)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "点击: $clickCount",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraLight,
                    color = Color.White.copy(alpha = 0.9f)
                )
            }
        }
    }
}

/**
 * 翻转演示瓷砖（特殊）
 */
@Composable
private fun FlipDemoTile(
    baseCellSize: Dp,
    dynamicGap: Dp,
    modifier: Modifier = Modifier
) {
    // 计算 2×2 瓷砖尺寸
    val tileWidth = TileGrid.calculateTileWidth(baseCellSize, 2, dynamicGap)
    val tileHeight = TileGrid.calculateTileHeight(baseCellSize, 2, dynamicGap)

    TileTapFlipTrigger(
        frontContent = {
            Box(
                modifier = Modifier
                    .width(tileWidth)
                    .height(tileHeight)
                    .background(Color(0xFF00ABA9))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "点击翻转",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "TAP FLIP",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraLight,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "正面 →",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraLight,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }
        },
        backContent = {
            Box(
                modifier = Modifier
                    .width(tileWidth)
                    .height(tileHeight)
                    .background(Color(0xFF8CBF26))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "再次点击",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "TAP AGAIN",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraLight,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "← 背面",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraLight,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }
        },
        modifier = modifier
    )
}
