package top.yaotutu.deskmate.presentation.ui.component.tiles

import top.yaotutu.deskmate.data.model.TileRegistry
import top.yaotutu.deskmate.data.model.TileVariantSpec
import top.yaotutu.deskmate.presentation.ui.component.tiles.clock.ClockDetailedTile
import top.yaotutu.deskmate.presentation.ui.component.tiles.clock.ClockSimpleTile
import top.yaotutu.deskmate.presentation.ui.component.tiles.clock.ClockStandardTile

/**
 * 注册所有瓷砖变体
 *
 * 应在应用启动时调用一次
 */
fun registerAllTileVariants() {
    registerClockVariants()
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
            view = { _, uiState ->
                ClockSimpleTile(
                    time = uiState.currentTime
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
            view = { _, uiState ->
                ClockStandardTile(
                    time = uiState.currentTime,
                    date = uiState.currentDate
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
            view = { _, uiState ->
                ClockDetailedTile(
                    time = uiState.currentTime,
                    date = uiState.currentDate,
                    lunarDate = uiState.lunarDate
                )
            }
        )
    )
}
