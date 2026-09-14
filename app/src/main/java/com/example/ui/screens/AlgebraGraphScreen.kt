package com.example.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.CalculationViewModel
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.PI

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlgebraGraphScreen(
    viewModel: CalculationViewModel,
    modifier: Modifier = Modifier
) {
    var selectedFunc by remember { mutableStateOf(0) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("3D Algebra Visualizer", fontWeight = FontWeight.ExtraBold) },
                navigationIcon = {
                    IconButton(onClick = { viewModel.navigateTo(AppScreen.HOME) }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        },
        modifier = modifier
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Surface(
                color = AccentAmber.copy(alpha = 0.1f),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Understand algebra spatially! Select an equation to view its 3D/2D representation.",
                    modifier = Modifier.padding(12.dp),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = selectedFunc == 0,
                    onClick = { selectedFunc = 0 },
                    label = { Text("z = x² + y² (Paraboloid)") }
                )
                FilterChip(
                    selected = selectedFunc == 1,
                    onClick = { selectedFunc = 1 },
                    label = { Text("y = x³ (Cubic)") }
                )
            }
            
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = Color(0xFF1E1E1E),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                if (selectedFunc == 0) {
                    Paraboloid3DGraph()
                } else {
                    Cubic2DGraph()
                }
            }
            
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = PrimaryIndigo.copy(alpha = 0.1f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = if (selectedFunc == 0) "🔥 SSC PYQ Concept: Min/Max Values" else "🔥 SSC PYQ Concept: Roots & Polynomials",
                        fontWeight = FontWeight.Bold,
                        color = PrimaryIndigo
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = if (selectedFunc == 0) "Notice how x² and y² are always positive. The minimum value of this 3D bowl is exactly at (0,0). Used in Algebra max/min questions." else "A cubic curve crosses the x-axis up to 3 times, representing its 3 roots. x³+1/x³ concepts can be visualized as steep growth curves.",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@Composable
fun Paraboloid3DGraph() {
    Canvas(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        val cx = size.width / 2
        val cy = size.height / 2
        val scale = size.width / 15f
        
        // Draw Isometric Grid
        val angle = PI / 6
        val cosA = cos(angle).toFloat()
        val sinA = sin(angle).toFloat()
        
        fun iso(x: Float, y: Float, z: Float): Offset {
            val px = (x - z) * cosA
            val py = y + (x + z) * sinA
            return Offset(cx + px * scale, cy - py * scale) // -py because canvas y is down
        }
        
        // Draw Axes
        drawLine(Color.DarkGray, iso(-5f,0f,0f), iso(5f,0f,0f), strokeWidth = 2f)
        drawLine(Color.DarkGray, iso(0f,-5f,0f), iso(0f,5f,0f), strokeWidth = 2f)
        drawLine(Color.DarkGray, iso(0f,0f,-5f), iso(0f,0f,5f), strokeWidth = 2f)
        
        // Draw Paraboloid z = x^2 + y^2 => y_up = x^2 + z_depth^2
        val range = -4..4
        for (x in range) {
            val path = Path()
            var first = true
            for (z in range) {
                val y = (x * x + z * z) * 0.15f
                val pt = iso(x.toFloat(), y, z.toFloat())
                if (first) {
                    path.moveTo(pt.x, pt.y)
                    first = false
                } else {
                    path.lineTo(pt.x, pt.y)
                }
            }
            drawPath(path, color = AccentEmerald, style = Stroke(width = 3f))
        }
        for (z in range) {
            val path = Path()
            var first = true
            for (x in range) {
                val y = (x * x + z * z) * 0.15f
                val pt = iso(x.toFloat(), y, z.toFloat())
                if (first) {
                    path.moveTo(pt.x, pt.y)
                    first = false
                } else {
                    path.lineTo(pt.x, pt.y)
                }
            }
            drawPath(path, color = AccentCyan, style = Stroke(width = 3f))
        }
    }
}

@Composable
fun Cubic2DGraph() {
    Canvas(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        val cx = size.width / 2
        val cy = size.height / 2
        val scaleX = size.width / 10f
        val scaleY = size.height / 20f
        
        // Draw Axes
        drawLine(Color.Gray, Offset(0f, cy), Offset(size.width, cy), strokeWidth = 2f)
        drawLine(Color.Gray, Offset(cx, 0f), Offset(cx, size.height), strokeWidth = 2f)
        
        val path = Path()
        var first = true
        for (pixelX in 0..size.width.toInt() step 2) {
            val mathX = (pixelX - cx) / scaleX
            val mathY = mathX * mathX * mathX
            val pixelY = cy - (mathY * scaleY)
            
            if (first) {
                path.moveTo(pixelX.toFloat(), pixelY)
                first = false
            } else {
                path.lineTo(pixelX.toFloat(), pixelY)
            }
        }
        
        drawPath(path, color = AccentOrange, style = Stroke(width = 6f))
    }
}
