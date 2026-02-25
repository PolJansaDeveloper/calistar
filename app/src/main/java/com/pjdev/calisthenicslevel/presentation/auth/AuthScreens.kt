package com.pjdev.calisthenicslevel.presentation.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun AuthGateScreen(
    loading: Boolean,
    onGoogle: () -> Unit,
    onEmail: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = onGoogle, enabled = !loading, modifier = Modifier.fillMaxWidth()) {
            Text("Continuar con Google")
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = onEmail, enabled = !loading, modifier = Modifier.fillMaxWidth()) {
            Text("Continuar con Email")
        }
    }
}

@Composable
fun AuthFormScreen(
    state: AuthUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onModeChange: (Boolean) -> Unit,
    onSubmit: () -> Unit,
    onBack: () -> Unit,
    onErrorShown: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let {
            snackbarHostState.showSnackbar(it)
            onErrorShown()
        }
    }

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            TextButton(onClick = onBack) { Text("Atrás") }
            Text(
                if (state.isRegisterMode) "Crear cuenta" else "Iniciar sesión",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = state.email,
                onValueChange = onEmailChange,
                label = { Text("Email") },
                isError = state.emailError != null,
                modifier = Modifier.fillMaxWidth(),
                supportingText = { state.emailError?.let { Text(it) } }
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = state.password,
                onValueChange = onPasswordChange,
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                isError = state.passwordError != null,
                modifier = Modifier.fillMaxWidth(),
                supportingText = { state.passwordError?.let { Text(it) } }
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = onSubmit, enabled = !state.loading, modifier = Modifier.fillMaxWidth()) {
                if (state.loading) CircularProgressIndicator() else Text(if (state.isRegisterMode) "Registrarme" else "Entrar")
            }
            TextButton(onClick = { onModeChange(!state.isRegisterMode) }) {
                Text(if (state.isRegisterMode) "Ya tengo cuenta" else "No tengo cuenta")
            }
        }
    }
}
