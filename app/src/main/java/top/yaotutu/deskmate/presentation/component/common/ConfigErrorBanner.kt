package top.yaotutu.deskmate.presentation.component.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import top.yaotutu.deskmate.data.model.ConfigErrorType
import top.yaotutu.deskmate.data.model.ConfigLoadResult

/**
 * 配置错误提示横幅
 * 当配置文件加载失败时显示在屏幕顶部
 *
 * Metro 设计风格：
 * - 红色警告背景
 * - 扁平化设计
 * - 清晰的错误信息
 */
@Composable
fun ConfigErrorBanner(
    error: ConfigLoadResult.Error,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {}
) {
    var isExpanded by remember { mutableStateOf(false) }
    var isVisible by remember { mutableStateOf(true) }

    AnimatedVisibility(
        visible = isVisible,
        enter = expandVertically() + fadeIn(),
        exit = shrinkVertically() + fadeOut()
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(MetroErrorRed)
                .clickable { isExpanded = !isExpanded }
                .padding(16.dp)
        ) {
            // 标题行
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "警告",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = error.errorType.displayName,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                        Text(
                            text = "使用降级配置运行",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                    }
                }

                IconButton(onClick = {
                    isVisible = false
                    onDismiss()
                }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "关闭",
                        tint = Color.White
                    )
                }
            }

            // 展开的详细信息
            AnimatedVisibility(visible = isExpanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                ) {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color.White.copy(alpha = 0.3f))
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    ErrorDetailItem("配置文件", error.fileName)
                    Spacer(modifier = Modifier.height(8.dp))

                    ErrorDetailItem("错误详情", error.errorMessage)
                    Spacer(modifier = Modifier.height(8.dp))

                    ErrorDetailItem(
                        "降级方案",
                        if (error.fallbackConfig != null) {
                            "已使用默认配置（${error.fallbackConfig.tiles.size} 个瓷砖）"
                        } else {
                            "无降级配置"
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // 建议
                    Text(
                        text = "💡 建议：${getSuggestion(error.errorType)}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.White.copy(alpha = 0.95f),
                        lineHeight = 16.sp
                    )
                }
            }

            // 提示可以点击展开
            if (!isExpanded) {
                Text(
                    text = "点击查看详情 ▼",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.ExtraLight,
                    color = Color.White.copy(alpha = 0.7f),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

/**
 * 错误详情项
 */
@Composable
private fun ErrorDetailItem(
    label: String,
    value: String
) {
    Row {
        Text(
            text = "$label: ",
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White.copy(alpha = 0.8f)
        )
        Text(
            text = value,
            fontSize = 12.sp,
            fontWeight = FontWeight.Light,
            color = Color.White,
            modifier = Modifier.weight(1f)
        )
    }
}

/**
 * 根据错误类型提供建议
 */
private fun getSuggestion(errorType: ConfigErrorType): String {
    return when (errorType) {
        ConfigErrorType.FILE_NOT_FOUND ->
            "检查 assets 目录下是否存在该配置文件"

        ConfigErrorType.PARSE_ERROR ->
            "使用 JSON 验证工具检查文件格式，确保符合 LayoutConfig 结构"

        ConfigErrorType.INVALID_FORMAT ->
            "确保配置文件包含 'tiles' 数组，且每个瓷砖有 type、variant、columns、rows 字段"

        ConfigErrorType.IO_ERROR ->
            "检查文件权限，或尝试重新构建应用"

        ConfigErrorType.UNKNOWN ->
            "查看 Logcat 获取详细错误日志"
    }
}

/**
 * Metro 错误红色
 * Windows Phone 官方错误配色
 */
private val MetroErrorRed = Color(0xFFE51400)
