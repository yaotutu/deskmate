package top.yaotutu.deskmate.presentation.component.tiles.animationdemo

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import top.yaotutu.deskmate.presentation.component.base.AnimationType
import top.yaotutu.deskmate.presentation.component.base.BaseTile
import top.yaotutu.deskmate.presentation.component.base.TileSpec
import top.yaotutu.deskmate.presentation.component.base.FlipContent
import top.yaotutu.deskmate.presentation.component.base.SlideContent
import top.yaotutu.deskmate.presentation.component.base.FadeContent
import top.yaotutu.deskmate.presentation.component.base.CounterContent
import top.yaotutu.deskmate.presentation.theme.MetroTileColors

/**
 * 动画演示瓷砖集合
 *
 * 这个文件包含所有 10 种 Metro 动画类型的演示瓷砖，
 * 用于展示和测试完整的动画系统。
 *
 * @author Deskmate Team
 */

// ==================== 1. NONE - 无动画 ====================

/**
 * 无动画演示瓷砖 - 小尺寸 (1×1)
 */
@Composable
fun AnimationDemoNoneSmall(
    backgroundColor: Color = MetroTileColors.Time,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    BaseTile(
        spec = TileSpec.small(backgroundColor, AnimationType.NONE),
        onClick = onClick,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "1×1",
                fontSize = 24.sp,
                fontWeight = FontWeight.Thin,
                color = Color.White
            )
        }
    }
}

/**
 * 无动画演示瓷砖 (2×2)
 */
@Composable
fun AnimationDemoNone(
    backgroundColor: Color = MetroTileColors.Time,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    BaseTile(
        spec = TileSpec.square(backgroundColor, AnimationType.NONE),
        onClick = onClick,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "NONE",
                fontSize = 40.sp,
                fontWeight = FontWeight.Thin,
                color = Color.White
            )
            Text(
                text = "无动画",
                fontSize = 18.sp,
                fontWeight = FontWeight.Light,
                color = Color.White.copy(alpha = 0.9f)
            )
        }
    }
}

// ==================== 2. FLIP - 翻转动画 ====================

/**
 * 翻转动画演示瓷砖 (4×2)
 */
@Composable
fun AnimationDemoFlip(
    backgroundColor: Color = MetroTileColors.Time,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    BaseTile(
        spec = TileSpec.wideMedium(backgroundColor, AnimationType.FLIP),
        onClick = onClick,
        modifier = modifier
    ) {
        FlipContent(
            front = {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "FLIP",
                            fontSize = 56.sp,
                            fontWeight = FontWeight.Thin,
                            color = Color.White
                        )
                        Text(
                            text = "正面",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                    }
                }
            },
            back = {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "翻转动画",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.White
                        )
                        Text(
                            text = "正反面切换",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraLight,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                    }
                }
            }
        )
    }
}

// ==================== 3. PULSE - 脉冲动画 ====================

/**
 * 脉冲动画演示瓷砖 (2×2)
 */
@Composable
fun AnimationDemoPulse(
    backgroundColor: Color = MetroTileColors.Calendar,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    BaseTile(
        spec = TileSpec.square(backgroundColor, AnimationType.PULSE),
        onClick = onClick,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "PULSE",
                fontSize = 40.sp,
                fontWeight = FontWeight.Thin,
                color = Color.White
            )
            Text(
                text = "脉冲动画",
                fontSize = 18.sp,
                fontWeight = FontWeight.Light,
                color = Color.White.copy(alpha = 0.9f)
            )
            Text(
                text = "周期缩放",
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraLight,
                color = Color.White.copy(alpha = 0.8f)
            )
        }
    }
}

// ==================== 4. SLIDE - 滑动动画 ====================

/**
 * 滑动动画演示瓷砖 (4×4)
 */
@Composable
fun AnimationDemoSlide(
    backgroundColor: Color = MetroTileColors.News,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    BaseTile(
        spec = TileSpec.large(backgroundColor, AnimationType.SLIDE),
        onClick = onClick,
        modifier = modifier
    ) {
        SlideContent(
            listOf(
                {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "SLIDE",
                                fontSize = 64.sp,
                                fontWeight = FontWeight.Thin,
                                color = Color.White
                            )
                            Text(
                                text = "滑动动画",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Light,
                                color = Color.White
                            )
                            Text(
                                text = "内容 1/3",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.ExtraLight,
                                color = Color.White.copy(alpha = 0.9f)
                            )
                        }
                    }
                },
                {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "内容轮播",
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Light,
                                color = Color.White
                            )
                            Text(
                                text = "自动滑动切换",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.ExtraLight,
                                color = Color.White.copy(alpha = 0.9f)
                            )
                            Text(
                                text = "内容 2/3",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.ExtraLight,
                                color = Color.White.copy(alpha = 0.8f)
                            )
                        }
                    }
                },
                {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "📰",
                                fontSize = 80.sp
                            )
                            Text(
                                text = "滑动轮播",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Light,
                                color = Color.White
                            )
                            Text(
                                text = "内容 3/3",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.ExtraLight,
                                color = Color.White.copy(alpha = 0.9f)
                            )
                        }
                    }
                }
            )
        )
    }
}

// ==================== 5. FADE - 淡入淡出动画 ====================

/**
 * 淡入淡出动画演示瓷砖 (2×2)
 */
@Composable
fun AnimationDemoFade(
    backgroundColor: Color = MetroTileColors.Todo,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    BaseTile(
        spec = TileSpec.square(backgroundColor, AnimationType.FADE),
        onClick = onClick,
        modifier = modifier
    ) {
        FadeContent(
            listOf(
                {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "FADE",
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Thin,
                            color = Color.White
                        )
                        Text(
                            text = "淡入淡出",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.White
                        )
                    }
                },
                {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "平滑切换",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.White
                        )
                        Text(
                            text = "内容过渡",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraLight,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                    }
                },
                {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "✨",
                            fontSize = 64.sp
                        )
                        Text(
                            text = "优雅过渡",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.White
                        )
                    }
                }
            )
        )
    }
}

// ==================== 6. COUNTER - 数字滚动动画 ====================

/**
 * 数字滚动动画演示瓷砖 (2×2)
 */
@Composable
fun AnimationDemoCounter(
    targetValue: Int = 42,
    backgroundColor: Color = MetroTileColors.Weather,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    // 自动递增的数字，每2秒变化一次
    var counter by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(2000)
            counter = (counter + 1) % 100  // 0-99 循环
        }
    }

    BaseTile(
        spec = TileSpec.square(backgroundColor, AnimationType.COUNTER),
        onClick = onClick,
        modifier = modifier
    ) {
        CounterContent(
            targetValue = counter,
            content = { value ->
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "$value",
                        fontSize = 72.sp,
                        fontWeight = FontWeight.Thin,
                        color = Color.White
                    )
                    Text(
                        text = "COUNTER",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.White
                    )
                    Text(
                        text = "数字滚动",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraLight,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }
        )
    }
}

// ==================== 7. ROTATE - 旋转动画 ====================

/**
 * 旋转动画演示瓷砖 (2×2)
 */
@Composable
fun AnimationDemoRotate(
    backgroundColor: Color = Color(0xFF00ABA9), // Teal
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    BaseTile(
        spec = TileSpec.square(backgroundColor, AnimationType.ROTATE),
        onClick = onClick,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "🔄",
                fontSize = 64.sp
            )
            Text(
                text = "ROTATE",
                fontSize = 24.sp,
                fontWeight = FontWeight.Light,
                color = Color.White
            )
            Text(
                text = "旋转动画",
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraLight,
                color = Color.White.copy(alpha = 0.9f)
            )
        }
    }
}

// ==================== 8. BOUNCE - 弹跳动画 ====================

/**
 * 弹跳动画演示瓷砖 (2×2)
 */
@Composable
fun AnimationDemoBounce(
    backgroundColor: Color = Color(0xFFF472B6), // Pink
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    BaseTile(
        spec = TileSpec.square(backgroundColor, AnimationType.BOUNCE),
        onClick = onClick,
        modifier = modifier
    ) {
        // 添加白色边框让移动更明显
        Box(
            modifier = Modifier
                .fillMaxSize()
                .border(
                    4.dp,
                    Color.White,
                    RoundedCornerShape(4.dp)
                )
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "⬆️⬇️",
                    fontSize = 72.sp
                )
                Text(
                    text = "BOUNCE",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.White
                )
                Text(
                    text = "弹跳动画",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraLight,
                    color = Color.White.copy(alpha = 0.9f)
                )
            }
        }
    }
}

// ==================== 9. SHAKE - 抖动动画 ====================

/**
 * 抖动动画演示瓷砖 (2×2)
 */
@Composable
fun AnimationDemoShake(
    backgroundColor: Color = Color(0xFFDC2626), // Red
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    BaseTile(
        spec = TileSpec.square(backgroundColor, AnimationType.SHAKE),
        onClick = onClick,
        modifier = modifier
    ) {
        // 添加白色边框让移动更明显
        Box(
            modifier = Modifier
                .fillMaxSize()
                .border(
                    4.dp,
                    Color.White,
                    RoundedCornerShape(4.dp)
                )
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "⚠️⬅️➡️",
                    fontSize = 64.sp
                )
                Text(
                    text = "SHAKE",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.White
                )
                Text(
                    text = "抖动动画",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraLight,
                    color = Color.White.copy(alpha = 0.9f)
                )
            }
        }
    }
}

// ==================== 10. SHIMMER - 微光动画 ====================

/**
 * 微光动画演示瓷砖 (2×2)
 */
@Composable
fun AnimationDemoShimmer(
    backgroundColor: Color = Color(0xFF9CA3AF), // Gray
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    BaseTile(
        spec = TileSpec.square(backgroundColor, AnimationType.SHIMMER),
        onClick = onClick,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "✨",
                fontSize = 64.sp
            )
            Text(
                text = "SHIMMER",
                fontSize = 24.sp,
                fontWeight = FontWeight.Light,
                color = Color.White
            )
            Text(
                text = "微光动画",
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraLight,
                color = Color.White.copy(alpha = 0.9f)
            )
        }
    }
}
