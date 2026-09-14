package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.FractionPercentEntry
import com.example.data.repository.CalculationRepository
import com.example.ui.components.AppTopBar
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.CalculationViewModel

enum class CheatSheetTab {
    FRACTIONS_PERCENT,
    SQUARES_CUBES,
    CI_RATES,
    RULES
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheatSheetScreen(
    viewModel: CalculationViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    var currentTab by remember { mutableStateOf(CheatSheetTab.FRACTIONS_PERCENT) }
    var filterText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Speed Math Tables",
                subtitle = "Formulas, Percentages & Powers",
                streak = uiState.userStats.currentStreak,
                showBackButton = true,
                onBackClick = { viewModel.navigateTo(AppScreen.HOME) },
                onStatsClick = { viewModel.navigateTo(AppScreen.STATS) }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Tab Selector
            TabRow(
                selectedTabIndex = currentTab.ordinal,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = PrimaryIndigo,
                modifier = Modifier.clip(RoundedCornerShape(12.dp))
            ) {
                Tab(
                    selected = currentTab == CheatSheetTab.FRACTIONS_PERCENT,
                    onClick = { currentTab = CheatSheetTab.FRACTIONS_PERCENT },
                    text = { Text("Fraction ↔ %", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
                )
                Tab(
                    selected = currentTab == CheatSheetTab.SQUARES_CUBES,
                    onClick = { currentTab = CheatSheetTab.SQUARES_CUBES },
                    text = { Text("x² & x³", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
                )
                Tab(
                    selected = currentTab == CheatSheetTab.CI_RATES,
                    onClick = { currentTab = CheatSheetTab.CI_RATES },
                    text = { Text("CI Net Rates", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
                )
                Tab(
                    selected = currentTab == CheatSheetTab.RULES,
                    onClick = { currentTab = CheatSheetTab.RULES },
                    text = { Text("Mental Rules", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
                )
            }

            // Search within table
            OutlinedTextField(
                value = filterText,
                onValueChange = { filterText = it },
                placeholder = { Text("Filter table values...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = PrimaryIndigo) },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth().testTag("table_filter_input")
            )

            when (currentTab) {
                CheatSheetTab.FRACTIONS_PERCENT -> FractionPercentTableView(filterText = filterText)
                CheatSheetTab.SQUARES_CUBES -> SquaresCubesTableView(filterText = filterText)
                CheatSheetTab.CI_RATES -> CiRatesTableView(filterText = filterText)
                CheatSheetTab.RULES -> MentalRulesView()
            }
        }
    }
}

@Composable
private fun FractionPercentTableView(filterText: String) {
    val items = remember(filterText) {
        if (filterText.isBlank()) CalculationRepository.fractionPercentTable
        else {
            val q = filterText.trim().lowercase()
            CalculationRepository.fractionPercentTable.filter {
                it.fraction.contains(q) || it.percentage.contains(q) || it.mixedPercentage.contains(q) || it.decimal.contains(q)
            }
        }
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(PrimaryIndigo.copy(alpha = 0.12f), RoundedCornerShape(8.dp))
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Fraction", fontWeight = FontWeight.Bold, color = PrimaryIndigo, modifier = Modifier.weight(1f))
                Text("Decimal", fontWeight = FontWeight.Bold, color = PrimaryIndigo, modifier = Modifier.weight(1f))
                Text("Percentage", fontWeight = FontWeight.Bold, color = PrimaryIndigo, modifier = Modifier.weight(1.2f))
                Text("Mixed %", fontWeight = FontWeight.Bold, color = PrimaryIndigo, modifier = Modifier.weight(1.2f))
            }
        }

        items(items) { item ->
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = MaterialTheme.colorScheme.surface,
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.fraction,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.ExtraBold,
                        color = PrimaryIndigo,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = item.decimal,
                        fontFamily = FontFamily.Monospace,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = item.percentage,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.SemiBold,
                        color = AccentEmerald,
                        modifier = Modifier.weight(1.2f)
                    )
                    Text(
                        text = item.mixedPercentage,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.SemiBold,
                        color = AccentOrange,
                        modifier = Modifier.weight(1.2f)
                    )
                }
            }
        }
    }
}

@Composable
private fun SquaresCubesTableView(filterText: String) {
    val items = remember(filterText) {
        (1..50).map { n ->
            Triple(n, n * n, if (n <= 30) n * n * n else null)
        }.filter {
            if (filterText.isBlank()) true
            else {
                val q = filterText.trim()
                it.first.toString().contains(q) || it.second.toString().contains(q) || (it.third?.toString()?.contains(q) == true)
            }
        }
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(PrimaryIndigo.copy(alpha = 0.12f), RoundedCornerShape(8.dp))
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Number (N)", fontWeight = FontWeight.Bold, color = PrimaryIndigo, modifier = Modifier.weight(1f))
                Text("Square (N²)", fontWeight = FontWeight.Bold, color = PrimaryIndigo, modifier = Modifier.weight(1f))
                Text("Cube (N³)", fontWeight = FontWeight.Bold, color = PrimaryIndigo, modifier = Modifier.weight(1f))
            }
        }

        items(items) { (n, sq, cube) ->
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = MaterialTheme.colorScheme.surface,
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "$n",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "$sq",
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryIndigo,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = if (cube != null) "$cube" else "-",
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        color = AccentOrange,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun CiRatesTableView(filterText: String) {
    val rates = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 20, 25)
    val list = remember(filterText) {
        rates.map { r ->
            val ci2 = 2 * r + (r * r) / 100.0
            val ci3 = 3 * r + 3 * (r * r) / 100.0 + (r * r * r) / 10000.0
            val diff2 = (r * r) / 100.0
            Triple(r, "%.2f%%".format(ci2), "%.4f%%".format(ci3)) to "%.2f%%".format(diff2)
        }.filter {
            if (filterText.isBlank()) true
            else {
                val q = filterText.trim()
                it.first.first.toString().contains(q) || it.first.second.contains(q) || it.first.third.contains(q)
            }
        }
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(PrimaryIndigo.copy(alpha = 0.12f), RoundedCornerShape(8.dp))
                    .padding(horizontal = 10.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Rate", fontWeight = FontWeight.Bold, color = PrimaryIndigo, modifier = Modifier.weight(0.8f))
                Text("2 Yrs CI", fontWeight = FontWeight.Bold, color = PrimaryIndigo, modifier = Modifier.weight(1.2f))
                Text("3 Yrs CI", fontWeight = FontWeight.Bold, color = PrimaryIndigo, modifier = Modifier.weight(1.4f))
                Text("Diff (2Y)", fontWeight = FontWeight.Bold, color = PrimaryIndigo, modifier = Modifier.weight(1f))
            }
        }

        items(list) { (data, diff2) ->
            val (r, ci2, ci3) = data
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = MaterialTheme.colorScheme.surface,
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "$r%",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(0.8f)
                    )
                    Text(
                        text = ci2,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryIndigo,
                        modifier = Modifier.weight(1.2f)
                    )
                    Text(
                        text = ci3,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        color = AccentEmerald,
                        modifier = Modifier.weight(1.4f)
                    )
                    Text(
                        text = diff2,
                        fontFamily = FontFamily.Monospace,
                        color = AccentRose,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun MentalRulesView() {
    val rules = listOf(
        "Tens-First Addition" to "Always convert 2-digit numbers to tens first in mind (e.g. 67 -> 60), add units, then combine.",
        "Vedic Base Subtraction" to "For 1000 - N: Subtract all digits from 9 and the last unit digit from 10 (All from 9, last from 10).",
        "Cross Multiplication" to "(10a+b)(10c+d) = Step 1: b×d, Step 2: a×d + b×c + carry, Step 3: a×c + carry.",
        "Square Shortcut" to "(10a+b)² = b² (unit) -> 2ab + carry (tens) -> a² + carry (hundreds).",
        "Net Percentage Change" to "Net % = x + y + (xy/100). If both are increases, add. If decrease, put negative sign.",
        "Time & Work Rule" to "If (a/b) work is done in D days, total days required = D × (b/a).",
        "CI 2-Year Rate" to "2a + a²/100. CI-SI difference for 2 years is exactly (a²/100)%.",
        "Fraction Division" to "(a/b) ÷ (c/d) = (a×d) / (b×c). Invert the second fraction and cross multiply."
    )

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        items(rules) { (title, desc) ->
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = MaterialTheme.colorScheme.surface,
                border = androidx.compose.foundation.BorderStroke(1.dp, PrimaryIndigo.copy(alpha = 0.25f)),
                tonalElevation = 2.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Bolt, contentDescription = null, tint = AccentAmber, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = PrimaryIndigo
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = desc,
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
