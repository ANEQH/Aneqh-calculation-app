with open("app/src/main/java/com/example/ui/screens/HomeScreen.kt", "r") as f:
    content = f.read()

import_icon = "import androidx.compose.material.icons.filled.Category"
new_import_icon = "import androidx.compose.material.icons.filled.Category\nimport androidx.compose.material.icons.filled.Architecture"
content = content.replace(import_icon, new_import_icon)

tools_old = """                        item { ChipCard(title = "Formulas", subtitle = "Flashcards", color = PrimaryIndigo, icon = Icons.Default.Style) { viewModel.navigateTo(AppScreen.FORMULA_CARDS) } }
                    }
                }
            }"""
tools_new = """                        item { ChipCard(title = "Formulas", subtitle = "Flashcards", color = PrimaryIndigo, icon = Icons.Default.Style) { viewModel.navigateTo(AppScreen.FORMULA_CARDS) } }
                        item { ChipCard(title = "Geometry", subtitle = "Shapes & Formula", color = AccentEmerald, icon = Icons.Default.Architecture) { viewModel.navigateTo(AppScreen.GEOMETRY) } }
                    }
                }
            }"""

content = content.replace(tools_old, tools_new)
with open("app/src/main/java/com/example/ui/screens/HomeScreen.kt", "w") as f:
    f.write(content)
