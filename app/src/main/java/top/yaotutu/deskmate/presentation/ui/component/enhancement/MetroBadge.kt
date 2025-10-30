package top.yaotutu.deskmate.presentation.ui.component.enhancement

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
