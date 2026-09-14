with open("app/src/main/java/com/example/ui/MainApp.kt", "r") as f:
    content = f.read()

imports = """import com.example.ui.screens.FormulaCardsScreen"""
new_imports = """import com.example.ui.screens.FormulaCardsScreen
import com.example.ui.screens.GeometryScreen"""

routing_old = """                AppScreen.FORMULA_CARDS -> FormulaCardsScreen(viewModel = viewModel)
            }"""
routing_new = """                AppScreen.FORMULA_CARDS -> FormulaCardsScreen(viewModel = viewModel)
                AppScreen.GEOMETRY -> GeometryScreen(viewModel = viewModel)
            }"""

content = content.replace(imports, new_imports).replace(routing_old, routing_new)
with open("app/src/main/java/com/example/ui/MainApp.kt", "w") as f:
    f.write(content)
