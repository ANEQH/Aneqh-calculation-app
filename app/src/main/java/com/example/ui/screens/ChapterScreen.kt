package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CalculationStep
import com.example.data.model.CalculationType
import com.example.data.model.Chapter
import com.example.ui.components.AppTopBar
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.CalculationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChapterScreen(
    viewModel: CalculationViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val chapter = uiState.selectedChapter ?: return

    val title = if (uiState.appLanguage == com.example.ui.viewmodel.AppLanguage.HINDI) chapter.titleHindi else chapter.titleEnglish
    val subtitle = if (uiState.appLanguage == com.example.ui.viewmodel.AppLanguage.HINDI) chapter.titleEnglish else chapter.titleHindi

    Scaffold(
        topBar = {
            AppTopBar(
                title = "${chapter.id}. $title",
                subtitle = "$subtitle (Pages ${chapter.pageRange})",
                streak = uiState.userStats.currentStreak,
                showBackButton = true,
                currentLanguage = uiState.appLanguage,
                onLanguageToggle = { viewModel.toggleLanguage() },
                onBackClick = { viewModel.navigateTo(AppScreen.HOME) },
                onStatsClick = { viewModel.navigateTo(AppScreen.STATS) }
            )
        },
        bottomBar = {
            Surface(
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 6.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = { viewModel.startSpeedQuiz(questionCount = 10, chapterId = chapter.id) },
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryIndigo),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .testTag("chapter_start_drill_btn")
                    ) {
                        Icon(Icons.Default.Bolt, contentDescription = null, tint = AccentAmber)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Practice Speed Drill (10Q)", fontWeight = FontWeight.Bold)
                    }
                }
            }
        },
        modifier = modifier
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(top = 8.dp, bottom = 100.dp)
        ) {
            // Chapter Quick Overview Card
            item {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = PrimaryIndigo.copy(alpha = 0.08f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, PrimaryIndigo.copy(alpha = 0.3f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Lightbulb,
                                contentDescription = null,
                                tint = AccentAmber,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Inspector's Golden Rule",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = PrimaryIndigo
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = chapter.quickTip,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp
                            ),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            // Techniques Header
            item {
                Text(
                    text = "Method Types (${chapter.types.size})",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            // Calculation Types List
            items(chapter.types) { calcType ->
                TypeTechniqueCard(
                    calcType = calcType,
                    language = uiState.appLanguage,
                    onPracticeClick = {
                        viewModel.startPracticeForType(calcType)
                    }
                )
            }
        }
    }
}

@Composable
private fun TypeTechniqueCard(
    calcType: CalculationType,
    language: com.example.ui.viewmodel.AppLanguage = com.example.ui.viewmodel.AppLanguage.HINDI,
    onPracticeClick: () -> Unit
) {
    var isExpanded by remember { mutableStateOf(true) }
    
    val title = if (language == com.example.ui.viewmodel.AppLanguage.HINDI) calcType.titleHindi else calcType.titleEnglish
    val subtitle = if (language == com.example.ui.viewmodel.AppLanguage.HINDI) calcType.titleEnglish else calcType.titleHindi
    val hint = if (language == com.example.ui.viewmodel.AppLanguage.HINDI) calcType.hintHindi else calcType.hintEnglish
    val hintTitle = if (language == com.example.ui.viewmodel.AppLanguage.HINDI) "💡 Hint (दिमाग में रखने का नियम):" else "💡 Mental Calculation Hint:"

    Surface(
        shape = RoundedCornerShape(18.dp),
        color = MaterialTheme.colorScheme.surface,
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f)),
        tonalElevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .testTag("type_card_${calcType.typeNumber}")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Card Title Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isExpanded = !isExpanded }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(PrimaryIndigo),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "T${calcType.typeNumber}",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            ),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = subtitle,
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                IconButton(onClick = { isExpanded = !isExpanded }) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = if (isExpanded) "Collapse" else "Expand"
                    )
                }
            }

            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                Column(
                    modifier = Modifier.padding(top = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Formula Box if exists
                    if (calcType.formula != null) {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Formula: ",
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                    color = PrimaryIndigo
                                )
                                Text(
                                    text = calcType.formula,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontFamily = FontFamily.Monospace,
                                        fontWeight = FontWeight.SemiBold
                                    ),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }

                    // Hint Box
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = AccentAmber.copy(alpha = 0.1f),
                        border = androidx.compose.foundation.BorderStroke(1.dp, AccentAmber.copy(alpha = 0.4f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = hintTitle,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = AccentOrange
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = hint,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontSize = 13.sp,
                                    lineHeight = 18.sp
                                ),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    // Worked Steps Breakdown
                    if (calcType.steps.isNotEmpty()) {
                        Text(
                            text = if (language == com.example.ui.viewmodel.AppLanguage.HINDI) "Step-by-Step Solution Breakdown:" else "Step-by-Step Solution:",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            calcType.steps.forEach { step ->
                                StepRowItem(step = step, language = language)
                            }
                        }
                    }

                    // Practice MCQ Button
                    Button(
                        onClick = onPracticeClick,
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryIndigo),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 6.dp)
                            .testTag("practice_type_${calcType.typeNumber}_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Quiz,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Practice ${calcType.exercises.size} Exercise MCQs",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun StepRowItem(step: CalculationStep, language: com.example.ui.viewmodel.AppLanguage) {
    val explanation = if (language == com.example.ui.viewmodel.AppLanguage.HINDI) step.explanationHindi else step.explanationEnglish
    
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                color = PrimaryIndigo.copy(alpha = 0.15f),
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(
                    text = step.stepLabel,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = PrimaryIndigo
                    ),
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = step.mathExpression,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = explanation,
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
