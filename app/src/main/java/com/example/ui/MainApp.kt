package com.example.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.screens.*
import com.example.ui.theme.PrimaryIndigo
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.CalculationViewModel

@Composable
fun MainApp(
    viewModel: CalculationViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    val showBottomBar = uiState.currentScreen in listOf(
        AppScreen.HOME,
        AppScreen.SOLVERS,
        AppScreen.CHEAT_SHEETS,
        AppScreen.STATS
    )

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 8.dp,
                    windowInsets = WindowInsets.navigationBars,
                    modifier = Modifier.testTag("main_navigation_bar")
                ) {
                    NavigationBarItem(
                        selected = uiState.currentScreen == AppScreen.HOME,
                        onClick = { viewModel.navigateTo(AppScreen.HOME) },
                        icon = {
                            Icon(
                                imageVector = if (uiState.currentScreen == AppScreen.HOME) Icons.Filled.MenuBook else Icons.Outlined.MenuBook,
                                contentDescription = "Chapters"
                            )
                        },
                        label = { Text("Chapters", fontSize = 11.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = PrimaryIndigo,
                            indicatorColor = PrimaryIndigo.copy(alpha = 0.15f)
                        ),
                        modifier = Modifier.testTag("nav_item_chapters")
                    )

                    NavigationBarItem(
                        selected = false,
                        onClick = { viewModel.startSpeedQuiz(questionCount = 10, timePerQuestion = 20) },
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.Bolt,
                                contentDescription = "Speed Drill"
                            )
                        },
                        label = { Text("Speed Drill", fontSize = 11.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = PrimaryIndigo,
                            indicatorColor = PrimaryIndigo.copy(alpha = 0.15f)
                        ),
                        modifier = Modifier.testTag("nav_item_drill")
                    )

                    NavigationBarItem(
                        selected = uiState.currentScreen == AppScreen.SOLVERS,
                        onClick = { viewModel.navigateTo(AppScreen.SOLVERS) },
                        icon = {
                            Icon(
                                imageVector = if (uiState.currentScreen == AppScreen.SOLVERS) Icons.Filled.Calculate else Icons.Outlined.Calculate,
                                contentDescription = "Solvers"
                            )
                        },
                        label = { Text("10x Solvers", fontSize = 11.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = PrimaryIndigo,
                            indicatorColor = PrimaryIndigo.copy(alpha = 0.15f)
                        ),
                        modifier = Modifier.testTag("nav_item_solvers")
                    )

                    NavigationBarItem(
                        selected = uiState.currentScreen == AppScreen.CHEAT_SHEETS,
                        onClick = { viewModel.navigateTo(AppScreen.CHEAT_SHEETS) },
                        icon = {
                            Icon(
                                imageVector = if (uiState.currentScreen == AppScreen.CHEAT_SHEETS) Icons.Filled.TableChart else Icons.Outlined.TableChart,
                                contentDescription = "Tables"
                            )
                        },
                        label = { Text("Tables", fontSize = 11.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = PrimaryIndigo,
                            indicatorColor = PrimaryIndigo.copy(alpha = 0.15f)
                        ),
                        modifier = Modifier.testTag("nav_item_tables")
                    )

                    NavigationBarItem(
                        selected = uiState.currentScreen == AppScreen.STATS,
                        onClick = { viewModel.navigateTo(AppScreen.STATS) },
                        icon = {
                            Icon(
                                imageVector = if (uiState.currentScreen == AppScreen.STATS) Icons.Filled.Leaderboard else Icons.Outlined.Leaderboard,
                                contentDescription = "Stats"
                            )
                        },
                        label = { Text("Stats", fontSize = 11.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = PrimaryIndigo,
                            indicatorColor = PrimaryIndigo.copy(alpha = 0.15f)
                        ),
                        modifier = Modifier.testTag("nav_item_stats")
                    )
                }
            }
        },
        modifier = modifier
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (uiState.currentScreen) {
                AppScreen.HOME -> HomeScreen(viewModel = viewModel)
                AppScreen.CHAPTER_DETAIL -> ChapterScreen(viewModel = viewModel)
                AppScreen.PRACTICE_QUESTION -> PracticeQuestionScreen(viewModel = viewModel)
                AppScreen.SPEED_QUIZ -> SpeedQuizScreen(viewModel = viewModel)
                AppScreen.SOLVERS -> SolversScreen(viewModel = viewModel)
                AppScreen.CHEAT_SHEETS -> CheatSheetScreen(viewModel = viewModel)
                AppScreen.STATS -> StatsScreen(viewModel = viewModel)
            }
        }
    }
}
