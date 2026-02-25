package com.pjdev.calisthenicslevel.presentation.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pjdev.calisthenicslevel.presentation.components.StepperField
import com.pjdev.calisthenicslevel.presentation.theme.CalisthenicsLevelTheme

@Composable
fun EpicIntroScreen(onContinue: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Cada atleta tiene un nivel.", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Hoy vamos a descubrir el tuyo.", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = onContinue, modifier = Modifier.fillMaxWidth()) {
            Text("Empezar evaluación")
        }
    }
}

@Composable
fun TestExplainerScreen(onContinue: () -> Unit, onBack: () -> Unit) {
    BackTopBarScaffold(title = "3 pruebas rápidas", onBack = onBack) { modifier ->
        Column(modifier = modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text("• Flexiones")
            Text("• Dominadas (o asistida)")
            Text("• Core")
            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = onContinue, modifier = Modifier.fillMaxWidth()) { Text("Empezar pruebas") }
        }
    }
}

@Composable
fun TestScreen(
    title: String,
    helperText: String? = null,
    value: Int,
    onDecrease: () -> Unit,
    onIncrease: () -> Unit,
    onBack: () -> Unit,
    onContinue: () -> Unit
) {
    BackTopBarScaffold(title = title, onBack = onBack) { modifier ->
        Column(
            modifier = modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Completa esta prueba con tu mejor esfuerzo.")
            helperText?.let { Text(it, style = MaterialTheme.typography.bodyMedium) }
            Card(modifier = Modifier.fillMaxWidth()) {
                StepperField(
                    value = value,
                    onDecrease = onDecrease,
                    onIncrease = onIncrease,
                    modifier = Modifier.padding(12.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onContinue, modifier = Modifier.fillMaxWidth()) {
                Text("Continuar")
            }
        }
    }
}

@Composable
fun LevelRevealScreen(
    state: OnboardingState,
    onSaveProgress: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        if (state.isAnalyzing) {
            Text("Analizando tu rendimiento…", style = MaterialTheme.typography.titleLarge)
        } else {
            Text("Tu nivel actual es: ${state.level}", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
            Text(state.tier, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(16.dp))
            LinearProgressIndicator(progress = { state.progress }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = onSaveProgress, modifier = Modifier.fillMaxWidth()) {
                Text("Guardar mi progreso")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BackTopBarScaffold(
    title: String,
    onBack: () -> Unit,
    content: @Composable (Modifier) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        Text("Atrás")
                    }
                }
            )
        }
    ) { padding ->
        content(Modifier.fillMaxSize().padding(padding))
    }
}

@Preview(showBackground = true)
@Composable
private fun EpicIntroPreview() {
    CalisthenicsLevelTheme {
        EpicIntroScreen(onContinue = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun LevelRevealPreview() {
    CalisthenicsLevelTheme {
        LevelRevealScreen(
            state = OnboardingState(level = 12, tier = "Intermediate", progress = 0.45f, isAnalyzing = false),
            onSaveProgress = {}
        )
    }
}
