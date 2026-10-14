import re

with open("app/src/main/java/com/example/ui/screens/HomeScreen.kt", "r") as f:
    content = f.read()

old_call = """            AppTopBar(
                title = "Anek Calculation",
                subtitle = if (uiState.appLanguage == com.example.ui.viewmodel.AppLanguage.HINDI) "SSC CGL Math Speed Maker" else "SSC CGL Math Speed Maker",
                streak = uiState.userStats.currentStreak,
                showBackButton = false,
                currentLanguage = uiState.appLanguage,
                onLanguageToggle = { viewModel.toggleLanguage() },
                onStatsClick = { viewModel.navigateTo(AppScreen.STATS) }
            )"""

new_call = """            AppTopBar(
                title = "Anek Calculation",
                subtitle = if (uiState.appLanguage == com.example.ui.viewmodel.AppLanguage.HINDI) "SSC CGL Math Speed Maker" else "SSC CGL Math Speed Maker",
                streak = uiState.userStats.currentStreak,
                showBackButton = false,
                currentLanguage = uiState.appLanguage,
                onLanguageToggle = { viewModel.toggleLanguage() },
                onStatsClick = { viewModel.navigateTo(AppScreen.STATS) },
                onThemeToggle = { viewModel.toggleTheme() }
            )"""
content = content.replace(old_call, new_call)

with open("app/src/main/java/com/example/ui/screens/HomeScreen.kt", "w") as f:
    f.write(content)
