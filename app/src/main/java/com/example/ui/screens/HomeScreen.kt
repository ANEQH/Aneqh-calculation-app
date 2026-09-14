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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.repository.CalculationRepository
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
                        text = "Crazy Features & Tricks",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 18.sp),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        item { ChipCard(title = "Brain Gym", subtitle = "1M+ Questions", color = AccentRose, icon = Icons.Default.FitnessCenter) { viewModel.navigateTo(AppScreen.MATH_GYM) } }
                        item { ChipCard(title = "Vedic Tricks", subtitle = "100+ Hacks", color = AccentAmber, icon = Icons.Default.Psychology) { viewModel.navigateTo(AppScreen.VEDIC_TRICKS) } }
                        item { ChipCard(title = "Formulas", subtitle = "Flashcards", color = PrimaryIndigo, icon = Icons.Default.Style) { viewModel.navigateTo(AppScreen.FORMULA_CARDS) } }
                        item { ChipCard(title = "Geometry", subtitle = "Shapes & Formula", color = AccentEmerald, icon = Icons.Default.Architecture) { viewModel.navigateTo(AppScreen.GEOMETRY) } }
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
