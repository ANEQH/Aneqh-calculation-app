package com.example.ui.screens

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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Chapter
import com.example.data.repository.CalculationRepository
import com.example.ui.components.AneqhAnimatedLogo
import com.example.ui.components.AneqhIntroBanner
import com.example.ui.components.AppTopBar
import com.example.ui.components.StatCard
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.CalculationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: CalculationViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    val filteredChapters = remember(searchQuery) {
        if (searchQuery.isBlank()) {
            CalculationRepository.chapters
        } else {
            val q = searchQuery.trim().lowercase()
            CalculationRepository.chapters.filter { ch ->
                ch.titleEnglish.lowercase().contains(q) ||
                ch.titleHindi.lowercase().contains(q) ||
                ch.description.lowercase().contains(q) ||
                ch.types.any { t -> t.titleEnglish.lowercase().contains(q) || t.titleHindi.lowercase().contains(q) }
            }
        }
    }

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
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    AneqhAnimatedLogo(
                        size = 200.dp,
                        showParticles = true,
                        interactive = true
                    )
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
                title = "Anek Calculation",
                subtitle = if (uiState.appLanguage == com.example.ui.viewmodel.AppLanguage.HINDI) "SSC CGL Math Speed Maker" else "SSC CGL Math Speed Maker",
                streak = uiState.userStats.currentStreak,
                showBackButton = false,
                currentLanguage = uiState.appLanguage,
                onLanguageToggle = { viewModel.toggleLanguage() },
                onStatsClick = { viewModel.navigateTo(AppScreen.STATS) }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            // Hero Banner
            item {
                HeroDrillBanner(
                    onStartSpeedDrill = {
                        viewModel.startSpeedQuiz(questionCount = 10, timePerQuestion = 20)
                    },
                    onOpenSolvers = {
                        viewModel.navigateTo(AppScreen.SOLVERS)
                    },
                    onLogoClick = {
                        showLogoDialog = true
                    }
                )
            }

            // Quick Stats Row
            item {
                val stats = uiState.userStats
                val accuracy = if (stats.totalSolved > 0) {
                    ((stats.totalCorrect.toDouble() / stats.totalSolved) * 100).toInt()
                } else 0

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    StatCard(
                        label = "Solved",
                        value = "${stats.totalSolved}",
                        icon = Icons.Default.CheckCircle,
                        color = AccentEmerald,
                        modifier = Modifier.weight(1f).testTag("stat_solved")
                    )
                    StatCard(
                        label = "Accuracy",
                        value = "$accuracy%",
                        icon = Icons.Default.Psychology,
                        color = PrimaryIndigo,
                        modifier = Modifier.weight(1f).testTag("stat_accuracy")
                    )
                    StatCard(
                        label = "Best Streak",
                        value = "${stats.bestStreak}",
                        icon = Icons.Default.LocalFireDepartment,
                        color = AccentOrange,
                        modifier = Modifier.weight(1f).testTag("stat_best_streak")
                    )
                }
            }

            // Search Bar
            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search chapters or methods (e.g., Squares, CI, Fractions)...") },
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = "Search", tint = PrimaryIndigo)
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(Icons.Default.Clear, contentDescription = "Clear")
                            }
                        }
                    },
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryIndigo,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("home_search_input")
                )
            }

            // Quick Shortcuts Row
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Quick Tools & Shortcuts",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        contentPadding = PaddingValues(horizontal = 2.dp)
                    ) {
                        item {
                            QuickActionChip(
                                title = "⚡ 10s Speed Quiz",
                                subtitle = "Daily Test",
                                color = AccentAmber,
                                onClick = { viewModel.startSpeedQuiz(questionCount = 10, timePerQuestion = 10) }
                            )
                        }
                        item {
                            QuickActionChip(
                                title = "🔢 Smart Solvers",
                                subtitle = "Step-by-step",
                                color = PrimaryIndigo,
                                onClick = { viewModel.navigateTo(AppScreen.SOLVERS) }
                            )
                        }
                        item {
                            QuickActionChip(
                                title = "📊 Fraction ↔ % Table",
                                subtitle = "Cheat Sheet",
                                color = AccentCyan,
                                onClick = { viewModel.navigateTo(AppScreen.CHEAT_SHEETS) }
                            )
                        }
                        item {
                            QuickActionChip(
                                title = "📈 CI & SI Rate Sheet",
                                subtitle = "2 & 3 Yrs",
                                color = AccentPurple,
                                onClick = { viewModel.navigateTo(AppScreen.CHEAT_SHEETS) }
                            )
                        }
                    }
                }
            }

            // Section Header: Chapters
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Calculation Chapters (12)",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "Inspector Chalisa",
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                        color = PrimaryIndigo
                    )
                }
            }

            // Chapters Grid / List
            items(filteredChapters) { chapter ->
                ChapterCard(
                    chapter = chapter,
                    language = uiState.appLanguage,
                    onClick = { viewModel.selectChapter(chapter) },
                    onStartQuiz = { viewModel.startSpeedQuiz(questionCount = 8, chapterId = chapter.id) }
                )
            }
        }
    }
}

@Composable
private fun HeroDrillBanner(
    onStartSpeedDrill: () -> Unit,
    onOpenSolvers: () -> Unit,
    onLogoClick: () -> Unit = {}
) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = Color.Transparent,
        modifier = Modifier
            .fillMaxWidth()
            .testTag("hero_drill_banner")
    ) {
        Box(
            modifier = Modifier
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF1E1B4B),
                            Color(0xFF312E81),
                            Color(0xFF4338CA)
                        )
                    )
                )
                .border(1.dp, Color(0xFF6366F1).copy(alpha = 0.5f), RoundedCornerShape(20.dp))
                .padding(18.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Surface(
                        color = AccentAmber.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(8.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, AccentAmber)
                    ) {
                        Text(
                            text = "OFFICIAL ANEK CHALISA",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Black,
                                letterSpacing = 1.sp
                            ),
                            color = AccentAmberLight,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    Surface(
                        shape = CircleShape,
                        color = Color.White.copy(alpha = 0.15f),
                        modifier = Modifier.clickable { onLogoClick() }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = "View Logo",
                                tint = AccentAmberLight,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Logo View",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = Color.White
                            )
                        }
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "ANEK Speed Math",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Mental shortcuts for 10x faster calculations in SSC CGL.",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color(0xFFE0E7FF),
                                fontSize = 12.sp,
                                lineHeight = 17.sp
                            )
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    // Embedded Animated Emblem
                    AneqhAnimatedLogo(
                        size = 80.dp,
                        showParticles = true,
                        interactive = true,
                        modifier = Modifier.clickable { onLogoClick() }
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Button(
                        onClick = onStartSpeedDrill,
                        colors = ButtonDefaults.buttonColors(containerColor = AccentAmber),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("hero_start_drill_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = null,
                            tint = Color(0xFF1E1B4B),
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Speed Drill",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E1B4B)
                        )
                    }

                    OutlinedButton(
                        onClick = onOpenSolvers,
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF818CF8)),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("hero_open_solvers_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Calculate,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "10x Solvers", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
private fun QuickActionChip(
    title: String,
    subtitle: String,
    color: Color,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = MaterialTheme.colorScheme.surface,
        border = androidx.compose.foundation.BorderStroke(1.dp, color.copy(alpha = 0.35f)),
        tonalElevation = 2.dp,
        modifier = Modifier
            .clickable { onClick() }
            .testTag("quick_chip_${title}")
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                color = color
            )
        }
    }
}

@Composable
private fun ChapterCard(
    chapter: Chapter,
    language: com.example.ui.viewmodel.AppLanguage = com.example.ui.viewmodel.AppLanguage.HINDI,
    onClick: () -> Unit,
    onStartQuiz: () -> Unit
) {
    val title = if (language == com.example.ui.viewmodel.AppLanguage.HINDI) chapter.titleHindi else chapter.titleEnglish
    val subtitle = if (language == com.example.ui.viewmodel.AppLanguage.HINDI) chapter.titleEnglish else chapter.titleHindi

    val iconVector = when (chapter.iconName) {
        "plus" -> Icons.Default.Add
        "minus" -> Icons.Default.Remove
        "multiply" -> Icons.Default.Close
        "divide" -> Icons.Default.Percent
        "decimal" -> Icons.Default.MoreHoriz
        "root" -> Icons.Default.SquareFoot
        "lcm" -> Icons.Default.Hub
        "fraction" -> Icons.Default.PieChart
        "percent" -> Icons.Default.Percent
        "profit" -> Icons.Default.TrendingUp
        "interest" -> Icons.Default.AccountBalance
        "work" -> Icons.Default.Engineering
        else -> Icons.Default.Calculate
    }

    val themeColor = when (chapter.id % 4) {
        1 -> PrimaryIndigo
        2 -> AccentOrange
        3 -> AccentEmerald
        else -> AccentPurple
    }

    Surface(
        shape = RoundedCornerShape(18.dp),
        color = MaterialTheme.colorScheme.surface,
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f)),
        tonalElevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag("chapter_card_${chapter.id}")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(themeColor.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = iconVector,
                            contentDescription = chapter.titleEnglish,
                            tint = themeColor,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "${chapter.id}. $title",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                ),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "($subtitle)",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 12.sp
                                ),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Text(
                            text = "${chapter.types.size} Techniques • Book Pages ${chapter.pageRange}",
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                            color = themeColor
                        )
                    }
                }

                FilledTonalIconButton(
                    onClick = onStartQuiz,
                    colors = IconButtonDefaults.filledTonalIconButtonColors(
                        containerColor = themeColor.copy(alpha = 0.15f),
                        contentColor = themeColor
                    ),
                    modifier = Modifier.size(36.dp).testTag("chapter_quiz_btn_${chapter.id}")
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Test Chapter",
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = chapter.description,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Quick Tip Box
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.TipsAndUpdates,
                        contentDescription = "Tip",
                        tint = AccentAmber,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = chapter.quickTip,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium
                        ),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}
