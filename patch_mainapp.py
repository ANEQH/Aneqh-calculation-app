with open("app/src/main/java/com/example/ui/MainApp.kt", "r") as f:
    content = f.read()

old_when = """                AppScreen.CHEAT_SHEETS -> CheatSheetScreen(viewModel = viewModel)
                AppScreen.STATS -> StatsScreen(viewModel = viewModel)
            }
        }
    }
}"""

new_when = """                AppScreen.CHEAT_SHEETS -> CheatSheetScreen(viewModel = viewModel)
                AppScreen.STATS -> StatsScreen(viewModel = viewModel)
                AppScreen.ALGEBRA_GRAPH -> AlgebraGraphScreen(viewModel = viewModel)
            }
        }
    }
}"""

with open("app/src/main/java/com/example/ui/MainApp.kt", "w") as f:
    f.write(content.replace(old_when, new_when))
