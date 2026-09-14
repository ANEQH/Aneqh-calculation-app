package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
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
import com.example.ui.viewmodel.QuizState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpeedQuizScreen(
    viewModel: CalculationViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val quiz = uiState.quizState

    if (quiz.isQuizFinished) {
        QuizResultScreen(
            quiz = quiz,
            onRestart = { viewModel.startSpeedQuiz(questionCount = 10, timePerQuestion = 20) },
            onHomeClick = { viewModel.navigateTo(AppScreen.HOME) }
        )
    } else {
        ActiveQuizScreen(
            quiz = quiz,
            onOptionSelect = { viewModel.selectQuizOption(it) },
            onNext = { viewModel.nextQuizQuestion() },
            onExit = { viewModel.navigateTo(AppScreen.HOME) }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ActiveQuizScreen(
    quiz: QuizState,
    onOptionSelect: (String) -> Unit,
    onNext: () -> Unit,
    onExit: () -> Unit
) {
    val question = quiz.questions.getOrNull(quiz.currentIndex) ?: return
    val total = quiz.questions.size
    val current = quiz.currentIndex + 1

    val timerProgress = quiz.timeLeftSeconds.toFloat() / quiz.maxTimePerQuestion.toFloat()
    val timerColor = when {
        quiz.timeLeftSeconds <= 5 -> AccentRose
        quiz.timeLeftSeconds <= 10 -> AccentAmber
        else -> PrimaryIndigo
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Speed Chalisa Drill",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "Q $current of $total • ${question.chapterTitle}",
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onExit, modifier = Modifier.testTag("quiz_exit_btn")) {
                        Icon(Icons.Default.Close, contentDescription = "Exit")
                    }
                },
                actions = {
                    // Live Score
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = PrimaryIndigo.copy(alpha = 0.15f),
                        modifier = Modifier.padding(end = 12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Icon(Icons.Default.Stars, contentDescription = null, tint = AccentAmber, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${quiz.score} pts",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                color = PrimaryIndigo
                            )
                        }
                    }
                }
            )
        },
        bottomBar = {
            if (quiz.isAnswerSubmitted) {
                Surface(
                    color = MaterialTheme.colorScheme.surface,
                    tonalElevation = 6.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = onNext,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (quiz.currentIndex + 1 >= total) AccentAmber else PrimaryIndigo
                        ),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .height(50.dp)
                            .testTag("quiz_next_btn")
                    ) {
                        Text(
                            text = if (quiz.currentIndex + 1 >= total) "View Drill Results" else "Next Question →",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = if (quiz.currentIndex + 1 >= total) Color(0xFF1E1B4B) else Color.White
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Timer + Streak Header Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Circular Timer
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.size(54.dp).testTag("quiz_timer_box")
                ) {
                    CircularProgressIndicator(
                        progress = { timerProgress },
                        modifier = Modifier.fillMaxSize(),
                        color = timerColor,
                        strokeWidth = 5.dp,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                    Text(
                        text = "${quiz.timeLeftSeconds}s",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = timerColor
                        )
                    )
                }

                // Multiplier / Streak
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = if (quiz.streak > 1) AccentOrange.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant,
                    border = if (quiz.streak > 1) androidx.compose.foundation.BorderStroke(1.dp, AccentOrange) else null
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocalFireDepartment,
                            contentDescription = null,
                            tint = if (quiz.streak > 1) AccentOrange else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${quiz.streak} Combo",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = if (quiz.streak > 1) AccentOrange else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }
            }

            // Question Box
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 4.dp,
                border = androidx.compose.foundation.BorderStroke(1.dp, PrimaryIndigo.copy(alpha = 0.25f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("quiz_question_box")
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = question.questionText,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 24.sp
                        ),
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Options
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                listOf(
                    "a" to question.optionA,
                    "b" to question.optionB,
                    "c" to question.optionC,
                    "d" to question.optionD
                ).forEach { (letter, optText) ->
                    val isSelected = quiz.selectedOption == letter
                    val isSubmitted = quiz.isAnswerSubmitted
                    val isCorrectChoice = question.correctOption.equals(letter, ignoreCase = true)

                    val bg = when {
                        !isSubmitted && isSelected -> PrimaryIndigo.copy(alpha = 0.15f)
                        isSubmitted && isCorrectChoice -> AccentEmerald.copy(alpha = 0.2f)
                        isSubmitted && isSelected && !isCorrectChoice -> AccentRose.copy(alpha = 0.2f)
                        else -> MaterialTheme.colorScheme.surface
                    }

                    val border = when {
                        !isSubmitted && isSelected -> PrimaryIndigo
                        isSubmitted && isCorrectChoice -> AccentEmerald
                        isSubmitted && isSelected && !isCorrectChoice -> AccentRose
                        else -> MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f)
                    }

                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = bg,
                        border = androidx.compose.foundation.BorderStroke(1.5.dp, border),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(enabled = !isSubmitted) {
                                onOptionSelect(letter)
                            }
                            .testTag("quiz_option_${letter}")
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(30.dp)
                                    .clip(CircleShape)
                                    .background(if (isSelected || (isSubmitted && isCorrectChoice)) border else MaterialTheme.colorScheme.surfaceVariant),
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
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = optText,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.weight(1f)
                            )
                            if (isSubmitted) {
                                if (isCorrectChoice) {
                                    Icon(Icons.Default.CheckCircle, contentDescription = "Correct", tint = AccentEmerald)
                                } else if (isSelected) {
                                    Icon(Icons.Default.Cancel, contentDescription = "Wrong", tint = AccentRose)
                                }
                            }
                        }
                    }
                }
            }

            // Explanation Reveal (Notebook Style)
            if (quiz.isAnswerSubmitted) {
                val header = if (quiz.isCorrect) "🎉 Correct! (+${10 + quiz.streak * 2} pts)" else "❌ Incorrect (Correct: ${question.correctOption})"
                com.example.ui.components.NotebookPage(
                    title = header,
                    text = question.explanation,
                    modifier = Modifier.fillMaxWidth().testTag("quiz_explanation_box")
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun QuizResultScreen(
    quiz: QuizState,
    onRestart: () -> Unit,
    onHomeClick: () -> Unit
) {
    val total = quiz.questions.size
    val correct = quiz.userAnswers.count { it.isCorrect }
    val accuracy = if (total > 0) ((correct.toDouble() / total) * 100).toInt() else 0

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Drill Complete", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onHomeClick) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 40.dp)
        ) {
            // Score Summary Card
            item {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = PrimaryIndigo.copy(alpha = 0.1f),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, PrimaryIndigo.copy(alpha = 0.4f)),
                    modifier = Modifier.fillMaxWidth().testTag("quiz_result_card")
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = if (accuracy >= 80) "🏆 Outstanding Speed!" else if (accuracy >= 50) "👍 Good Practice!" else "💪 Keep Practicing!",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold),
                            color = PrimaryIndigo
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "$correct / $total",
                                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                                    color = AccentEmerald
                                )
                                Text("Correct", style = MaterialTheme.typography.bodySmall)
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "$accuracy%",
                                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                                    color = PrimaryIndigo
                                )
                                Text("Accuracy", style = MaterialTheme.typography.bodySmall)
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "${quiz.score}",
                                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                                    color = AccentAmber
                                )
                                Text("Score", style = MaterialTheme.typography.bodySmall)
                            }
                        }

                        Spacer(modifier = Modifier.height(18.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Button(
                                onClick = onRestart,
                                colors = ButtonDefaults.buttonColors(containerColor = PrimaryIndigo),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.weight(1f).testTag("quiz_retry_btn")
                            ) {
                                Icon(Icons.Default.Refresh, contentDescription = null)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Try Again")
                            }

                            OutlinedButton(
                                onClick = onHomeClick,
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.weight(1f).testTag("quiz_home_btn")
                            ) {
                                Text("Home")
                            }
                        }
                    }
                }
            }

            // Smart AI Performance Diagnostics & Prescription Card
            item {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.surface,
                    border = androidx.compose.foundation.BorderStroke(1.2.dp, AccentAmber.copy(alpha = 0.4f)),
                    modifier = Modifier.fillMaxWidth().testTag("smart_diagnostics_card")
                ) {
                    Column(
                        modifier = Modifier
                            .background(
                                Brush.verticalGradient(
                                    listOf(AccentAmber.copy(alpha = 0.12f), Color.Transparent)
                                )
                            )
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = AccentOrange, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Smart AI Speed Diagnosis",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = AccentOrange
                            )
                        }

                        val diagnosis = when {
                            accuracy >= 90 -> "🔥 Superhuman Speed! Your Tier-1 Mental Accuracy is in the top 1% percentiles. Keep this momentum consistent."
                            accuracy >= 70 -> "⚡ High Speed Cadet: Solid grip on mental calculations. Eliminate careless errors in options matching."
                            accuracy >= 50 -> "📊 Developing Pace: You are solving correctly, but mental calculation takes 5-10s longer than ideal. Practice Vedic tricks."
                            else -> "🎯 High Yield Focus Needed: Practice the Vedic Math 16 Sutras & reciprocal percentage values to double your solving speed without paper."
                        }

                        Text(
                            text = diagnosis,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = PrimaryIndigo.copy(alpha = 0.1f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Lightbulb, contentDescription = null, tint = PrimaryIndigo, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Smart Tip: Always apply Digital Root check (mod 9) on big multiplication & square questions.",
                                    fontSize = 12.sp,
                                    color = PrimaryIndigo,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }

            // Question by Question Review Header
            item {
                Text(
                    text = "Question Review & Solutions",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            // Question Review Items
            itemsIndexed(quiz.userAnswers) { index, item ->
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = MaterialTheme.colorScheme.surface,
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (item.isCorrect) AccentEmerald.copy(alpha = 0.4f) else AccentRose.copy(alpha = 0.4f)
                    ),
                    tonalElevation = 2.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Q${index + 1}: ${item.question.questionText}",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Icon(
                                imageVector = if (item.isCorrect) Icons.Default.CheckCircle else Icons.Default.Cancel,
                                contentDescription = null,
                                tint = if (item.isCorrect) AccentEmerald else AccentRose,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Correct: (${item.question.correctOption}) • Your Choice: ${if (item.chosenOption != null) "(${item.chosenOption})" else "Timed Out"}",
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                            color = if (item.isCorrect) AccentEmerald else AccentRose
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Surface(
                            color = PrimaryIndigo.copy(alpha = 0.05f),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.School, contentDescription = null, tint = AccentOrange, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "Concept & Trick",
                                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                        color = AccentOrange
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = item.question.explanation,
                                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 13.sp, fontWeight = FontWeight.Medium),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
