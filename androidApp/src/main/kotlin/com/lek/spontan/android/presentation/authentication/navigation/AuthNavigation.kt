package com.lek.spontan.android.presentation.authentication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lek.spontan.android.presentation.authentication.AuthScreenRoute
import com.lek.spontan.android.presentation.authentication.SignupViewModel
import com.lek.spontan.android.presentation.authentication.login.LoginViewEvent
import com.lek.spontan.android.presentation.authentication.login.LoginViewModel
import com.lek.spontan.android.presentation.authentication.login.composables.LoginScreen
import com.lek.spontan.android.presentation.authentication.signup.SignUpViewEvent
import com.lek.spontan.android.presentation.authentication.signup.composables.SignupScreen

@Composable
fun AuthScreen(
    loginViewModel: LoginViewModel,
    signupViewModel: SignupViewModel
) {
    val navController = rememberNavController()

    val loginViewState by loginViewModel.uiState.collectAsState()
    val signupViewState by signupViewModel.uiState.collectAsState()

    NavHost(navController, startDestination = AuthScreenRoute.LOGIN) {

        composable(route = AuthScreenRoute.LOGIN) {
            LoginScreen(
                state = loginViewState,
                onEmailChanged = { loginViewModel.onEvent(LoginViewEvent.EmailTyped(it)) },
                onPasswordChanged = { loginViewModel.onEvent(LoginViewEvent.PasswordTyped(it)) },
                onLoginClicked = { loginViewModel.onEvent(LoginViewEvent.LoginButtonClick) }
            ) {
                navController.clearBackStack(AuthScreenRoute.LOGIN)
                navController.navigate(AuthScreenRoute.SIGNUP)
            }
        }

        composable(route = AuthScreenRoute.SIGNUP) {
            SignupScreen(
                state = signupViewState,
                onNameChanged = { signupViewModel.onEvent(SignUpViewEvent.NameTyped(it)) },
                onPasswordChanged = { signupViewModel.onEvent(SignUpViewEvent.PasswordTyped(it)) },
                onEmailChanged = { signupViewModel.onEvent(SignUpViewEvent.EmailTyped(it)) },
                onSignupClicked = { signupViewModel.onEvent(SignUpViewEvent.SignupButtonClicked) }
            ) {
                navController.clearBackStack(AuthScreenRoute.SIGNUP)
                navController.navigate(AuthScreenRoute.LOGIN)
            }
        }
    }
}