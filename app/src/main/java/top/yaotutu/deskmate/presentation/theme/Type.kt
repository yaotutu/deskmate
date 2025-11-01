package top.yaotutu.deskmate.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// 使用 Material Design 3 标准 Typography
// 只针对此应用需求做必要的字体大小调整
val Typography = Typography(
    // 超大显示 - 用于时钟
    displayLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 96.sp
    ),
    // 中等显示 - 用于温度
    displayMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 52.sp
    )
    // 其他使用 Material Design 默认值
)