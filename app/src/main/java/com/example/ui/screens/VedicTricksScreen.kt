package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.AppTopBar
import com.example.ui.theme.AccentAmber
import com.example.ui.theme.PrimaryIndigo
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.CalculationViewModel

data class VedicTrick(val title: String, val description: String, val example: String)

val tricksList = listOf(
    VedicTrick(
        title = "Squaring numbers ending in 5",
        description = "Multiply the first digit by its next higher number and append 25.",
        example = "35² -> 3 × 4 = 12 -> 1225\n75² -> 7 × 8 = 56 -> 5625"
    ),
    VedicTrick(
        title = "Multiply by 11",
        description = "Write the first and last digits, and in the middle write the sum of adjacent digits.",
        example = "32 × 11 -> 3 | (3+2) | 2 -> 352\n45 × 11 -> 4 | (4+5) | 5 -> 495"
    ),
    VedicTrick(
        title = "Base 100 Multiplication",
        description = "For numbers close to 100. Find difference from 100, cross subtract/add, then multiply differences.",
        example = "97 × 96 -> Diff: -3, -4\nCross: 97 - 4 = 93\nMultiply diffs: -3 × -4 = 12\nResult: 9312"
    ),
    VedicTrick(
        title = "Divide by 5",
        description = "Multiply the number by 2 and move the decimal point one place to the left.",
        example = "235 / 5 -> 235 × 2 = 470 -> 47.0\n18 / 5 -> 18 × 2 = 36 -> 3.6"
    ),
    VedicTrick(
        title = "Multiply by 5",
        description = "Divide the number by 2 and multiply by 10 (or add a zero).",
        example = "48 × 5 -> 48 / 2 = 24 -> 240\n13 × 5 -> 13 / 2 = 6.5 -> 65"
    ),
    VedicTrick(
        title = "Square of numbers near 50",
        description = "Base is 25. Add/subtract the difference from 50 to 25. Then square the difference.",
        example = "52² -> Diff is +2. (25+2) = 27. 2² = 04. Result: 2704\n48² -> Diff is -2. (25-2) = 23. 2² = 04. Result: 2304"
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VedicTricksScreen(viewModel: CalculationViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val lang = uiState.appLanguage

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Vedic Math Tricks",
                subtitle = "Mind-blowing shortcuts",
                streak = uiState.userStats.currentStreak,
                showBackButton = true,
                onBackClick = { viewModel.navigateTo(AppScreen.HOME) },
                onThemeToggle = { viewModel.toggleTheme() },
                onLanguageToggle = { viewModel.toggleLanguage() },
                currentLanguage = lang
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 100.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(tricksList) { trick ->
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.surface,
                    border = androidx.compose.foundation.BorderStroke(1.dp, PrimaryIndigo.copy(alpha = 0.2f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = AccentAmber.copy(alpha = 0.2f)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Lightbulb,
                                    contentDescription = null,
                                    tint = AccentAmber,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = trick.title,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = trick.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Surface(
                            color = PrimaryIndigo.copy(alpha = 0.05f),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = trick.example,
                                modifier = Modifier.padding(12.dp),
                                style = MaterialTheme.typography.bodyMedium.copy(fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace),
                                color = PrimaryIndigo
                            )
                        }
                    }
                }
            }
        }
    }
}
