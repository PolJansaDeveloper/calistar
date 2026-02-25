package com.pjdev.calisthenicslevel.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pjdev.calisthenicslevel.presentation.auth.AuthFormScreen
import com.pjdev.calisthenicslevel.presentation.auth.AuthGateScreen
import com.pjdev.calisthenicslevel.presentation.auth.AuthViewModel
import com.pjdev.calisthenicslevel.presentation.home.HomeScreen
import com.pjdev.calisthenicslevel.presentation.onboarding.EpicIntroScreen
import com.pjdev.calisthenicslevel.presentation.onboarding.LevelRevealScreen
import com.pjdev.calisthenicslevel.presentation.onboarding.OnboardingViewModel
import com.pjdev.calisthenicslevel.presentation.onboarding.TestExplainerScreen
import com.pjdev.calisthenicslevel.presentation.onboarding.TestScreen

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    authViewModel: AuthViewModel = hiltViewModel(),
    onboardingViewModel: OnboardingViewModel = hiltViewModel()
) {
    val authState = authViewModel.uiState.collectAsStateWithLifecycle().value
    val onboardingState = onboardingViewModel.uiState.collectAsStateWithLifecycle().value

    val startDestination = if (authState.isLoggedIn) AppRoute.Home.route else AppRoute.EpicIntro.route

    LaunchedEffect(authState.isLoggedIn) {
        if (authState.isLoggedIn && navController.currentDestination?.route != AppRoute.Home.route) {
            navController.navigate(AppRoute.Home.route) {
                popUpTo(0) { inclusive = true }
                launchSingleTop = true
            }
        }
    }

    NavHost(navController = navController, startDestination = startDestination) {
        composable(AppRoute.EpicIntro.route) {
            EpicIntroScreen(onContinue = { navController.navigate(AppRoute.TestExplainer.route) })
        }
        composable(AppRoute.TestExplainer.route) {
            TestExplainerScreen(
                onContinue = { navController.navigate(AppRoute.TestPush.route) },
                onBack = { navController.popBackStack() }
            )
        }
        composable(AppRoute.TestPush.route) {
            TestScreen(
                title = "Prueba de flexiones",
                value = onboardingState.pushReps,
                onDecrease = { onboardingViewModel.updatePushReps(onboardingState.pushReps - 1) },
                onIncrease = { onboardingViewModel.updatePushReps(onboardingState.pushReps + 1) },
                onBack = { navController.popBackStack() },
                onContinue = { navController.navigate(AppRoute.TestPull.route) }
            )
        }
        composable(AppRoute.TestPull.route) {
            TestScreen(
                title = "Prueba de dominadas",
                helperText = "Si aún no haces dominadas, pon 0.",
                value = onboardingState.pullReps,
                onDecrease = { onboardingViewModel.updatePullReps(onboardingState.pullReps - 1) },
                onIncrease = { onboardingViewModel.updatePullReps(onboardingState.pullReps + 1) },
                onBack = { navController.popBackStack() },
                onContinue = { navController.navigate(AppRoute.TestCore.route) }
            )
        }
        composable(AppRoute.TestCore.route) {
            TestScreen(
                title = "Prueba de core (segundos)",
                value = onboardingState.coreSeconds,
                onDecrease = { onboardingViewModel.updateCoreSeconds(onboardingState.coreSeconds - 5) },
                onIncrease = { onboardingViewModel.updateCoreSeconds(onboardingState.coreSeconds + 5) },
                onBack = { navController.popBackStack() },
                onContinue = { navController.navigate(AppRoute.TestLegs.route) }
            )
        }
        composable(AppRoute.TestLegs.route) {
            TestScreen(
                title = "Prueba de piernas",
                value = onboardingState.legsReps,
                onDecrease = { onboardingViewModel.updateLegsReps(onboardingState.legsReps - 1) },
                onIncrease = { onboardingViewModel.updateLegsReps(onboardingState.legsReps + 1) },
                onBack = { navController.popBackStack() },
                onContinue = {
                    onboardingViewModel.startAnalysis()
                    navController.navigate(AppRoute.LevelReveal.route)
                }
            )
        }
        composable(AppRoute.LevelReveal.route) {
            LevelRevealScreen(
                state = onboardingState,
                onSaveProgress = { navController.navigate(AppRoute.AuthGate.route) }
            )
        }
        composable(AppRoute.AuthGate.route) {
            AuthGateScreen(
                loading = authState.loading,
                onGoogle = { authViewModel.loginWithGoogle() },
                onEmail = { navController.navigate(AppRoute.AuthForm.route) }
            )
        }
        composable(AppRoute.AuthForm.route) {
            AuthFormScreen(
                state = authState,
                onEmailChange = authViewModel::onEmailChange,
                onPasswordChange = authViewModel::onPasswordChange,
                onModeChange = authViewModel::onModeChange,
                onSubmit = authViewModel::submitWithEmail,
                onBack = { navController.popBackStack() },
                onErrorShown = authViewModel::consumeError
            )
        }
        composable(AppRoute.Home.route) {
            HomeScreen(
                onLogout = {
                    authViewModel.logout {
                        navController.navigate(AppRoute.EpicIntro.route) {
                            popUpTo(0) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                }
            )
        }
    }
}
