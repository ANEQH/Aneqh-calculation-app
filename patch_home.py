with open("app/src/main/java/com/example/ui/screens/HomeScreen.kt", "r") as f:
    content = f.read()

old_shortcut = """                        item {
                            QuickActionChip(
                                title = "📈 CI & SI Rate Sheet",
                                subtitle = "2 & 3 Yrs",
                                color = AccentPurple,
                                onClick = { viewModel.navigateTo(AppScreen.CHEAT_SHEETS) }
                            )
                        }"""

new_shortcut = """                        item {
                            QuickActionChip(
                                title = "📈 CI & SI Rate Sheet",
                                subtitle = "2 & 3 Yrs",
                                color = AccentPurple,
                                onClick = { viewModel.navigateTo(AppScreen.CHEAT_SHEETS) }
                            )
                        }
                        item {
                            QuickActionChip(
                                title = "🧊 3D Visual Math",
                                subtitle = "Algebra Graphs",
                                color = AccentOrange,
                                onClick = { viewModel.navigateTo(AppScreen.ALGEBRA_GRAPH) }
                            )
                        }"""

with open("app/src/main/java/com/example/ui/screens/HomeScreen.kt", "w") as f:
    f.write(content.replace(old_shortcut, new_shortcut))
