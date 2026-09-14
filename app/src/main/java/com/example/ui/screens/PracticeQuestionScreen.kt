package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.AppTopBar
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.CalculationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PracticeQuestionScreen(
    viewModel: CalculationViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val chapter = uiState.selectedChapter ?: return
    val type = uiState.selectedType ?: return
    val exercises = type.exercises
    val currentProblem = exercises.getOrNull(uiState.currentExerciseIndex) ?: return

    val totalQuestions = exercises.size
    val currentIndex = uiState.currentExerciseIndex

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Type ${type.typeNumber}: ${type.titleHindi}",
                subtitle = "Question ${currentIndex + 1} of $totalQuestions",
                streak = uiState.userStats.currentStreak,
                showBackButton = true,
                onBackClick = { viewModel.navigateTo(AppScreen.CHAPTER_DETAIL) },
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
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        onClick = { viewModel.previousExerciseProblem() },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.testTag("practice_prev_btn")
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Previous")
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Prev")
                    }

                    if (!uiState.isExerciseAnswerSubmitted) {
                        Button(
                            onClick = { viewModel.submitExerciseAnswer() },
                            enabled = uiState.selectedExerciseOption != null,
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryIndigo),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.testTag("practice_check_answer_btn")
                        ) {
                            Text("Check Answer", fontWeight = FontWeight.Bold)
                        }
                    } else {
                        Button(
                            onClick = { viewModel.nextExerciseProblem() },
                            colors = ButtonDefaults.buttonColors(containerColor = AccentEmerald),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.testTag("practice_next_btn")
                        ) {
                            Text("Next Question", fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(Icons.Default.ArrowForward, contentDescription = "Next")
                        }
                    }
                }
            }
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Linear Progress Indicator
            LinearProgressIndicator(
                progress = { (currentIndex + 1).toFloat() / totalQuestions.toFloat() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = PrimaryIndigo,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )

            // Problem Question Card
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 3.dp,
                border = androidx.compose.foundation.BorderStroke(1.dp, PrimaryIndigo.copy(alpha = 0.3f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("practice_question_card")
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            color = PrimaryIndigo.copy(alpha = 0.12f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "Problem #${currentProblem.id}",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = PrimaryIndigo
                                ),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }

                        IconButton(
                            onClick = { viewModel.toggleExerciseSolution() },
                            modifier = Modifier.size(32.dp).testTag("practice_hint_toggle_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.HelpOutline,
                                contentDescription = "Hint",
                                tint = AccentAmber
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = currentProblem.question,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 26.sp
                        ),
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                }
            }

            // Options List
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                listOf(
                    "a" to currentProblem.optionA,
                    "b" to currentProblem.optionB,
                    "c" to currentProblem.optionC,
                    "d" to currentProblem.optionD
                ).forEach { (letter, text) ->
                    val isSelected = uiState.selectedExerciseOption == letter
                    val isSubmitted = uiState.isExerciseAnswerSubmitted
                    val isCorrectChoice = currentProblem.correctOption.equals(letter, ignoreCase = true)

                    val backgroundColor = when {
                        !isSubmitted && isSelected -> PrimaryIndigo.copy(alpha = 0.15f)
                        isSubmitted && isCorrectChoice -> AccentEmerald.copy(alpha = 0.2f)
                        isSubmitted && isSelected && !isCorrectChoice -> AccentRose.copy(alpha = 0.2f)
                        else -> MaterialTheme.colorScheme.surface
                    }

                    val borderColor = when {
                        !isSubmitted && isSelected -> PrimaryIndigo
                        isSubmitted && isCorrectChoice -> AccentEmerald
                        isSubmitted && isSelected && !isCorrectChoice -> AccentRose
                        else -> MaterialTheme.colorScheme.outlineVariant
                    }

                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = backgroundColor,
                        border = androidx.compose.foundation.BorderStroke(1.5.dp, borderColor),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(enabled = !isSubmitted) {
                                viewModel.selectExerciseOption(letter)
                            }
                            .testTag("practice_option_${letter}")
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (isSelected || (isSubmitted && isCorrectChoice)) borderColor else MaterialTheme.colorScheme.surfaceVariant
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "($letter)",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSelected || (isSubmitted && isCorrectChoice)) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Text(
                                text = text,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 16.sp
                                ),
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.weight(1f)
                            )
                            if (isSubmitted) {
                                if (isCorrectChoice) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = "Correct",
                                        tint = AccentEmerald,
                                        modifier = Modifier.size(24.dp)
                                    )
                                } else if (isSelected) {
                                    Icon(
                                        imageVector = Icons.Default.Cancel,
                                        contentDescription = "Wrong",
                                        tint = AccentRose,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Solution Step Breakdown Card
            AnimatedVisibility(
                visible = uiState.showExerciseSolution || uiState.isExerciseAnswerSubmitted,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = AccentAmber.copy(alpha = 0.1f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, AccentAmber.copy(alpha = 0.4f)),
                    modifier = Modifier.fillMaxWidth().testTag("practice_solution_card")
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = AccentEmerald,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Inspector's Mental Solution (Correct: (${currentProblem.correctOption}))",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = AccentOrange
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = currentProblem.solutionSteps,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontFamily = FontFamily.Monospace,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            ),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "💡 Tip: ${type.hintHindi}",
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
