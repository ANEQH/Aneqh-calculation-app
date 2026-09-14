package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.AppTopBar
import com.example.ui.theme.PrimaryIndigo
import com.example.ui.theme.AccentCyan
import com.example.ui.theme.AccentEmerald
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.CalculationViewModel
import androidx.compose.ui.graphics.Color

data class FormulaCard(val category: String, val formula: String, val meaning: String, val color: Color)

val allFormulas = listOf(
    FormulaCard("Algebra", "(a+b)² = a² + b² + 2ab", "Square of sum", AccentCyan),
    FormulaCard("Algebra", "(a-b)² = a² + b² - 2ab", "Square of diff", AccentCyan),
    FormulaCard("Algebra", "a² - b² = (a-b)(a+b)", "Difference of squares", AccentCyan),
    FormulaCard("Algebra", "(a+b)³ = a³ + b³ + 3ab(a+b)", "Cube of sum", AccentCyan),
    FormulaCard("Geometry", "Area(Circle) = πr²", "r = radius", AccentEmerald),
    FormulaCard("Geometry", "Circumference = 2πr", "Perimeter of circle", AccentEmerald),
    FormulaCard("Geometry", "Area(Triangle) = ½bh", "b=base, h=height", AccentEmerald),
    FormulaCard("Geometry", "Pythagoras: a² + b² = c²", "Right angled triangle", AccentEmerald),
    FormulaCard("Arithmetic", "SI = (P×R×T)/100", "Simple Interest", PrimaryIndigo),
    FormulaCard("Arithmetic", "Amount = P(1+R/100)^T", "Compound Interest", PrimaryIndigo),
    FormulaCard("Arithmetic", "Speed = Distance / Time", "Basic Kinematics", PrimaryIndigo),
    FormulaCard("Arithmetic", "Work = Rate × Time", "Work & Time", PrimaryIndigo)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormulaCardsScreen(viewModel: CalculationViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val lang = uiState.appLanguage

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Formula Flashcards",
                subtitle = "Memorize essential formulas",
                streak = uiState.userStats.currentStreak,
                showBackButton = true,
                onBackClick = { viewModel.navigateTo(AppScreen.HOME) },
                onThemeToggle = { viewModel.toggleTheme() },
                onLanguageToggle = { viewModel.toggleLanguage() },
                currentLanguage = lang
            )
        }
    ) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 100.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(allFormulas) { card ->
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = card.color.copy(alpha = 0.1f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, card.color.copy(alpha = 0.3f)),
                    modifier = Modifier.aspectRatio(1f)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Surface(
                            color = card.color,
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = card.category,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = card.formula,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = card.meaning,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}
