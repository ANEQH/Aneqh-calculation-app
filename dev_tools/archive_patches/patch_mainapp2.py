with open("app/src/main/java/com/example/ui/MainApp.kt", "r") as f:
    content = f.read()

old_routing = """                AppScreen.PYQ_TESTS -> PyqTestsScreen(viewModel = viewModel)
                AppScreen.CALCULATOR -> CalculatorScreen(viewModel = viewModel)
            }"""

new_routing = """                AppScreen.PYQ_TESTS -> PyqTestsScreen(viewModel = viewModel)
                AppScreen.CALCULATOR -> CalculatorScreen(viewModel = viewModel)
                AppScreen.SYLLABUS -> SyllabusScreen(viewModel = viewModel)
                AppScreen.TABLES -> TablesScreen(viewModel = viewModel)
                AppScreen.TABLE_DRILL -> TableDrillScreen(viewModel = viewModel)
            }"""

content = content.replace(old_routing, new_routing)
with open("app/src/main/java/com/example/ui/MainApp.kt", "w") as f:
    f.write(content)
