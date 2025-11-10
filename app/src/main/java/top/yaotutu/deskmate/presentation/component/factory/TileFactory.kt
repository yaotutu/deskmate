package top.yaotutu.deskmate.presentation.component.factory

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import top.yaotutu.deskmate.data.model.TileConfig
import top.yaotutu.deskmate.data.model.TileRegistry
import top.yaotutu.deskmate.data.model.TileType
import top.yaotutu.deskmate.presentation.component.tiles.common.ErrorTile
import top.yaotutu.deskmate.presentation.component.tiles.common.TileErrorType
import top.yaotutu.deskmate.presentation.component.animation.StaggerEnterAnimation
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
     * @param onClick 点击回调
     * @param modifier 修饰符
     */
    @Composable
    fun CreateTile(
        config: TileConfig,
        uiState: DashboardUiState,
        index: Int,
        onClick: () -> Unit = {},
        modifier: Modifier = Modifier
    ) {
        Log.d("TileFactory", "创建瓷砖: ${config.type}:${config.variant} (${config.rows}×${config.columns})")  // ⭐ 修复：行×列

        // 使用 StaggerEnterAnimation 实现错峰入场动画
        StaggerEnterAnimation(index = index) {
            // 所有类型都使用变体系统
            CreateVariantTile(config, uiState, onClick, modifier)
        }
    }

    /**
     * 使用变体系统创建瓷砖
     */
    @Composable
    private fun CreateVariantTile(
        config: TileConfig,
        uiState: DashboardUiState,
        onClick: () -> Unit,
        modifier: Modifier
    ) {
        val spec = TileRegistry.get(config.type, config.variant)
        Log.d("TileFactory", "查找变体: ${config.type}:${config.variant} -> ${if (spec == null) "未找到" else "找到"}")

        when {
            spec == null -> {
                // 未知变体
                Log.e("TileFactory", "未知变体: ${config.type}:${config.variant}")
                ErrorTile(
                    columns = config.columns,
                    rows = config.rows,
                    errorType = TileErrorType.UNKNOWN_VARIANT,
                    message = "未知变体：${config.type}:${config.variant}",
                    details = mapOf("建议" to "检查配置文件拼写")
                )
            }

            spec.supportedSizes.isEmpty() -> {
                // 配置错误：变体未定义支持的尺寸
                Log.e("TileFactory", "配置错误: ${config.type}:${config.variant} 的 supportedSizes 为空")
                ErrorTile(
                    columns = config.columns,
                    rows = config.rows,
                    errorType = TileErrorType.CONFIG_ERROR,
                    message = "配置错误：变体未定义支持的尺寸",
                    details = mapOf(
                        "变体" to "${config.type}:${config.variant}",
                        "建议" to "在 TileRegistryInit 中为该变体添加 supportedSizes"
                    )
                )
            }

            !spec.supportedSizes.contains(config.rows to config.columns) -> {  // ⭐ 修复：(行, 列)
                // 尺寸不匹配
                Log.e("TileFactory", "尺寸不匹配: ${config.type}:${config.variant} 需要${spec.supportedSizes}, 配置${config.rows}×${config.columns}")
                ErrorTile(
                    columns = config.columns,
                    rows = config.rows,
                    errorType = TileErrorType.SIZE_MISMATCH,
                    message = "尺寸不匹配：${config.type}:${config.variant}",
                    details = mapOf(
                        "当前配置" to "${config.rows}×${config.columns}",  // ⭐ 修复：行×列
                        "支持的尺寸" to TileRegistry.getSupportedSizesString(config.type, config.variant)
                    )
                )
            }

            else -> {
                // 正常渲染
                Log.d("TileFactory", "正常渲染: ${config.type}:${config.variant}")
                // ✅ 直接渲染，让 BaseTile/Tile 自己处理尺寸
                spec.view(config, uiState, onClick)
            }
        }
    }
}
