package com.pjdev.calisthenicslevel.presentation.navigation

sealed class AppRoute(val route: String) {
    data object EpicIntro : AppRoute("epic_intro")
    data object TestExplainer : AppRoute("test_explainer")
    data object TestPush : AppRoute("test_push")
    data object TestPull : AppRoute("test_pull")
    data object TestCore : AppRoute("test_core")
    data object TestLegs : AppRoute("test_legs")
    data object LevelReveal : AppRoute("level_reveal")
    data object AuthGate : AppRoute("auth_gate")
    data object AuthForm : AppRoute("auth_form")
    data object Home : AppRoute("home")
}
