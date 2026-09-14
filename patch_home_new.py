with open("app/src/main/java/com/example/ui/screens/HomeScreen.kt", "r") as f:
    content = f.read()

import_icon = "import androidx.compose.material.icons.filled.Checklist"
new_import_icon = "import androidx.compose.material.icons.filled.Checklist\nimport androidx.compose.material.icons.filled.FitnessCenter\nimport androidx.compose.material.icons.filled.Psychology\nimport androidx.compose.material.icons.filled.Style"
content = content.replace(import_icon, new_import_icon)

tools_old = """                        item { ChipCard(title = "Table Drill", subtitle = "Infinite MCQs", color = AccentCyan, icon = Icons.Default.FormatListNumbered) { viewModel.navigateTo(AppScreen.TABLE_DRILL) } }
                    }
                }
            }"""
tools_new = """                        item { ChipCard(title = "Table Drill", subtitle = "Infinite MCQs", color = AccentCyan, icon = Icons.Default.FormatListNumbered) { viewModel.navigateTo(AppScreen.TABLE_DRILL) } }
                    }
                }
            }

            // Section: CRAZY FEATURES
            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = "Crazy Features & Tricks",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 18.sp),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        item { ChipCard(title = "Brain Gym", subtitle = "1M+ Questions", color = AccentRose, icon = Icons.Default.FitnessCenter) { viewModel.navigateTo(AppScreen.MATH_GYM) } }
                        item { ChipCard(title = "Vedic Tricks", subtitle = "100+ Hacks", color = AccentAmber, icon = Icons.Default.Psychology) { viewModel.navigateTo(AppScreen.VEDIC_TRICKS) } }
                        item { ChipCard(title = "Formulas", subtitle = "Flashcards", color = PrimaryIndigo, icon = Icons.Default.Style) { viewModel.navigateTo(AppScreen.FORMULA_CARDS) } }
                    }
                }
            }"""

content = content.replace(tools_old, tools_new)
with open("app/src/main/java/com/example/ui/screens/HomeScreen.kt", "w") as f:
    f.write(content)
