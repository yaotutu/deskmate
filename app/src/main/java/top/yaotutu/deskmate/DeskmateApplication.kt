package top.yaotutu.deskmate

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.SvgDecoder
import timber.log.Timber
import top.yaotutu.deskmate.presentation.component.factory.registerAllTileVariants

/**
 * Deskmate 应用入口
 *
 * 职责：
 * 1. 初始化瓷砖注册表
 * 2. 初始化日志系统
 * 3. 配置 Coil 图片加载器（支持SVG）
 */
class DeskmateApplication : Application(), ImageLoaderFactory {

    override fun onCreate() {
        super.onCreate()

        // 初始化日志（开发模式）
        Timber.plant(Timber.DebugTree())
        Timber.d("应用启动")

        // 初始化瓷砖注册表
        registerAllTileVariants()
        Timber.d("瓷砖注册表初始化完成")
    }

    /**
     * 配置 Coil ImageLoader
     *
     * 添加 SVG 解码器支持，用于加载和风天气图标
     */
    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .components {
                add(SvgDecoder.Factory())  // 添加 SVG 支持
            }
            .crossfade(true)  // 启用淡入动画
            .respectCacheHeaders(false)  // 忽略缓存头，使用本地缓存策略
            .build()
            .also {
                Timber.d("Coil ImageLoader 已配置（支持 SVG）")
            }
    }
}
