package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.AppTopBar
import com.example.ui.components.StatCard
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.CalculationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsScreen(
    viewModel: CalculationViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val stats = uiState.userStats
    val accuracy = if (stats.totalSolved > 0) {
        ((stats.totalCorrect.toDouble() / stats.totalSolved) * 100).toInt()
    } else 0

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Performance & Mastery",
                subtitle = "Calculation Speed Tracker",
                streak = stats.currentStreak,
                showBackButton = true,
                onBackClick = { viewModel.navigateTo(AppScreen.HOME) },
                onStatsClick = {}
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
            contentPadding = PaddingValues(top = 8.dp, bottom = 100.dp)
        ) {
            // Rank Badge Card
            item {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = PrimaryIndigo.copy(alpha = 0.12f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, PrimaryIndigo.copy(alpha = 0.4f)),
                    modifier = Modifier.fillMaxWidth().testTag("rank_card")
                ) {
                    Row(
                        modifier = Modifier.padding(18.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(PrimaryIndigo),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.MilitaryTech,
                                contentDescription = null,
                                tint = AccentAmber,
                                modifier = Modifier.size(32.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column {
                            val rankTitle = when {
                                stats.totalSolved >= 100 -> "Inspector Master (10x Speed)"
                                stats.totalSolved >= 50 -> "Senior Speed Math Cadet"
                                stats.totalSolved >= 20 -> "Mental Calculator"
                                else -> "Speed Apprentice"
                            }
                            Text(
                                text = rankTitle,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
                                color = PrimaryIndigo
                            )
                            Text(
                                text = "Aneqh Speed Calculation League",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            // Stats Matrix
            item {
                Text(
                    text = "Overall Statistics",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        StatCard(
                            label = "Total Questions Solved",
                            value = "${stats.totalSolved}",
                            icon = Icons.Default.Functions,
                            color = PrimaryIndigo,
                            modifier = Modifier.weight(1f)
                        )
                        StatCard(
                            label = "Correct Answers",
                            value = "${stats.totalCorrect}",
                            icon = Icons.Default.CheckCircle,
                            color = AccentEmerald,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        StatCard(
                            label = "Accuracy Rate",
                            value = "$accuracy%",
                            icon = Icons.Default.QueryStats,
                            color = AccentCyan,
                            modifier = Modifier.weight(1f)
                        )
                        StatCard(
                            label = "Best Streak",
                            value = "${stats.bestStreak}",
                            icon = Icons.Default.LocalFireDepartment,
                            color = AccentOrange,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        StatCard(
                            label = "Drills Finished",
                            value = "${stats.speedQuizzesCompleted}",
                            icon = Icons.Default.Timer,
                            color = AccentPurple,
                            modifier = Modifier.weight(1f)
                        )
                        StatCard(
                            label = "High Score",
                            value = "${stats.bestScore} pts",
                            icon = Icons.Default.EmojiEvents,
                            color = AccentAmber,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // Quick Reset Button
            item {
                OutlinedButton(
                    onClick = { viewModel.resetStats() },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = AccentRose),
                    border = androidx.compose.foundation.BorderStroke(1.dp, AccentRose.copy(alpha = 0.5f)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().testTag("reset_stats_btn")
                ) {
                    Icon(Icons.Default.DeleteSweep, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Reset Progress & Stats")
                }
            }
        }
    }
}
