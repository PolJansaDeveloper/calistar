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
import com.pjdev.calisthenicslevel.presentation.onboarding.AssessmentStepScaffold
import com.pjdev.calisthenicslevel.presentation.onboarding.TestExplainerScreen

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
            AssessmentStepScaffold(
                stepTitle = "Prueba de flexiones",
                stepSubtitle = "Paso 1 de 4",
                instruction = "Haz flexiones estrictas en una sola serie. Cuenta solo repeticiones limpias.",
                value = onboardingState.pushReps,
                unitLabel = "reps",
                onDecrement = { onboardingViewModel.updatePushReps(onboardingState.pushReps - 1) },
                onIncrement = { onboardingViewModel.updatePushReps(onboardingState.pushReps + 1) },
                helperText = "",
                onBack = { navController.popBackStack() },
                onContinue = { navController.navigate(AppRoute.TestPull.route) },
                currentStep = 0
            )
        }
        composable(AppRoute.TestPull.route) {
            AssessmentStepScaffold(
                stepTitle = "Prueba de dominadas",
                stepSubtitle = "Paso 2 de 4",
                instruction = "Realiza dominadas estrictas, mentón por encima de la barra en cada repetición.",
                value = onboardingState.pullReps,
                unitLabel = "reps",
                onDecrement = { onboardingViewModel.updatePullReps(onboardingState.pullReps - 1) },
                onIncrement = { onboardingViewModel.updatePullReps(onboardingState.pullReps + 1) },
                helperText = "Si aún no haces dominadas, pon 0.",
                onBack = { navController.popBackStack() },
                onContinue = { navController.navigate(AppRoute.TestCore.route) },
                currentStep = 1
            )
        }
        composable(AppRoute.TestCore.route) {
            AssessmentStepScaffold(
                stepTitle = "Prueba de core",
                stepSubtitle = "Paso 3 de 4",
                instruction = "Mantén plancha frontal con técnica limpia y cadera estable.",
                value = onboardingState.coreSeconds,
                unitLabel = "seg",
                onDecrement = { onboardingViewModel.updateCoreSeconds(onboardingState.coreSeconds - 5) },
                onIncrement = { onboardingViewModel.updateCoreSeconds(onboardingState.coreSeconds + 5) },
                helperText = "",
                onBack = { navController.popBackStack() },
                onContinue = { navController.navigate(AppRoute.TestLegs.route) },
                currentStep = 2
            )
        }
        composable(AppRoute.TestLegs.route) {
            AssessmentStepScaffold(
                stepTitle = "Prueba de piernas",
                stepSubtitle = "Paso 4 de 4",
                instruction = "Completa sentadillas controladas y profundas. Mantén ritmo constante.",
                value = onboardingState.legsReps,
                unitLabel = "reps",
                onDecrement = { onboardingViewModel.updateLegsReps(onboardingState.legsReps - 1) },
                onIncrement = { onboardingViewModel.updateLegsReps(onboardingState.legsReps + 1) },
                helperText = "",
                onBack = { navController.popBackStack() },
                onContinue = {
                    onboardingViewModel.startAnalysis()
                    navController.navigate(AppRoute.LevelReveal.route)
                },
                currentStep = 3
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
