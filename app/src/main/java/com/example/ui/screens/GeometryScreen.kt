package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.ChangeHistory
import androidx.compose.material.icons.filled.CropSquare
import androidx.compose.material.icons.filled.Pentagon
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Rectangle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ui.components.AppTopBar
import com.example.ui.theme.AccentEmerald
import com.example.ui.theme.PrimaryIndigo
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.CalculationViewModel

data class GeoShape(
    val name: String,
    val type: String, // 2D or 3D
    val methods: List<String>,
    val icon: ImageVector
)

val shapesList = listOf(
    GeoShape(
        name = "Square (वर्ग)",
        type = "2D Shape",
        methods = listOf(
            "Area (क्षेत्रफल) = a²",
            "Perimeter (परिमाप) = 4a",
            "Diagonal (विकर्ण) = a√2"
        ),
        icon = Icons.Default.CropSquare
    ),
    GeoShape(
        name = "Rectangle (आयत)",
        type = "2D Shape",
        methods = listOf(
            "Area (क्षेत्रफल) = l × b",
            "Perimeter (परिमाप) = 2(l + b)",
            "Diagonal (विकर्ण) = √(l² + b²)"
        ),
        icon = Icons.Default.Rectangle
    ),
    GeoShape(
        name = "Triangle (त्रिभुज)",
        type = "2D Shape",
        methods = listOf(
            "Area (क्षेत्रफल) = ½ × base × height",
            "Perimeter (परिमाप) = a + b + c",
            "Hero's Formula (हीरोन सूत्र) = √[s(s-a)(s-b)(s-c)]"
        ),
        icon = Icons.Default.ChangeHistory
    ),
    GeoShape(
        name = "Circle (वृत्त)",
        type = "2D Shape",
        methods = listOf(
            "Area (क्षेत्रफल) = πr²",
            "Circumference (परिधि) = 2πr",
            "Diameter (व्यास) = 2r"
        ),
        icon = Icons.Default.RadioButtonUnchecked
    ),
    GeoShape(
        name = "Cube (घन)",
        type = "3D Shape",
        methods = listOf(
            "Volume (आयतन) = a³",
            "Total Surface Area (संपूर्ण पृष्ठ) = 6a²",
            "Diagonal (विकर्ण) = a√3"
        ),
        icon = Icons.Default.Category
    ),
    GeoShape(
        name = "Cylinder (बेलन)",
        type = "3D Shape",
        methods = listOf(
            "Volume (आयतन) = πr²h",
            "Curved Surface Area (वक्र पृष्ठ) = 2πrh",
            "Total Surface Area = 2πr(r + h)"
        ),
        icon = Icons.Default.Pentagon
    ),
    GeoShape(
        name = "Sphere (गोला)",
        type = "3D Shape",
        methods = listOf(
            "Volume (आयतन) = 4/3 πr³",
            "Surface Area (पृष्ठ क्षेत्रफल) = 4πr²",
            "Hemisphere Volume (अर्धगोले का आयतन) = 2/3 πr³"
        ),
        icon = Icons.Default.RadioButtonUnchecked
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeometryScreen(viewModel: CalculationViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val lang = uiState.appLanguage

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Geometry Shapes",
                subtitle = "Methods & Properties",
                streak = uiState.userStats.currentStreak,
                showBackButton = true,
                onBackClick = { viewModel.navigateTo(AppScreen.HOME) },
                onThemeToggle = { viewModel.toggleTheme() },
                onLanguageToggle = { viewModel.toggleLanguage() },
                currentLanguage = lang
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 100.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(shapesList) { shape ->
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.surface,
                    border = androidx.compose.foundation.BorderStroke(1.dp, PrimaryIndigo.copy(alpha = 0.2f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = AccentEmerald.copy(alpha = 0.2f)
                            ) {
                                Icon(
                                    imageVector = shape.icon,
                                    contentDescription = null,
                                    tint = AccentEmerald,
                                    modifier = Modifier.padding(12.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(
                                    text = shape.name,
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = shape.type,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        shape.methods.forEach { method ->
                            Row(modifier = Modifier.padding(vertical = 4.dp)) {
                                Text(
                                    text = "•",
                                    color = PrimaryIndigo,
                                    modifier = Modifier.padding(end = 8.dp),
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = method,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
