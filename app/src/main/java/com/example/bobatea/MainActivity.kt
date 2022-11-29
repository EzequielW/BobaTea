package com.example.bobatea

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bobatea.ui.checkout.Checkout
import com.example.bobatea.ui.product_detail.ProductDetail
import com.example.bobatea.ui.product_list.ProductList
import com.example.bobatea.ui.theme.BobaTeaTheme

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
    val showBottomBar: Boolean
) {
    object Profile : AppRoute(
        "profile",
        R.drawable.profile_icon,
        true
    )
    object ProductList : AppRoute(
        "product_list",
        R.drawable.drink_icon,
        true
    )
    object Order : AppRoute(
        "order",
        R.drawable.cart_icon,
        false
    )
}

@Composable
fun BobaTeaApp() {
    val routes = listOf(
        AppRoute.Profile,
        AppRoute.ProductList,
        AppRoute.Order,
    )

    BobaTeaTheme {
        val navController = rememberNavController()
        var bottomBarState by rememberSaveable { (mutableStateOf(true)) }
        val navBackStackEntry2 by navController.currentBackStackEntryAsState()

        for (app_route in routes){
            if(navBackStackEntry2?.destination?.route == app_route.route){
                bottomBarState = app_route.showBottomBar
            }
        }

        Scaffold(
            bottomBar = {
                AnimatedVisibility(
                    visible = bottomBarState,
                    enter = slideInVertically(initialOffsetY = { it }),
                    exit = slideOutVertically(targetOffsetY = { it }),
                    content = {
                        BottomNavigation (
                            backgroundColor = Color(0xFF102346)
                        ) {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentDestination = navBackStackEntry?.destination

                            routes.forEach { appRoute ->
                                BottomNavigationItem(
                                    icon = { Icon(painter = painterResource(id = appRoute.icon), contentDescription = null, tint = Color.White) },
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
                                    }
                                )
                            }
                        }
                    }
                )
            }
        ) { innerPadding ->
            NavHost(navController, startDestination = AppRoute.ProductList.route, Modifier.padding(innerPadding)) {
                composable(AppRoute.Profile.route) { ProductDetail() }
                composable(AppRoute.ProductList.route) { ProductList() }
                composable(AppRoute.Order.route) { Checkout() }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BobaTeaApp()
}