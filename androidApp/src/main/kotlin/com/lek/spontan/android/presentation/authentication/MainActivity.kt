package com.lek.spontan.android.presentation.authentication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.lifecycleScope
import com.lek.spontan.android.presentation.authentication.login.LoginViewModel
import com.lek.spontan.android.presentation.authentication.navigation.AuthScreen
import com.lek.spontan.android.presentation.eventslist.EventsListActivity
import com.lek.spontan.android.presentation.shared.theme.SpontanTheme
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val loginViewModel = LoginViewModel()
    private val signupViewModel = SignupViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            loginViewModel.uiState.collect {
                if (it.error.isNotEmpty()) {
                    Toast.makeText(this@MainActivity, it.error, Toast.LENGTH_SHORT).show()
                }
                if(it.hasLoggedIn) {
                    startActivity(Intent(this@MainActivity, EventsListActivity::class.java))
                    this@MainActivity.finish()
                }
            }
        }

        lifecycleScope.launch {
            signupViewModel.uiState.collect {
                if (it.userHasAccount) {
                    startActivity(Intent(this@MainActivity, EventsListActivity::class.java))
                    this@MainActivity.finish()
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
