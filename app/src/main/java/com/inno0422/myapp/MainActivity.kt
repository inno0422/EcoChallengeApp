package com.inno0422.myapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.inno0422.myapp.ui.challenge.ChallengeScreen
import com.inno0422.myapp.ui.challenge.ChallengeViewModel
import com.inno0422.myapp.ui.community.CommunityScreen
import com.inno0422.myapp.ui.community.CommunityViewModel
import com.inno0422.myapp.ui.dashboard.DashboardScreen
import com.inno0422.myapp.ui.dashboard.DashboardViewModel
import com.inno0422.myapp.ui.log.LogScreen
import com.inno0422.myapp.ui.theme.EcoChallengeTheme

sealed class Screen(val route: String, val resourceId: String, val icon: ImageVector) {
    object Dashboard : Screen("dashboard", "대시보드", Icons.Default.Dashboard)
    object Log : Screen("log", "기록", Icons.Default.Assessment)
    object Challenge : Screen("challenge", "챌린지", Icons.Default.Campaign)
    object Community : Screen("community", "커뮤니티", Icons.Default.Groups)
}

val items = listOf(
    Screen.Dashboard,
    Screen.Log,
    Screen.Challenge,
    Screen.Community,
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EcoChallengeTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val dashboardViewModel: DashboardViewModel = viewModel()
    val challengeViewModel: ChallengeViewModel = viewModel()
    val communityViewModel: CommunityViewModel = viewModel() // 커뮤니티 ViewModel 추가

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = null) },
                        label = { Text(screen.resourceId) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = Screen.Dashboard.route,
            Modifier.padding(innerPadding)
        ) {
            composable(Screen.Dashboard.route) { DashboardScreen(viewModel = dashboardViewModel) }
            composable(Screen.Log.route) { LogScreen(viewModel = dashboardViewModel) }
            composable(Screen.Challenge.route) { ChallengeScreen(viewModel = challengeViewModel) }
            // 커뮤니티 화면에 ViewModel 전달
            composable(Screen.Community.route) { CommunityScreen(viewModel = communityViewModel) }
        }
    }
}