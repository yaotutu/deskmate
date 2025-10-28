package top.yaotutu.deskmate.presentation.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * Windows Phone Metro 设计语言标准配色
 *
 * 这些颜色来自微软官方 Metro Design Language 规范，
 * 具有高饱和度、高对比度的特点，适合在深色背景上使用。
 */
object MetroColors {
    // === 主要颜色 (Primary Colors) ===

    /** 天蓝色 - 时钟、系统瓷砖的主色调 */
    val Blue = Color(0xFF0078D7)

    /** 深蓝色 - OneDrive、Office 等应用 */
    val DarkBlue = Color(0xFF00188F)

    /** 橙色 - 天气、音乐等瓷砖 */
    val Orange = Color(0xFFFF8C00)

    /** 绿色 - 日历、联系人等瓷砖 */
    val Green = Color(0xFF00A300)

    /** 紫色 - 待办、邮件等瓷砖 */
    val Purple = Color(0xFFAA00FF)

    /** 品红色 - OneNote、相机等应用 */
    val Magenta = Color(0xFFE3008C)

    /** 深红色 - 新闻、头条等瓷砖 */
    val Red = Color(0xFFE51400)

    /** 青色 - 消息、Skype 等应用 */
    val Cyan = Color(0xFF00ABA9)

    // === 辅助颜色 (Accent Colors) ===

    /** 棕色 - 文件管理器等 */
    val Brown = Color(0xFF825A2C)

    /** 橄榄绿 - 地图、导航等 */
    val Olive = Color(0xFF6D8764)

    /** 钢蓝色 - 设置、系统工具 */
    val Steel = Color(0xFF647687)

    /** 紫罗兰 - 视频、娱乐等 */
    val Violet = Color(0xFFAA00AA)

    /** 玫瑰红 */
    val Rose = Color(0xFFE671B8)

    /** 石灰绿 - 健康、运动等 */
    val Lime = Color(0xFFA4C400)

    /** 琥珀色 - 通知、提醒等 */
    val Amber = Color(0xFFF0A30A)

    /** 蓝绿色 - 天气、地球等 */
    val Teal = Color(0xFF1BA1E2)

    /** 靛蓝色 - 夜间模式等 */
    val Indigo = Color(0xFF6A00FF)

    // === 背景和文本颜色 (Backgrounds & Text) ===

    /** 纯黑背景 - Metro 应用的标准背景色 */
    val Background = Color(0xFF000000)

    /** 深灰色 - 次要背景、分割线 */
    val DarkGray = Color(0xFF1E1E1E)

    /** 白色文本 - 主要文本颜色 */
    val TextPrimary = Color(0xFFFFFFFF)

    /** 半透明白色 - 次要文本颜色 */
    val TextSecondary = Color(0xB3FFFFFF)  // 70% 不透明度

    /** 淡灰色文本 - 禁用状态、占位符 */
    val TextDisabled = Color(0x80FFFFFF)   // 50% 不透明度

    // === 状态颜色 (Status Colors) ===

    /** 成功状态 - 使用绿色 */
    val Success = Green

    /** 警告状态 - 使用琥珀色 */
    val Warning = Amber

    /** 错误状态 - 使用深红色 */
    val Error = Red

    /** 信息状态 - 使用蓝色 */
    val Info = Blue
}

/**
 * Metro 瓷砖预设配色方案
 *
 * 根据内容类型提供推荐的瓷砖颜色
 */
object MetroTileColors {
    /** 时间相关瓷砖（时钟、日历、倒计时等） */
    val Time = MetroColors.Blue

    /** 天气相关瓷砖 */
    val Weather = MetroColors.Orange

    /** 日历、日期相关瓷砖 */
    val Calendar = MetroColors.Green

    /** 待办事项、任务列表 */
    val Todo = MetroColors.Purple

    /** 新闻、头条、资讯 */
    val News = MetroColors.Red

    /** 照片、图库、相机 */
    val Photo = MetroColors.Magenta

    /** 联系人、通讯录 */
    val Contact = MetroColors.Cyan

    /** 音乐、播放器 */
    val Music = MetroColors.Orange

    /** 邮件、消息 */
    val Mail = MetroColors.Teal

    /** 视频、电影 */
    val Video = MetroColors.Violet

    /** 设置、系统 */
    val Settings = MetroColors.Steel

    /** 地图、导航 */
    val Map = MetroColors.Olive

    /** 健康、运动 */
    val Health = MetroColors.Lime

    /** 笔记、文档 */
    val Note = MetroColors.Purple
}
