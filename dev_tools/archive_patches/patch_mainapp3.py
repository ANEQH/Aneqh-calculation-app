with open("app/src/main/java/com/example/ui/MainApp.kt", "r") as f:
    content = f.read()

imports = """import com.example.ui.screens.TableDrillScreen"""
new_imports = """import com.example.ui.screens.TableDrillScreen
import com.example.ui.screens.MathGymScreen
import com.example.ui.screens.VedicTricksScreen
import com.example.ui.screens.FormulaCardsScreen"""

routing_old = """                AppScreen.TABLE_DRILL -> TableDrillScreen(viewModel = viewModel)
            }"""
routing_new = """                AppScreen.TABLE_DRILL -> TableDrillScreen(viewModel = viewModel)
                AppScreen.MATH_GYM -> MathGymScreen(viewModel = viewModel)
                AppScreen.VEDIC_TRICKS -> VedicTricksScreen(viewModel = viewModel)
                AppScreen.FORMULA_CARDS -> FormulaCardsScreen(viewModel = viewModel)
            }"""

content = content.replace(imports, new_imports).replace(routing_old, routing_new)
with open("app/src/main/java/com/example/ui/MainApp.kt", "w") as f:
    f.write(content)
