package com.lek.spontan.android.presentation.authentication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.lek.spontan.android.presentation.authentication.login.LoginViewModel
import com.lek.spontan.android.presentation.authentication.navigation.AuthScreen
import com.lek.spontan.android.presentation.shared.theme.SpontanTheme
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val loginViewModel = LoginViewModel()
    private val signupViewModel = SignupViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            loginViewModel.uiState.collect {
                if (it.isLoginSuccess) {
                    Toast.makeText(this@MainActivity, "Login is successful", Toast.LENGTH_SHORT)
                        .show()
                }
                if (it.error.isNotEmpty()) {
                    Toast.makeText(this@MainActivity, it.error, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        lifecycleScope.launch {
            signupViewModel.uiState.collect {
                if (it.isSignUpSuccess) {
                    Toast.makeText(this@MainActivity, "Signup is successful", Toast.LENGTH_SHORT)
                        .show()
                }
                if (it.error.isNotEmpty()) {
                    Toast.makeText(this@MainActivity, it.error, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        setContent {
            SpontanTheme {
                AuthScreen(
                    loginViewModel,
                    signupViewModel
                )
            }
        }
    }
}
