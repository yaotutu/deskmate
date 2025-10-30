package top.yaotutu.deskmate.presentation.ui.component.factory

import top.yaotutu.deskmate.data.model.TileRegistry
import top.yaotutu.deskmate.data.model.TileVariantSpec
import top.yaotutu.deskmate.presentation.ui.component.tiles.clock.*
import top.yaotutu.deskmate.presentation.ui.component.tiles.animationdemo.*

/**
 * 注册所有瓷砖变体
 *
 * 应在应用启动时调用一次
 */
fun registerAllTileVariants() {
    registerClockVariants()
    registerAnimationDemoVariants()
    // 未来扩展：
    // registerWeatherVariants()
    // registerCalendarVariants()
    // registerTodoVariants()
    // registerNewsVariants()
}

/**
 * 注册时钟瓷砖的所有变体
 */
private fun registerClockVariants() {
    // simple 变体 (1×1)
    TileRegistry.register(
        TileVariantSpec(
            type = "clock",
            variant = "simple",
            supportedSizes = listOf(1 to 1),
            defaultSize = 1 to 1,
            view = { _, uiState, onClick ->
                ClockSimpleTile(
                    time = uiState.currentTime,
                    onClick = onClick
                )
            }
        )
    )

    // standard 变体 (2×2)
    TileRegistry.register(
        TileVariantSpec(
            type = "clock",
            variant = "standard",
            supportedSizes = listOf(2 to 2),
            defaultSize = 2 to 2,
            view = { _, uiState, onClick ->
                ClockStandardTile(
                    time = uiState.currentTime,
                    date = uiState.currentDate,
                    onClick = onClick
                )
            }
        )
    )

    // detailed 变体 (4×2)
    TileRegistry.register(
        TileVariantSpec(
            type = "clock",
            variant = "detailed",
            supportedSizes = listOf(4 to 2),
            defaultSize = 4 to 2,
            view = { _, uiState, onClick ->
                ClockDetailedTile(
                    time = uiState.currentTime,
                    date = uiState.currentDate,
                    lunarDate = uiState.lunarDate,
                    onClick = onClick
                )
            }
        )
    )

    // large 变体 (4×4) - 新增
    TileRegistry.register(
        TileVariantSpec(
            type = "clock",
            variant = "large",
            supportedSizes = listOf(4 to 4),
            defaultSize = 4 to 4,
            view = { _, uiState, onClick ->
                ClockLargeTile(
                    time = uiState.currentTime,
                    date = uiState.currentDate,
                    weekday = "星期二",  // TODO: 从 uiState 获取
                    lunarDate = uiState.lunarDate,
                    onClick = onClick
                )
            }
        )
    )

    // tall 变体 (2×4) - 新增
    TileRegistry.register(
        TileVariantSpec(
            type = "clock",
            variant = "tall",
            supportedSizes = listOf(2 to 4),
            defaultSize = 2 to 4,
            view = { _, uiState, onClick ->
                ClockTallTile(
                    time = uiState.currentTime,
                    date = uiState.currentDate,
                    weekday = "星期二",  // TODO: 从 uiState 获取
                    onClick = onClick
                )
            }
        )
    )

    // compact 变体 (2×1) - 新增
    TileRegistry.register(
        TileVariantSpec(
            type = "clock",
            variant = "compact",
            supportedSizes = listOf(2 to 1),
            defaultSize = 2 to 1,
            view = { _, uiState, onClick ->
                ClockCompactTile(
                    time = uiState.currentTime,
                    date = "10/28",  // TODO: 格式化日期
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

    // 2. FLIP - 翻转动画 (4×2)
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
