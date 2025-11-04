package top.yaotutu.deskmate.presentation.component.factory

import top.yaotutu.deskmate.data.model.TileRegistry
import top.yaotutu.deskmate.data.model.TileVariantSpec
import top.yaotutu.deskmate.presentation.component.tiles.clock.*
import top.yaotutu.deskmate.presentation.component.tiles.animationdemo.*
import top.yaotutu.deskmate.presentation.component.tiles.weather.*
import top.yaotutu.deskmate.presentation.component.tiles.calendar.*
import top.yaotutu.deskmate.presentation.component.tiles.todo.*
import top.yaotutu.deskmate.presentation.component.tiles.news.*
import top.yaotutu.deskmate.presentation.component.base.BaseTile
import top.yaotutu.deskmate.presentation.component.base.TileSpec
import top.yaotutu.deskmate.presentation.component.base.AnimationType
import top.yaotutu.deskmate.presentation.component.base.presets.*
import top.yaotutu.deskmate.presentation.theme.MetroTileColors
import top.yaotutu.deskmate.presentation.theme.MetroColors

/**
 * 注册所有瓷砖变体
 *
 * 应在应用启动时调用一次
 */
fun registerAllTileVariants() {
    registerClockVariants()
    registerAnimationDemoVariants()
    registerPresetsDemoVariants()
    registerWeatherVariants()
    registerCalendarVariants()
    registerTodoVariants()
    registerNewsVariants()
}

/**
 * 注册时钟瓷砖的所有变体
 */
private fun registerClockVariants() {
    // 1×1 变体
    TileRegistry.register(
        TileVariantSpec(
            type = "clock",
            variant = "1x1",
            supportedSizes = listOf(1 to 1),
            defaultSize = 1 to 1,
            view = { _, uiState, onClick ->
                Clock1x1Tile(
                    time = uiState.currentTime,
                    onClick = onClick
                )
            }
        )
    )

    // 2×1 变体
    TileRegistry.register(
        TileVariantSpec(
            type = "clock",
            variant = "1x2",
            supportedSizes = listOf(2 to 1),
            defaultSize = 2 to 1,
            view = { _, uiState, onClick ->
                Clock2x1Tile(
                    time = uiState.currentTime,
                    date = "10/28",  // TODO: 格式化日期
                    onClick = onClick
                )
            }
        )
    )

    // 2×2 变体
    TileRegistry.register(
        TileVariantSpec(
            type = "clock",
            variant = "2x2",
            supportedSizes = listOf(2 to 2),
            defaultSize = 2 to 2,
            view = { _, uiState, onClick ->
                Clock2x2Tile(
                    time = uiState.currentTime,
                    date = uiState.currentDate,
                    weekday = uiState.currentWeekday,
                    onClick = onClick
                )
            }
        )
    )

    // 2×4 变体
    TileRegistry.register(
        TileVariantSpec(
            type = "clock",
            variant = "4x2",
            supportedSizes = listOf(2 to 4),
            defaultSize = 2 to 4,
            view = { _, uiState, onClick ->
                Clock2x4Tile(
                    time = uiState.currentTime,
                    date = uiState.currentDate,
                    weekday = "星期二",  // TODO: 从 uiState 获取
                    onClick = onClick
                )
            }
        )
    )

    // 4×2 变体
    TileRegistry.register(
        TileVariantSpec(
            type = "clock",
            variant = "2x4",
            supportedSizes = listOf(4 to 2),
            defaultSize = 4 to 2,
            view = { _, uiState, onClick ->
                Clock4x2Tile(
                    time = uiState.currentTime,
                    date = uiState.currentDate,
                    lunarDate = uiState.lunarDate,
                    onClick = onClick
                )
            }
        )
    )

    // 4×4 变体
    TileRegistry.register(
        TileVariantSpec(
            type = "clock",
            variant = "4x4",
            supportedSizes = listOf(4 to 4),
            defaultSize = 4 to 4,
            view = { _, uiState, onClick ->
                Clock4x4Tile(
                    time = uiState.currentTime,
                    date = uiState.currentDate,
                    weekday = "星期二",  // TODO: 从 uiState 获取
                    lunarDate = uiState.lunarDate,
                    onClick = onClick
                )
            }
        )
    )
}

/**
 * 注册动画演示瓷砖的所有变体
 *
 * 包含所有 10 种 Metro 动画类型的演示
 */
private fun registerAnimationDemoVariants() {
    // 1. NONE - 无动画 (1×1 小尺寸)
    TileRegistry.register(
        TileVariantSpec(
            type = "animation_demo",
            variant = "none_small",
            supportedSizes = listOf(1 to 1),
            defaultSize = 1 to 1,
            view = { _, _, onClick ->
                AnimationDemoNoneSmall(onClick = onClick)
            }
        )
    )

    // 1. NONE - 无动画 (2×2)
    TileRegistry.register(
        TileVariantSpec(
            type = "animation_demo",
            variant = "none",
            supportedSizes = listOf(2 to 2),
            defaultSize = 2 to 2,
            view = { _, _, onClick ->
                AnimationDemoNone(onClick = onClick)
            }
        )
    )

    // 2. FLIP - 翻转动画 (2×4)
    TileRegistry.register(
        TileVariantSpec(
            type = "animation_demo",
            variant = "flip",
            supportedSizes = listOf(4 to 2),
            defaultSize = 4 to 2,
            view = { _, _, onClick ->
                AnimationDemoFlip(onClick = onClick)
            }
        )
    )

    // 3. PULSE - 脉冲动画 (2×2)
    TileRegistry.register(
        TileVariantSpec(
            type = "animation_demo",
            variant = "pulse",
            supportedSizes = listOf(2 to 2),
            defaultSize = 2 to 2,
            view = { _, _, onClick ->
                AnimationDemoPulse(onClick = onClick)
            }
        )
    )

    // 4. SLIDE - 滑动动画 (4×4)
    TileRegistry.register(
        TileVariantSpec(
            type = "animation_demo",
            variant = "slide",
            supportedSizes = listOf(4 to 4),
            defaultSize = 4 to 4,
            view = { _, _, onClick ->
                AnimationDemoSlide(onClick = onClick)
            }
        )
    )

    // 5. FADE - 淡入淡出动画 (2×2)
    TileRegistry.register(
        TileVariantSpec(
            type = "animation_demo",
            variant = "fade",
            supportedSizes = listOf(2 to 2),
            defaultSize = 2 to 2,
            view = { _, _, onClick ->
                AnimationDemoFade(onClick = onClick)
            }
        )
    )

    // 6. COUNTER - 数字滚动动画 (2×2)
    TileRegistry.register(
        TileVariantSpec(
            type = "animation_demo",
            variant = "counter",
            supportedSizes = listOf(2 to 2),
            defaultSize = 2 to 2,
            view = { _, uiState, onClick ->
                // 使用温度作为计数器演示
                AnimationDemoCounter(targetValue = uiState.temperature, onClick = onClick)
            }
        )
    )

    // 7. ROTATE - 旋转动画 (2×2)
    TileRegistry.register(
        TileVariantSpec(
            type = "animation_demo",
            variant = "rotate",
            supportedSizes = listOf(2 to 2),
            defaultSize = 2 to 2,
            view = { _, _, onClick ->
                AnimationDemoRotate(onClick = onClick)
            }
        )
    )

    // 8. BOUNCE - 弹跳动画 (2×2)
    TileRegistry.register(
        TileVariantSpec(
            type = "animation_demo",
            variant = "bounce",
            supportedSizes = listOf(2 to 2),
            defaultSize = 2 to 2,
            view = { _, _, onClick ->
                AnimationDemoBounce(onClick = onClick)
            }
        )
    )

    // 9. SHAKE - 抖动动画 (2×2)
    TileRegistry.register(
        TileVariantSpec(
            type = "animation_demo",
            variant = "shake",
            supportedSizes = listOf(2 to 2),
            defaultSize = 2 to 2,
            view = { _, _, onClick ->
                AnimationDemoShake(onClick = onClick)
            }
        )
    )

    // 10. SHIMMER - 微光动画 (2×2)
    TileRegistry.register(
        TileVariantSpec(
            type = "animation_demo",
            variant = "shimmer",
            supportedSizes = listOf(2 to 2),
            defaultSize = 2 to 2,
            view = { _, _, onClick ->
                AnimationDemoShimmer(onClick = onClick)
            }
        )
    )
}

/**
 * 注册预设系统演示瓷砖
 *
 * 这些瓷砖展示如何使用预设系统
 */
private fun registerPresetsDemoVariants() {

    // === SmallTilePresets 演示 ===

    // 图标瓷砖 (1×1)
    TileRegistry.register(
        TileVariantSpec(
            type = "demo_icon",
            variant = "icon_only",
            supportedSizes = listOf(1 to 1),
            defaultSize = 1 to 1,
            view = { _, _, onClick ->
                BaseTile(
                    spec = TileSpec.small(MetroColors.Cyan),
                    onClick = onClick
                ) {
                    SmallTilePresets.IconOnly(icon = "📱")
                }
            }
        )
    )

    // === CompactTilePresets 演示 ===

    // 进度条 (1×2)
    TileRegistry.register(
        TileVariantSpec(
            type = "demo_progress",
            variant = "progress",
            supportedSizes = listOf(2 to 1),
            defaultSize = 2 to 1,
            view = { _, _, onClick ->
                BaseTile(
                    spec = TileSpec(2, 1, MetroColors.Green),
                    onClick = onClick
                ) {
                    CompactTilePresets.ProgressBar(
                        label = "下载中",
                        progress = "75%"
                    )
                }
            }
        )
    )

    // === MediumTilePresets 演示 ===

    // 天气计数器 (2×2) - 自动获得 COUNTER 动画
    TileRegistry.register(
        TileVariantSpec(
            type = "demo_weather",
            variant = "counter",
            supportedSizes = listOf(2 to 2),
            defaultSize = 2 to 2,
            view = { _, uiState, onClick ->
                BaseTile(
                    spec = TileSpec.square(MetroTileColors.Weather),
                    onClick = onClick
                ) {
                    MediumTilePresets.Counter(
                        value = uiState.temperature.toString(),
                        unit = "°",
                        label = "温度"
                        // 自动获得 COUNTER 动画！
                    )
                }
            }
        )
    )

    // 步数计数器 (2×2) - 自动获得 COUNTER 动画
    TileRegistry.register(
        TileVariantSpec(
            type = "demo_steps",
            variant = "counter",
            supportedSizes = listOf(2 to 2),
            defaultSize = 2 to 2,
            view = { _, _, onClick ->
                BaseTile(
                    spec = TileSpec.square(MetroColors.Lime),
                    onClick = onClick
                ) {
                    MediumTilePresets.Counter(
                        value = "8,456",
                        label = "步数"
                        // 自动获得 COUNTER 动画！
                    )
                }
            }
        )
    )

    // 音乐播放器 (2×2)
    TileRegistry.register(
        TileVariantSpec(
            type = "demo_music",
            variant = "music",
            supportedSizes = listOf(2 to 2),
            defaultSize = 2 to 2,
            view = { _, _, onClick ->
                BaseTile(
                    spec = TileSpec.square(MetroTileColors.Music),
                    onClick = onClick
                ) {
                    MediumTilePresets.IconTitleSubtitle(
                        icon = "🎵",
                        title = "美好的一天",
                        subtitle = "陈奕迅"
                    )
                }
            }
        )
    )

    // 照片 (2×2)
    TileRegistry.register(
        TileVariantSpec(
            type = "demo_photo",
            variant = "photo",
            supportedSizes = listOf(2 to 2),
            defaultSize = 2 to 2,
            view = { _, _, onClick ->
                BaseTile(
                    spec = TileSpec.square(MetroTileColors.Photo),
                    onClick = onClick
                ) {
                    MediumTilePresets.IconTitle(
                        icon = "📷",
                        title = "相册"
                    )
                }
            }
        )
    )

    // 联系人 (2×2)
    TileRegistry.register(
        TileVariantSpec(
            type = "demo_contact",
            variant = "icon_title",
            supportedSizes = listOf(2 to 2),
            defaultSize = 2 to 2,
            view = { _, _, onClick ->
                BaseTile(
                    spec = TileSpec.square(MetroTileColors.Contact),
                    onClick = onClick
                ) {
                    MediumTilePresets.IconTitle(
                        icon = "👤",
                        title = "联系人"
                    )
                }
            }
        )
    )

    // 通知 (2×2)
    TileRegistry.register(
        TileVariantSpec(
            type = "demo_notification",
            variant = "header_body",
            supportedSizes = listOf(2 to 2),
            defaultSize = 2 to 2,
            view = { _, _, onClick ->
                BaseTile(
                    spec = TileSpec.square(MetroColors.Purple),
                    onClick = onClick
                ) {
                    MediumTilePresets.HeaderBody(
                        header = "通知",
                        body = "3 条新消息"
                    )
                }
            }
        )
    )

    // 状态卡片 1-3 (2×2)
    TileRegistry.register(
        TileVariantSpec(
            type = "demo_status1",
            variant = "icon_title_subtitle",
            supportedSizes = listOf(2 to 2),
            defaultSize = 2 to 2,
            view = { _, _, onClick ->
                BaseTile(
                    spec = TileSpec.square(MetroColors.Teal),
                    onClick = onClick
                ) {
                    MediumTilePresets.IconTitleSubtitle(
                        icon = "🔔",
                        title = "提醒",
                        subtitle = "5 个未完成"
                    )
                }
            }
        )
    )

    TileRegistry.register(
        TileVariantSpec(
            type = "demo_status2",
            variant = "icon_title_subtitle",
            supportedSizes = listOf(2 to 2),
            defaultSize = 2 to 2,
            view = { _, _, onClick ->
                BaseTile(
                    spec = TileSpec.square(MetroColors.Indigo),
                    onClick = onClick
                ) {
                    MediumTilePresets.IconTitleSubtitle(
                        icon = "📊",
                        title = "统计",
                        subtitle = "本周数据"
                    )
                }
            }
        )
    )

    TileRegistry.register(
        TileVariantSpec(
            type = "demo_status3",
            variant = "icon_title_subtitle",
            supportedSizes = listOf(2 to 2),
            defaultSize = 2 to 2,
            view = { _, _, onClick ->
                BaseTile(
                    spec = TileSpec.square(MetroColors.Violet),
                    onClick = onClick
                ) {
                    MediumTilePresets.IconTitleSubtitle(
                        icon = "⚙️",
                        title = "设置",
                        subtitle = "系统配置"
                    )
                }
            }
        )
    )

    // 照片网格 (2×4)
    TileRegistry.register(
        TileVariantSpec(
            type = "demo_photos",
            variant = "icon_grid",
            supportedSizes = listOf(4 to 2),
            defaultSize = 4 to 2,
            view = { _, _, onClick ->
                BaseTile(
                    spec = TileSpec.wideMedium(MetroColors.Rose),
                    onClick = onClick
                ) {
                    MediumTilePresets.IconGrid2x2(
                        icons = listOf("📷", "🖼️", "🎨", "📸")
                    )
                }
            }
        )
    )

    // === WideTilePresets 演示 ===

    // 媒体播放器 (2×4)
    TileRegistry.register(
        TileVariantSpec(
            type = "demo_media",
            variant = "media_player",
            supportedSizes = listOf(4 to 2),
            defaultSize = 4 to 2,
            view = { _, _, onClick ->
                BaseTile(
                    spec = TileSpec.wideMedium(MetroTileColors.Music),
                    onClick = onClick
                ) {
                    WideTilePresets.MediaPlayer(
                        icon = "▶",
                        title = "晴天",
                        artist = "周杰伦",
                        duration = "4:29"
                    )
                }
            }
        )
    )

    // 时间线 (2×4)
    TileRegistry.register(
        TileVariantSpec(
            type = "demo_timeline",
            variant = "timeline",
            supportedSizes = listOf(4 to 2),
            defaultSize = 4 to 2,
            view = { _, _, onClick ->
                BaseTile(
                    spec = TileSpec.wideMedium(MetroColors.Olive),
                    onClick = onClick
                ) {
                    WideTilePresets.Timeline(
                        items = listOf(
                            "09:00" to "晨会",
                            "14:00" to "项目讨论",
                            "16:30" to "代码评审"
                        )
                    )
                }
            }
        )
    )

    // 三列数据 (2×4)
    TileRegistry.register(
        TileVariantSpec(
            type = "demo_metrics",
            variant = "three_columns",
            supportedSizes = listOf(4 to 2),
            defaultSize = 4 to 2,
            view = { _, _, onClick ->
                BaseTile(
                    spec = TileSpec.wideMedium(MetroColors.Steel),
                    onClick = onClick
                ) {
                    WideTilePresets.ThreeColumns(
                        items = listOf(
                            Triple("心率", "72", "bpm"),
                            Triple("血压", "120", "mmHg"),
                            Triple("血氧", "98", "%")
                        )
                    )
                }
            }
        )
    )

    // === TallTilePresets 演示 ===

    // 待办列表 (4×2)
    TileRegistry.register(
        TileVariantSpec(
            type = "demo_todo",
            variant = "list",
            supportedSizes = listOf(2 to 4),
            defaultSize = 2 to 4,
            view = { _, _, onClick ->
                BaseTile(
                    spec = TileSpec.tall(MetroTileColors.Todo),
                    onClick = onClick
                ) {
                    TallTilePresets.VerticalList(
                        items = listOf(
                            "完成项目文档",
                            "代码评审",
                            "团队会议",
                            "更新测试用例",
                            "优化性能"
                        )
                    )
                }
            }
        )
    )

    // 天气预报 (4×2)
    TileRegistry.register(
        TileVariantSpec(
            type = "demo_forecast",
            variant = "weather_forecast",
            supportedSizes = listOf(2 to 4),
            defaultSize = 2 to 4,
            view = { _, _, onClick ->
                BaseTile(
                    spec = TileSpec.tall(MetroTileColors.Weather),
                    onClick = onClick
                ) {
                    TallTilePresets.WeatherForecast(
                        forecasts = listOf(
                            Triple("周一", "☀️", "25°"),
                            Triple("周二", "🌤️", "23°"),
                            Triple("周三", "🌧️", "18°"),
                            Triple("周四", "⛅", "22°")
                        )
                    )
                }
            }
        )
    )

    // === LargeTilePresets 演示 ===

    // 仪表盘 (4×4)
    TileRegistry.register(
        TileVariantSpec(
            type = "demo_dashboard",
            variant = "dashboard",
            supportedSizes = listOf(4 to 4),
            defaultSize = 4 to 4,
            view = { _, _, onClick ->
                BaseTile(
                    spec = TileSpec.large(MetroColors.DarkBlue),
                    onClick = onClick
                ) {
                    LargeTilePresets.Dashboard(
                        title = "系统监控",
                        metrics = listOf(
                            Triple("CPU", "45", "%"),
                            Triple("内存", "68", "%"),
                            Triple("磁盘", "82", "%"),
                            Triple("网络", "12", "MB/s"),
                            Triple("温度", "56", "°C"),
                            Triple("电量", "85", "%")
                        )
                    )
                }
            }
        )
    )

    // 新闻列表 (4×4)
    TileRegistry.register(
        TileVariantSpec(
            type = "demo_news",
            variant = "news_list",
            supportedSizes = listOf(4 to 4),
            defaultSize = 4 to 4,
            view = { _, _, onClick ->
                BaseTile(
                    spec = TileSpec.large(MetroTileColors.News),
                    onClick = onClick
                ) {
                    LargeTilePresets.NewsList(
                        title = "今日头条",
                        items = listOf(
                            "科技新闻" to "AI技术突破性进展，自然语言处理达到新高度",
                            "财经要闻" to "股市今日震荡上行，科技股表现强劲",
                            "体育快讯" to "本地球队夺得联赛冠军，创造历史最佳战绩",
                            "娱乐八卦" to "新片上映首日票房破亿，口碑爆棚引热议"
                        )
                    )
                }
            }
        )
    )
}

/**
 * 注册天气瓷砖的所有变体
 */
fun registerWeatherVariants() {
    // 小型天气瓷砖 (1×1)
    TileRegistry.register(
        TileVariantSpec(
            type = "weather",
            variant = "1x1",
            supportedSizes = listOf(1 to 1),
            defaultSize = 1 to 1,
            view = { _, uiState, onClick ->
                Weather1x1Tile(
                    icon = "☀️",
                    onClick = onClick
                )
            }
        )
    )

    // 紧凑天气瓷砖 (1×2)
    TileRegistry.register(
        TileVariantSpec(
            type = "weather",
            variant = "1x2",
            supportedSizes = listOf(2 to 1),
            defaultSize = 2 to 1,
            view = { _, uiState, onClick ->
                Weather1x2Tile(
                    temperature = uiState.temperature,
                    condition = "晴朗",
                    onClick = onClick
                )
            }
        )
    )

    // 标准天气瓷砖 (2×2)
    TileRegistry.register(
        TileVariantSpec(
            type = "weather",
            variant = "2x2",
            supportedSizes = listOf(2 to 2),
            defaultSize = 2 to 2,
            view = { config, uiState, onClick ->
                WeatherStandardTile(
                    temperature = uiState.temperature,
                    condition = "晴朗",
                    onClick = onClick
                )
            }
        )
    )

    // 详细天气瓷砖 (2×4)
    TileRegistry.register(
        TileVariantSpec(
            type = "weather",
            variant = "2x4",
            supportedSizes = listOf(4 to 2),
            defaultSize = 4 to 2,
            view = { config, uiState, onClick ->
                WeatherDetailedTile(
                    icon = "☀️",
                    title = "晴朗 ${uiState.temperature}°",
                    details = "湿度 65% | 风速 12km/h",
                    forecast = "明日最高 28°",
                    onClick = onClick
                )
            }
        )
    )

    // 高版天气瓷砖 (4×2)
    TileRegistry.register(
        TileVariantSpec(
            type = "weather",
            variant = "4x2",
            supportedSizes = listOf(2 to 4),
            defaultSize = 2 to 4,
            view = { _, uiState, onClick ->
                Weather4x2Tile(
                    forecasts = listOf(
                        Triple("周一", "☀️", "25°"),
                        Triple("周二", "🌤️", "23°"),
                        Triple("周三", "🌧️", "18°"),
                        Triple("周四", "⛅", "22°")
                    ),
                    onClick = onClick
                )
            }
        )
    )

    // 大型天气瓷砖 (4×4)
    TileRegistry.register(
        TileVariantSpec(
            type = "weather",
            variant = "4x4",
            supportedSizes = listOf(4 to 4),
            defaultSize = 4 to 4,
            view = { _, uiState, onClick ->
                Weather4x4Tile(
                    metrics = listOf(
                        Triple("温度", "${uiState.temperature}", "°"),
                        Triple("湿度", "65", "%"),
                        Triple("风速", "12", "km/h"),
                        Triple("气压", "1013", "hPa")
                    ),
                    onClick = onClick
                )
            }
        )
    )
}

/**
 * 注册日历瓷砖的所有变体
 */
fun registerCalendarVariants() {
    // 小型日历瓷砖 (1×1)
    TileRegistry.register(
        TileVariantSpec(
            type = "calendar",
            variant = "1x1",
            supportedSizes = listOf(1 to 1),
            defaultSize = 1 to 1,
            view = { _, _, onClick ->
                Calendar1x1Tile(
                    icon = "📅",
                    onClick = onClick
                )
            }
        )
    )

    // 紧凑日历瓷砖 (1×2)
    TileRegistry.register(
        TileVariantSpec(
            type = "calendar",
            variant = "1x2",
            supportedSizes = listOf(2 to 1),
            defaultSize = 2 to 1,
            view = { _, uiState, onClick ->
                Calendar1x2Tile(
                    day = uiState.currentDay.toString(),
                    weekday = "周五",
                    onClick = onClick
                )
            }
        )
    )

    // 标准日历瓷砖 (2×2)
    TileRegistry.register(
        TileVariantSpec(
            type = "calendar",
            variant = "2x2",
            supportedSizes = listOf(2 to 2),
            defaultSize = 2 to 2,
            view = { config, uiState, onClick ->
                // 优先显示节日或节气，其次显示农历日期
                val backLabel = when {
                    !uiState.lunarFestival.isNullOrEmpty() -> uiState.lunarFestival
                    !uiState.lunarSolarTerm.isNullOrEmpty() -> uiState.lunarSolarTerm
                    else -> uiState.lunarDayName
                }

                CalendarStandardTile(
                    dayNumber = uiState.currentDayNumber,
                    monthName = uiState.currentMonthName,
                    lunarDayName = backLabel,
                    onClick = onClick
                )
            }
        )
    )

    // 黄历瓷砖 (2×2) - 紧凑的传统黄历
    TileRegistry.register(
        TileVariantSpec(
            type = "calendar",
            variant = "almanac",
            supportedSizes = listOf(2 to 2),
            defaultSize = 2 to 2,
            view = { config, uiState, onClick ->
                // 构建紧凑的信息
                val lunarInfo = when {
                    !uiState.lunarFestival.isNullOrEmpty() -> uiState.lunarFestival!!
                    !uiState.lunarSolarTerm.isNullOrEmpty() -> uiState.lunarSolarTerm!!
                    else -> uiState.lunarDayName
                }

                val ganZhi = "${uiState.lunarYearGanZhi}\n${uiState.lunarMonthGanZhi}月 ${uiState.lunarDayGanZhi}日"

                // 简化宜忌，取前3项
                val luckyItems = uiState.lunarDayLucky.split(" ").take(3).joinToString(" ")
                val avoidItems = uiState.lunarDayAvoid.split(" ").take(3).joinToString(" ")
                val luck = "宜: $luckyItems\n忌: $avoidItems"

                CalendarAlmanacTile(
                    dayNumber = uiState.currentDayNumber,
                    monthName = uiState.currentMonthName,
                    lunarInfo = lunarInfo,
                    ganZhi = ganZhi,
                    constellation = uiState.lunarConstellation,
                    luck = luck,
                    onClick = onClick
                )
            }
        )
    )

    // 宽版日历瓷砖 (2×4)
    TileRegistry.register(
        TileVariantSpec(
            type = "calendar",
            variant = "2x4",
            supportedSizes = listOf(4 to 2),
            defaultSize = 4 to 2,
            view = { _, _, onClick ->
                Calendar2x4Tile(
                    timeline = listOf(
                        "09:00" to "晨会",
                        "14:00" to "项目讨论",
                        "16:30" to "代码评审"
                    ),
                    onClick = onClick
                )
            }
        )
    )

    // 高版日历瓷砖 (4×2)
    TileRegistry.register(
        TileVariantSpec(
            type = "calendar",
            variant = "4x2",
            supportedSizes = listOf(2 to 4),
            defaultSize = 2 to 4,
            view = { _, _, onClick ->
                Calendar4x2Tile(
                    events = listOf(
                        "会议 10:00",
                        "午餐 12:00",
                        "健身 17:00",
                        "晚餐 19:00"
                    ),
                    onClick = onClick
                )
            }
        )
    )

    // 大型黄历瓷砖 (4×4) - 完整的传统黄历信息
    TileRegistry.register(
        TileVariantSpec(
            type = "calendar",
            variant = "4x4",
            supportedSizes = listOf(4 to 4),
            defaultSize = 4 to 4,
            view = { config, uiState, onClick ->
                // 构建农历字符串（去掉"农历"前缀，因为瓷砖中已有标签）
                val lunarDateWithoutPrefix = "${uiState.lunarYearGanZhi}${uiState.lunarMonthName}${uiState.lunarDayName}"

                Calendar4x4Tile(
                    solarDate = "${uiState.currentYear}年${uiState.currentMonth + 1}月${uiState.currentDay}日 ${uiState.currentWeekday}",
                    lunarDate = lunarDateWithoutPrefix,
                    ganZhi = "${uiState.lunarYearGanZhi} ${uiState.lunarMonthGanZhi}月 ${uiState.lunarDayGanZhi}日",
                    solarTerm = uiState.lunarSolarTerm,
                    festival = uiState.lunarFestival,
                    constellation = uiState.lunarConstellation,
                    dayLucky = uiState.lunarDayLucky,
                    dayAvoid = uiState.lunarDayAvoid,
                    onClick = onClick
                )
            }
        )
    )
}

/**
 * 注册待办瓷砖的所有变体
 */
fun registerTodoVariants() {
    // 小型待办瓷砖 (1×1)
    TileRegistry.register(
        TileVariantSpec(
            type = "todo",
            variant = "1x1",
            supportedSizes = listOf(1 to 1),
            defaultSize = 1 to 1,
            view = { _, _, onClick ->
                Todo1x1Tile(
                    icon = "✓",
                    onClick = onClick
                )
            }
        )
    )

    // 紧凑待办瓷砖 (1×2)
    TileRegistry.register(
        TileVariantSpec(
            type = "todo",
            variant = "1x2",
            supportedSizes = listOf(2 to 1),
            defaultSize = 2 to 1,
            view = { config, uiState, onClick ->
                TodoCompactTile(
                    label = "今日任务",
                    progress = "3/5 完成",
                    onClick = onClick
                )
            }
        )
    )

    // 标准待办瓷砖 (2×2)
    TileRegistry.register(
        TileVariantSpec(
            type = "todo",
            variant = "2x2",
            supportedSizes = listOf(2 to 2),
            defaultSize = 2 to 2,
            view = { _, _, onClick ->
                Todo2x2Tile(
                    completedCount = 3,
                    totalCount = 5,
                    onClick = onClick
                )
            }
        )
    )

    // 宽版待办瓷砖 (2×4)
    TileRegistry.register(
        TileVariantSpec(
            type = "todo",
            variant = "2x4",
            supportedSizes = listOf(4 to 2),
            defaultSize = 4 to 2,
            view = { _, _, onClick ->
                Todo2x4Tile(
                    metrics = listOf(
                        Triple("待办", "5", "项"),
                        Triple("进行中", "3", "项"),
                        Triple("已完成", "12", "项")
                    ),
                    onClick = onClick
                )
            }
        )
    )

    // 高版待办列表瓷砖 (4×2)
    TileRegistry.register(
        TileVariantSpec(
            type = "todo",
            variant = "4x2",
            supportedSizes = listOf(2 to 4),
            defaultSize = 2 to 4,
            view = { config, uiState, onClick ->
                TodoListTile(
                    items = listOf(
                        "完成项目报告",
                        "回复邮件",
                        "团队会议 15:00",
                        "健身锻炼",
                        "阅读专业书籍"
                    ),
                    onClick = onClick
                )
            }
        )
    )

    // 大型待办瓷砖 (4×4)
    TileRegistry.register(
        TileVariantSpec(
            type = "todo",
            variant = "4x4",
            supportedSizes = listOf(4 to 4),
            defaultSize = 4 to 4,
            view = { _, _, onClick ->
                Todo4x4Tile(
                    metrics = listOf(
                        Triple("今日", "5", "项"),
                        Triple("本周", "12", "项"),
                        Triple("完成", "87", "%"),
                        Triple("逾期", "2", "项")
                    ),
                    onClick = onClick
                )
            }
        )
    )
}

/**
 * 注册新闻瓷砖的所有变体
 */
fun registerNewsVariants() {
    // 小型新闻瓷砖 (1×1)
    TileRegistry.register(
        TileVariantSpec(
            type = "news",
            variant = "1x1",
            supportedSizes = listOf(1 to 1),
            defaultSize = 1 to 1,
            view = { _, _, onClick ->
                News1x1Tile(
                    icon = "📰",
                    onClick = onClick
                )
            }
        )
    )

    // 紧凑新闻瓷砖 (1×2)
    TileRegistry.register(
        TileVariantSpec(
            type = "news",
            variant = "1x2",
            supportedSizes = listOf(2 to 1),
            defaultSize = 2 to 1,
            view = { _, _, onClick ->
                News1x2Tile(
                    title = "科技突破：AI技术新进展",
                    time = "2小时前",
                    onClick = onClick
                )
            }
        )
    )

    // 标准新闻瓷砖 (2×2)
    TileRegistry.register(
        TileVariantSpec(
            type = "news",
            variant = "2x2",
            supportedSizes = listOf(2 to 2),
            defaultSize = 2 to 2,
            view = { _, _, onClick ->
                News2x2Tile(
                    icon = "📰",
                    title = "科技新闻",
                    summary = "AI技术取得突破性进展",
                    onClick = onClick
                )
            }
        )
    )

    // 详细新闻瓷砖 (2×4)
    TileRegistry.register(
        TileVariantSpec(
            type = "news",
            variant = "2x4",
            supportedSizes = listOf(4 to 2),
            defaultSize = 4 to 2,
            view = { config, uiState, onClick ->
                NewsTile(
                    icon = "📰",
                    title = "科技新闻：AI 技术突破性进展",
                    summary = "自然语言处理达到新高度，应用前景广阔",
                    time = "2小时前",
                    onClick = onClick
                )
            }
        )
    )

    // 高版新闻瓷砖 (4×2)
    TileRegistry.register(
        TileVariantSpec(
            type = "news",
            variant = "4x2",
            supportedSizes = listOf(2 to 4),
            defaultSize = 2 to 4,
            view = { _, _, onClick ->
                News4x2Tile(
                    headlines = listOf(
                        "科技：AI技术突破",
                        "财经：股市创新高",
                        "体育：中国队夺冠",
                        "娱乐：新片上映"
                    ),
                    onClick = onClick
                )
            }
        )
    )

    // 大型新闻瓷砖 (4×4)
    TileRegistry.register(
        TileVariantSpec(
            type = "news",
            variant = "4x4",
            supportedSizes = listOf(4 to 4),
            defaultSize = 4 to 4,
            view = { _, _, onClick ->
                News4x4Tile(
                    metrics = listOf(
                        Triple("科技", "12", "条"),
                        Triple("财经", "8", "条"),
                        Triple("体育", "5", "条"),
                        Triple("娱乐", "10", "条")
                    ),
                    onClick = onClick
                )
            }
        )
    )
}
