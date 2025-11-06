package top.yaotutu.deskmate.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import top.yaotutu.deskmate.presentation.screen.AnimationDemoScreen
import top.yaotutu.deskmate.presentation.screen.DashboardScreen
// import top.yaotutu.deskmate.presentation.screen.BaseTileTestScreen
// import top.yaotutu.deskmate.presentation.screen.PresetsDemoScreen
// import top.yaotutu.deskmate.presentation.screen.ScaleTestScreen

/**
 * 导航图配置
 */
@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = Screen.Dashboard.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // 主页面 - Dashboard
        composable(Screen.Dashboard.route) {
            DashboardScreen(
                onTileClick = {
                    navController.navigate(Screen.AnimationDemo.route)
                }
            )
        }

        // 动画演示页面
        composable(Screen.AnimationDemo.route) {
            AnimationDemoScreen()
        }

        // 预设系统演示页面 - 暂时禁用（需要适配新 API）
        // composable(Screen.PresetsDemo.route) {
        //     PresetsDemoScreen()
        // }

        // 缩放测试页面 - 暂时禁用（需要适配新 API）
        // composable(Screen.ScaleTest.route) {
        //     ScaleTestScreen()
        // }

        // BaseTile 尺寸验证测试页面 - 暂时禁用（需要适配新 API）
        // composable(Screen.BaseTileTest.route) {
        //     BaseTileTestScreen()
        // }
    }
}
