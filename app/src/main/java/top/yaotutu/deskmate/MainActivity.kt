package top.yaotutu.deskmate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.compose.rememberNavController
import top.yaotutu.deskmate.navigation.NavGraph
import top.yaotutu.deskmate.presentation.ui.component.factory.registerAllTileVariants
import top.yaotutu.deskmate.presentation.ui.theme.DeskmateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 初始化瓷砖变体注册表
        registerAllTileVariants()

        // 隐藏系统导航栏和状态栏
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController?.apply {
            // 隐藏系统栏
            hide(WindowInsetsCompat.Type.systemBars())
            // 设置为沉浸式粘性模式
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        setContent {
            DeskmateTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}