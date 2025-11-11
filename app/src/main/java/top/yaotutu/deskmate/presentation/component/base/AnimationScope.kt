package top.yaotutu.deskmate.presentation.component.base

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import top.yaotutu.deskmate.presentation.component.animation.*

/**
 * AnimationScope - 动画配置 DSL 接口
 *
 * 提供统一的动画配置 API,支持所有 Metro 动画类型。
 * 业务组件通过 Lambda 接收器方式使用此接口配置动画内容。
 *
 * 设计理念:
 * - **API 一致性**: 简单动画和复杂动画使用相同的 DSL 模式
 * - **类型安全**: 编译时检查动画配置是否正确
 * - **代码简洁**: 消除样板代码,提高开发效率
 *
 * 使用示例:
 * ```kotlin
 * // 简单内容
 * BaseTile(spec) {
 *     single { Text("内容") }
 * }
 *
 * // 翻转动画
 * BaseTile(spec) {
 *     flip(
 *         front = { Text("正面") },
 *         back = { Text("背面") }
 *     )
 * }
 *
 * // 滑动动画
 * BaseTile(spec) {
 *     slide(
 *         { NewsItem("新闻1") },
 *         { NewsItem("新闻2") },
 *         { NewsItem("新闻3") }
 *     )
 * }
 * ```
 */
interface AnimationScope {

    /**
     * 单一内容 - 用于不需要多内容的动画
     *
     * 适用动画类型: NONE, PULSE, ROTATE, SHIMMER, DEPTH, BOUNCE, SHAKE
     *
     * @param content 内容
     */
    fun single(content: @Composable () -> Unit)

    /**
     * 翻转动画 - 正反面切换
     *
     * 适用动画类型: FLIP
     *
     * @param front 正面内容
     * @param back 背面内容
     */
    fun flip(
        front: @Composable () -> Unit,
        back: @Composable () -> Unit
    )

    /**
     * 滑动动画 - 内容列表轮播
     *
     * 适用动画类型: SLIDE
     *
     * @param contents 内容列表（至少1项）
     */
    fun slide(vararg contents: @Composable () -> Unit) {
        slide(contents.toList())
    }

    /**
     * 滑动动画 - 内容列表轮播
     *
     * 适用动画类型: SLIDE
     *
     * @param contents 内容列表（至少1项）
     */
    fun slide(contents: List<@Composable () -> Unit>)

    /**
     * 淡入淡出动画 - 内容平滑切换
     *
     * 适用动画类型: FADE
     *
     * @param contents 内容列表（至少1项）
     */
    fun fade(vararg contents: @Composable () -> Unit) {
        fade(contents.toList())
    }

    /**
     * 淡入淡出动画 - 内容平滑切换
     *
     * 适用动画类型: FADE
     *
     * @param contents 内容列表（至少1项）
     */
    fun fade(contents: List<@Composable () -> Unit>)

    /**
     * 数字滚动动画 - 数值变化时的滚动效果
     *
     * 适用动画类型: COUNTER
     *
     * @param targetValue 目标数值
     * @param durationMillis 动画持续时间（默认 500ms）
     * @param content 渲染函数，接收当前动画值
     */
    fun counter(
        targetValue: Int,
        durationMillis: Int = top.yaotutu.deskmate.presentation.theme.MetroDuration.SLOW,
        content: @Composable (Int) -> Unit
    )

    /**
     * 探出动画 - Windows Phone 标志性动画
     *
     * 适用动画类型: PEEK
     *
     * @param mainContent 主要内容（一直显示）
     * @param peekContent 探出内容（从指定方向探出）
     * @param peekHeight 探出高度比例（0.0-1.0，默认 0.3）
     * @param direction 探出方向（默认从底部探出）
     */
    fun peek(
        mainContent: @Composable () -> Unit,
        peekContent: @Composable () -> Unit,
        peekHeight: Float = 0.3f,
        direction: PeekDirection = PeekDirection.BOTTOM
    )

    /**
     * 跑马灯动画 - 连续滚动
     *
     * 适用动画类型: MARQUEE
     *
     * @param direction 滚动方向（默认水平滚动）
     * @param speed 滚动速度（dp/秒，默认 30）
     * @param spacing 循环间距（dp，默认 50）
     * @param content 要滚动的内容
     */
    fun marquee(
        direction: MarqueeDirection = MarqueeDirection.HORIZONTAL,
        speed: Float = 30f,
        spacing: Int = 50,
        content: @Composable () -> Unit
    )

    /**
     * 擦除动画 - 内容擦除切换
     *
     * 适用动画类型: WIPE
     *
     * @param contents 内容列表（至少2项）
     * @param direction 擦除方向（默认从左到右）
     * @param style 擦除样式（默认滑动）
     */
    fun wipe(
        contents: List<@Composable () -> Unit>,
        direction: WipeDirection = WipeDirection.LEFT_TO_RIGHT,
        style: WipeStyle = WipeStyle.SLIDE
    )

    /**
     * 擦除动画 - 内容擦除切换
     *
     * 适用动画类型: WIPE
     *
     * @param contents 内容列表（至少2项）
     * @param direction 擦除方向（默认从左到右）
     * @param style 擦除样式（默认滑动）
     */
    fun wipe(
        vararg contents: @Composable () -> Unit,
        direction: WipeDirection = WipeDirection.LEFT_TO_RIGHT,
        style: WipeStyle = WipeStyle.SLIDE
    ) {
        wipe(contents.toList(), direction, style)
    }
}

/**
 * AnimationScopeImpl - AnimationScope 的默认实现
 *
 * 内部类,由 BaseTile 创建和管理。
 * 负责根据 AnimationType 应用相应的动画效果。
 *
 * @param animationType 动画类型
 * @param applyAnimation 应用动画的回调函数
 */
internal class AnimationScopeImpl(
    private val animationType: AnimationType?,
    private val applyAnimation: (@Composable () -> Unit) -> Unit
) : AnimationScope {

    override fun single(content: @Composable () -> Unit) {
        when (animationType) {
            AnimationType.PULSE -> applyAnimation { PulseTileAnimation(content = content) }
            AnimationType.ROTATE -> applyAnimation { RotateTileAnimation(content = content) }
            AnimationType.SHIMMER -> applyAnimation { ShimmerTileAnimation(content = content) }
            AnimationType.DEPTH -> applyAnimation { DepthTileAnimation(content = content) }
            AnimationType.BOUNCE -> applyAnimation { BounceTileAnimation(content = content) }
            AnimationType.SHAKE -> applyAnimation { ShakeTileAnimation(content = content) }
            else -> applyAnimation { Box { content() } }
        }
    }

    override fun flip(
        front: @Composable () -> Unit,
        back: @Composable () -> Unit
    ) {
        applyAnimation {
            FlipTileAnimation(
                frontContent = front,
                backContent = back
            )
        }
    }

    override fun slide(contents: List<@Composable () -> Unit>) {
        applyAnimation {
            SlideTileAnimation(contents = contents)
        }
    }

    override fun fade(contents: List<@Composable () -> Unit>) {
        applyAnimation {
            FadeTileAnimation(contents = contents)
        }
    }

    override fun counter(
        targetValue: Int,
        durationMillis: Int,
        content: @Composable (Int) -> Unit
    ) {
        applyAnimation {
            CounterAnimation(
                targetValue = targetValue,
                durationMillis = durationMillis,
                content = content
            )
        }
    }

    override fun peek(
        mainContent: @Composable () -> Unit,
        peekContent: @Composable () -> Unit,
        peekHeight: Float,
        direction: PeekDirection
    ) {
        applyAnimation {
            PeekTileAnimation(
                mainContent = mainContent,
                peekContent = peekContent,
                peekHeight = peekHeight,
                direction = direction
            )
        }
    }

    override fun marquee(
        direction: MarqueeDirection,
        speed: Float,
        spacing: Int,
        content: @Composable () -> Unit
    ) {
        applyAnimation {
            MarqueeTileAnimation(
                direction = direction,
                speed = speed,
                spacing = spacing,
                content = content
            )
        }
    }

    override fun wipe(
        contents: List<@Composable () -> Unit>,
        direction: WipeDirection,
        style: WipeStyle
    ) {
        applyAnimation {
            WipeTileAnimation(
                contents = contents,
                direction = direction,
                style = style
            )
        }
    }
}
