package top.yaotutu.deskmate.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import top.yaotutu.deskmate.presentation.component.base.*
import top.yaotutu.deskmate.presentation.component.animation.MarqueeDirection
import top.yaotutu.deskmate.presentation.component.animation.PeekDirection
import top.yaotutu.deskmate.presentation.component.animation.WipeDirection
import top.yaotutu.deskmate.presentation.component.animation.WipeStyle
import top.yaotutu.deskmate.presentation.theme.MetroColors

/**
 * 动画演示页面 - 展示所有 14 种 Metro 动画效果
 *
 * 包括：
 * - 核心动画（6种）：Flip, Pulse, Slide, Fade, Peek ⭐, Marquee ⭐
 * - 高级动画（5种）：Rotate, StaggerEnter, Shimmer, Wipe ⭐, Depth ⭐
 * - 交互动画（2种）：Bounce, Shake
 * - 特殊动画（1种）：Counter
 *
 * ⭐ 标记表示新实现的 Windows Phone 标志性动画
 */
@Composable
fun AnimationDemoScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 标题
        Text(
            text = "Metro 动画演示",
            fontSize = 32.sp,
            fontWeight = FontWeight.Thin,
            color = Color.White,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // ========== 核心动画 (6种) ==========
        SectionTitle("核心动画 (6种)")

        // 1. Flip 翻转动画
        AnimationCard(title = "Flip - 翻转动画") {
            // 使用场景说明
            UsageInfo("适用场景：时钟、日历、双面卡片")
            BaseTile(spec = TileSpec.square(MetroColors.Blue, AnimationType.FLIP)) {
                flip(
                    front = {
                        CenteredContent(emoji = "🕐", text = "10:12")
                    },
                    back = {
                        CenteredContent(emoji = "📅", text = "10月30日")
                    }
                )
            }
        }

        // 2. Pulse 脉冲动画
        AnimationCard(title = "Pulse - 脉冲动画") {
            // 使用场景说明
            UsageInfo("适用场景：新消息提醒、待办事项")
            BaseTile(spec = TileSpec.square(MetroColors.Orange, AnimationType.PULSE)) {
                CenteredContent(emoji = "☀", text = "22°")
            }
        }

        // 3. Slide 滑动动画
        AnimationCard(title = "Slide - 滑动动画") {
            // 使用场景说明
            UsageInfo("适用场景：新闻列表、图片轮播")
            BaseTile(spec = TileSpec.wideMedium(MetroColors.Red, AnimationType.SLIDE)) {
                slide(
                    listOf(
                        { CenteredContent(emoji = "📰", text = "新闻1") },
                        { CenteredContent(emoji = "📰", text = "新闻2") },
                        { CenteredContent(emoji = "📰", text = "新闻3") }
                    )
                )
            }
        }

        // 4. Fade 淡入淡出动画
        AnimationCard(title = "Fade - 淡入淡出动画") {
            // 使用场景说明
            UsageInfo("适用场景：天气预报、广告轮播")
            BaseTile(spec = TileSpec.square(MetroColors.Green, AnimationType.FADE)) {
                fade(
                    listOf(
                        { CenteredContent(emoji = "📸", text = "照片1") },
                        { CenteredContent(emoji = "📸", text = "照片2") },
                        { CenteredContent(emoji = "📸", text = "照片3") }
                    )
                )
            }
        }

        // ⭐ 5. Peek 探出动画（新增）
        AnimationCard(title = "⭐ Peek - 探出动画 (WP 标志性)") {
            // 使用场景说明
            UsageInfo("适用场景：通知预览、消息提示")
            BaseTile(spec = TileSpec.square(MetroColors.Blue, AnimationType.PEEK)) {
                peek(
                    mainContent = {
                        CenteredContent(emoji = "📧", text = "3 封新邮件")
                    },
                    peekContent = {
                        Column(
                            Modifier.fillMaxSize().padding(16.dp),
                            Arrangement.Bottom
                        ) {
                            Text("来自：张三", fontSize = 16.sp, color = Color.White)
                            Text("会议提醒", fontSize = 14.sp, color = Color.White.copy(0.8f))
                        }
                    },
                    peekHeight = 0.4f,
                    direction = PeekDirection.BOTTOM
                )
            }
        }

        // ⭐ 6. Marquee 跑马灯动画（新增）
        AnimationCard(title = "⭐ Marquee - 跑马灯动画") {
            // 使用场景说明
            UsageInfo("适用场景：长文本滚动、标题展示")
            BaseTile(spec = TileSpec.wideMedium(MetroColors.Red, AnimationType.MARQUEE)) {
                marquee(
                    direction = MarqueeDirection.HORIZONTAL,
                    speed = 40f
                ) {
                    Text(
                        text = "突发新闻：这是一条很长的新闻标题，需要滚动显示，让用户能够完整阅读全部内容...",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.White,
                        maxLines = 1
                    )
                }
            }
        }

        // ========== 高级动画 (5种) ==========
        SectionTitle("高级动画 (5种)")

        // 7. Rotate 旋转动画
        AnimationCard(title = "Rotate - 旋转动画") {
            // 使用场景说明
            UsageInfo("适用场景：加载指示、刷新动画")
            BaseTile(spec = TileSpec.square(MetroColors.Purple, AnimationType.ROTATE)) {
                CenteredContent(emoji = "🔄", text = "刷新中")
            }
        }

        // 8. Shimmer 微光动画
        AnimationCard(title = "Shimmer - 微光动画") {
            // 使用场景说明
            UsageInfo("适用场景：内容加载、数据刷新")
            BaseTile(spec = TileSpec.square(MetroColors.Teal, AnimationType.SHIMMER)) {
                CenteredContent(emoji = "⏳", text = "加载中")
            }
        }

        // ⭐ 9. Wipe 擦除动画（新增）
        AnimationCard(title = "⭐ Wipe - 擦除动画") {
            // 使用场景说明
            UsageInfo("适用场景：内容切换、页面过渡")
            BaseTile(spec = TileSpec.wideMedium(MetroColors.Red, AnimationType.WIPE)) {
                wipe(
                    contents = listOf(
                        { CenteredContent(emoji = "📰", text = "新闻A") },
                        { CenteredContent(emoji = "📰", text = "新闻B") },
                        { CenteredContent(emoji = "📰", text = "新闻C") }
                    ),
                    direction = WipeDirection.LEFT_TO_RIGHT,
                    style = WipeStyle.SLIDE
                )
            }
        }

        // ⭐ 10. Depth 深度动画（新增）
        AnimationCard(title = "⭐ Depth - 深度动画") {
            // 使用场景说明
            UsageInfo("适用场景：图片展示、卡片效果")
            BaseTile(spec = TileSpec.square(MetroColors.Purple, AnimationType.DEPTH)) {
                CenteredContent(emoji = "📷", text = "照片")
            }
        }

        // ========== 交互动画 (2种) ==========
        SectionTitle("交互动画 (2种)")

        // 11. Bounce 弹跳动画
        AnimationCard(title = "Bounce - 弹跳动画") {
            // 使用场景说明
            UsageInfo("适用场景：新消息提醒、重要通知")
            BaseTile(spec = TileSpec.square(MetroColors.Green, AnimationType.BOUNCE)) {
                CenteredContent(emoji = "🔔", text = "新通知")
            }
        }

        // 12. Shake 抖动动画
        AnimationCard(title = "Shake - 抖动动画") {
            // 使用场景说明
            UsageInfo("适用场景：错误提示、警告通知")
            BaseTile(spec = TileSpec.square(MetroColors.Red, AnimationType.SHAKE)) {
                CenteredContent(emoji = "⚠️", text = "警告")
            }
        }

        // ========== 特殊动画 (1种) ==========
        SectionTitle("特殊动画 (1种)")

        // 13. Counter 数字滚动动画
        AnimationCard(title = "Counter - 数字滚动动画") {
            // 使用场景说明
            UsageInfo("适用场景：温度、股票、计数器")
            BaseTile(spec = TileSpec.square(MetroColors.Orange, AnimationType.COUNTER)) {
                counter(
                    targetValue = 22
                ) { value ->
                    CenteredContent(emoji = "🌡️", text = "$value°")
                }
            }
        }

        // 底部间距
        Spacer(modifier = Modifier.height(32.dp))
    }
}

/**
 * 使用场景信息
 */
@Composable
private fun UsageInfo(info: String) {
    Text(
        text = info,
        fontSize = 14.sp,
        fontWeight = FontWeight.ExtraLight,
        color = Color.White.copy(0.6f),
        modifier = Modifier.padding(bottom = 4.dp)
    )
}

/**
 * 分类标题
 */
@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 24.sp,
        fontWeight = FontWeight.Light,
        color = Color.White,
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
    )
}

/**
 * 动画卡片包装器
 */
@Composable
private fun AnimationCard(
    title: String,
    content: @Composable () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.ExtraLight,
            color = Color.White.copy(0.8f)
        )
        ProvideTileGrid(
            baseCellSize = 80.dp,
            dynamicGap = 8.dp,
            columns = 6
        ) {
            content()
        }
    }
}

/**
 * 居中内容辅助组件
 */
@Composable
private fun CenteredContent(emoji: String, text: String) {
    Column(
        Modifier.fillMaxSize(),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        Text(emoji, fontSize = 64.sp)
        Text(
            text,
            fontSize = 20.sp,
            fontWeight = FontWeight.Light,
            color = Color.White
        )
    }
}
