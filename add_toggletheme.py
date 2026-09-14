import re

with open("app/src/main/java/com/example/ui/viewmodel/CalculationViewModel.kt", "r") as f:
    content = f.read()

new_toggle = """    fun toggleTheme() {
        _uiState.update { it.copy(isDarkMode = !it.isDarkMode) }
    }
"""

if "fun toggleLanguage()" in content:
    content = content.replace("fun toggleLanguage() {", new_toggle + "\n    fun toggleLanguage() {")
    with open("app/src/main/java/com/example/ui/viewmodel/CalculationViewModel.kt", "w") as f:
        f.write(content)
