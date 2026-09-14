import re

with open("app/src/main/java/com/example/ui/screens/CheatSheetScreen.kt", "r") as f:
    content = f.read()

# 1. Update Enum
old_enum = """enum class CheatSheetTab {
    FRACTIONS_PERCENT,
    SQUARES_CUBES,
    CI_RATES,
    RULES
}"""
new_enum = """enum class CheatSheetTab {
    FRACTIONS_PERCENT,
    SQUARES_CUBES,
    CI_RATES,
    RULES,
    ADVANCED_MATH
}"""
content = content.replace(old_enum, new_enum)

# 2. Add imports for advanced math
import_str = "import com.example.data.repository.CalculationRepository\nimport com.example.data.repository.SscCglAdvancedMath"
content = content.replace("import com.example.data.repository.CalculationRepository", import_str)


# 3. Update TabRow - since we only have space for so many, let's change ScrollableTabRow
old_tabrow = """            TabRow(
                selectedTabIndex = currentTab.ordinal,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = PrimaryIndigo,
                modifier = Modifier.clip(RoundedCornerShape(12.dp))
            ) {"""
new_tabrow = """            ScrollableTabRow(
                selectedTabIndex = currentTab.ordinal,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = PrimaryIndigo,
                edgePadding = 8.dp,
                modifier = Modifier.clip(RoundedCornerShape(12.dp))
            ) {"""
content = content.replace(old_tabrow, new_tabrow)

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
                    text = { Text("Advanced PYQ Math", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
                )
            }"""
content = content.replace(old_tabs, new_tabs)

# 4. Update the when condition
old_when = """            when (currentTab) {
                CheatSheetTab.FRACTIONS_PERCENT -> FractionPercentTableView(filterText = filterText)
                CheatSheetTab.SQUARES_CUBES -> SquaresCubesTableView(filterText = filterText)
                CheatSheetTab.CI_RATES -> CiRatesTableView(filterText = filterText)
                CheatSheetTab.RULES -> MentalRulesView()
            }"""
new_when = """            when (currentTab) {
                CheatSheetTab.FRACTIONS_PERCENT -> FractionPercentTableView(filterText = filterText)
                CheatSheetTab.SQUARES_CUBES -> SquaresCubesTableView(filterText = filterText)
                CheatSheetTab.CI_RATES -> CiRatesTableView(filterText = filterText)
                CheatSheetTab.RULES -> MentalRulesView()
                CheatSheetTab.ADVANCED_MATH -> AdvancedMathView(filterText = filterText)
            }"""
content = content.replace(old_when, new_when)


new_function = """

@Composable
private fun AdvancedMathView(filterText: String) {
    val items = SscCglAdvancedMath.concepts.filter {
        if (filterText.isBlank()) true
        else {
            val q = filterText.lowercase()
            it.title.lowercase().contains(q) || it.formula.lowercase().contains(q) || it.trick.lowercase().contains(q)
        }
    }
    
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        item {
            Surface(
                color = AccentAmber.copy(alpha = 0.15f),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.School, contentDescription = null, tint = AccentOrange)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "SSC CGL Mains level formulas, theorems & shortcuts.",
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
        items(items) { concept ->
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surface,
                border = androidx.compose.foundation.BorderStroke(1.dp, PrimaryIndigo.copy(alpha = 0.3f)),
                tonalElevation = 4.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = concept.title,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
                        color = PrimaryIndigo
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    
                    Surface(color = PrimaryIndigo.copy(alpha = 0.05f), shape = RoundedCornerShape(8.dp), modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = concept.formula,
                            modifier = Modifier.padding(10.dp),
                            style = MaterialTheme.typography.bodyMedium.copy(fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(10.dp))
                    Row {
                        Text("🔥 Trick: ", fontWeight = FontWeight.Bold, color = AccentOrange, fontSize = 13.sp)
                        Text(concept.trick, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    
                    Spacer(modifier = Modifier.height(6.dp))
                    Row {
                        Text("🎯 PYQ Context: ", fontWeight = FontWeight.Bold, color = AccentEmerald, fontSize = 13.sp)
                        Text(concept.pyqContext, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
    }
}
"""
with open("app/src/main/java/com/example/ui/screens/CheatSheetScreen.kt", "w") as f:
    f.write(content + new_function)

