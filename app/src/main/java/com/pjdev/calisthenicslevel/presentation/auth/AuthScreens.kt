package com.pjdev.calisthenicslevel.presentation.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pjdev.calisthenicslevel.presentation.components.AppLogo
import com.pjdev.calisthenicslevel.presentation.components.CaliStarWordmark
import com.pjdev.calisthenicslevel.presentation.components.PremiumButton
import com.pjdev.calisthenicslevel.presentation.components.SurfaceCard
import com.pjdev.calisthenicslevel.presentation.theme.CalisthenicsLevelTheme

@Composable
fun AuthGateScreen(
    loading: Boolean,
    onGoogle: () -> Unit,
    onEmail: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(MaterialTheme.colorScheme.background, MaterialTheme.colorScheme.surface)))
            .safeDrawingPadding()
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                AppLogo(modifier = Modifier.height(58.dp).fillMaxWidth(0.2f))
                CaliStarWordmark()
                Spacer(modifier = Modifier.height(12.dp))
                Text("Guarda tu progreso", style = MaterialTheme.typography.headlineMedium)
            }

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                PremiumButton(
                    text = "Continuar con Google",
                    onClick = onGoogle,
                    enabled = !loading,
                    secondary = true,
                    modifier = Modifier.fillMaxWidth()
                )
                PremiumButton(
                    text = "Continuar con Email",
                    onClick = onEmail,
                    enabled = !loading,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Text(
                "Tu progreso es tuyo. Puedes borrar tu cuenta cuando quieras.",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
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
                .background(Brush.verticalGradient(listOf(MaterialTheme.colorScheme.background, MaterialTheme.colorScheme.surface)))
                .padding(padding)
                .safeDrawingPadding()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            TextButton(onClick = onBack) { Text("Atrás") }
            SurfaceCard(modifier = Modifier.fillMaxWidth()) {
                Text(
                    if (state.isRegisterMode) "Crear cuenta" else "Iniciar sesión",
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = state.email,
                    onValueChange = onEmailChange,
                    label = { Text("Email") },
                    isError = state.emailError != null,
                    modifier = Modifier.fillMaxWidth(),
                    supportingText = { state.emailError?.let { Text(it) } },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = state.password,
                    onValueChange = onPasswordChange,
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    isError = state.passwordError != null,
                    modifier = Modifier.fillMaxWidth(),
                    supportingText = { state.passwordError?.let { Text(it) } },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(18.dp))
                PremiumButton(
                    text = if (state.isRegisterMode) "Registrarme" else "Entrar",
                    onClick = onSubmit,
                    enabled = !state.loading,
                    modifier = Modifier.fillMaxWidth()
                )
                if (state.loading) {
                    Spacer(modifier = Modifier.height(10.dp))
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally), strokeWidth = 2.dp)
                }
                TextButton(onClick = { onModeChange(!state.isRegisterMode) }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    Text(if (state.isRegisterMode) "Ya tengo cuenta" else "No tengo cuenta")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AuthGatePreview() {
    CalisthenicsLevelTheme {
        AuthGateScreen(loading = false, onGoogle = {}, onEmail = {})
    }
}
