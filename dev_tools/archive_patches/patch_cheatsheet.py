import re

with open("app/src/main/java/com/example/ui/screens/CheatSheetScreen.kt", "r") as f:
    content = f.read()

# Add ADVANCED_MATH to tabs
old_tabs = """                Tab(
                    selected = currentTab == CheatSheetTab.RULES,
                    onClick = { currentTab = CheatSheetTab.RULES },
                    text = { Text("Mental Rules", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
                )
            }"""

new_tabs = """                Tab(
                    selected = currentTab == CheatSheetTab.RULES,
                    onClick = { currentTab = CheatSheetTab.RULES },
                    text = { Text("Mental Rules", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
                )
                Tab(
                    selected = currentTab == CheatSheetTab.ADVANCED_MATH,
                    onClick = { currentTab = CheatSheetTab.ADVANCED_MATH },
                    text = { Text("1000x Concept Book", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
                )
            }"""
content = content.replace(old_tabs, new_tabs)

# Add ADVANCED_MATH when block
old_when = """                CheatSheetTab.RULES -> RulesTab(searchQuery = filterText)
            }
        }
    }
}"""

new_when = """                CheatSheetTab.RULES -> RulesTab(searchQuery = filterText)
                CheatSheetTab.ADVANCED_MATH -> AdvancedMathTab()
            }
        }
    }
}

@Composable
fun AdvancedMathTab() {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        item {
            com.example.ui.components.NotebookPage(
                title = "Algebra: Concept 1",
                text = "If x + 1/x = k\n\n1) x² + 1/x² = k² - 2\n2) x³ + 1/x³ = k³ - 3k\n3) x⁴ + 1/x⁴ = (k²-2)² - 2\n4) x⁵ + 1/x⁵ = (k²-2)(k³-3k) - k\n\nPYQ Trick:\nAlways check if power is odd or even. For x - 1/x = k, sign changes to + in squares."
            )
        }
        item {
            com.example.ui.components.NotebookPage(
                title = "Trigonometry: Concept 2",
                text = "Value Putting Method:\n\n1) If sin, cos only => put θ = 0° or 90°\n2) If tan, cot, sec, csc => put θ = 45°\n3) For asecθ + btanθ = c, use sec²θ - tan²θ = 1.\n\nPYQ Trick:\nCheck options first. If options give same value for 45°, use 30°."
            )
        }
        item {
            com.example.ui.components.NotebookPage(
                title = "Geometry: Incenter & Circumcenter",
                text = "1) Angle at Incenter (I):\n   ∠BIC = 90° + ∠A/2\n\n2) Angle at Circumcenter (O):\n   ∠BOC = 2 × ∠A\n\n3) Orthocenter (H):\n   ∠BHC = 180° - ∠A\n\nPYQ Trick:\nAlways draw the circle touching inside for incenter. Radius r = Area / Semi-perimeter."
            )
        }
        item {
            com.example.ui.components.NotebookPage(
                title = "Number System: Divisibility",
                text = "Rule of 72 => Check 8 and 9.\nRule of 88 => Check 8 and 11.\n\nRule of 11:\nSum of odd places - Sum of even places = 0 or 11k.\n\nRule of 8:\nLast 3 digits must be divisible by 8.\n\nPYQ Trick:\nStart checking from the end (unit digit) to eliminate options quickly."
            )
        }
        item {
            com.example.ui.components.NotebookPage(
                title = "Time Speed & Distance",
                text = "Average Speed Formula:\nIf distance is same:\nAvg Spd = 2xy / (x + y)\n\nRelative Speed:\nOpposite Direction = x + y\nSame Direction = |x - y|\n\nTrain passing platform:\nDistance = Train Length + Platform Length."
            )
        }
    }
}"""
content = content.replace(old_when, new_when)

with open("app/src/main/java/com/example/ui/screens/CheatSheetScreen.kt", "w") as f:
    f.write(content)
