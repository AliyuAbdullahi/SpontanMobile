package com.lek.spontan.android.presentation.authentication.signup.composables

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
import com.lek.spontan.android.presentation.authentication.signup.SignUpViewState
import com.lek.spontan.android.presentation.shared.composables.LoadingScreen
import com.lek.spontan.android.presentation.shared.composables.VerticalSpace
import com.lek.spontan.android.presentation.shared.theme.SpontanTheme

@Composable
fun SignupScreen(
    state: SignUpViewState,
    onNameChanged: (String) -> Unit = {},
    onEmailChanged: (String) -> Unit = {},
    onPasswordChanged: (String) -> Unit = {},
    onSignupClicked: () -> Unit = {},
    onGotoLoginClicked: () -> Unit = {},
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (state.isLoading) {
            LoadingScreen()
        } else {
            SignupInputScreen(
                state,
                onNameChanged,
                onEmailChanged,
                onPasswordChanged,
                onSignupClicked,
                onGotoLoginClicked
            )
        }
    }
}

@Composable
fun SignupInputScreen(
    state: SignUpViewState,
    onNameChanged: (String) -> Unit = {},
    onEmailChanged: (String) -> Unit = {},
    onPasswordChanged: (String) -> Unit = {},
    onSignupClicked: () -> Unit = {},
    onGoToLoginClicked: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = state.name,
            maxLines = 1,
            onValueChange = onNameChanged,
            placeholder = {
                Text(text = stringResource(id = R.string.enter_your_name))
            }
        )
        VerticalSpace()
        OutlinedTextField(
            value = state.email,
            maxLines = 1,
            onValueChange = onEmailChanged,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
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
            enabled = state.isInputValid,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            onClick = onSignupClicked
        ) {
            Text(text = stringResource(id = R.string.signup_button_label))
        }
        VerticalSpace()
        Text(
            text = stringResource(id = R.string.already_have_account_login),
            modifier = Modifier.clickable { onGoToLoginClicked() }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    SpontanTheme() {
        SignupScreen(
            state = SignUpViewState.EMPTY.copy(
                email = "",
                name = "",
                password = ""
            )
        )
    }
}