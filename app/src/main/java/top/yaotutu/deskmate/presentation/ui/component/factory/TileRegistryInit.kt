package top.yaotutu.deskmate.presentation.ui.component.factory

import top.yaotutu.deskmate.data.model.TileRegistry
import top.yaotutu.deskmate.data.model.TileVariantSpec
import top.yaotutu.deskmate.presentation.ui.component.tiles.clock.*

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

    // large 变体 (4×4) - 新增
    TileRegistry.register(
        TileVariantSpec(
            type = "clock",
            variant = "large",
            supportedSizes = listOf(4 to 4),
            defaultSize = 4 to 4,
            view = { _, uiState ->
                ClockLargeTile(
                    time = uiState.currentTime,
                    date = uiState.currentDate,
                    weekday = "星期二",  // TODO: 从 uiState 获取
                    lunarDate = uiState.lunarDate
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
            view = { _, uiState ->
                ClockTallTile(
                    time = uiState.currentTime,
                    date = uiState.currentDate,
                    weekday = "星期二"  // TODO: 从 uiState 获取
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
            view = { _, uiState ->
                ClockCompactTile(
                    time = uiState.currentTime,
                    date = "10/28"  // TODO: 格式化日期
                )
            }
        )
    )
}
