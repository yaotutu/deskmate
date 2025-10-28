package top.yaotutu.deskmate.presentation.ui.component.base

import androidx.compose.ui.graphics.Color

/**
 * 瓷砖规格配置 - 业务组件通过这个配置瓷砖
 *
 * 这个类封装了瓷砖的所有配置信息，业务组件只需要传递这个配置对象
 * 给 BaseTile，无需关心底层的布局实现
 *
 * @param columns 瓷砖占用的列数（1-8）
 * @param rows 瓷砖占用的行数（1-8）
 * @param color 背景颜色（Metro 配色）
 * @param animation 动画类型
 */
data class TileSpec(
    val columns: Int,
    val rows: Int,
    val color: Color,
    val animation: AnimationType = AnimationType.NONE
) {
    companion object {
        /**
         * 创建小方形瓷砖规格 (1×1)
         */
        fun small(color: Color, animation: AnimationType = AnimationType.NONE) =
            TileSpec(1, 1, color, animation)

        /**
         * 创建标准方形瓷砖规格 (2×2)
         */
        fun square(color: Color, animation: AnimationType = AnimationType.NONE) =
            TileSpec(2, 2, color, animation)

        /**
         * 创建宽矩形瓷砖规格 (4×2)
         */
        fun wideMedium(color: Color, animation: AnimationType = AnimationType.NONE) =
            TileSpec(4, 2, color, animation)

        /**
         * 创建高矩形瓷砖规格 (2×4)
         */
        fun tall(color: Color, animation: AnimationType = AnimationType.NONE) =
            TileSpec(2, 4, color, animation)

        /**
         * 创建大方形瓷砖规格 (4×4)
         */
        fun large(color: Color, animation: AnimationType = AnimationType.NONE) =
            TileSpec(4, 4, color, animation)
    }
}

/**
 * 动画类型枚举
 *
 * 定义了 Metro 风格的标准动画效果
 */
enum class AnimationType {
    /**
     * 无动画 - 静态显示
     * 适用场景：天气、日历等不需要动画的瓷砖
     */
    NONE,

    /**
     * 翻转动画 - 正反面切换
     * 适用场景：时钟（时间/日期切换）
     * 实现：使用 FlipTileAnimation
     */
    FLIP,

    /**
     * 脉冲动画 - 周期性缩放
     * 适用场景：天气温度、音乐播放器
     * 实现：使用 PulseTileAnimation
     */
    PULSE,

    /**
     * 滑动动画 - 内容轮播
     * 适用场景：新闻列表、图片轮播
     * 实现：使用 SlideTileAnimation
     */
    SLIDE
}
