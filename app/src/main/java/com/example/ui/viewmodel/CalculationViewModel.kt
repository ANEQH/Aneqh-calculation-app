package com.example.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.*
import com.example.data.repository.CalculationRepository
import com.example.data.repository.CalculationSolvers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class AppScreen {
    HOME,
    CHAPTER_DETAIL,
    PRACTICE_QUESTION,
    SPEED_QUIZ,
    SOLVERS,
    CHEAT_SHEETS,
    STATS
}

data class QuizState(
    val isActive: Boolean = false,
    val questions: List<QuizQuestion> = emptyList(),
    val currentIndex: Int = 0,
    val selectedOption: String? = null,
    val isAnswerSubmitted: Boolean = false,
    val isCorrect: Boolean = false,
    val score: Int = 0,
    val streak: Int = 0,
    val bestStreakInQuiz: Int = 0,
    val timeLeftSeconds: Int = 20,
    val maxTimePerQuestion: Int = 20,
    val isQuizFinished: Boolean = false,
    val userAnswers: List<UserQuizAnswer> = emptyList()
)

data class UserQuizAnswer(
    val question: QuizQuestion,
    val chosenOption: String?,
    val isCorrect: Boolean,
    val timeSpentSeconds: Int
)

data class UiState(
    val currentScreen: AppScreen = AppScreen.HOME,
    val selectedChapter: Chapter? = null,
    val selectedType: CalculationType? = null,
    val currentExerciseIndex: Int = 0,
    val selectedExerciseOption: String? = null,
    val isExerciseAnswerSubmitted: Boolean = false,
    val showExerciseSolution: Boolean = false,
    val searchQuery: String = "",
    val quizState: QuizState = QuizState(),
    val userStats: UserStats = UserStats()
)

class CalculationViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = application.getSharedPreferences("aneqh_calc_prefs", Context.MODE_PRIVATE)

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private var timerJob: Job? = null

    init {
        loadStats()
    }

    private fun loadStats() {
        val totalSolved = prefs.getInt("total_solved", 0)
        val totalCorrect = prefs.getInt("total_correct", 0)
        val bestStreak = prefs.getInt("best_streak", 0)
        val currentStreak = prefs.getInt("current_streak", 0)
        val quizzesCompleted = prefs.getInt("quizzes_completed", 0)
        val bestScore = prefs.getInt("best_score", 0)
        val lastScore = prefs.getInt("last_score", 0)

        _uiState.update {
            it.copy(
                userStats = UserStats(
                    totalSolved = totalSolved,
                    totalCorrect = totalCorrect,
                    currentStreak = currentStreak,
                    bestStreak = bestStreak,
                    speedQuizzesCompleted = quizzesCompleted,
                    bestScore = bestScore,
                    lastScore = lastScore
                )
            )
        }
    }

    private fun saveStats(newStats: UserStats) {
        prefs.edit().apply {
            putInt("total_solved", newStats.totalSolved)
            putInt("total_correct", newStats.totalCorrect)
            putInt("best_streak", newStats.bestStreak)
            putInt("current_streak", newStats.currentStreak)
            putInt("quizzes_completed", newStats.speedQuizzesCompleted)
            putInt("best_score", newStats.bestScore)
            putInt("last_score", newStats.lastScore)
            apply()
        }
        _uiState.update { it.copy(userStats = newStats) }
    }

    fun navigateTo(screen: AppScreen) {
        _uiState.update { it.copy(currentScreen = screen) }
    }

    fun selectChapter(chapter: Chapter) {
        _uiState.update {
            it.copy(
                selectedChapter = chapter,
                selectedType = chapter.types.firstOrNull(),
                currentScreen = AppScreen.CHAPTER_DETAIL
            )
        }
    }

    fun selectType(type: CalculationType) {
        _uiState.update {
            it.copy(
                selectedType = type,
                currentExerciseIndex = 0,
                selectedExerciseOption = null,
                isExerciseAnswerSubmitted = false,
                showExerciseSolution = false
            )
        }
    }

    fun startPracticeForType(type: CalculationType) {
        _uiState.update {
            it.copy(
                selectedType = type,
                currentExerciseIndex = 0,
                selectedExerciseOption = null,
                isExerciseAnswerSubmitted = false,
                showExerciseSolution = false,
                currentScreen = AppScreen.PRACTICE_QUESTION
            )
        }
    }

    fun selectExerciseOption(optionLetter: String) {
        if (_uiState.value.isExerciseAnswerSubmitted) return
        _uiState.update { it.copy(selectedExerciseOption = optionLetter) }
    }

    fun submitExerciseAnswer() {
        val state = _uiState.value
        val type = state.selectedType ?: return
        val currentEx = type.exercises.getOrNull(state.currentExerciseIndex) ?: return
        val chosen = state.selectedExerciseOption ?: return

        val isCorrect = chosen.equals(currentEx.correctOption, ignoreCase = true)
        val currentStats = state.userStats

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

        _uiState.update {
            it.copy(
                isExerciseAnswerSubmitted = true,
                showExerciseSolution = true
            )
        }
    }

    fun nextExerciseProblem() {
        val state = _uiState.value
        val type = state.selectedType ?: return
        val nextIdx = (state.currentExerciseIndex + 1) % type.exercises.size
        _uiState.update {
            it.copy(
                currentExerciseIndex = nextIdx,
                selectedExerciseOption = null,
                isExerciseAnswerSubmitted = false,
                showExerciseSolution = false
            )
        }
    }

    fun previousExerciseProblem() {
        val state = _uiState.value
        val type = state.selectedType ?: return
        val prevIdx = if (state.currentExerciseIndex > 0) state.currentExerciseIndex - 1 else type.exercises.size - 1
        _uiState.update {
            it.copy(
                currentExerciseIndex = prevIdx,
                selectedExerciseOption = null,
                isExerciseAnswerSubmitted = false,
                showExerciseSolution = false
            )
        }
    }

    fun toggleExerciseSolution() {
        _uiState.update { it.copy(showExerciseSolution = !it.showExerciseSolution) }
    }

    fun setSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    // --- Speed Chalisa Quiz Engine ---
    fun startSpeedQuiz(questionCount: Int = 10, chapterId: Int? = null, timePerQuestion: Int = 20) {
        timerJob?.cancel()
        val questions = CalculationRepository.generateSpeedQuizQuestions(questionCount, chapterId)
        _uiState.update {
            it.copy(
                currentScreen = AppScreen.SPEED_QUIZ,
                quizState = QuizState(
                    isActive = true,
                    questions = questions,
                    currentIndex = 0,
                    selectedOption = null,
                    isAnswerSubmitted = false,
                    isCorrect = false,
                    score = 0,
                    streak = 0,
                    bestStreakInQuiz = 0,
                    timeLeftSeconds = timePerQuestion,
                    maxTimePerQuestion = timePerQuestion,
                    isQuizFinished = false,
                    userAnswers = emptyList()
                )
            )
        }
        startQuizTimer()
    }

    private fun startQuizTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                val current = _uiState.value.quizState
                if (!current.isActive || current.isAnswerSubmitted || current.isQuizFinished) {
                    break
                }
                if (current.timeLeftSeconds <= 1) {
                    // Time out
                    onQuizTimeOut()
                    break
                } else {
                    _uiState.update {
                        it.copy(quizState = it.quizState.copy(timeLeftSeconds = it.quizState.timeLeftSeconds - 1))
                    }
                }
            }
        }
    }

    fun selectQuizOption(option: String) {
        val quiz = _uiState.value.quizState
        if (!quiz.isActive || quiz.isAnswerSubmitted) return

        timerJob?.cancel()
        val question = quiz.questions.getOrNull(quiz.currentIndex) ?: return
        val isCorrect = option.equals(question.correctOption, ignoreCase = true)
        val timeSpent = quiz.maxTimePerQuestion - quiz.timeLeftSeconds

        val newStreak = if (isCorrect) quiz.streak + 1 else 0
        val newBestStreak = maxOf(newStreak, quiz.bestStreakInQuiz)
        val newScore = if (isCorrect) quiz.score + (10 + newStreak * 2) else quiz.score

        val updatedAnswers = quiz.userAnswers + UserQuizAnswer(
            question = question,
            chosenOption = option,
            isCorrect = isCorrect,
            timeSpentSeconds = timeSpent
        )

        _uiState.update {
            it.copy(
                quizState = quiz.copy(
                    selectedOption = option,
                    isAnswerSubmitted = true,
                    isCorrect = isCorrect,
                    score = newScore,
                    streak = newStreak,
                    bestStreakInQuiz = newBestStreak,
                    userAnswers = updatedAnswers
                )
            )
        }
    }

    private fun onQuizTimeOut() {
        val quiz = _uiState.value.quizState
        val question = quiz.questions.getOrNull(quiz.currentIndex) ?: return
        val updatedAnswers = quiz.userAnswers + UserQuizAnswer(
            question = question,
            chosenOption = null,
            isCorrect = false,
            timeSpentSeconds = quiz.maxTimePerQuestion
        )
        _uiState.update {
            it.copy(
                quizState = quiz.copy(
                    selectedOption = null,
                    isAnswerSubmitted = true,
                    isCorrect = false,
                    streak = 0,
                    userAnswers = updatedAnswers
                )
            )
        }
    }

    fun nextQuizQuestion() {
        val quiz = _uiState.value.quizState
        if (quiz.currentIndex + 1 >= quiz.questions.size) {
            // Quiz completed
            finishQuiz()
        } else {
            _uiState.update {
                it.copy(
                    quizState = quiz.copy(
                        currentIndex = quiz.currentIndex + 1,
                        selectedOption = null,
                        isAnswerSubmitted = false,
                        isCorrect = false,
                        timeLeftSeconds = quiz.maxTimePerQuestion
                    )
                )
            }
            startQuizTimer()
        }
    }

    private fun finishQuiz() {
        timerJob?.cancel()
        val quiz = _uiState.value.quizState
        val currentStats = _uiState.value.userStats
        val totalSolvedInQuiz = quiz.questions.size
        val totalCorrectInQuiz = quiz.userAnswers.count { it.isCorrect }

        val newStats = currentStats.copy(
            totalSolved = currentStats.totalSolved + totalSolvedInQuiz,
            totalCorrect = currentStats.totalCorrect + totalCorrectInQuiz,
            bestStreak = maxOf(currentStats.bestStreak, quiz.bestStreakInQuiz),
            speedQuizzesCompleted = currentStats.speedQuizzesCompleted + 1,
            bestScore = maxOf(currentStats.bestScore, quiz.score),
            lastScore = quiz.score
        )
        saveStats(newStats)

        _uiState.update {
            it.copy(quizState = quiz.copy(isQuizFinished = true, isActive = false))
        }
    }

    fun resetStats() {
        saveStats(UserStats())
    }
}
