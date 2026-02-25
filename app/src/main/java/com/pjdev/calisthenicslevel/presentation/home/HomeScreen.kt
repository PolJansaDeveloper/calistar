package com.pjdev.calisthenicslevel.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.pjdev.calisthenicslevel.presentation.components.AppLogo
import com.pjdev.calisthenicslevel.presentation.components.CaliStarWordmark
import com.pjdev.calisthenicslevel.presentation.components.PremiumButton
import com.pjdev.calisthenicslevel.presentation.components.SurfaceCard

@Composable
fun HomeScreen(onLogout: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(MaterialTheme.colorScheme.background, MaterialTheme.colorScheme.surface)))
            .safeDrawingPadding()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AppLogo(modifier = Modifier.height(46.dp).fillMaxWidth(0.2f))
        CaliStarWordmark()

        SurfaceCard(modifier = Modifier.fillMaxWidth()) {
            Text("Dashboard", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(10.dp))
            Text("Tu nivel: —", style = MaterialTheme.typography.headlineMedium)
            Text("Tu próxima evolución empieza hoy.", color = MaterialTheme.colorScheme.onSurfaceVariant)
        }

        PremiumButton(
            text = "Entrenar hoy (próximamente)",
            onClick = {},
            enabled = false,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.weight(1f))

        PremiumButton(
            text = "Cerrar sesión",
            onClick = onLogout,
            modifier = Modifier.fillMaxWidth(),
            secondary = true
        )
    }
}
