package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Architecture
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.ViewInAr
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.GridOn
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Style
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.GpsFixed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.repository.CalculationRepository
import com.example.util.SmartMathAnalysis
import com.example.util.SmartMathAssistant
import com.example.ui.components.AneqhAnimatedLogo
import com.example.ui.components.AppTopBar
import com.example.ui.components.StatCard
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppDictionary.tr
import com.example.ui.viewmodel.AppLanguage
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.CalculationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: CalculationViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val lang = uiState.appLanguage
    var showLogoDialog by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    val searchAnalysis = remember(searchQuery) {
        if (searchQuery.isNotBlank()) SmartMathAssistant.analyze(searchQuery) else null
    }

    if (showLogoDialog) {
        AlertDialog(
            onDismissRequest = { showLogoDialog = false },
            confirmButton = {
                TextButton(onClick = { showLogoDialog = false }) {
                    Text("Close", fontWeight = FontWeight.Bold)
                }
            },
            title = {
                Text(
                    text = "ANEK Official Insignia",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    AneqhAnimatedLogo(size = 200.dp, showParticles = true, interactive = true)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Tap emblem to pulse particles",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        )
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = tr("app_title", lang),
                subtitle = tr("app_subtitle", lang),
                streak = uiState.userStats.currentStreak,
                showBackButton = false,
                currentLanguage = lang,
                onLanguageToggle = { viewModel.toggleLanguage() },
                onStatsClick = { viewModel.navigateTo(AppScreen.STATS) },
                onThemeToggle = { viewModel.toggleTheme() }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 100.dp)
        ) {
            
            // Section: Smart Universal Search & Quick Math Assistant
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier.fillMaxWidth().testTag("smart_search_input"),
                        placeholder = {
                            Text(
                                text = if (lang == AppLanguage.HINDI) "स्मार्ट AI सर्च (उदा: 75², 48×52, 3/8, focus, cube 12)..." else "Smart AI Math Search (e.g. 75², 48x52, 3/8, focus)...",
                                style = MaterialTheme.typography.bodyMedium,
                                fontSize = 13.sp
                            )
                        },
                        leadingIcon = {
                            Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = PrimaryIndigo)
                        },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { searchQuery = "" }) {
                                    Icon(Icons.Default.Close, contentDescription = "Clear")
                                }
                            }
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryIndigo,
                            unfocusedBorderColor = PrimaryIndigo.copy(alpha = 0.3f),
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface
                        )
                    )

                    // Quick Smart Query Chips
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        val quickChips = listOf(
                            "75²" to "75^2",
                            "48 × 52" to "48 * 52",
                            "3/8 in %" to "3/8",
                            "15% of 840" to "15% of 840",
                            "Cube 12" to "12^3",
                            "√7056" to "sqrt 7056",
                            "Triplet 8" to "triplet 8",
                            "19 Table" to "19 table"
                        )
                        items(quickChips.size) { i ->
                            val chip = quickChips[i]
                            AssistChip(
                                onClick = { searchQuery = chip.second },
                                label = { Text(chip.first, fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
                                colors = AssistChipDefaults.assistChipColors(
                                    containerColor = PrimaryIndigo.copy(alpha = 0.08f),
                                    labelColor = PrimaryIndigo
                                ),
                                border = androidx.compose.foundation.BorderStroke(1.dp, PrimaryIndigo.copy(alpha = 0.25f))
                            )
                        }
                    }

                    // Smart Analysis Result Card
                    if (searchAnalysis != null) {
                        Surface(
                            shape = RoundedCornerShape(18.dp),
                            color = MaterialTheme.colorScheme.surface,
                            border = androidx.compose.foundation.BorderStroke(1.5.dp, PrimaryIndigo.copy(alpha = 0.5f)),
                            modifier = Modifier.fillMaxWidth().padding(top = 4.dp).testTag("smart_analysis_card")
                        ) {
                            Column(
                                modifier = Modifier
                                    .background(
                                        Brush.verticalGradient(
                                            listOf(
                                                PrimaryIndigo.copy(alpha = 0.12f),
                                                Color.Transparent
                                            )
                                        )
                                    )
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = searchAnalysis.title,
                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                        color = PrimaryIndigo,
                                        modifier = Modifier.weight(1f)
                                    )
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = AccentEmerald.copy(alpha = 0.15f)
                                    ) {
                                        Text(
                                            text = searchAnalysis.result,
                                            fontWeight = FontWeight.Black,
                                            fontSize = 13.sp,
                                            color = AccentEmerald,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                        )
                                    }
                                }

                                Text(
                                    text = "⚡ विधि: ${searchAnalysis.method}",
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )

                                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    searchAnalysis.explanationSteps.forEach { step ->
                                        Text(
                                            text = "• $step",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }

                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = AccentAmber.copy(alpha = 0.12f),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = "💡 ${searchAnalysis.mentalShortcut}",
                                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                                        color = AccentOrange,
                                        modifier = Modifier.padding(8.dp)
                                    )
                                }

                                if (searchAnalysis.digitalRootCheck != null) {
                                    Text(
                                        text = "🔍 ${searchAnalysis.digitalRootCheck}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = AccentCyan,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }

                                if (searchAnalysis.targetScreen != null) {
                                    Button(
                                        onClick = { viewModel.navigateTo(searchAnalysis.targetScreen) },
                                        modifier = Modifier.fillMaxWidth().testTag("launch_smart_target_btn")
                                    ) {
                                        Icon(Icons.Default.Launch, contentDescription = null, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text("Open Feature")
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Section: Stats & Daily Target
            item {
                val stats = uiState.userStats
                val accuracy = if (stats.totalSolved > 0) ((stats.totalCorrect.toDouble() / stats.totalSolved) * 100).toInt() else 0
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    StatCard(label = tr("solved", lang), value = "${stats.totalSolved}", color = PrimaryIndigo, icon = Icons.Default.CheckCircle, modifier = Modifier.weight(1f))
                    StatCard(label = tr("streak", lang), value = "${stats.currentStreak} 🔥", color = AccentAmber, icon = Icons.Default.LocalFireDepartment, modifier = Modifier.weight(1f))
                    StatCard(label = tr("accuracy", lang), value = "$accuracy%", color = AccentEmerald, icon = Icons.Default.GpsFixed, modifier = Modifier.weight(1f))
                }
            }

            // Section: Live Events & Core Engine
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                    MegaActionCard(
                        title = tr("start_speed_drill", lang),
                        subtitle = tr("speed_drill_desc", lang),
                        icon = Icons.Default.FlashOn,
                        color = AccentAmber,
                        onClick = { viewModel.startSpeedQuiz(questionCount = 10, timePerQuestion = 20) },
                        modifier = Modifier.weight(1.5f)
                    )
                    MegaActionCard(
                        title = tr("live_test", lang),
                        subtitle = "SSC CGL 2024",
                        icon = Icons.Default.Sensors,
                        color = AccentRose,
                        onClick = { viewModel.navigateTo(AppScreen.PYQ_TESTS) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Section: SSC PYQ Chapterwise
            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = tr("pyq_zone_title", lang),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 18.sp),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        item {
                            ChipCard(title = tr("pyq_mock_test", lang), subtitle = "Mixed Qs", color = AccentRose, icon = Icons.Default.Quiz) { viewModel.navigateTo(AppScreen.PYQ_TESTS) }
                        }
                        item {
                            ChipCard(title = tr("pyq_algebra", lang), subtitle = "250+ Qs", color = PrimaryIndigo, icon = Icons.Default.Calculate) { viewModel.navigateTo(AppScreen.PYQ_TESTS) }
                        }
                        item {
                            ChipCard(title = tr("pyq_geometry", lang), subtitle = "180+ Qs", color = AccentEmerald, icon = Icons.Default.Category) { viewModel.navigateTo(AppScreen.PYQ_TESTS) }
                        }
                        item {
                            ChipCard(title = tr("pyq_arithmetic", lang), subtitle = "400+ Qs", color = AccentCyan, icon = Icons.Default.TrendingUp) { viewModel.navigateTo(AppScreen.PYQ_TESTS) }
                        }
                    }
                }
            }

            // Section: Advanced Pro Tools
            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = tr("advanced_tools", lang),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 18.sp),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        item { ChipCard(title = tr("10x_solvers", lang), subtitle = "Instant Ans", color = AccentOrange, icon = Icons.Default.AutoAwesome) { viewModel.navigateTo(AppScreen.SOLVERS) } }
                        item { ChipCard(title = tr("calculator", lang), subtitle = "Math Pro", color = AccentCyan, icon = Icons.Default.Calculate) { viewModel.navigateTo(AppScreen.CALCULATOR) } }
                        item { ChipCard(title = tr("ci_si_rates", lang), subtitle = "Fast %", color = AccentPurple, icon = Icons.Default.TrendingUp) { viewModel.navigateTo(AppScreen.CHEAT_SHEETS) } }
                        item { ChipCard(title = tr("visual_math", lang), subtitle = "3D Graphs", color = PrimaryIndigo, icon = Icons.Default.ViewInAr) { viewModel.navigateTo(AppScreen.ALGEBRA_GRAPH) } }
                        item { ChipCard(title = tr("concept_book", lang), subtitle = "1000x Theory", color = AccentEmerald, icon = Icons.Default.MenuBook) { viewModel.navigateTo(AppScreen.CHEAT_SHEETS) } }
                        item { ChipCard(title = "SSC Syllabus", subtitle = "Tier 1 & 2", color = AccentRose, icon = Icons.Default.Checklist) { viewModel.navigateTo(AppScreen.SYLLABUS) } }
                        item { ChipCard(title = "Tables 1-30", subtitle = "Pahade", color = AccentAmber, icon = Icons.Default.GridOn) { viewModel.navigateTo(AppScreen.TABLES) } }
                        item { ChipCard(title = "Table Drill", subtitle = "Infinite MCQs", color = AccentCyan, icon = Icons.Default.FormatListNumbered) { viewModel.navigateTo(AppScreen.TABLE_DRILL) } }
                    }
                }
            }

            // Section: CRAZY FEATURES
            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = "Crazy Features & Study Suite",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 18.sp),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        item { ChipCard(title = "Focus Clock", subtitle = "Pomodoro & Zen", color = PrimaryIndigo, icon = Icons.Default.Timer) { viewModel.navigateTo(AppScreen.FOCUS_CLOCK) } }
                        item { ChipCard(title = "Study Planner", subtitle = "Exam Countdown", color = AccentCyan, icon = Icons.Default.CalendarToday) { viewModel.navigateTo(AppScreen.STUDY_PLANNER) } }
                        item { ChipCard(title = "Geometry 3D", subtitle = "Realistic Shapes", color = AccentEmerald, icon = Icons.Default.Architecture) { viewModel.navigateTo(AppScreen.GEOMETRY) } }
                        item { ChipCard(title = "Brain Gym", subtitle = "1M+ Questions", color = AccentRose, icon = Icons.Default.FitnessCenter) { viewModel.navigateTo(AppScreen.MATH_GYM) } }
                        item { ChipCard(title = "Vedic Tricks", subtitle = "100+ Hacks", color = AccentAmber, icon = Icons.Default.Psychology) { viewModel.navigateTo(AppScreen.VEDIC_TRICKS) } }
                        item { ChipCard(title = "Formulas", subtitle = "Flashcards", color = PrimaryIndigo, icon = Icons.Default.Style) { viewModel.navigateTo(AppScreen.FORMULA_CARDS) } }
                    }
                }
            }

            // Spotlight: Focus Mode Clock (Pomodoro & Zen Study)
            item {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = MaterialTheme.colorScheme.surface,
                    border = androidx.compose.foundation.BorderStroke(1.2.dp, PrimaryIndigo.copy(alpha = 0.4f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .clickable { viewModel.navigateTo(AppScreen.FOCUS_CLOCK) }
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(
                                        PrimaryIndigo.copy(alpha = 0.18f),
                                        AccentRose.copy(alpha = 0.12f)
                                    )
                                )
                            )
                            .padding(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(14.dp),
                                    color = PrimaryIndigo.copy(alpha = 0.25f),
                                    modifier = Modifier.size(50.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            Icons.Default.Timer,
                                            contentDescription = null,
                                            tint = PrimaryIndigo,
                                            modifier = Modifier.size(30.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.width(14.dp))
                                Column {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = if (lang == AppLanguage.HINDI) "फोकस मोड क्लॉक" else "Focus Mode Clock",
                                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Surface(
                                            shape = RoundedCornerShape(6.dp),
                                            color = PrimaryIndigo
                                        ) {
                                            Text(
                                                text = "POMODORO",
                                                color = Color.White,
                                                fontSize = 9.sp,
                                                fontWeight = FontWeight.ExtraBold,
                                                modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.dp)
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = if (lang == AppLanguage.HINDI)
                                            "25m / 50m डीप स्टडी • अल्फा वेव्स ऑडियो • ज़ेन मोड"
                                        else
                                            "25m / 50m Deep Study • Alpha Waves Audio • Zen Fullscreen",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                            Icon(
                                Icons.Default.ChevronRight,
                                contentDescription = null,
                                tint = PrimaryIndigo,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }

            // Spotlight: 3D Geometry Studio
            item {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = MaterialTheme.colorScheme.surface,
                    border = androidx.compose.foundation.BorderStroke(1.2.dp, AccentEmerald.copy(alpha = 0.4f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .clickable { viewModel.navigateTo(AppScreen.GEOMETRY) }
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(
                                        AccentEmerald.copy(alpha = 0.18f),
                                        PrimaryIndigo.copy(alpha = 0.12f)
                                    )
                                )
                            )
                            .padding(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(14.dp),
                                    color = AccentEmerald.copy(alpha = 0.25f),
                                    modifier = Modifier.size(50.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            Icons.Default.Architecture,
                                            contentDescription = null,
                                            tint = AccentEmerald,
                                            modifier = Modifier.size(30.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.width(14.dp))
                                Column {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = "Realistic Geometry 3D",
                                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Surface(
                                            shape = RoundedCornerShape(6.dp),
                                            color = AccentEmerald
                                        ) {
                                            Text(
                                                text = "3D LAB",
                                                color = Color.White,
                                                fontSize = 9.sp,
                                                fontWeight = FontWeight.ExtraBold,
                                                modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.dp)
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = "Cube, Cylinder, Cone, Sphere with live dimension sliders & exam hacks",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                            Icon(
                                Icons.Default.ChevronRight,
                                contentDescription = null,
                                tint = AccentEmerald,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }

            // Section: Inspector Chalisa Chapters
            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = tr("inspector_chapters", lang),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 18.sp),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    CalculationRepository.chapters.forEach { chapter ->
                        Surface(
                            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp).clip(RoundedCornerShape(16.dp)).clickable { viewModel.selectChapter(chapter) },
                            color = MaterialTheme.colorScheme.surface,
                            border = androidx.compose.foundation.BorderStroke(1.dp, PrimaryIndigo.copy(alpha = 0.15f))
                        ) {
                            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                                Surface(shape = CircleShape, color = AccentAmber.copy(alpha = 0.15f), modifier = Modifier.size(48.dp)) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(text = chapter.id.toString(), fontWeight = FontWeight.Bold, color = AccentOrange)
                                    }
                                }
                                Spacer(modifier = Modifier.width(16.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = if (lang == AppLanguage.HINDI) chapter.titleHindi else chapter.titleEnglish,
                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "${chapter.types.size} Concepts • ${chapter.types.sumOf { it.exercises.size }} Exercises",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                Icon(Icons.Default.ChevronRight, contentDescription = null, tint = PrimaryIndigo)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MegaActionCard(title: String, subtitle: String, icon: androidx.compose.ui.graphics.vector.ImageVector, color: Color, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.clip(RoundedCornerShape(16.dp)).clickable(onClick = onClick),
        color = color.copy(alpha = 0.15f),
        border = androidx.compose.foundation.BorderStroke(1.dp, color.copy(alpha = 0.3f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(28.dp))
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = title, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold), color = MaterialTheme.colorScheme.onSurface)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
fun ChipCard(title: String, subtitle: String, color: Color, icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    Surface(
        modifier = Modifier.width(140.dp).clip(RoundedCornerShape(12.dp)).clickable(onClick = onClick),
        color = MaterialTheme.colorScheme.surface,
        border = androidx.compose.foundation.BorderStroke(1.dp, color.copy(alpha = 0.3f))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = title, style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold), color = MaterialTheme.colorScheme.onSurface, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = subtitle, style = MaterialTheme.typography.labelSmall, color = color, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }
}
