package com.example.bobatea

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.bobatea.model.*
import com.example.bobatea.ui.checkout.Checkout
import com.example.bobatea.ui.product_detail.ProductDetail
import com.example.bobatea.ui.product_list.ProductList
import com.example.bobatea.ui.theme.BobaTeaTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import java.math.BigDecimal

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BobaTeaApp()
        }
    }
}

sealed class AppRoute(
    val route: String,
    val icon: Int,
    val selectedColor: Color,
    val showBottomBar: Boolean
) {
    object Profile : AppRoute(
        "profile",
        R.drawable.profile_icon,
        Color.White,
        true
    )
    object ProductList : AppRoute(
        "product_list",
        R.drawable.drink_icon,
        Color.Unspecified,
        true
    )
    object ProductDetail : AppRoute(
        "product_detail/{drink}",
        R.drawable.drink_icon,
        Color.Unspecified,
        false
    )
    object Order : AppRoute(
        "order",
        R.drawable.cart_icon,
        Color.White,
        false
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BobaTeaApp() {
    val bottomRoutes = listOf(
        AppRoute.Profile,
        AppRoute.ProductList,
        AppRoute.Order,
    )

    val allRoutes = listOf(
        AppRoute.Profile,
        AppRoute.ProductList,
        AppRoute.Order,
        AppRoute.ProductDetail
    )

    val drinkList = remember { mutableStateListOf(
        Drink("regular", "Milk tea", "Sweet tint of caramel and chocolate",
            BigDecimal.valueOf(4.99), R.drawable.milk_tea),
        Drink("coffee", "Milk tea", "PREMIUM COLD BREW W/ BLACK TEA",
            BigDecimal.valueOf(4.99), R.drawable.coffee),
        Drink("matcha", "Milk tea", "SWEET JAPANESE MATCHA TEA",
            BigDecimal.valueOf(4.99), R.drawable.matcha),
        Drink("chat", "Milk tea", "MADE ITH GOLDEN CHAI TEA",
            BigDecimal.valueOf(5.99), R.drawable.chat)
    )}

    val cart = remember {
        Cart(
            mutableStateListOf(),
            BigDecimal.valueOf(1.00),
            BigDecimal.valueOf(3.00)
        )
    }

    BobaTeaTheme {
        val navController = rememberAnimatedNavController()
        var bottomBarState by rememberSaveable { (mutableStateOf(true)) }
        var selectedColor by remember { (mutableStateOf(Color.White)) }
        val navBackStackEntry2 by navController.currentBackStackEntryAsState()

        for (app_route in allRoutes){
            if(navBackStackEntry2?.destination?.route == app_route.route){
                bottomBarState = app_route.showBottomBar
                selectedColor = app_route.selectedColor
            }
        }

        Scaffold(
            bottomBar = {
                if(bottomBarState){
                    BottomNavigation (
                        backgroundColor = Color(0xFF102346)
                    ) {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination

                        bottomRoutes.forEach { appRoute ->
                            BottomNavigationItem(
                                icon = {
                                   if(appRoute.route == "order" && cart.cartItems.size > 0){
                                       BadgedBox(badge = {
                                           Badge(backgroundColor = Color(0xFFFF0076)){
                                               Text(
                                                   cart.cartItems.size.toString(),
                                                   color = Color.White
                                               )
                                           }
                                       }) {
                                           Icon(
                                               painter = painterResource(id = appRoute.icon),
                                               contentDescription = null,
                                           )
                                       }
                                   }
                                   else{
                                       Icon(
                                           painter = painterResource(id = appRoute.icon),
                                           contentDescription = null,
                                       )
                                   }
                                },
                                selected = currentDestination?.hierarchy?.any { it.route == appRoute.route } == true,
                                onClick = {
                                    navController.navigate(appRoute.route) {
                                        // Pop up to the start destination of the graph to
                                        // avoid building up a large stack of destinations
                                        // on the back stack as users select items
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        // Avoid multiple copies of the same destination when
                                        // reselecting the same item
                                        launchSingleTop = true
                                        // Restore state when reselecting a previously selected item
                                        restoreState = true
                                    }
                                },
                                selectedContentColor = selectedColor,
                                unselectedContentColor = Color.White.copy(alpha = 0.3f)
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            AnimatedNavHost(navController, startDestination = AppRoute.ProductList.route, Modifier.padding(innerPadding)) {
                composable(AppRoute.Profile.route) { Checkout(navController, cart) }
                composable(AppRoute.ProductList.route) { ProductList(navController, drinkList) }
                composable(
                    AppRoute.Order.route,
                    enterTransition = {
                        slideIntoContainer(
                            AnimatedContentScope.SlideDirection.Up,
                            animationSpec = tween(700)
                        )},
                    exitTransition = {
                        slideOutOfContainer(
                            AnimatedContentScope.SlideDirection.Down,
                            animationSpec = tween(700)
                        )
                    }
                ) { Checkout(navController, cart) }
                composable(
                    AppRoute.ProductDetail.route,
                    enterTransition = {
                            slideIntoContainer(
                                AnimatedContentScope.SlideDirection.Up,
                                animationSpec = tween(700)
                            )},
                    exitTransition = {
                        slideOutOfContainer(
                            AnimatedContentScope.SlideDirection.Down,
                            animationSpec = tween(700)
                        )
                    }
                ) { backStackEntry ->
                    val drinkIndex =  backStackEntry.arguments?.getString("drink")
                    if (drinkIndex != null) {
                        ProductDetail(navController, drinkList[drinkIndex.toInt()], cart)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BobaTeaApp()
}