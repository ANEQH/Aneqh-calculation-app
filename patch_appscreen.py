with open("app/src/main/java/com/example/ui/viewmodel/CalculationViewModel.kt", "r") as f:
    content = f.read()

old_enum = """enum class AppScreen {
    SPLASH,
    HOME,
    CHAPTER_DETAIL,
    PRACTICE_QUESTION,
    SPEED_QUIZ,
    SOLVERS,
    CHEAT_SHEETS,
    STATS,
    ALGEBRA_GRAPH
}"""

new_enum = """enum class AppScreen {
    SPLASH,
    HOME,
    CHAPTER_DETAIL,
    PRACTICE_QUESTION,
    SPEED_QUIZ,
    SOLVERS,
    CHEAT_SHEETS,
    STATS,
    ALGEBRA_GRAPH,
    PYQ_TESTS,
    CALCULATOR
}"""

with open("app/src/main/java/com/example/ui/viewmodel/CalculationViewModel.kt", "w") as f:
    f.write(content.replace(old_enum, new_enum))
