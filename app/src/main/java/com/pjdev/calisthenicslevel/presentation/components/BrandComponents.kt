package com.pjdev.calisthenicslevel.presentation.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp

@Composable
fun AppLogo(
    modifier: Modifier = Modifier,
    tinted: Boolean = true
) {
    val primary = if (tinted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
    val secondary = if (tinted) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)

    Canvas(modifier = modifier) {
        val cx = size.width / 2f
        val cy = size.height / 2f
        val radius = size.minDimension / 2.2f

        drawCircle(
            brush = Brush.radialGradient(listOf(primary.copy(alpha = 0.3f), Color.Transparent)),
            radius = radius * 1.2f,
            center = Offset(cx, cy)
        )

        val starPath = Path().apply {
            val points = 5
            val outer = radius * 0.9f
            val inner = outer * 0.45f
            for (i in 0 until points * 2) {
                val r = if (i % 2 == 0) outer else inner
                val angle = Math.PI / points * i - Math.PI / 2
                val x = cx + (r * kotlin.math.cos(angle)).toFloat()
                val y = cy + (r * kotlin.math.sin(angle)).toFloat()
                if (i == 0) moveTo(x, y) else lineTo(x, y)
            }
            close()
        }
        drawPath(starPath, color = primary)
        drawLine(
            color = secondary,
            start = Offset(cx - radius * 0.55f, cy + radius * 0.7f),
            end = Offset(cx, cy + radius * 0.35f),
            strokeWidth = 9f,
            cap = StrokeCap.Round
        )
        drawLine(
            color = secondary,
            start = Offset(cx, cy + radius * 0.35f),
            end = Offset(cx + radius * 0.62f, cy - radius * 0.1f),
            strokeWidth = 9f,
            cap = StrokeCap.Round
        )
    }
}

@Composable
fun CaliStarWordmark(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = buildAnnotatedString {
            withStyle(MaterialTheme.typography.headlineMedium.toSpanStyle().copy(fontWeight = FontWeight.Medium)) {
                append("Cali")
            }
            withStyle(
                MaterialTheme.typography.headlineMedium.toSpanStyle().copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary
                )
            ) {
                append("STAR")
            }
        }
    )
}

@Composable
fun PremiumButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    secondary: Boolean = false
) {
    if (secondary) {
        OutlinedButton(
            onClick = onClick,
            enabled = enabled,
            modifier = modifier,
            shape = RoundedCornerShape(20.dp),
            border = ButtonDefaults.outlinedButtonBorder.copy(width = 1.2.dp)
        ) {
            Text(text)
        }
    } else {
        Button(
            onClick = onClick,
            enabled = enabled,
            modifier = modifier.drawBehind {
                drawCircle(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.14f),
                    radius = size.maxDimension,
                    center = Offset(size.width / 2, size.height / 2)
                )
            },
            shape = RoundedCornerShape(22.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text(text, style = MaterialTheme.typography.labelLarge)
        }
    }
}

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier
            .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f), RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f))
    ) {
        Column(modifier = Modifier.padding(18.dp), content = content)
    }
}

@Composable
fun SurfaceCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        tonalElevation = 8.dp,
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(modifier = Modifier.padding(20.dp), content = content)
    }
}

@Composable
fun StepProgressIndicator(
    currentStep: Int,
    totalSteps: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
    ) {
        repeat(totalSteps) { index ->
            val active = index <= currentStep
            Box(
                modifier = Modifier
                    .size(if (active) 28.dp else 10.dp, 10.dp)
                    .background(
                        if (active) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.45f),
                        RoundedCornerShape(50)
                    )
            )
        }
    }
}

@Composable
fun LevelBadge(tier: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(99.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.65f))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(Icons.Outlined.CheckCircle, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
            Text(tier, style = MaterialTheme.typography.labelLarge)
        }
    }
}

@Composable
fun LevelCard(level: Int, modifier: Modifier = Modifier) {
    SurfaceCard(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Icon(Icons.Rounded.Star, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Text("Nivel certificado", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(level.toString(), style = MaterialTheme.typography.displayLarge)
    }
}

@Composable
fun PremiumProgress(progress: Float, modifier: Modifier = Modifier) {
    val animated by animateFloatAsState(targetValue = progress, animationSpec = tween(950), label = "premium_progress")
    LinearProgressIndicator(
        progress = { animated.coerceIn(0f, 1f) },
        modifier = modifier
            .fillMaxWidth()
            .height(10.dp),
        trackColor = MaterialTheme.colorScheme.surfaceVariant,
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
fun LoadingAnimation(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition(label = "loading")
    val rotation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(1400, easing = LinearEasing), RepeatMode.Restart),
        label = "rotation"
    )
    val pulse by transition.animateFloat(
        initialValue = 0.2f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(900), RepeatMode.Reverse),
        label = "pulse"
    )

    Canvas(modifier = modifier.size(92.dp).rotate(rotation)) {
        drawArc(
            brush = Brush.sweepGradient(
                listOf(
                    MaterialTheme.colorScheme.primary.copy(alpha = pulse),
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                )
            ),
            startAngle = 0f,
            sweepAngle = 290f,
            useCenter = false,
            style = Stroke(width = 10f, cap = StrokeCap.Round),
            size = Size(size.width, size.height)
        )
    }
}
