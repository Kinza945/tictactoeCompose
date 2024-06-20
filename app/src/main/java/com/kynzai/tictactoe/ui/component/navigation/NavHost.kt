package com.kynzai.tictactoe.ui.component.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.kynzai.tictactoe.data.TicTacToeScreen
import com.kynzai.tictactoe.ui.screen.ScoreScreen
import com.kynzai.tictactoe.ui.viewmodel.ScoreViewModel
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.kynzai.tictactoe.ui.screen.DashboardScreen
import com.kynzai.tictactoe.ui.viewmodel.DashboardViewModel
import com.kynzai.tictactoe.ui.screen.GameScreen
import com.kynzai.tictactoe.ui.viewmodel.GameViewModel

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.ScoreNavHost(navController: NavController) {
    navigation(
        startDestination = TicTacToeScreen.Score.Home.route,
        route = TicTacToeScreen.Score.Root.route
    ) {
        composable(
            route = TicTacToeScreen.Score.Home.route,
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() },
            popExitTransition = { fadeOut() }
        ) { backEntry ->
            val viewModel = hiltViewModel<ScoreViewModel>(backEntry)

            ScoreScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}
@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.GameNavHost(navController: NavController) {
    navigation(
        startDestination = TicTacToeScreen.Game.Home.route,
        route = TicTacToeScreen.Game.Root.route
    ) {
        composable(
            route = TicTacToeScreen.Game.Home.route,
            arguments = TicTacToeScreen.Game.Home.arguments,
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() },
            popExitTransition = { fadeOut() }
        ) { backEntry ->
            val viewModel = hiltViewModel<GameViewModel>(backEntry)

            GameScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.DashboardNavHost(navController: NavController) {
    navigation(
        startDestination = TicTacToeScreen.Dashboard.Home.route,
        route = TicTacToeScreen.Dashboard.Root.route
    ) {
        composable(
            route = TicTacToeScreen.Dashboard.Home.route,
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() },
            popExitTransition = { fadeOut() }
        ) { backEntry ->
            val viewModel = hiltViewModel<DashboardViewModel>(backEntry)

            DashboardScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}

