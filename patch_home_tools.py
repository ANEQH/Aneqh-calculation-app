import re

with open("app/src/main/java/com/example/ui/screens/HomeScreen.kt", "r") as f:
    content = f.read()

import_icon = "import androidx.compose.material.icons.filled.CheckCircle"
new_import_icon = "import androidx.compose.material.icons.filled.CheckCircle\nimport androidx.compose.material.icons.filled.FormatListNumbered\nimport androidx.compose.material.icons.filled.GridOn\nimport androidx.compose.material.icons.filled.Checklist"
content = content.replace(import_icon, new_import_icon)

tools_old = """                        item { ChipCard(title = tr("concept_book", lang), subtitle = "1000x Theory", color = AccentEmerald, icon = Icons.Default.MenuBook) { viewModel.navigateTo(AppScreen.CHEAT_SHEETS) } }"""
tools_new = """                        item { ChipCard(title = tr("concept_book", lang), subtitle = "1000x Theory", color = AccentEmerald, icon = Icons.Default.MenuBook) { viewModel.navigateTo(AppScreen.CHEAT_SHEETS) } }
                        item { ChipCard(title = "SSC Syllabus", subtitle = "Tier 1 & 2", color = AccentRose, icon = Icons.Default.Checklist) { viewModel.navigateTo(AppScreen.SYLLABUS) } }
                        item { ChipCard(title = "Tables 1-30", subtitle = "Pahade", color = AccentAmber, icon = Icons.Default.GridOn) { viewModel.navigateTo(AppScreen.TABLES) } }
                        item { ChipCard(title = "Table Drill", subtitle = "Infinite MCQs", color = AccentCyan, icon = Icons.Default.FormatListNumbered) { viewModel.navigateTo(AppScreen.TABLE_DRILL) } }"""

content = content.replace(tools_old, tools_new)

with open("app/src/main/java/com/example/ui/screens/HomeScreen.kt", "w") as f:
    f.write(content)
