package top.yaotutu.deskmate.presentation.theme

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Easing

/**
 * Windows Phone Metro 设计语言标准缓动曲线
 *
 * 这些缓动曲线来自微软官方的 Metro Design Language 动画规范，
 * 为应用提供流畅、自然的动画效果。
 *
 * 参考资源：
 * - Windows Phone Motion Design Guidelines
 * - Microsoft Design Language Animation Principles
 *
 * @author Deskmate Team
 * @since 2.0
 */
object MetroEasing {

    /**
     * Metro 标准缓动曲线（推荐）
     *
     * 特点：
     * - 快速启动（10% 时间到达 90% 进度）
     * - 平滑减速（最后 80% 时间完成剩余 10%）
     * - 提供流畅、优雅的动画体验
     *
     * 用途：
     * - 瓷砖进入/退出动画
     * - 翻转动画
     * - 大部分 UI 转场
     */
    val Standard: Easing = CubicBezierEasing(0.1f, 0.9f, 0.2f, 1.0f)

    /**
     * 快速缓出（Fast Out）
     *
     * 特点：
     * - 瞬间启动
     * - 逐渐减速
     *
     * 用途：
     * - 按压反馈
     * - 快速交互动画
     */
    val FastOut: Easing = CubicBezierEasing(0.4f, 0.0f, 0.2f, 1.0f)

    /**
     * 慢速缓入（Slow In）
     *
     * 特点：
     * - 缓慢启动
     * - 快速完成
     *
     * 用途：
     * - 元素消失动画
     * - 滑出屏幕
     */
    val SlowIn: Easing = CubicBezierEasing(0.0f, 0.0f, 0.2f, 1.0f)

    /**
     * 标准缓入缓出（Ease In Out）
     *
     * 特点：
     * - 慢速启动和结束
     * - 中间加速
     *
     * 用途：
     * - 循环动画
     * - 来回移动
     */
    val EaseInOut: Easing = CubicBezierEasing(0.42f, 0.0f, 0.58f, 1.0f)

    /**
     * 弹性缓出（Bounce Out）- Metro 特有
     *
     * 特点：
     * - 快速移动到目标
     * - 轻微超出后回弹
     * - Windows Phone 标志性动画
     *
     * 用途：
     * - 列表项弹出
     * - 瓷砖激活效果
     */
    val BounceOut: Easing = CubicBezierEasing(0.25f, 0.8f, 0.25f, 1.0f)

    /**
     * 急速缓出（Sharp Out）
     *
     * 特点：
     * - 极快启动
     * - 急刹车式停止
     *
     * 用途：
     * - 突出强调的动画
     * - 惊喜效果
     */
    val SharpOut: Easing = CubicBezierEasing(0.1f, 1.0f, 0.1f, 1.0f)

    /**
     * 平滑缓出（Smooth Out）
     *
     * 特点：
     * - 温和启动
     * - 极其平滑的减速
     *
     * 用途：
     * - 大型内容滚动
     * - 页面切换
     */
    val SmoothOut: Easing = CubicBezierEasing(0.0f, 0.0f, 0.0f, 1.0f)

    /**
     * 缓出（Ease Out）
     *
     * 特点：
     * - 快速启动
     * - 平滑减速
     *
     * 用途：
     * - 弹跳动画的上升阶段
     * - 元素进入动画
     */
    val EaseOut: Easing = CubicBezierEasing(0.0f, 0.0f, 0.2f, 1.0f)

    /**
     * 缓入（Ease In）
     *
     * 特点：
     * - 缓慢启动
     * - 快速完成
     *
     * 用途：
     * - 弹跳动画的下降阶段
     * - 元素退出动画
     */
    val EaseIn: Easing = CubicBezierEasing(0.8f, 0.0f, 1.0f, 1.0f)
}

/**
 * Metro 动画时长规范（毫秒）
 *
 * 根据 Windows Phone 设计规范，不同类型的动画应使用特定的时长，
 * 确保用户体验的一致性和流畅性。
 */
object MetroDuration {
    /**
     * 极快动画 - 80ms
     *
     * 用途：
     * - 抖动动画单次抖动
     * - 快速反馈效果
     */
    const val VERY_FAST = 80

    /**
     * 超快速动画 - 100ms
     *
     * 用途：
     * - 按钮按压反馈
     * - 小型元素高亮
     */
    const val EXTRA_FAST = 100

    /**
     * 快速动画 - 200ms
     *
     * 用途：
     * - 瓷砖点击效果
     * - 工具提示显示
     * - 弹跳动画单次弹跳
     */
    const val FAST = 200

    /**
     * 标准动画 - 300ms（推荐）
     *
     * 用途：
     * - 瓷砖进入/退出
     * - 大部分 UI 转场
     * - 翻转动画
     */
    const val STANDARD = 300

    /**
     * 中等动画 - 400ms
     *
     * 用途：
     * - 复杂转场
     * - 多步动画
     */
    const val MEDIUM = 400

    /**
     * 慢速动画 - 500ms
     *
     * 用途：
     * - 页面切换
     * - 大型内容滚动
     */
    const val SLOW = 500

    /**
     * 超慢速动画 - 700ms
     *
     * 用途：
     * - 引导动画
     * - 特殊展示效果
     */
    const val EXTRA_SLOW = 700

    /**
     * 错峰动画延迟 - 50ms
     *
     * 用途：
     * - Stagger 动画中每个元素的延迟
     * - 列表项依次出现
     */
    const val STAGGER_DELAY = 50

    /**
     * 脉冲动画周期 - 2000ms
     *
     * 用途：
     * - 活动瓷砖的呼吸效果
     * - 循环脉冲动画
     */
    const val PULSE_CYCLE = 2000

    /**
     * 翻转动画周期 - 6000ms
     *
     * 用途：
     * - 时钟瓷砖正反面切换
     * - 自动轮播
     */
    const val FLIP_CYCLE = 6000

    /**
     * 滑动动画周期 - 4000ms
     *
     * 用途：
     * - 新闻瓷砖内容切换
     * - 图片轮播
     */
    const val SLIDE_CYCLE = 4000

    /**
     * 旋转动画周期 - 1200ms
     *
     * 用途：
     * - 刷新图标旋转一周时间
     * - 加载指示器
     */
    const val ROTATE_CYCLE = 1200

    /**
     * 旋转间隔 - 3000ms
     *
     * 用途：
     * - 单次旋转模式的触发间隔
     */
    const val ROTATE_INTERVAL = 3000

    /**
     * 弹跳间隔 - 5000ms
     *
     * 用途：
     * - 弹跳动画触发间隔
     * - 新内容提醒频率
     */
    const val BOUNCE_INTERVAL = 5000

    /**
     * 抖动间隔 - 10000ms
     *
     * 用途：
     * - 抖动动画触发间隔
     * - 重要提醒频率
     */
    const val SHAKE_INTERVAL = 10000

    /**
     * 微光动画周期 - 1500ms
     *
     * 用途：
     * - 微光从左到右扫过的时间
     * - 骨架屏效果
     */
    const val SHIMMER_CYCLE = 1500

    /**
     * 微光间隔 - 2000ms
     *
     * 用途：
     * - 两次微光之间的间隔
     */
    const val SHIMMER_INTERVAL = 2000
}

/**
 * Metro 动画距离规范（dp）
 */
object MetroDistance {
    /**
     * 微小移动 - 4dp
     */
    const val TINY = 4

    /**
     * 小距离移动 - 8dp
     */
    const val SMALL = 8

    /**
     * 中等移动 - 16dp
     */
    const val MEDIUM = 16

    /**
     * 大距离移动 - 32dp
     */
    const val LARGE = 32

    /**
     * 超大移动 - 64dp
     */
    const val EXTRA_LARGE = 64
}
