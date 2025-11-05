package top.yaotutu.deskmate.presentation.component.base

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * 动态自适应网格系统 - 基于屏幕尺寸的瓷砖布局
 *
 * 核心理念：
 * - min(宽度, 高度) ÷ 4 = 基础格子尺寸（正方形）
 * - 横屏时 min(宽, 高) 就是高度
 * - 根据屏幕宽度动态计算列数（2-8列）
 * - 动态间距填满整个宽度，无黑边
 * - 确保旋转后瓷砖尺寸保持一致
 *
 * @author Deskmate Team
 * @since 2.0
 */
object TileGrid {
    /**
     * 列数限制
     */
    const val MIN_COLUMNS = 2  // 最少2列
    const val MAX_COLUMNS = 8  // 最多8列

    /**
     * 基准：横屏高度的 1/4
     */
    const val BASE_GRID_ROWS = 4

    /**
     * 固定间距（符合 Metro / Material Design 最佳实践）
     * 水平和垂直间距保持一致，提供稳定的视觉节奏
     */
    const val FIXED_GAP = 8  // dp

    /**
     * 计算基础格子尺寸（正方形）
     *
     * 基于可用尺寸除以配置的行数，预留固定间距空间
     * 注：传入的 screenWidth/Height 应该是 BoxWithConstraints 的 maxWidth/maxHeight，
     * 已经减去了外层容器的 padding
     *
     * 公式：baseCellSize = (可用高度 - 间距总和) / rows
     * 其中：间距总和 = FIXED_GAP × (rows - 1)
     *
     * 最佳实践：固定间距（8dp），动态瓷砖尺寸
     *
     * @param screenWidth 可用宽度（BoxWithConstraints.maxWidth）
     * @param screenHeight 可用高度（BoxWithConstraints.maxHeight）
     * @param gridRows 网格行数（从配置文件读取），默认为 4
     * @return 基础格子边长
     * @throws IllegalArgumentException 如果参数不合法
     */
    fun calculateBaseCellSize(
        screenWidth: Dp,
        screenHeight: Dp,
        gridRows: Int = BASE_GRID_ROWS
    ): Dp {
        require(screenWidth > 0.dp && screenHeight > 0.dp) {
            "screenWidth 和 screenHeight 必须大于 0，当前值: width=$screenWidth, height=$screenHeight"
        }
        require(gridRows > 0) {
            "gridRows 必须大于 0，当前值: $gridRows"
        }

        // 基准尺寸 = min(可用宽度, 可用高度)
        val baseScreenSize = minOf(screenWidth.value, screenHeight.value)

        // 预留固定间距空间（rows - 1 个间距，每个 8dp）
        val totalGapSpace = FIXED_GAP * (gridRows - 1)

        // 实际可用于格子的空间
        val availableForCells = baseScreenSize - totalGapSpace

        return (availableForCells / gridRows).dp
    }

    /**
     * 计算实际列数（2-8 列）
     *
     * 根据屏幕宽度和基础格子尺寸计算理论列数，
     * 然后应用最小最大限制
     *
     * 公式：(宽度 + FIXED_GAP) / (格子尺寸 + FIXED_GAP)
     *
     * @param screenWidth 屏幕宽度
     * @param baseCellSize 基础格子尺寸
     * @return 实际列数（2-8）
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

        // 计算理论列数（使用固定间距 8dp）
        // 公式：(宽度 + 间距) / (格子尺寸 + 间距)
        val theoreticalColumns = ((screenWidth.value + FIXED_GAP) /
                (baseCellSize.value + FIXED_GAP)).toInt()

        // 应用限制：2-8 列
        return theoreticalColumns.coerceIn(MIN_COLUMNS, MAX_COLUMNS)
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
 * 网格容器 - 自动计算并提供网格参数
 *
 * 检测屏幕尺寸，计算基础格子尺寸、列数和固定间距，
 * 并通过回调传递给子组件
 *
 * 注：使用 BoxWithConstraints 获取可用尺寸，已经减去了外层容器的 padding
 *
 * 最佳实践：固定间距（8dp）+ 动态瓷砖尺寸
 *
 * @param modifier 修饰符
 * @param gridRows 网格行数（从配置文件读取），默认为 4
 * @param content 接收网格参数的内容 lambda
 */
@Composable
fun TileGridContainer(
    modifier: Modifier = Modifier,
    gridRows: Int = TileGrid.BASE_GRID_ROWS,
    content: @Composable (baseCellSize: Dp, fixedGap: Dp, columns: Int, screenHeight: Dp) -> Unit
) {
    BoxWithConstraints(modifier = modifier) {
        // 获取可用尺寸（BoxWithConstraints 自动减去了父容器的 padding）
        val screenWidth = maxWidth
        val screenHeight = maxHeight

        // 步骤1: 计算基础格子尺寸（预留固定间距 8dp）
        val baseCellSize = TileGrid.calculateBaseCellSize(screenWidth, screenHeight, gridRows)

        // 步骤2: 计算实际列数（基于固定间距 8dp）
        val columns = TileGrid.calculateColumns(screenWidth, baseCellSize)

        // 步骤3: 使用固定间距（8dp）
        val fixedGap = TileGrid.getFixedGap()

        // 传递参数给子组件
        content(baseCellSize, fixedGap, columns, screenHeight)
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
