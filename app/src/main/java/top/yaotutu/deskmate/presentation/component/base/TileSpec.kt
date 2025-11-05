package top.yaotutu.deskmate.presentation.component.base

import androidx.compose.ui.graphics.Color

/**
 * 瓷砖规格配置 - 业务组件通过这个配置瓷砖
 *
 * 这个类封装了瓷砖的基础配置信息，业务组件只需要传递这个配置对象
 * 给 BaseTile，无需关心底层的布局实现。动画可以由 BaseTile 或预设处理。
 *
 * @param columns 瓷砖占用的列数（1-8）
 * @param rows 瓷砖占用的行数（1-8）
 * @param color 背景颜色（Metro 配色）
 * @param animation 可选的动画类型，null 表示让预设自己处理动画
 */
data class TileSpec(
    val columns: Int,
    val rows: Int,
    val color: Color,
    val animation: AnimationType? = null  // 改为可选，null 表示预设自己处理动画
) {
    companion object {
        /**
         * 创建小方形瓷砖规格 (1×1) - 用于预设，自动获得动画
         * 默认使用 PULSE 轻微脉冲动画，符合 Metro 设计规范
         */
        fun small(color: Color) = TileSpec(1, 1, color, AnimationType.PULSE)

        /**
         * 创建标准方形瓷砖规格 (2×2) - 用于预设，自动获得动画
         * 默认使用 FLIP 翻转动画，适合展示多面信息
         */
        fun square(color: Color) = TileSpec(2, 2, color, AnimationType.FLIP)

        /**
         * 创建宽矩形瓷砖规格 (4×2) - 用于预设，自动获得动画
         * 默认使用 SLIDE 滑动动画，适合列表和滚动内容
         */
        fun wideMedium(color: Color) = TileSpec(4, 2, color, AnimationType.SLIDE)

        /**
         * 创建高矩形瓷砖规格 (2×4) - 用于预设，自动获得动画
         * 默认使用 FLIP 翻转动画，适合详细信息展示
         */
        fun tall(color: Color) = TileSpec(2, 4, color, AnimationType.FLIP)

        /**
         * 创建大方形瓷砖规格 (4×4) - 用于预设，自动获得动画
         * 默认使用 FADE 淡入淡出动画，适合仪表盘和复杂布局
         */
        fun large(color: Color) = TileSpec(4, 4, color, AnimationType.FADE)

        /**
         * 创建带动画的小方形瓷砖规格 (1×1) - 用于自定义内容
         */
        fun small(color: Color, animation: AnimationType) = TileSpec(1, 1, color, animation)

        /**
         * 创建带动画的标准方形瓷砖规格 (2×2) - 用于自定义内容
         */
        fun square(color: Color, animation: AnimationType) = TileSpec(2, 2, color, animation)

        /**
         * 创建带动画的宽矩形瓷砖规格 (4×2) - 用于自定义内容
         */
        fun wideMedium(color: Color, animation: AnimationType) = TileSpec(4, 2, color, animation)

        /**
         * 创建带动画的高矩形瓷砖规格 (2×4) - 用于自定义内容
         */
        fun tall(color: Color, animation: AnimationType) = TileSpec(2, 4, color, animation)

        /**
         * 创建带动画的大方形瓷砖规格 (4×4) - 用于自定义内容
         */
        fun large(color: Color, animation: AnimationType) = TileSpec(4, 4, color, animation)
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
    SLIDE,

    /**
     * 淡入淡出动画 - 内容平滑切换
     * 适用场景：多内容平滑切换
     * 实现：使用 FadeTileAnimation
     */
    FADE,

    /**
     * 数字滚动动画 - 数值变化
     * 适用场景：计数器、温度、股票价格等数值更新
     * 实现：使用 CounterAnimation
     */
    COUNTER,

    /**
     * 旋转动画 - 持续或单次旋转
     * 适用场景：数据刷新指示、加载状态
     * 实现：使用 RotateTileAnimation
     */
    ROTATE,

    /**
     * 弹跳动画 - 强调新内容
     * 适用场景：新消息到达、重要数据更新提醒
     * 实现：使用 BounceTileAnimation
     */
    BOUNCE,

    /**
     * 抖动动画 - 重要提醒
     * 适用场景：紧急通知、天气预警、错误提示
     * 实现：使用 ShakeTileAnimation
     */
    SHAKE,

    /**
     * 微光动画 - 加载状态指示
     * 适用场景：内容加载中、数据更新中
     * 实现：使用 ShimmerTileAnimation
     */
    SHIMMER,

    /**
     * 探出动画 - 内容预览（Windows Phone Live Tile 标志性动画）⭐
     * 适用场景：邮件预览、日历事件、通知消息
     * 实现：使用 PeekTileAnimation
     */
    PEEK,

    /**
     * 跑马灯动画 - 连续滚动文本
     * 适用场景：新闻标题、长文本展示、通知栏
     * 实现：使用 MarqueeTileAnimation
     */
    MARQUEE,

    /**
     * 擦除动画 - 百叶窗式内容切换
     * 适用场景：新闻切换、图片轮播、内容刷新
     * 实现：使用 WipeTileAnimation
     */
    WIPE,

    /**
     * 深度动画 - 3D 透视效果
     * 适用场景：照片瓷砖、专辑封面、视差效果
     * 实现：使用 DepthTileAnimation
     */
    DEPTH
}
