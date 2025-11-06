package top.yaotutu.deskmate.presentation.component.base

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import top.yaotutu.deskmate.presentation.theme.MetroScaleSystem
import top.yaotutu.deskmate.presentation.theme.ProvideScaleRatio

/**
 * 动态自适应网格系统 - 基于屏幕尺寸的瓷砖布局
 *
 * 核心理念（2025-01-06 重构）：
 * - 平板：min(宽度, 高度) ÷ 8 = 基础格子尺寸（8行固定）
 * - 手机：min(宽度, 高度) ÷ 4 = 基础格子尺寸（4行固定）
 * - 列数 = floor(宽度 / 基础格子尺寸)（动态计算）
 * - 间距固定 8dp（渲染时应用，计算时不预留）
 * - 确保旋转后瓷砖尺寸保持一致
 *
 * @author Deskmate Team
 * @since 2.0
 */
object TileGrid {
    /**
     * 固定行数（2025-01-06 重构）
     */
    const val TABLET_GRID_ROWS = 8  // 平板固定 8 行
    const val PHONE_GRID_ROWS = 4   // 手机固定 4 行

    /**
     * 固定间距（符合 Metro / Material Design 最佳实践）
     * 水平和垂直间距保持一致，提供稳定的视觉节奏
     */
    const val FIXED_GAP = 8  // dp

    /**
     * 计算基础格子尺寸（正方形）
     *
     * **2025-01-06 重构**：
     * - 基于设备类型确定固定行数（平板8行，手机4行）
     * - 直接除法，不预留间距（间距在渲染时应用）
     *
     * 公式：baseCellSize = min(宽度, 高度) / rows
     *
     * 注：传入的 screenWidth/Height 应该是 BoxWithConstraints 的 maxWidth/maxHeight，
     * 已经减去了外层容器的 padding
     *
     * @param screenWidth 可用宽度（BoxWithConstraints.maxWidth）
     * @param screenHeight 可用高度（BoxWithConstraints.maxHeight）
     * @param isTablet 是否为平板设备
     * @return 基础格子边长
     * @throws IllegalArgumentException 如果参数不合法
     */
    fun calculateBaseCellSize(
        screenWidth: Dp,
        screenHeight: Dp,
        isTablet: Boolean
    ): Dp {
        require(screenWidth > 0.dp && screenHeight > 0.dp) {
            "screenWidth 和 screenHeight 必须大于 0，当前值: width=$screenWidth, height=$screenHeight"
        }

        // 基准尺寸 = min(可用宽度, 可用高度)
        val baseScreenSize = minOf(screenWidth.value, screenHeight.value)

        // 根据设备类型确定固定行数
        val gridRows = if (isTablet) TABLET_GRID_ROWS else PHONE_GRID_ROWS

        // 直接除法，不预留间距
        return (baseScreenSize / gridRows).dp
    }

    /**
     * 计算实际列数
     *
     * **2025-01-06 重构**：
     * - 直接除法计算列数，移除范围限制
     *
     * 公式：columns = floor(宽度 / 基础格子尺寸)
     *
     * @param screenWidth 屏幕宽度
     * @param baseCellSize 基础格子尺寸
     * @return 实际列数
     * @throws IllegalArgumentException 如果任何参数 <= 0
     */
    fun calculateColumns(
        screenWidth: Dp,
        baseCellSize: Dp
    ): Int {
        require(screenWidth > 0.dp) {
            "screenWidth 必须大于 0，当前值: $screenWidth"
        }
        require(baseCellSize > 0.dp) {
            "baseCellSize 必须大于 0，当前值: $baseCellSize"
        }

        // 直接除法计算列数
        return (screenWidth.value / baseCellSize.value).toInt()
    }

    /**
     * 获取固定间距
     *
     * 最佳实践：使用固定间距（8dp），而非动态计算
     * 水平和垂直间距保持一致，提供稳定的视觉节奏
     *
     * @return 固定间距 8dp
     */
    fun getFixedGap(): Dp {
        return FIXED_GAP.dp
    }

    /**
     * 计算瓷砖实际宽度
     *
     * 宽度 = 格子尺寸 × 列数 + 间距 × (列数 - 1)
     *
     * @param baseCellSize 基础格子尺寸
     * @param gridColumns 瓷砖占用的列数
     * @param dynamicGap 动态间距
     * @return 瓷砖实际宽度
     */
    fun calculateTileWidth(
        baseCellSize: Dp,
        gridColumns: Int,
        dynamicGap: Dp
    ): Dp {
        return baseCellSize * gridColumns + dynamicGap * (gridColumns - 1)
    }

    /**
     * 计算瓷砖实际高度
     *
     * 高度 = 格子尺寸 × 行数 + 间距 × (行数 - 1)
     *
     * @param baseCellSize 基础格子尺寸
     * @param gridRows 瓷砖占用的行数
     * @param dynamicGap 动态间距
     * @return 瓷砖实际高度
     */
    fun calculateTileHeight(
        baseCellSize: Dp,
        gridRows: Int,
        dynamicGap: Dp
    ): Dp {
        return baseCellSize * gridRows + dynamicGap * (gridRows - 1)
    }
}

/**
 * 网格容器 - 自动计算并提供网格参数，支持全局缩放
 *
 * 检测屏幕尺寸，计算基础格子尺寸、列数和固定间距，
 * 并通过回调传递给子组件。
 *
 * **2025-01-06 重构**：
 * - 基于设备类型确定固定行数（平板8行，手机4行）
 * - baseCellSize = min(width, height) / rows（不预留间距）
 * - columns = floor(width / baseCellSize)
 * - 支持容器级全局缩放（类似前端 zoom）
 *
 * 注：使用 BoxWithConstraints 获取可用尺寸，已经减去了外层容器的 padding
 *
 * 最佳实践：固定间距（8dp）+ 动态瓷砖尺寸 + 容器级缩放
 *
 * @param modifier 修饰符
 * @param isTablet 是否为平板设备
 * @param enableScale 是否启用容器级缩放，默认为 true
 * @param content 接收网格参数的内容 lambda (baseCellSize, fixedGap, columns, gridRows)
 */
@Composable
fun TileGridContainer(
    modifier: Modifier = Modifier,
    isTablet: Boolean,
    enableScale: Boolean = true,
    content: @Composable (baseCellSize: Dp, fixedGap: Dp, columns: Int, gridRows: Int) -> Unit
) {
    BoxWithConstraints(modifier = modifier) {
        // 获取可用尺寸（BoxWithConstraints 自动减去了父容器的 padding）
        val screenWidth = maxWidth
        val screenHeight = maxHeight

        // 步骤1: 计算当前屏幕的 baseCellSize（不预留间距）
        val currentBaseCellSize = TileGrid.calculateBaseCellSize(screenWidth, screenHeight, isTablet)

        // 步骤2: 获取固定行数
        val gridRows = if (isTablet) TileGrid.TABLET_GRID_ROWS else TileGrid.PHONE_GRID_ROWS

        // 步骤3-7: 根据是否启用缩放，选择不同的布局策略
        if (enableScale) {
            // ===== 缩放模式：使用基准尺寸 + 容器级缩放 =====
            val scaleRatio = MetroScaleSystem.calculateScaleRatio(currentBaseCellSize, isTablet)
            val baseBaseCellSize = Dp(MetroScaleSystem.getBaseCellSize(isTablet))
            val baseColumns = TileGrid.calculateColumns(screenWidth / scaleRatio, baseBaseCellSize)
            val fixedGap = TileGrid.getFixedGap()
            val scaledWidth = MetroScaleSystem.calculateScaledSize(screenWidth, scaleRatio)
            val scaledHeight = MetroScaleSystem.calculateScaledSize(screenHeight, scaleRatio)

            android.util.Log.d("TileGridContainer", "=== 网格系统信息 (缩放模式) ===")
            android.util.Log.d("TileGridContainer", "设备类型: ${if (isTablet) "平板" else "手机"}")
            android.util.Log.d("TileGridContainer", "屏幕: ${screenWidth} × ${screenHeight}")
            android.util.Log.d("TileGridContainer", "网格: $gridRows 行 × $baseColumns 列")
            android.util.Log.d("TileGridContainer", "current baseCellSize: $currentBaseCellSize")
            android.util.Log.d("TileGridContainer", "base baseCellSize: $baseBaseCellSize")
            android.util.Log.d("TileGridContainer", "scaleRatio: $scaleRatio")

            ProvideScaleRatio(scaleRatio) {
                Box(
                    modifier = Modifier
                        .size(scaledWidth, scaledHeight)
                        .graphicsLayer {
                            this.scaleX = scaleRatio
                            this.scaleY = scaleRatio
                            this.transformOrigin = TransformOrigin(0f, 0f)
                        }
                ) {
                    content(baseBaseCellSize, fixedGap, baseColumns, gridRows)
                }
            }
        } else {
            // ===== 直接模式：使用实际计算的 baseCellSize =====
            val actualColumns = TileGrid.calculateColumns(screenWidth, currentBaseCellSize)
            val fixedGap = TileGrid.getFixedGap()

            android.util.Log.d("TileGridContainer", "=== 网格系统信息 (直接模式) ===")
            android.util.Log.d("TileGridContainer", "设备类型: ${if (isTablet) "平板" else "手机"}")
            android.util.Log.d("TileGridContainer", "屏幕: ${screenWidth} × ${screenHeight}")
            android.util.Log.d("TileGridContainer", "网格: $gridRows 行 × $actualColumns 列")
            android.util.Log.d("TileGridContainer", "baseCellSize: $currentBaseCellSize")
            android.util.Log.d("TileGridContainer", "fixedGap: $fixedGap")

            // 不应用缩放，直接使用 fillMaxSize
            ProvideScaleRatio(1f) {
                Box(modifier = Modifier.fillMaxSize()) {
                    content(currentBaseCellSize, fixedGap, actualColumns, gridRows)
                }
            }
        }
    }
}

// ==================== CompositionLocal 网格参数传递 ====================

/**
 * 网格参数 CompositionLocal
 *
 * 用于在组件树中传递网格参数，避免手动传递参数。
 * 这些参数由 ProvideTileGrid 提供，子组件通过 .current 访问。
 */
val LocalBaseCellSize = compositionLocalOf<Dp> { error("BaseCellSize not provided") }
val LocalDynamicGap = compositionLocalOf<Dp> { error("DynamicGap not provided") }
val LocalColumns = compositionLocalOf<Int> { error("Columns not provided") }

/**
 * 提供网格参数的容器
 *
 * 使用 CompositionLocalProvider 向子组件提供网格布局参数。
 * 子组件可以通过 LocalBaseCellSize.current 等方式访问这些参数。
 *
 * @param baseCellSize 基础格子尺寸（正方形边长）
 * @param dynamicGap 动态间距
 * @param columns 实际列数
 * @param content 子组件
 */
@Composable
fun ProvideTileGrid(
    baseCellSize: Dp,
    dynamicGap: Dp,
    columns: Int,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalBaseCellSize provides baseCellSize,
        LocalDynamicGap provides dynamicGap,
        LocalColumns provides columns
    ) {
        content()
    }
}
