package com.lek.spontan.android.presentation.authentication.login.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lek.spontan.android.R
import com.lek.spontan.android.presentation.authentication.login.LoginViewState
import com.lek.spontan.android.presentation.authentication.login.isValid
import com.lek.spontan.android.presentation.shared.composables.LoadingScreen
import com.lek.spontan.android.presentation.shared.composables.VerticalSpace
import com.lek.spontan.android.presentation.shared.theme.SpontanTheme

@Composable
fun LoginScreen(
    state: LoginViewState,
    onEmailChanged: (String) -> Unit = {},
    onPasswordChanged: (String) -> Unit = {},
    onLoginClicked: () -> Unit = {},
    onSignupClick: () -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (state.isLoading) {
            LoadingScreen()
        } else {
            LoginScreenInputScreen(
                state,
                onEmailChanged,
                onPasswordChanged,
                onLoginClicked,
                onSignupClick
            )
        }
    }
}

@Composable
fun LoginScreenInputScreen(
    state: LoginViewState,
    onEmailChanged: (String) -> Unit = {},
    onPasswordChanged: (String) -> Unit = {},
    onLoginClicked: () -> Unit = {},
    onSignupClick: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = state.email,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            onValueChange = onEmailChanged,
            placeholder = {
                Text(text = stringResource(id = R.string.enter_email))
            }
        )
        VerticalSpace()
        OutlinedTextField(
            value = state.password,
            onValueChange = onPasswordChanged,
            visualTransformation = PasswordVisualTransformation(),
            maxLines = 1,
            placeholder = {
                Text(text = stringResource(id = R.string.enter_password))
            }
        )
        Button(
            enabled = state.isValid(),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            onClick = onLoginClicked
        ) {
            Text(text = stringResource(id = R.string.login_button_label))
        }
        VerticalSpace()
        Text(
            text = stringResource(id = R.string.dont_have_account),
            modifier = Modifier.clickable { onSignupClick() }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    SpontanTheme() {
        LoginScreen(
            state = LoginViewState.EMPTY.copy(
                email = "",
                name = "",
                password = ""
            )
        )
    }
}