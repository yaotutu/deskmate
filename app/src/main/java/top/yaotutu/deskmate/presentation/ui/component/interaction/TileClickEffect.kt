package top.yaotutu.deskmate.presentation.ui.component.interaction

/**
 * Windows Phone Metro 风格的瓷砖点击效果类型
 *
 * 定义了可用的点击交互动效类型，用于配置瓷砖的交互行为
 */

/**
 * 点击效果类型
 *
 * @property PRESS_SCALE 按压缩放（经典 Metro 效果）- 按下时缩小至 95% 并降低透明度
 * @property PRESS_FLASH 按压闪烁 - 按下时透明度快速降低
 * @property TAP_BOUNCE 点击弹跳 - 点击后先放大至 110%，然后弹回
 * @property TAP_PULSE 点击脉冲 - 点击后快速脉冲一次（缩小-放大-恢复）
 * @property TAP_FLIP_TRIGGER 点击触发翻转 - 点击后触发瓷砖翻转显示背面
 * @property TAP_SHAKE 点击抖动 - 点击后瓷砖左右快速抖动
 * @property NONE 无效果 - 不应用任何交互动效
 */
enum class TileClickEffect {
    PRESS_SCALE,        // 按压缩放（经典 Metro 效果）
    PRESS_FLASH,        // 按压闪烁
    TAP_BOUNCE,         // 点击弹跳
    TAP_PULSE,          // 点击脉冲
    TAP_FLIP_TRIGGER,   // 点击触发翻转
    TAP_SHAKE,          // 点击抖动
    NONE                // 无效果
}
