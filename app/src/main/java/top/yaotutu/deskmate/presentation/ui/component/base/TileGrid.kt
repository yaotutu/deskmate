package top.yaotutu.deskmate.presentation.ui.component.base

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * 动态自适应网格系统 - 基于屏幕高度的瓷砖布局
 *
 * 核心理念：
 * - 横屏高度 ÷ 4 = 基础格子尺寸（正方形）
 * - 根据屏幕宽度动态计算列数（4-8列）
 * - 动态间距填满整个宽度，无黑边
 *
 * @author Deskmate Team
 * @since 2.0
 */
object TileGrid {
    /**
     * 列数限制
     */
    const val MIN_COLUMNS = 4  // 最少4列
    const val MAX_COLUMNS = 8  // 最多8列

    /**
     * 基准：横屏高度的 1/4
     */
    const val BASE_GRID_ROWS = 4

    /**
     * 参考间距（用于初始列数计算）
     */
    private const val REFERENCE_GAP = 8  // dp

    /**
     * 最小基础格子尺寸（保护极小屏幕）
     */
    private const val MIN_BASE_CELL_SIZE = 120  // dp

    /**
     * 计算基础格子尺寸（正方形）
     *
     * 基于横屏高度的 1/4
     *
     * @param screenHeight 屏幕高度（横屏）
     * @return 基础格子边长
     */
    fun calculateBaseCellSize(screenHeight: Dp): Dp {
        val baseCellSize = screenHeight / BASE_GRID_ROWS
        // 确保最小尺寸
        return maxOf(baseCellSize, MIN_BASE_CELL_SIZE.dp)
    }

    /**
     * 计算实际列数（4-8 列）
     *
     * 根据屏幕宽度和基础格子尺寸计算理论列数，
     * 然后应用最小最大限制
     *
     * @param screenWidth 屏幕宽度
     * @param baseCellSize 基础格子尺寸
     * @param referenceGap 参考间距（默认 8dp）
     * @return 实际列数（4-8）
     */
    fun calculateColumns(
        screenWidth: Dp,
        baseCellSize: Dp,
        referenceGap: Dp = REFERENCE_GAP.dp
    ): Int {
        // 计算理论列数
        // 公式：(宽度 + 间距) / (格子尺寸 + 间距)
        val theoreticalColumns = ((screenWidth.value + referenceGap.value) /
                (baseCellSize.value + referenceGap.value)).toInt()

        // 应用限制：4-8 列
        return theoreticalColumns.coerceIn(MIN_COLUMNS, MAX_COLUMNS)
    }

    /**
     * 计算动态间距（填满宽度）
     *
     * 将剩余空间均匀分配到所有间距位置
     * 间距数量 = 列数 + 1（左右各一个 + 中间列数-1个）
     *
     * @param screenWidth 屏幕宽度
     * @param columns 实际列数
     * @param baseCellSize 基础格子尺寸
     * @return 动态计算的间距
     */
    fun calculateDynamicGap(
        screenWidth: Dp,
        columns: Int,
        baseCellSize: Dp
    ): Dp {
        // 总间距空间 = 屏幕宽度 - 所有列占用的宽度
        val totalGapSpace = screenWidth.value - (columns * baseCellSize.value)

        // 间距数量：左右边距 + 列间距
        val gapCount = columns + 1

        // 平均分配
        val dynamicGap = totalGapSpace / gapCount

        // 确保最小间距 6dp（Metro 风格：保持适度间距）
        return maxOf(dynamicGap.dp, 6.dp)
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
 * 检测屏幕尺寸，计算基础格子尺寸、列数和动态间距，
 * 并通过回调传递给子组件
 *
 * @param modifier 修饰符
 * @param content 接收网格参数的内容 lambda（增加了 screenHeight 参数）
 */
@Composable
fun TileGridContainer(
    modifier: Modifier = Modifier,
    content: @Composable (baseCellSize: Dp, dynamicGap: Dp, columns: Int, screenHeight: Dp) -> Unit
) {
    BoxWithConstraints(modifier = modifier) {
        // 获取屏幕尺寸
        val screenWidth = maxWidth
        val screenHeight = maxHeight

        // 步骤1: 计算基础格子尺寸（横屏高度的 1/4）
        val baseCellSize = TileGrid.calculateBaseCellSize(screenHeight)

        // 步骤2: 计算实际列数（4-8 列）
        val columns = TileGrid.calculateColumns(screenWidth, baseCellSize)

        // 步骤3: 计算动态间距（填满宽度）
        val dynamicGap = TileGrid.calculateDynamicGap(screenWidth, columns, baseCellSize)

        // 传递参数给子组件（包含实际屏幕高度）
        content(baseCellSize, dynamicGap, columns, screenHeight)
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
