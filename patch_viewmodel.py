import re

with open("app/src/main/java/com/example/ui/viewmodel/CalculationViewModel.kt", "r") as f:
    content = f.read()

new_method = """    fun submitDrillAnswer(isCorrect: Boolean) {
        val currentStats = _uiState.value.userStats
        val newStreak = if (isCorrect) currentStats.currentStreak + 1 else 0
        val newBestStreak = maxOf(newStreak, currentStats.bestStreak)
        val newSolved = currentStats.totalSolved + 1
        val newCorrect = if (isCorrect) currentStats.totalCorrect + 1 else currentStats.totalCorrect
        
        saveStats(
            currentStats.copy(
                totalSolved = newSolved,
                totalCorrect = newCorrect,
                currentStreak = newStreak,
                bestStreak = newBestStreak
            )
        )
    }

    fun resetStats() {"""

content = content.replace("    fun resetStats() {", new_method)

with open("app/src/main/java/com/example/ui/viewmodel/CalculationViewModel.kt", "w") as f:
    f.write(content)
