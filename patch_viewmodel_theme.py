import re

with open("app/src/main/java/com/example/ui/viewmodel/CalculationViewModel.kt", "r") as f:
    content = f.read()

old_uistate = """data class UiState(
    val currentScreen: AppScreen = AppScreen.SPLASH,
    val selectedChapter: Chapter? = null,
    val selectedType: CalculationType? = null,
    val currentExerciseIndex: Int = 0,
    val selectedExerciseOption: String? = null,
    val isExerciseAnswerSubmitted: Boolean = false,
    val showExerciseSolution: Boolean = false,
    val searchQuery: String = "",
    val quizState: QuizState = QuizState(),
    val userStats: UserStats = UserStats(),
    val appLanguage: AppLanguage = AppLanguage.HINDI
)"""

new_uistate = """data class UiState(
    val currentScreen: AppScreen = AppScreen.SPLASH,
    val selectedChapter: Chapter? = null,
    val selectedType: CalculationType? = null,
    val currentExerciseIndex: Int = 0,
    val selectedExerciseOption: String? = null,
    val isExerciseAnswerSubmitted: Boolean = false,
    val showExerciseSolution: Boolean = false,
    val searchQuery: String = "",
    val quizState: QuizState = QuizState(),
    val userStats: UserStats = UserStats(),
    val appLanguage: AppLanguage = AppLanguage.HINDI,
    val isDarkMode: Boolean = true
)"""
content = content.replace(old_uistate, new_uistate)

old_toggle = """    fun toggleLanguage() {
        _uiState.update { 
            it.copy(appLanguage = if (it.appLanguage == AppLanguage.HINDI) AppLanguage.ENGLISH else AppLanguage.HINDI)
        }
    }"""
new_toggle = """    fun toggleLanguage() {
        _uiState.update { 
            it.copy(appLanguage = if (it.appLanguage == AppLanguage.HINDI) AppLanguage.ENGLISH else AppLanguage.HINDI)
        }
    }
    
    fun toggleTheme() {
        _uiState.update { it.copy(isDarkMode = !it.isDarkMode) }
    }"""
content = content.replace(old_toggle, new_toggle)

with open("app/src/main/java/com/example/ui/viewmodel/CalculationViewModel.kt", "w") as f:
    f.write(content)
