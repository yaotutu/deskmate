package top.yaotutu.deskmate.navigation

/**
 * 导航路由定义
 */
sealed class Screen(val route: String) {
    data object Dashboard : Screen("dashboard")
    data object AnimationDemo : Screen("animation_demo")

    // 示例：带参数的路由
    // data object Detail : Screen("detail/{itemId}") {
    //     fun createRoute(itemId: String) = "detail/$itemId"
    // }
}
