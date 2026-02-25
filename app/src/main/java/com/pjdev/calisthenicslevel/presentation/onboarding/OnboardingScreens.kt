package com.pjdev.calisthenicslevel.presentation.onboarding

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material.icons.outlined.SelfImprovement
import androidx.compose.material.icons.outlined.SportsGymnastics
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pjdev.calisthenicslevel.presentation.components.AppLogo
import com.pjdev.calisthenicslevel.presentation.components.CaliStarWordmark
import com.pjdev.calisthenicslevel.presentation.components.GlassCard
import com.pjdev.calisthenicslevel.presentation.components.LevelBadge
import com.pjdev.calisthenicslevel.presentation.components.LevelCard
import com.pjdev.calisthenicslevel.presentation.components.LoadingAnimation
import com.pjdev.calisthenicslevel.presentation.components.PremiumButton
import com.pjdev.calisthenicslevel.presentation.components.PremiumProgress
import com.pjdev.calisthenicslevel.presentation.components.StepProgressIndicator
import com.pjdev.calisthenicslevel.presentation.theme.CalisthenicsLevelTheme
import kotlinx.coroutines.delay

@Composable
fun EpicIntroScreen(onContinue: () -> Unit) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.surface,
                        Color(0xFF080A0F)
                    )
                )
            )
            .safeDrawingPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            AnimatedVisibility(visible, enter = fadeIn(tween(650)) + slideInHorizontally()) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    AppLogo(modifier = Modifier.size(46.dp))
                    CaliStarWordmark()
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                AnimatedVisibility(visible, enter = fadeIn(tween(700, delayMillis = 120))) {
                    Text("Cada atleta tiene un nivel.", style = MaterialTheme.typography.displayMedium)
                }
                AnimatedVisibility(visible, enter = fadeIn(tween(700, delayMillis = 260))) {
                    Text("Hoy vamos a descubrir el tuyo.", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                AnimatedVisibility(visible, enter = fadeIn(tween(650, delayMillis = 380)) + scaleIn()) {
                    PremiumButton(
                        text = "Empezar evaluación",
                        onClick = onContinue,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Text(
                    "3 pruebas. 2 minutos. Tu nivel real.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Composable
fun TestExplainerScreen(onContinue: () -> Unit, onBack: () -> Unit) {
    BackTopBarScaffold(title = "Evaluación oficial", onBack = onBack) { modifier ->
        Column(
            modifier = modifier
                .padding(horizontal = 24.dp, vertical = 18.dp)
                .safeDrawingPadding(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Text("3 pruebas rápidas", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(14.dp))
                OfficialCheckChip("Push", "Flexiones", Icons.Outlined.SportsGymnastics)
                OfficialCheckChip("Pull", "Dominadas", Icons.Outlined.FitnessCenter)
                OfficialCheckChip("Core", "Plancha", Icons.Outlined.SelfImprovement)
            }
            Text(
                "Sin prisa. Técnica limpia. Resultados honestos.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.weight(1f))
            PremiumButton(
                text = "Empezar pruebas",
                onClick = onContinue,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun AssessmentStepScaffold(
    stepTitle: String,
    stepSubtitle: String,
    instruction: String,
    value: Int,
    unitLabel: String,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    helperText: String,
    onContinue: () -> Unit,
    onBack: () -> Unit,
    currentStep: Int
) {
    BackTopBarScaffold(title = stepTitle, onBack = onBack) { modifier ->
        Column(
            modifier = modifier
                .padding(horizontal = 24.dp, vertical = 12.dp)
                .safeDrawingPadding(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            StepProgressIndicator(currentStep = currentStep, totalSteps = 4)
            Text(stepSubtitle, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)

            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Text(instruction, style = MaterialTheme.typography.bodyLarge)
                if (helperText.isNotBlank()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(helperText, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            AnimatedContent(
                targetState = value,
                transitionSpec = {
                    (fadeIn(tween(220)) + scaleIn(tween(240, easing = FastOutSlowInEasing))).togetherWith(fadeOut(tween(180)))
                },
                label = "value_change"
            ) { animatedValue ->
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(animatedValue.toString(), style = MaterialTheme.typography.displayLarge, fontWeight = FontWeight.Black)
                    Text(unitLabel, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                StepperCircleButton(symbol = "−", onClick = onDecrement)
                Spacer(modifier = Modifier.width(18.dp))
                StepperCircleButton(symbol = "+", onClick = onIncrement)
            }

            Spacer(modifier = Modifier.weight(1f))
            PremiumButton(
                text = "Continuar",
                onClick = onContinue,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun LevelRevealScreen(
    state: OnboardingState,
    onSaveProgress: () -> Unit
) {
    var revealStarted by remember { mutableStateOf(false) }
    var showTier by remember { mutableStateOf(false) }
    var showLevel by remember { mutableStateOf(false) }
    var showProgress by remember { mutableStateOf(false) }
    var showButton by remember { mutableStateOf(false) }

    LaunchedEffect(state.isAnalyzing) {
        if (!state.isAnalyzing && !revealStarted) {
            revealStarted = true
            showTier = true
            delay(220)
            showLevel = true
            delay(280)
            showProgress = true
            delay(220)
            showButton = true
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(MaterialTheme.colorScheme.background, MaterialTheme.colorScheme.surface)
                )
            )
            .safeDrawingPadding()
    ) {
        if (state.isAnalyzing) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LoadingAnimation()
                Spacer(modifier = Modifier.height(20.dp))
                Text("Analizando tu rendimiento…", style = MaterialTheme.typography.titleLarge)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Text("Tu nivel actual es:", style = MaterialTheme.typography.titleLarge)
                }
                item {
                    AnimatedVisibility(visible = showTier, enter = fadeIn(tween(350))) {
                        LevelBadge(state.tier)
                    }
                }
                item {
                    AnimatedVisibility(visible = showLevel, enter = scaleIn(tween(420)) + fadeIn()) {
                        LevelCard(level = state.level, modifier = Modifier.fillMaxWidth())
                    }
                }
                item {
                    AnimatedVisibility(visible = showProgress, enter = fadeIn()) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            PremiumProgress(progress = state.progress)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                "Progreso al siguiente nivel: ${(state.progress * 100).toInt()}%",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
                item {
                    Text(
                        "Evaluación inicial completada",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                item {
                    AnimatedVisibility(visible = showButton, enter = fadeIn(tween(260))) {
                        PremiumButton("Guardar mi progreso", onSaveProgress, modifier = Modifier.fillMaxWidth())
                    }
                }
            }
        }
    }
}

@Composable
private fun OfficialCheckChip(code: String, label: String, icon: ImageVector) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(34.dp)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.18f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        }
        Text(code, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
        Text(label, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
private fun StepperCircleButton(symbol: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(64.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant, CircleShape)
            .padding(1.dp),
        contentAlignment = Alignment.Center
    ) {
        PremiumButton(
            text = symbol,
            onClick = onClick,
            modifier = Modifier.size(56.dp),
            secondary = true
        )
    }
}

@Composable
private fun BackTopBarScaffold(
    title: String,
    onBack: () -> Unit,
    content: @Composable (Modifier) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(title, style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Rounded.ArrowBack, contentDescription = "Atrás")
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
private fun AssessmentStepPreview() {
    CalisthenicsLevelTheme {
        AssessmentStepScaffold(
            stepTitle = "Prueba de flexiones",
            stepSubtitle = "Paso 1 de 4",
            instruction = "Realiza flexiones estrictas en una sola serie.",
            value = 20,
            unitLabel = "reps",
            onIncrement = {},
            onDecrement = {},
            helperText = "",
            onContinue = {},
            onBack = {},
            currentStep = 0
        )
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
