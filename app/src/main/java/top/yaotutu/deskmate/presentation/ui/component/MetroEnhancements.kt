package top.yaotutu.deskmate.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import top.yaotutu.deskmate.presentation.ui.theme.MetroTileColors

// ==================== Metro 状态栏 ====================

/**
 * Metro 风格状态栏
 *
 * 显示时间、电量、信号等信息
 *
 * @param time 当前时间
 * @param batteryLevel 电量百分比（0-100）
 * @param signalStrength 信号强度（0-4）
 * @param backgroundColor 背景颜色
 */
@Composable
fun MetroStatusBar(
    time: String,
    batteryLevel: Int = 100,
    signalStrength: Int = 4,
    backgroundColor: Color = Color(0xFF000000),
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 时间
        Text(
            text = time,
            fontSize = 14.sp,
            fontWeight = FontWeight.Light,
            color = Color.White
        )

        // 状态图标
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 信号强度
            SignalIcon(strength = signalStrength)

            // 电量
            BatteryIcon(level = batteryLevel)
        }
    }
}

@Composable
private fun SignalIcon(strength: Int) {
    Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
        repeat(4) { index ->
            Box(
                modifier = Modifier
                    .width(3.dp)
                    .height(((index + 1) * 3).dp)
                    .background(
                        if (index < strength) Color.White else Color.White.copy(alpha = 0.3f)
                    )
            )
        }
    }
}

@Composable
private fun BatteryIcon(level: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        // 电池图标
        Box(
            modifier = Modifier
                .width(20.dp)
                .height(10.dp)
                .background(Color.White.copy(alpha = 0.3f))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(level.toFloat() / 100f)
                    .background(
                        when {
                            level > 50 -> Color.White
                            level > 20 -> Color.Yellow
                            else -> Color.Red
                        }
                    )
            )
        }

        // 电量百分比
        Text(
            text = "$level%",
            fontSize = 12.sp,
            fontWeight = FontWeight.ExtraLight,
            color = Color.White,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}

// ==================== 角标系统 ====================

/**
 * Metro 风格角标
 *
 * 显示在瓷砖右上角的数字角标
 *
 * @param count 角标数字（>99 显示 99+）
 * @param backgroundColor 背景颜色
 */
@Composable
fun MetroBadge(
    count: Int,
    backgroundColor: Color = Color(0xFFE51400),
    modifier: Modifier = Modifier
) {
    if (count > 0) {
        Box(
            modifier = modifier
                .clip(CircleShape)
                .background(backgroundColor)
                .padding(horizontal = 6.dp, vertical = 2.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (count > 99) "99+" else count.toString(),
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

/**
 * 带角标的瓷砖包装器
 *
 * @param badgeCount 角标数字
 * @param badgeColor 角标颜色
 * @param content 瓷砖内容
 */
@Composable
fun TileWithBadge(
    badgeCount: Int,
    badgeColor: Color = Color(0xFFE51400),
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(modifier = modifier) {
        content()

        // 角标显示在右上角
        if (badgeCount > 0) {
            MetroBadge(
                count = badgeCount,
                backgroundColor = badgeColor,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = (-4).dp, y = 4.dp)
            )
        }
    }
}

// ==================== 新瓷砖类型 ====================

/**
 * 照片瓷砖 (2×2)
 *
 * @param imageUrl 图片URL（暂时用占位符代替）
 * @param caption 标题
 * @param backgroundColor 背景颜色
 */
@Composable
fun PhotoTile(
    imageUrl: String = "",
    caption: String = "照片",
    backgroundColor: Color = MetroTileColors.Photo,
    modifier: Modifier = Modifier
) {
    val baseCellSize = LocalBaseCellSize.current
    val dynamicGap = LocalDynamicGap.current

    Tile(
        size = TileSize.MEDIUM,
        backgroundColor = backgroundColor,
        baseCellSize = baseCellSize,
        dynamicGap = dynamicGap,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "📷",
                    fontSize = 48.sp
                )
                Text(
                    text = caption,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.White
                )
            }
        }
    }
}

/**
 * 音乐瓷砖 (2×2)
 *
 * @param songName 歌曲名
 * @param artist 艺术家
 * @param isPlaying 是否正在播放
 * @param backgroundColor 背景颜色
 */
@Composable
fun MusicTile(
    songName: String = "歌曲",
    artist: String = "艺术家",
    isPlaying: Boolean = false,
    backgroundColor: Color = MetroTileColors.Music,
    modifier: Modifier = Modifier
) {
    val baseCellSize = LocalBaseCellSize.current
    val dynamicGap = LocalDynamicGap.current

    Tile(
        size = TileSize.MEDIUM,
        backgroundColor = backgroundColor,
        baseCellSize = baseCellSize,
        dynamicGap = dynamicGap,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = if (isPlaying) "⏸" else "▶",
                    fontSize = 48.sp,
                    color = Color.White
                )
                Text(
                    text = songName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.White
                )
                Text(
                    text = artist,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraLight,
                    color = Color.White.copy(alpha = 0.9f)
                )
            }
        }
    }
}

/**
 * 联系人瓷砖 (1×1 或 2×2)
 *
 * @param name 联系人姓名
 * @param avatar 头像（暂时用首字母代替）
 * @param size 瓷砖尺寸
 * @param backgroundColor 背景颜色
 */
@Composable
fun ContactTile(
    name: String = "联系人",
    avatar: String = name.take(1),
    size: TileSize = TileSize.SMALL,
    backgroundColor: Color = MetroTileColors.Contact,
    modifier: Modifier = Modifier
) {
    val baseCellSize = LocalBaseCellSize.current
    val dynamicGap = LocalDynamicGap.current

    Tile(
        size = size,
        backgroundColor = backgroundColor,
        baseCellSize = baseCellSize,
        dynamicGap = dynamicGap,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(if (size == TileSize.SMALL) 4.dp else 8.dp)
            ) {
                // 头像圆形
                Box(
                    modifier = Modifier
                        .size(if (size == TileSize.SMALL) 32.dp else 48.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.3f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = avatar,
                        fontSize = if (size == TileSize.SMALL) 18.sp else 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                // 姓名（仅在2×2及以上显示）
                if (size != TileSize.SMALL) {
                    Text(
                        text = name,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.White
                    )
                }
            }
        }
    }
}

/**
 * 邮件瓷砖 (2×2)
 *
 * @param unreadCount 未读邮件数
 * @param latestSubject 最新邮件主题
 * @param backgroundColor 背景颜色
 */
@Composable
fun MailTile(
    unreadCount: Int = 0,
    latestSubject: String = "邮件",
    backgroundColor: Color = MetroTileColors.Mail,
    modifier: Modifier = Modifier
) {
    val baseCellSize = LocalBaseCellSize.current
    val dynamicGap = LocalDynamicGap.current

    TileWithBadge(
        badgeCount = unreadCount,
        modifier = modifier
    ) {
        Tile(
            size = TileSize.MEDIUM,
            backgroundColor = backgroundColor,
            baseCellSize = baseCellSize,
            dynamicGap = dynamicGap
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "邮件",
                        tint = Color.White,
                        modifier = Modifier.size(48.dp)
                    )
                    if (unreadCount > 0) {
                        Text(
                            text = "$unreadCount 封未读",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.White
                        )
                    } else {
                        Text(
                            text = "无新邮件",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}
