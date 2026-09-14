package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.AppTopBar
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.CalculationViewModel
import kotlin.math.*

enum class GeoCategory(val label: String, val hindi: String) {
    ALL("All Shapes", "सभी आकृतियाँ"),
    TWO_D("2D Plane", "द्विविमीय (2D)"),
    THREE_D("3D Solid", "त्रिविमीय (3D)"),
    EXAM_HACKS("SSC Tricks", "परीक्षा ट्रिक्स")
}

data class RealisticShape(
    val id: String,
    val nameEn: String,
    val nameHi: String,
    val category: String, // 2D or 3D
    val formulas: List<Pair<String, String>>, // Label to Formula
    val examTricks: List<String>,
    val defaultVal1: Float,
    val defaultVal2: Float = 0f,
    val label1: String = "Side (a)",
    val label2: String = "Height (h)"
)

val realisticShapesList = listOf(
    RealisticShape(
        id = "square",
        nameEn = "Square",
        nameHi = "वर्ग",
        category = "2D",
        formulas = listOf(
            "Area (क्षेत्रफल)" to "A = a²",
            "Perimeter (परिमाप)" to "P = 4a",
            "Diagonal (विकर्ण)" to "d = a√2 ≈ 1.414a",
            "Inradius (अंतःत्रिज्या)" to "r = a / 2",
            "Circumradius (परित्रिज्या)" to "R = a / √2"
        ),
        examTricks = listOf(
            "यदि भुजा x% बढ़े, तो क्षेत्रफल (2x + x²/100)% बढ़ेगा।",
            "Area = ½ × d² (जब विकर्ण दिया हो तो सीधा सूत्र)",
            "वर्ग के चारों कोनों से काटे गए समबाहु त्रिभुज का संबंध।"
        ),
        defaultVal1 = 6f,
        label1 = "Side (a)"
    ),
    RealisticShape(
        id = "rectangle",
        nameEn = "Rectangle",
        nameHi = "आयत",
        category = "2D",
        formulas = listOf(
            "Area (क्षेत्रफल)" to "A = l × b",
            "Perimeter (परिमाप)" to "P = 2(l + b)",
            "Diagonal (विकर्ण)" to "d = √(l² + b²)"
        ),
        examTricks = listOf(
            "Path outside width w: Area = 2w(l + b + 2w)",
            "Path inside width w: Area = 2w(l + b - 2w)",
            "Crossroads in middle width w: Area = w(l + b - w)"
        ),
        defaultVal1 = 8f,
        defaultVal2 = 5f,
        label1 = "Length (l)",
        label2 = "Breadth (b)"
    ),
    RealisticShape(
        id = "triangle",
        nameEn = "Right Triangle",
        nameHi = "समकोण त्रिभुज",
        category = "2D",
        formulas = listOf(
            "Area (क्षेत्रफल)" to "A = ½ × Base × Height",
            "Hypotenuse (कर्ण)" to "h = √(a² + b²)",
            "Perimeter (परिमाप)" to "P = a + b + h",
            "Inradius (अंतःत्रिज्या)" to "r = (a + b - h) / 2"
        ),
        examTricks = listOf(
            "Famous Triplets: (3, 4, 5), (5, 12, 13), (7, 24, 25), (8, 15, 17), (9, 40, 41), (20, 21, 29)",
            "Circumradius of right triangle = Hypotenuse / 2"
        ),
        defaultVal1 = 6f,
        defaultVal2 = 8f,
        label1 = "Base (b)",
        label2 = "Perpendicular (p)"
    ),
    RealisticShape(
        id = "circle",
        nameEn = "Circle",
        nameHi = "वृत्त",
        category = "2D",
        formulas = listOf(
            "Area (क्षेत्रफल)" to "A = πr²",
            "Circumference (परिधि)" to "C = 2πr",
            "Diameter (व्यास)" to "D = 2r",
            "Sector Area (त्रिज्यखंड)" to "A = (θ/360°) × πr²"
        ),
        examTricks = listOf(
            "SSC Golden Rule for r = 7k:\n• r = 7  => C = 44,  Area = 154\n• r = 14 => C = 88,  Area = 616\n• r = 21 => C = 132, Area = 1386\n• r = 28 => C = 176, Area = 2464",
            "अर्धवृत्त का परिमाप = (π + 2)r = 36/7 × r"
        ),
        defaultVal1 = 7f,
        label1 = "Radius (r)"
    ),
    RealisticShape(
        id = "cube",
        nameEn = "Cube",
        nameHi = "घन",
        category = "3D",
        formulas = listOf(
            "Volume (आयतन)" to "V = a³",
            "Total Surface Area (कुल पृष्ठ)" to "TSA = 6a²",
            "Curved Surface (वक्र पृष्ठ)" to "CSA = 4a²",
            "Body Diagonal (विकर्ण)" to "d = a√3 ≈ 1.732a"
        ),
        examTricks = listOf(
            "यदि भुजा x% बढ़े, तो आयतन (3x + 3x²/100 + x³/10000)% बढ़ता है।",
            "घन में रखी जा सकने वाली सबसे लंबी छड़ की लंबाई = a√3 होती है।"
        ),
        defaultVal1 = 5f,
        label1 = "Side (a)"
    ),
    RealisticShape(
        id = "cuboid",
        nameEn = "Cuboid",
        nameHi = "घनाभ",
        category = "3D",
        formulas = listOf(
            "Volume (आयतन)" to "V = l × b × h",
            "Total Surface Area" to "TSA = 2(lb + bh + hl)",
            "Lateral Surface Area (4 दीवारें)" to "LSA = 2h(l + b)",
            "Diagonal (विकर्ण)" to "d = √(l² + b² + h²)"
        ),
        examTricks = listOf(
            "चारों दीवारों का क्षेत्रफल = 2(l + b) × h",
            "यदि तीनों आसन्न फलकों (adjacent faces) का क्षेत्रफल x, y, z हो, तो Volume = √(x × y × z)"
        ),
        defaultVal1 = 6f,
        defaultVal2 = 4f,
        label1 = "Length (l)",
        label2 = "Breadth (b)"
    ),
    RealisticShape(
        id = "cylinder",
        nameEn = "Cylinder",
        nameHi = "बेलन",
        category = "3D",
        formulas = listOf(
            "Volume (आयतन)" to "V = πr²h",
            "Curved Surface Area (वक्र पृष्ठ)" to "CSA = 2πrh",
            "Total Surface Area (संपूर्ण पृष्ठ)" to "TSA = 2πr(r + h)"
        ),
        examTricks = listOf(
            "जब आयताकार चादर को मोड़ा जाता है, तो चादर की लंबाई बेलन की परिधि (2πr) बन जाती है।",
            "Ratio CSA : TSA = h : (r + h)"
        ),
        defaultVal1 = 7f,
        defaultVal2 = 10f,
        label1 = "Radius (r)",
        label2 = "Height (h)"
    ),
    RealisticShape(
        id = "cone",
        nameEn = "Right Cone",
        nameHi = "शंकु",
        category = "3D",
        formulas = listOf(
            "Slant Height (तिर्यक ऊंचाई)" to "l = √(r² + h²)",
            "Volume (आयतन)" to "V = ⅓ πr²h",
            "Curved Surface Area" to "CSA = πrl",
            "Total Surface Area" to "TSA = πr(l + r)"
        ),
        examTricks = listOf(
            "बेलन और समान त्रिज्या व ऊंचाई वाले शंकु के आयतन का अनुपात 3 : 1 होता है।",
            "शंकु को बीच से काटने पर Frustum (छिन्नक) बनता है।"
        ),
        defaultVal1 = 6f,
        defaultVal2 = 8f,
        label1 = "Radius (r)",
        label2 = "Height (h)"
    ),
    RealisticShape(
        id = "sphere",
        nameEn = "Sphere",
        nameHi = "गोला",
        category = "3D",
        formulas = listOf(
            "Volume (आयतन)" to "V = ⁴⁄₃ πr³",
            "Surface Area (पृष्ठ क्षेत्रफल)" to "A = 4πr²",
            "Hemisphere Volume (अर्धगोला)" to "V = ⅔ πr³",
            "Hemisphere TSA (संपूर्ण पृष्ठ)" to "TSA = 3πr²",
            "Hemisphere CSA (वक्र पृष्ठ)" to "CSA = 2πr²"
        ),
        examTricks = listOf(
            "गोले को पिघलाकर छोटी गोलियां बनाने पर: n = (R / r)³",
            "अर्धगोले का TSA = 3πr² (नीचे का आधार πr² + वक्र 2πr²)।"
        ),
        defaultVal1 = 7f,
        label1 = "Radius (r)"
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeometryScreen(viewModel: CalculationViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val lang = uiState.appLanguage
    var selectedCategory by remember { mutableStateOf(GeoCategory.ALL) }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Realistic Geometry 3D",
                subtitle = "Visual Shapes & Exam Methods",
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
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 100.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header Banner
            item {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = MaterialTheme.colorScheme.surface,
                    border = androidx.compose.foundation.BorderStroke(1.dp, PrimaryIndigo.copy(alpha = 0.3f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(
                                        PrimaryIndigo.copy(alpha = 0.15f),
                                        AccentEmerald.copy(alpha = 0.08f)
                                    )
                                )
                            )
                            .padding(18.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = RoundedCornerShape(14.dp),
                                color = PrimaryIndigo.copy(alpha = 0.2f),
                                modifier = Modifier.size(54.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        Icons.Default.Architecture,
                                        contentDescription = null,
                                        tint = PrimaryIndigo,
                                        modifier = Modifier.size(32.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "3D & 2D Geometry Studio",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "Realistic visual models with interactive parameter solvers & SSC CGL tricks",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }

            // Category Chips
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(GeoCategory.values().toList()) { cat ->
                        val isSelected = selectedCategory == cat
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedCategory = cat },
                            label = {
                                Text(
                                    text = cat.label,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = PrimaryIndigo,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }
            }

            // Shapes List
            val filteredShapes = when (selectedCategory) {
                GeoCategory.ALL -> realisticShapesList
                GeoCategory.TWO_D -> realisticShapesList.filter { it.category == "2D" }
                GeoCategory.THREE_D -> realisticShapesList.filter { it.category == "3D" }
                GeoCategory.EXAM_HACKS -> realisticShapesList
            }

            items(filteredShapes, key = { it.id }) { shape ->
                RealisticShapeCard(shape = shape)
            }
        }
    }
}

@Composable
fun RealisticShapeCard(shape: RealisticShape) {
    var val1 by remember { mutableStateOf(shape.defaultVal1) }
    var val2 by remember { mutableStateOf(shape.defaultVal2) }
    var showLab by remember { mutableStateOf(false) }

    Surface(
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.surface,
        border = androidx.compose.foundation.BorderStroke(1.2.dp, PrimaryIndigo.copy(alpha = 0.25f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header: Title & Badges
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (shape.category == "3D") AccentEmerald.copy(alpha = 0.15f) else AccentCyan.copy(alpha = 0.15f)
                    ) {
                        Text(
                            text = shape.category,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            color = if (shape.category == "3D") AccentEmerald else AccentCyan
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "${shape.nameEn} (${shape.nameHi})",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                // Interactive Lab Toggle
                TextButton(
                    onClick = { showLab = !showLab },
                    colors = ButtonDefaults.textButtonColors(contentColor = PrimaryIndigo)
                ) {
                    Icon(
                        imageVector = if (showLab) Icons.Default.Close else Icons.Default.Tune,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = if (showLab) "Hide Lab" else "Live Lab",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Realistic Visual Canvas Drawing
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f),
                border = androidx.compose.foundation.BorderStroke(1.dp, PrimaryIndigo.copy(alpha = 0.12f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(190.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    RealisticShapeCanvas(shapeId = shape.id, val1 = val1, val2 = val2)
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Interactive Live Lab (Slider & Calculation Output)
            AnimatedVisibility(visible = showLab) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f),
                            RoundedCornerShape(12.dp)
                        )
                        .padding(12.dp)
                ) {
                    Text(
                        text = "🧪 Live Dimension Lab (मान बदलकर देखें)",
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                        color = PrimaryIndigo
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Slider 1
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "${shape.label1}: ${val1.toInt()}",
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier.width(100.dp)
                        )
                        Slider(
                            value = val1,
                            onValueChange = { val1 = it.roundToInt().toFloat() },
                            valueRange = 1f..30f,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    // Slider 2 (if present)
                    if (shape.defaultVal2 > 0) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "${shape.label2}: ${val2.toInt()}",
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                                modifier = Modifier.width(100.dp)
                            )
                            Slider(
                                value = val2,
                                onValueChange = { val2 = it.roundToInt().toFloat() },
                                valueRange = 1f..30f,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    // Computed Values Card
                    val computedResults = computeShapeValues(shape.id, val1, val2)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        computedResults.forEach { (label, value) ->
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = AccentEmerald.copy(alpha = 0.12f),
                                modifier = Modifier.weight(1f)
                            ) {
                                Column(modifier = Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(text = label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Text(text = value, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold), color = AccentEmerald)
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

            // Formulas Section
            Text(
                text = "Core Formulas (मुख्य सूत्र):",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(6.dp))
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                shape.formulas.forEach { (name, formula) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f),
                                RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 10.dp, vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = name,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = formula,
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                            color = PrimaryIndigo
                        )
                    }
                }
            }

            // Exam Tricks
            if (shape.examTricks.isNotEmpty()) {
                Spacer(modifier = Modifier.height(10.dp))
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = AccentAmber.copy(alpha = 0.1f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, AccentAmber.copy(alpha = 0.3f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = AccentOrange,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "SSC Exam Hack",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = AccentOrange
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        shape.examTricks.forEach { trick ->
                            Text(
                                text = "• $trick",
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.5.sp),
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.padding(vertical = 2.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

fun computeShapeValues(id: String, v1: Float, v2: Float): List<Pair<String, String>> {
    fun fmt(d: Double): String = String.format("%.2f", d)
    return when (id) {
        "square" -> listOf(
            "Area" to fmt((v1 * v1).toDouble()),
            "Perimeter" to fmt((4 * v1).toDouble()),
            "Diagonal" to fmt((v1 * sqrt(2.0)))
        )
        "rectangle" -> listOf(
            "Area" to fmt((v1 * v2).toDouble()),
            "Perimeter" to fmt((2 * (v1 + v2)).toDouble()),
            "Diagonal" to fmt(sqrt((v1 * v1 + v2 * v2).toDouble()))
        )
        "triangle" -> {
            val h = sqrt((v1 * v1 + v2 * v2).toDouble())
            listOf(
                "Area" to fmt((0.5 * v1 * v2)),
                "Hypotenuse" to fmt(h),
                "Inradius" to fmt(((v1 + v2 - h) / 2.0))
            )
        }
        "circle" -> listOf(
            "Area" to fmt((Math.PI * v1 * v1)),
            "Circumference" to fmt((2 * Math.PI * v1)),
            "Diameter" to fmt((2 * v1).toDouble())
        )
        "cube" -> listOf(
            "Volume" to fmt((v1 * v1 * v1).toDouble()),
            "TSA" to fmt((6 * v1 * v1).toDouble()),
            "Diagonal" to fmt((v1 * sqrt(3.0)))
        )
        "cuboid" -> {
            val v3 = 4.0 // fixed height for quick preview
            val vol = v1 * v2 * v3
            val tsa = 2 * (v1 * v2 + v2 * v3 + v3 * v1)
            listOf(
                "Volume" to fmt(vol),
                "TSA" to fmt(tsa),
                "Diagonal" to fmt(sqrt((v1 * v1 + v2 * v2 + v3 * v3).toDouble()))
            )
        }
        "cylinder" -> listOf(
            "Volume" to fmt((Math.PI * v1 * v1 * v2)),
            "CSA" to fmt((2 * Math.PI * v1 * v2)),
            "TSA" to fmt((2 * Math.PI * v1 * (v1 + v2)))
        )
        "cone" -> {
            val l = sqrt((v1 * v1 + v2 * v2).toDouble())
            listOf(
                "Slant (l)" to fmt(l),
                "Volume" to fmt((Math.PI * v1 * v1 * v2 / 3.0)),
                "CSA" to fmt((Math.PI * v1 * l))
            )
        }
        "sphere" -> listOf(
            "Volume" to fmt((4.0 / 3.0 * Math.PI * v1 * v1 * v1)),
            "Surface Area" to fmt((4 * Math.PI * v1 * v1)),
            "Hemi Vol" to fmt((2.0 / 3.0 * Math.PI * v1 * v1 * v1))
        )
        else -> emptyList()
    }
}

/**
 * High-Fidelity Realistic Canvas Rendering for Geometric Shapes
 */
@Composable
fun RealisticShapeCanvas(shapeId: String, val1: Float, val2: Float) {
    Canvas(modifier = Modifier.fillMaxSize().padding(12.dp)) {
        val w = size.width
        val h = size.height
        val cx = w / 2f
        val cy = h / 2f

        // Draw Technical Blueprint Grid in background
        drawTechnicalGrid(w, h)

        when (shapeId) {
            "square" -> drawRealisticSquare(cx, cy)
            "rectangle" -> drawRealisticRectangle(cx, cy)
            "triangle" -> drawRealisticTriangle(cx, cy)
            "circle" -> drawRealisticCircle(cx, cy)
            "cube" -> drawRealistic3DCube(cx, cy)
            "cuboid" -> drawRealistic3DCuboid(cx, cy)
            "cylinder" -> drawRealistic3DCylinder(cx, cy)
            "cone" -> drawRealistic3DCone(cx, cy)
            "sphere" -> drawRealistic3DSphere(cx, cy)
        }
    }
}

private fun DrawScope.drawTechnicalGrid(w: Float, h: Float) {
    val step = 24f
    var x = 0f
    while (x <= w) {
        drawLine(
            color = Color(0x104F46E5),
            start = Offset(x, 0f),
            end = Offset(x, h),
            strokeWidth = 0.8f
        )
        x += step
    }
    var y = 0f
    while (y <= h) {
        drawLine(
            color = Color(0x104F46E5),
            start = Offset(0f, y),
            end = Offset(w, y),
            strokeWidth = 0.8f
        )
        y += step
    }
}

private fun DrawScope.drawRealisticSquare(cx: Float, cy: Float) {
    val s = 110f
    val left = cx - s / 2f
    val top = cy - s / 2f
    val right = cx + s / 2f
    val bottom = cy + s / 2f

    // Soft gradient fill
    drawRect(
        brush = Brush.linearGradient(
            colors = listOf(PrimaryIndigo.copy(alpha = 0.25f), AccentCyan.copy(alpha = 0.15f)),
            start = Offset(left, top),
            end = Offset(right, bottom)
        ),
        topLeft = Offset(left, top),
        size = Size(s, s)
    )

    // Border
    drawRect(
        color = PrimaryIndigo,
        topLeft = Offset(left, top),
        size = Size(s, s),
        style = Stroke(width = 2.5f)
    )

    // Diagonal (dashed)
    drawLine(
        color = AccentRose,
        start = Offset(left, bottom),
        end = Offset(right, top),
        strokeWidth = 2f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 6f), 0f)
    )

    // Right angle marker at bottom-left
    val raSize = 14f
    drawPath(
        path = Path().apply {
            moveTo(left, bottom - raSize)
            lineTo(left + raSize, bottom - raSize)
            lineTo(left + raSize, bottom)
        },
        color = AccentEmerald,
        style = Stroke(width = 1.5f)
    )

    // Corner nodes
    val corners = listOf(Offset(left, top), Offset(right, top), Offset(right, bottom), Offset(left, bottom))
    corners.forEach { pt ->
        drawCircle(color = Color.White, radius = 4f, center = pt)
        drawCircle(color = PrimaryIndigo, radius = 2f, center = pt)
    }

    // Dimension indicators
    drawLine(color = AccentCyan, start = Offset(left, top - 12f), end = Offset(right, top - 12f), strokeWidth = 1.5f)
    drawLine(color = AccentCyan, start = Offset(left, top - 16f), end = Offset(left, top - 8f), strokeWidth = 1.5f)
    drawLine(color = AccentCyan, start = Offset(right, top - 16f), end = Offset(right, top - 8f), strokeWidth = 1.5f)
}

private fun DrawScope.drawRealisticRectangle(cx: Float, cy: Float) {
    val rw = 150f
    val rh = 90f
    val left = cx - rw / 2f
    val top = cy - rh / 2f
    val right = cx + rw / 2f
    val bottom = cy + rh / 2f

    drawRect(
        brush = Brush.linearGradient(
            colors = listOf(AccentEmerald.copy(alpha = 0.22f), PrimaryIndigo.copy(alpha = 0.12f)),
            start = Offset(left, top),
            end = Offset(right, bottom)
        ),
        topLeft = Offset(left, top),
        size = Size(rw, rh)
    )

    drawRect(
        color = AccentEmerald,
        topLeft = Offset(left, top),
        size = Size(rw, rh),
        style = Stroke(width = 2.5f)
    )

    // Diagonal
    drawLine(
        color = AccentAmber,
        start = Offset(left, bottom),
        end = Offset(right, top),
        strokeWidth = 2f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 6f), 0f)
    )

    // Corner right angle marker
    val raSize = 14f
    drawPath(
        path = Path().apply {
            moveTo(left, bottom - raSize)
            lineTo(left + raSize, bottom - raSize)
            lineTo(left + raSize, bottom)
        },
        color = PrimaryIndigo,
        style = Stroke(width = 1.5f)
    )

    // Corner nodes
    listOf(Offset(left, top), Offset(right, top), Offset(right, bottom), Offset(left, bottom)).forEach { pt ->
        drawCircle(color = Color.White, radius = 4f, center = pt)
        drawCircle(color = AccentEmerald, radius = 2f, center = pt)
    }
}

private fun DrawScope.drawRealisticTriangle(cx: Float, cy: Float) {
    val b = 130f
    val h = 100f
    val pA = Offset(cx - b / 2f, cy + h / 2f) // Bottom-left
    val pB = Offset(cx + b / 2f, cy + h / 2f) // Bottom-right
    val pC = Offset(cx - b / 2f, cy - h / 2f) // Top-left (Right angle at A)

    val path = Path().apply {
        moveTo(pA.x, pA.y)
        lineTo(pB.x, pB.y)
        lineTo(pC.x, pC.y)
        close()
    }

    drawPath(
        path = path,
        brush = Brush.linearGradient(
            colors = listOf(AccentCyan.copy(alpha = 0.3f), AccentPurple.copy(alpha = 0.15f)),
            start = pA,
            end = pB
        )
    )
    drawPath(path = path, color = AccentCyan, style = Stroke(width = 2.5f))

    // Hypotenuse highlight
    drawLine(color = AccentRose, start = pC, end = pB, strokeWidth = 2.5f)

    // Right angle marker
    val ra = 14f
    drawPath(
        path = Path().apply {
            moveTo(pA.x, pA.y - ra)
            lineTo(pA.x + ra, pA.y - ra)
            lineTo(pA.x + ra, pA.y)
        },
        color = AccentAmber,
        style = Stroke(width = 1.5f)
    )

    listOf(pA, pB, pC).forEach { pt ->
        drawCircle(color = Color.White, radius = 4.5f, center = pt)
        drawCircle(color = AccentCyan, radius = 2.5f, center = pt)
    }
}

private fun DrawScope.drawRealisticCircle(cx: Float, cy: Float) {
    val r = 65f

    // Radial gradient disk
    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(AccentEmerald.copy(alpha = 0.35f), PrimaryIndigo.copy(alpha = 0.08f)),
            center = Offset(cx, cy),
            radius = r
        ),
        radius = r,
        center = Offset(cx, cy)
    )

    // Glowing circumference
    drawCircle(
        color = AccentEmerald,
        radius = r,
        center = Offset(cx, cy),
        style = Stroke(width = 2.5f)
    )

    // Radius line
    val angle = -35.0 * (Math.PI / 180.0)
    val edge = Offset(cx + (r * cos(angle)).toFloat(), cy + (r * sin(angle)).toFloat())
    drawLine(color = AccentAmber, start = Offset(cx, cy), end = edge, strokeWidth = 2.5f)

    // Diameter dashed line
    drawLine(
        color = AccentRose.copy(alpha = 0.7f),
        start = Offset(cx - r, cy),
        end = Offset(cx + r, cy),
        strokeWidth = 1.8f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(8f, 5f), 0f)
    )

    // Center point O
    drawCircle(color = Color.White, radius = 5f, center = Offset(cx, cy))
    drawCircle(color = AccentAmber, radius = 3f, center = Offset(cx, cy))
    drawCircle(color = Color.White, radius = 4f, center = edge)
}

/**
 * 3D Isometric Cube with Depth Shading & Translucent Back Edges
 */
private fun DrawScope.drawRealistic3DCube(cx: Float, cy: Float) {
    val a = 55f
    val cos30 = cos(Math.PI / 6.0).toFloat()
    val sin30 = sin(Math.PI / 6.0).toFloat()

    val dx = a * cos30
    val dy = a * sin30

    // Front center vertex
    val vCenter = Offset(cx, cy + 8f)
    val vTop = Offset(cx, vCenter.y - a)
    val vLeft = Offset(cx - dx, vCenter.y - dy)
    val vRight = Offset(cx + dx, vCenter.y - dy)
    val vTopApex = Offset(cx, vTop.y - dy * 2)

    val vBottom = Offset(cx, vCenter.y + a)
    val vBottomLeft = Offset(cx - dx, vBottom.y - dy)
    val vBottomRight = Offset(cx + dx, vBottom.y - dy)

    // Hidden back vertex
    val vBack = Offset(cx, vBottom.y - dy * 2)

    // Back hidden edges (dashed)
    val dashed = PathEffect.dashPathEffect(floatArrayOf(6f, 4f), 0f)
    drawLine(color = Color(0x60A78BFA), start = vBack, end = vTopApex, strokeWidth = 1.5f, pathEffect = dashed)
    drawLine(color = Color(0x60A78BFA), start = vBack, end = vBottomLeft, strokeWidth = 1.5f, pathEffect = dashed)
    drawLine(color = Color(0x60A78BFA), start = vBack, end = vBottomRight, strokeWidth = 1.5f, pathEffect = dashed)

    // Top Face (Brightest illumination)
    val topFace = Path().apply {
        moveTo(vCenter.x, vCenter.y - a)
        lineTo(vRight.x, vRight.y - a)
        lineTo(vTopApex.x, vTopApex.y)
        lineTo(vLeft.x, vLeft.y - a)
        close()
    }
    drawPath(
        path = topFace,
        brush = Brush.linearGradient(
            colors = listOf(Color(0xFFFDE68A), Color(0xFFF59E0B)),
            start = Offset(vLeft.x, vTopApex.y),
            end = Offset(vRight.x, vCenter.y)
        )
    )
    drawPath(path = topFace, color = Color(0xFFB45309), style = Stroke(width = 1.8f))

    // Left Face (Mid-tone cyan/indigo)
    val leftFace = Path().apply {
        moveTo(vCenter.x, vCenter.y)
        lineTo(vLeft.x, vLeft.y)
        lineTo(vBottomLeft.x, vBottomLeft.y)
        lineTo(vBottom.x, vBottom.y)
        close()
    }
    drawPath(
        path = leftFace,
        brush = Brush.linearGradient(
            colors = listOf(Color(0xFF38BDF8), Color(0xFF0369A1)),
            start = Offset(vLeft.x, vLeft.y),
            end = Offset(vBottom.x, vBottom.y)
        )
    )
    drawPath(path = leftFace, color = Color(0xFF0284C7), style = Stroke(width = 1.8f))

    // Right Face (Deep emerald tone)
    val rightFace = Path().apply {
        moveTo(vCenter.x, vCenter.y)
        lineTo(vRight.x, vRight.y)
        lineTo(vBottomRight.x, vBottomRight.y)
        lineTo(vBottom.x, vBottom.y)
        close()
    }
    drawPath(
        path = rightFace,
        brush = Brush.linearGradient(
            colors = listOf(Color(0xFF34D399), Color(0xFF047857)),
            start = Offset(vCenter.x, vCenter.y),
            end = Offset(vBottomRight.x, vBottomRight.y)
        )
    )
    drawPath(path = rightFace, color = Color(0xFF059669), style = Stroke(width = 1.8f))

    // Body diagonal (front bottom to back top apex)
    drawLine(
        color = Color.White,
        start = vBottom,
        end = vTopApex,
        strokeWidth = 2.2f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(8f, 5f), 0f)
    )

    // Corner Highlights
    listOf(vCenter, vBottom, vLeft, vRight, vBottomLeft, vBottomRight, vTopApex).forEach { pt ->
        drawCircle(color = Color.White, radius = 3.5f, center = pt)
    }
}

private fun DrawScope.drawRealistic3DCuboid(cx: Float, cy: Float) {
    val l = 75f
    val w = 45f
    val h = 60f

    val dx = w * 0.866f
    val dy = w * 0.5f

    val frontLeft = cx - l / 2f
    val frontRight = cx + l / 2f
    val frontTop = cy - h / 2f + 10f
    val frontBottom = cy + h / 2f + 10f

    // Front Face
    drawRect(
        brush = Brush.linearGradient(
            colors = listOf(PrimaryIndigo.copy(alpha = 0.5f), AccentCyan.copy(alpha = 0.35f)),
            start = Offset(frontLeft, frontTop),
            end = Offset(frontRight, frontBottom)
        ),
        topLeft = Offset(frontLeft, frontTop),
        size = Size(l, h)
    )
    drawRect(color = PrimaryIndigo, topLeft = Offset(frontLeft, frontTop), size = Size(l, h), style = Stroke(2f))

    // Top Face
    val topFace = Path().apply {
        moveTo(frontLeft, frontTop)
        lineTo(frontLeft + dx, frontTop - dy)
        lineTo(frontRight + dx, frontTop - dy)
        lineTo(frontRight, frontTop)
        close()
    }
    drawPath(path = topFace, brush = Brush.linearGradient(colors = listOf(AccentAmber.copy(0.7f), AccentOrange.copy(0.5f))))
    drawPath(path = topFace, color = AccentOrange, style = Stroke(2f))

    // Right Face
    val rightFace = Path().apply {
        moveTo(frontRight, frontTop)
        lineTo(frontRight + dx, frontTop - dy)
        lineTo(frontRight + dx, frontBottom - dy)
        lineTo(frontRight, frontBottom)
        close()
    }
    drawPath(path = rightFace, brush = Brush.linearGradient(colors = listOf(AccentEmerald.copy(0.6f), Color(0xFF065F46))))
    drawPath(path = rightFace, color = AccentEmerald, style = Stroke(2f))
}

private fun DrawScope.drawRealistic3DCylinder(cx: Float, cy: Float) {
    val rx = 65f
    val ry = 22f
    val h = 90f

    val topCy = cy - h / 2f
    val botCy = cy + h / 2f

    // Curved Body Gradient
    val bodyPath = Path().apply {
        moveTo(cx - rx, topCy)
        lineTo(cx + rx, topCy)
        lineTo(cx + rx, botCy)
        arcTo(
            rect = androidx.compose.ui.geometry.Rect(cx - rx, botCy - ry, cx + rx, botCy + ry),
            startAngleDegrees = 0f,
            sweepAngleDegrees = 180f,
            forceMoveTo = false
        )
        lineTo(cx - rx, topCy)
        close()
    }
    drawPath(
        path = bodyPath,
        brush = Brush.linearGradient(
            colors = listOf(
                PrimaryIndigo.copy(alpha = 0.6f),
                AccentCyan.copy(alpha = 0.2f),
                PrimaryIndigo.copy(alpha = 0.7f)
            ),
            start = Offset(cx - rx, cy),
            end = Offset(cx + rx, cy)
        )
    )

    // Side Borders
    drawLine(color = PrimaryIndigo, start = Offset(cx - rx, topCy), end = Offset(cx - rx, botCy), strokeWidth = 2.5f)
    drawLine(color = PrimaryIndigo, start = Offset(cx + rx, topCy), end = Offset(cx + rx, botCy), strokeWidth = 2.5f)

    // Bottom Ellipse front half
    drawArc(
        color = PrimaryIndigo,
        startAngle = 0f,
        sweepAngle = 180f,
        useCenter = false,
        topLeft = Offset(cx - rx, botCy - ry),
        size = Size(rx * 2, ry * 2),
        style = Stroke(width = 2.5f)
    )
    // Bottom Ellipse back half (dashed)
    drawArc(
        color = PrimaryIndigo.copy(alpha = 0.5f),
        startAngle = 180f,
        sweepAngle = 180f,
        useCenter = false,
        topLeft = Offset(cx - rx, botCy - ry),
        size = Size(rx * 2, ry * 2),
        style = Stroke(width = 1.5f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(6f, 4f), 0f))
    )

    // Top Ellipse (Filled cap)
    val topRect = androidx.compose.ui.geometry.Rect(cx - rx, topCy - ry, cx + rx, topCy + ry)
    drawOval(
        brush = Brush.linearGradient(
            colors = listOf(AccentCyan.copy(alpha = 0.5f), AccentEmerald.copy(alpha = 0.35f)),
            start = Offset(cx - rx, topCy - ry),
            end = Offset(cx + rx, topCy + ry)
        ),
        topLeft = Offset(cx - rx, topCy - ry),
        size = Size(rx * 2, ry * 2)
    )
    drawOval(
        color = AccentCyan,
        topLeft = Offset(cx - rx, topCy - ry),
        size = Size(rx * 2, ry * 2),
        style = Stroke(width = 2.5f)
    )

    // Radius marker on top
    drawLine(color = AccentAmber, start = Offset(cx, topCy), end = Offset(cx + rx, topCy), strokeWidth = 2f)
    drawCircle(color = Color.White, radius = 3.5f, center = Offset(cx, topCy))

    // Height marker
    drawLine(color = AccentRose, start = Offset(cx + rx + 16f, topCy), end = Offset(cx + rx + 16f, botCy), strokeWidth = 2f)
}

private fun DrawScope.drawRealistic3DCone(cx: Float, cy: Float) {
    val rx = 65f
    val ry = 22f
    val h = 105f

    val apex = Offset(cx, cy - h / 2f - 10f)
    val botCy = cy + h / 2f

    val conePath = Path().apply {
        moveTo(apex.x, apex.y)
        lineTo(cx + rx, botCy)
        arcTo(
            rect = androidx.compose.ui.geometry.Rect(cx - rx, botCy - ry, cx + rx, botCy + ry),
            startAngleDegrees = 0f,
            sweepAngleDegrees = 180f,
            forceMoveTo = false
        )
        lineTo(apex.x, apex.y)
        close()
    }

    drawPath(
        path = conePath,
        brush = Brush.linearGradient(
            colors = listOf(
                AccentAmber.copy(alpha = 0.65f),
                Color(0xFFFDE68A).copy(alpha = 0.3f),
                AccentOrange.copy(alpha = 0.7f)
            ),
            start = Offset(cx - rx, cy),
            end = Offset(cx + rx, cy)
        )
    )

    // Slant edges
    drawLine(color = AccentOrange, start = apex, end = Offset(cx - rx, botCy), strokeWidth = 2.5f)
    drawLine(color = AccentOrange, start = apex, end = Offset(cx + rx, botCy), strokeWidth = 2.5f)

    // Bottom Ellipse front
    drawArc(
        color = AccentOrange,
        startAngle = 0f,
        sweepAngle = 180f,
        useCenter = false,
        topLeft = Offset(cx - rx, botCy - ry),
        size = Size(rx * 2, ry * 2),
        style = Stroke(width = 2.5f)
    )
    // Bottom Ellipse back (dashed)
    drawArc(
        color = AccentOrange.copy(alpha = 0.5f),
        startAngle = 180f,
        sweepAngle = 180f,
        useCenter = false,
        topLeft = Offset(cx - rx, botCy - ry),
        size = Size(rx * 2, ry * 2),
        style = Stroke(width = 1.5f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(6f, 4f), 0f))
    )

    // Altitude (h) dashed center line
    drawLine(
        color = AccentRose,
        start = apex,
        end = Offset(cx, botCy),
        strokeWidth = 2f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(6f, 4f), 0f)
    )
    // Radius (r) line
    drawLine(color = AccentEmerald, start = Offset(cx, botCy), end = Offset(cx + rx, botCy), strokeWidth = 2f)

    drawCircle(color = Color.White, radius = 4f, center = apex)
    drawCircle(color = Color.White, radius = 3.5f, center = Offset(cx, botCy))
}

private fun DrawScope.drawRealistic3DSphere(cx: Float, cy: Float) {
    val r = 65f

    // Volumetric 3D specular gradient
    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(
                Color.White.copy(alpha = 0.85f),
                AccentCyan,
                PrimaryIndigo,
                Color(0xFF0F172A)
            ),
            center = Offset(cx - r * 0.35f, cy - r * 0.35f),
            radius = r * 1.3f
        ),
        radius = r,
        center = Offset(cx, cy)
    )

    // Outer rim
    drawCircle(
        color = AccentCyan.copy(alpha = 0.8f),
        radius = r,
        center = Offset(cx, cy),
        style = Stroke(width = 2.2f)
    )

    // Equator Ellipse
    val eqRy = 18f
    drawArc(
        color = Color.White.copy(alpha = 0.7f),
        startAngle = 0f,
        sweepAngle = 180f,
        useCenter = false,
        topLeft = Offset(cx - r, cy - eqRy),
        size = Size(r * 2, eqRy * 2),
        style = Stroke(width = 2f)
    )
    drawArc(
        color = Color.White.copy(alpha = 0.35f),
        startAngle = 180f,
        sweepAngle = 180f,
        useCenter = false,
        topLeft = Offset(cx - r, cy - eqRy),
        size = Size(r * 2, eqRy * 2),
        style = Stroke(width = 1.5f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(6f, 4f), 0f))
    )

    // Radius line
    val angle = -40.0 * (Math.PI / 180.0)
    val edge = Offset(cx + (r * cos(angle)).toFloat(), cy + (r * sin(angle)).toFloat())
    drawLine(color = AccentAmber, start = Offset(cx, cy), end = edge, strokeWidth = 2.5f)

    drawCircle(color = Color.White, radius = 4f, center = Offset(cx, cy))
    drawCircle(color = AccentAmber, radius = 2f, center = Offset(cx, cy))
    drawCircle(color = Color.White, radius = 3.5f, center = edge)
}
