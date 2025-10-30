package top.yaotutu.deskmate.presentation.ui.component.factory

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import top.yaotutu.deskmate.data.model.TileConfig
import top.yaotutu.deskmate.data.model.TileRegistry
import top.yaotutu.deskmate.data.model.TileType
import top.yaotutu.deskmate.presentation.ui.component.tiles.common.ErrorTile
import top.yaotutu.deskmate.presentation.ui.component.tiles.common.TileErrorType
import top.yaotutu.deskmate.presentation.ui.component.animation.advanced.StaggerEnterAnimation
import top.yaotutu.deskmate.presentation.viewmodel.DashboardUiState

/**
 * 瓷砖工厂
 *
 * 负责：
 * 1. 根据配置中的 type + variant 映射到对应的 Composable 组件
 * 2. 验证配置的尺寸是否与变体匹配
 * 3. 从 ViewModel 的 UiState 中自动提取数据
 * 4. 支持入场动画
 *
 * @author Deskmate Team
 */
object TileFactory {

    /**
     * 创建瓷砖 Composable
     *
     * @param config 瓷砖配置
     * @param uiState UI 状态（来自 ViewModel）
     * @param index 瓷砖索引（用于入场动画延迟）
     * @param modifier 修饰符
     */
    @Composable
    fun CreateTile(
        config: TileConfig,
        uiState: DashboardUiState,
        index: Int,
        modifier: Modifier = Modifier
    ) {
        // 使用 StaggerEnterAnimation 实现错峰入场动画
        StaggerEnterAnimation(index = index) {
            // 对于 clock 和 animation_demo 类型，使用变体系统
            if (config.type == "clock" || config.type == "animation_demo") {
                CreateVariantTile(config, uiState, modifier)
            } else {
                // 其他类型保持原有逻辑（向后兼容）
                CreateLegacyTile(config, uiState, modifier)
            }
        }
    }

    /**
     * 使用变体系统创建瓷砖
     */
    @Composable
    private fun CreateVariantTile(
        config: TileConfig,
        uiState: DashboardUiState,
        modifier: Modifier
    ) {
        val spec = TileRegistry.get(config.type, config.variant)

        when {
            spec == null -> {
                // 未知变体
                ErrorTile(
                    columns = config.columns,
                    rows = config.rows,
                    errorType = TileErrorType.UNKNOWN_VARIANT,
                    message = "未知变体：${config.type}:${config.variant}",
                    details = mapOf("建议" to "检查配置文件拼写"),
                    modifier = modifier
                )
            }

            !spec.supportedSizes.contains(config.columns to config.rows) -> {
                // 尺寸不匹配
                ErrorTile(
                    columns = config.columns,
                    rows = config.rows,
                    errorType = TileErrorType.SIZE_MISMATCH,
                    message = "尺寸不匹配：${config.type}:${config.variant}",
                    details = mapOf(
                        "当前配置" to "${config.columns}×${config.rows}",
                        "支持的尺寸" to TileRegistry.getSupportedSizesString(config.type, config.variant)
                    ),
                    modifier = modifier
                )
            }

            else -> {
                // 正常渲染
                spec.view(config, uiState)
            }
        }
    }

    /**
     * 处理已废弃的遗留瓷砖类型
     *
     * 这些类型（weather, calendar, todo, news）已被移除，
     * 显示错误提示建议用户使用新的变体系统。
     */
    @Composable
    private fun CreateLegacyTile(
        config: TileConfig,
        uiState: DashboardUiState,
        modifier: Modifier
    ) {
        val deprecatedTypes = setOf("weather", "calendar", "todo", "news")

        if (config.type in deprecatedTypes) {
            ErrorTile(
                columns = config.columns,
                rows = config.rows,
                errorType = TileErrorType.DEPRECATED_TYPE,
                message = "瓷砖类型 '${config.type}' 已废弃",
                details = mapOf(
                    "原因" to "此类型已从项目中移除",
                    "建议" to "使用新的变体系统重新实现或从配置中删除"
                ),
                modifier = modifier
            )
        }
    }
}
