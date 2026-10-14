import re
import os

files = [
    "app/src/main/java/com/example/ui/screens/CheatSheetScreen.kt",
    "app/src/main/java/com/example/ui/screens/SolversScreen.kt",
    "app/src/main/java/com/example/ui/screens/PracticeQuestionScreen.kt",
    "app/src/main/java/com/example/ui/screens/AlgebraGraphScreen.kt",
    "app/src/main/java/com/example/ui/screens/StatsScreen.kt"
]

for file in files:
    if os.path.exists(file):
        with open(file, "r") as f:
            content = f.read()
        
        # In TopBar, inject onLanguageToggle and currentLanguage
        content = content.replace(
            "onThemeToggle = { viewModel.toggleTheme() }",
            "onThemeToggle = { viewModel.toggleTheme() },\n                onLanguageToggle = { viewModel.toggleLanguage() },\n                currentLanguage = uiState.appLanguage"
        )
        
        with open(file, "w") as f:
            f.write(content)
