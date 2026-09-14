import re

with open("app/src/main/java/com/example/ui/screens/CheatSheetScreen.kt", "r") as f:
    content = f.read()

old_call = """            AppTopBar(
                title = "Speed Math Tables",
                subtitle = "Formulas, Percentages & Powers",
                streak = uiState.userStats.currentStreak,
                showBackButton = true,
                onBackClick = { viewModel.navigateTo(AppScreen.HOME) },
                onStatsClick = { viewModel.navigateTo(AppScreen.STATS) }
            )"""

new_call = """            AppTopBar(
                title = "Speed Math Tables",
                subtitle = "Formulas, Percentages & Powers",
                streak = uiState.userStats.currentStreak,
                showBackButton = true,
                onBackClick = { viewModel.navigateTo(AppScreen.HOME) },
                onStatsClick = { viewModel.navigateTo(AppScreen.STATS) },
                onThemeToggle = { viewModel.toggleTheme() }
            )"""
content = content.replace(old_call, new_call)

with open("app/src/main/java/com/example/ui/screens/CheatSheetScreen.kt", "w") as f:
    f.write(content)
