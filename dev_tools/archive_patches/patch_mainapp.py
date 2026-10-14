with open("app/src/main/java/com/example/ui/MainApp.kt", "r") as f:
    content = f.read()

import_lines = """import com.example.ui.screens.CheatSheetsScreen
import com.example.ui.screens.HomeScreen"""
new_imports = """import com.example.ui.screens.CheatSheetsScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.SyllabusScreen
import com.example.ui.screens.TablesScreen
import com.example.ui.screens.TableDrillScreen"""

routing_old = """                AppScreen.PYQ_TESTS -> PyqTestsScreen(viewModel)
                AppScreen.CALCULATOR -> CalculatorScreen(viewModel)
            }"""
routing_new = """                AppScreen.PYQ_TESTS -> PyqTestsScreen(viewModel)
                AppScreen.CALCULATOR -> CalculatorScreen(viewModel)
                AppScreen.SYLLABUS -> SyllabusScreen(viewModel)
                AppScreen.TABLES -> TablesScreen(viewModel)
                AppScreen.TABLE_DRILL -> TableDrillScreen(viewModel)
            }"""

content = content.replace(import_lines, new_imports).replace(routing_old, routing_new)
with open("app/src/main/java/com/example/ui/MainApp.kt", "w") as f:
    f.write(content)
