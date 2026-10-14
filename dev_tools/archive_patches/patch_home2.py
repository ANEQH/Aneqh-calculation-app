import re

with open("app/src/main/java/com/example/ui/screens/HomeScreen.kt", "r") as f:
    content = f.read()

old_hero = """private fun HeroDrillBanner(
    onStartSpeedDrill: () -> Unit,
    onOpenSolvers: () -> Unit,
    onLogoClick: () -> Unit
) {"""

new_hero = """private fun HeroDrillBanner(
    onStartSpeedDrill: () -> Unit,
    onOpenSolvers: () -> Unit,
    onLogoClick: () -> Unit,
    lang: com.example.ui.viewmodel.AppLanguage = com.example.ui.viewmodel.AppLanguage.ENGLISH
) {"""
content = content.replace(old_hero, new_hero)

# Fix the caller
content = content.replace("""                HeroDrillBanner(
                    onStartSpeedDrill = {
                        viewModel.startSpeedQuiz(questionCount = 10, timePerQuestion = 20)
                    },
                    onOpenSolvers = {
                        viewModel.navigateTo(AppScreen.SOLVERS)
                    },
                    onLogoClick = {
                        showLogoDialog = true
                    }
                )""", """                HeroDrillBanner(
                    onStartSpeedDrill = {
                        viewModel.startSpeedQuiz(questionCount = 10, timePerQuestion = 20)
                    },
                    onOpenSolvers = {
                        viewModel.navigateTo(AppScreen.SOLVERS)
                    },
                    onLogoClick = {
                        showLogoDialog = true
                    },
                    lang = uiState.appLanguage
                )""")

content = content.replace("""Text("Start Speed Drill",""", """Text(tr("start_speed_drill", lang),""")
content = content.replace("""Text("10 Qs • Mixed Concepts",""", """Text(tr("speed_drill_desc", lang),""")
content = content.replace("""Text("Mental Solvers",""", """Text(tr("mental_solvers", lang),""")
content = content.replace("""Text("10x PYQ Formula Engine",""", """Text(tr("mental_solvers_desc", lang),""")

with open("app/src/main/java/com/example/ui/screens/HomeScreen.kt", "w") as f:
    f.write(content)
