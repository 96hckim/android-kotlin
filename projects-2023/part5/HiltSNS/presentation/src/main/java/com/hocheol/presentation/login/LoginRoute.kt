package com.hocheol.presentation.login

sealed class LoginRoute(
    val name: String
) {
    data object WelcomeScreen : LoginRoute("WelcomeScreen")

    data object LoginScreen : LoginRoute("LoginScreen")

    data object SignUpScreen : LoginRoute("SignUpScreen")
}