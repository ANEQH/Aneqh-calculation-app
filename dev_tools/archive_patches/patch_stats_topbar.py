import re

with open("app/src/main/java/com/example/ui/screens/StatsScreen.kt", "r") as f:
    content = f.read()

old_call = """            AppTopBar(
                title = "My Progress",
                subtitle = "Stats & Analytics",
                streak = uiState.userStats.currentStreak,
                showBackButton = true,
                onBackClick = { viewModel.navigateTo(AppScreen.HOME) },
                onStatsClick = { }
            )"""

new_call = """            AppTopBar(
                title = "My Progress",
                subtitle = "Stats & Analytics",
                streak = uiState.userStats.currentStreak,
                showBackButton = true,
                onBackClick = { viewModel.navigateTo(AppScreen.HOME) },
                onStatsClick = { },
                onThemeToggle = { viewModel.toggleTheme() }
            )"""
content = content.replace(old_call, new_call)

with open("app/src/main/java/com/example/ui/screens/StatsScreen.kt", "w") as f:
    f.write(content)
