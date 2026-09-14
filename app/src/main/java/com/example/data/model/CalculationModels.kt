package com.example.data.model

data class Chapter(
    val id: Int,
    val titleEnglish: String,
    val titleHindi: String,
    val description: String,
    val iconName: String,
    val pageRange: String,
    val types: List<CalculationType>,
    val quickTip: String
)

data class CalculationType(
    val typeNumber: Int,
    val titleEnglish: String,
    val titleHindi: String,
    val hintHindi: String,
    val hintEnglish: String,
    val formula: String? = null,
    val steps: List<CalculationStep>,
    val exercises: List<ExerciseProblem>
)

data class CalculationStep(
    val stepLabel: String,
    val mathExpression: String,
    val explanationHindi: String,
    val explanationEnglish: String
)

data class ExerciseProblem(
    val id: Int,
    val question: String,
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val optionD: String,
    val correctOption: String, // "a", "b", "c", or "d"
    val solutionSteps: String
)

data class QuizQuestion(
    val chapterId: Int,
    val chapterTitle: String,
    val questionText: String,
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val optionD: String,
    val correctOption: String,
    val explanation: String
)

data class FractionPercentEntry(
    val fraction: String,
    val decimal: String,
    val percentage: String,
    val mixedPercentage: String
)

data class UserStats(
    val totalSolved: Int = 0,
    val totalCorrect: Int = 0,
    val currentStreak: Int = 0,
    val bestStreak: Int = 0,
    val speedQuizzesCompleted: Int = 0,
    val bestScore: Int = 0,
    val lastScore: Int = 0
)
