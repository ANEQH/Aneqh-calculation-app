import re

with open("app/src/main/java/com/example/ui/screens/HomeScreen.kt", "r") as f:
    content = f.read()

# Add AppDictionary import
content = content.replace("import com.example.ui.viewmodel.CalculationViewModel", "import com.example.ui.viewmodel.CalculationViewModel\nimport com.example.ui.viewmodel.AppDictionary.tr")

old_topbar = """            AppTopBar(
                title = "Anek Calculation",
                subtitle = if (uiState.appLanguage == com.example.ui.viewmodel.AppLanguage.HINDI) "SSC CGL Math Speed Maker" else "SSC CGL Math Speed Maker",
                streak = uiState.userStats.currentStreak,
                showBackButton = false,
                currentLanguage = uiState.appLanguage,
                onLanguageToggle = { viewModel.toggleLanguage() },
                onStatsClick = { viewModel.navigateTo(AppScreen.STATS) },
                onThemeToggle = { viewModel.toggleTheme() }
            )"""

new_topbar = """            AppTopBar(
                title = tr("app_title", uiState.appLanguage),
                subtitle = tr("app_subtitle", uiState.appLanguage),
                streak = uiState.userStats.currentStreak,
                showBackButton = false,
                currentLanguage = uiState.appLanguage,
                onLanguageToggle = { viewModel.toggleLanguage() },
                onStatsClick = { viewModel.navigateTo(AppScreen.STATS) },
                onThemeToggle = { viewModel.toggleTheme() }
            )"""
content = content.replace(old_topbar, new_topbar)

old_stats = """                    StatCard(
                        label = "Solved",
                        value = "${stats.totalSolved}",
                        color = PrimaryIndigo,
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        label = "Streak",
                        value = "${stats.currentStreak} 🔥",
                        color = AccentAmber,
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        label = "Accuracy",
                        value = "$accuracy%",
                        color = AccentEmerald,
                        modifier = Modifier.weight(1f)
                    )"""

new_stats = """                    StatCard(
                        label = tr("solved", uiState.appLanguage),
                        value = "${stats.totalSolved}",
                        color = PrimaryIndigo,
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        label = tr("streak", uiState.appLanguage),
                        value = "${stats.currentStreak} 🔥",
                        color = AccentAmber,
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        label = tr("accuracy", uiState.appLanguage),
                        value = "$accuracy%",
                        color = AccentEmerald,
                        modifier = Modifier.weight(1f)
                    )"""
content = content.replace(old_stats, new_stats)

old_tools = """                    Text(
                        text = "Quick Tools",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onBackground
                    )"""

new_tools = """                    Text(
                        text = tr("quick_tools", uiState.appLanguage),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onBackground
                    )"""
content = content.replace(old_tools, new_tools)

old_shortcuts = """                        item {
                            QuickActionChip(
                                title = "📈 CI & SI Rate Sheet",
                                subtitle = "2 & 3 Yrs",
                                color = AccentPurple,
                                onClick = { viewModel.navigateTo(AppScreen.CHEAT_SHEETS) }
                            )
                        }
                        item {
                            QuickActionChip(
                                title = "🧊 3D Visual Math",
                                subtitle = "Algebra Graphs",
                                color = AccentOrange,
                                onClick = { viewModel.navigateTo(AppScreen.ALGEBRA_GRAPH) }
                            )
                        }"""

new_shortcuts = """                        item {
                            QuickActionChip(
                                title = tr("ci_si_rates", uiState.appLanguage),
                                subtitle = "2 & 3 Yrs",
                                color = AccentPurple,
                                onClick = { viewModel.navigateTo(AppScreen.CHEAT_SHEETS) }
                            )
                        }
                        item {
                            QuickActionChip(
                                title = tr("visual_math", uiState.appLanguage),
                                subtitle = "Algebra Graphs",
                                color = AccentOrange,
                                onClick = { viewModel.navigateTo(AppScreen.ALGEBRA_GRAPH) }
                            )
                        }
                        item {
                            QuickActionChip(
                                title = tr("pyq_tests", uiState.appLanguage),
                                subtitle = tr("pyq_tests_sub", uiState.appLanguage),
                                color = AccentEmerald,
                                onClick = { viewModel.navigateTo(AppScreen.PYQ_TESTS) }
                            )
                        }
                        item {
                            QuickActionChip(
                                title = tr("calculator", uiState.appLanguage),
                                subtitle = tr("calculator_sub", uiState.appLanguage),
                                color = AccentCyan,
                                onClick = { viewModel.navigateTo(AppScreen.CALCULATOR) }
                            )
                        }"""
content = content.replace(old_shortcuts, new_shortcuts)

old_chapters = """            item {
                Text(
                    text = "Inspector Chalisa Chapters",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }"""

new_chapters = """            item {
                Text(
                    text = tr("inspector_chapters", uiState.appLanguage),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }"""
content = content.replace(old_chapters, new_chapters)

with open("app/src/main/java/com/example/ui/screens/HomeScreen.kt", "w") as f:
    f.write(content)
