with open("app/src/main/java/com/example/ui/MainApp.kt", "r") as f:
    content = f.read()

old_when = """                AppScreen.STATS -> StatsScreen(viewModel = viewModel)
                AppScreen.ALGEBRA_GRAPH -> AlgebraGraphScreen(viewModel = viewModel)
            }
        }
    }
}"""

new_when = """                AppScreen.STATS -> StatsScreen(viewModel = viewModel)
                AppScreen.ALGEBRA_GRAPH -> AlgebraGraphScreen(viewModel = viewModel)
                AppScreen.PYQ_TESTS -> PyqTestsScreen(viewModel = viewModel)
                AppScreen.CALCULATOR -> CalculatorScreen(viewModel = viewModel)
            }
        }
    }
}"""

with open("app/src/main/java/com/example/ui/MainApp.kt", "w") as f:
    f.write(content.replace(old_when, new_when))
