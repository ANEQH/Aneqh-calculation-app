package com.example.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.AppTopBar
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppDictionary.tr
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.CalculationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorScreen(viewModel: CalculationViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val lang = uiState.appLanguage
    
    var displayText by remember { mutableStateOf("0") }
    
    val buttons = listOf(
        "C", "(", ")", "÷",
        "7", "8", "9", "×",
        "4", "5", "6", "-",
        "1", "2", "3", "+",
        "0", ".", "DEL", "="
    )
    
    fun onBtnClick(btn: String) {
        when (btn) {
            "C" -> displayText = "0"
            "DEL" -> if (displayText.length > 1) displayText = displayText.dropLast(1) else displayText = "0"
            "=" -> displayText = "Done" // simplified
            else -> {
                if (displayText == "0" || displayText == "Done") displayText = btn
                else displayText += btn
            }
        }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = tr("pro_calculator", lang),
                subtitle = tr("pro_calculator_sub", lang),
                streak = uiState.userStats.currentStreak,
                showBackButton = true,
                onBackClick = { viewModel.navigateTo(AppScreen.HOME) },
                onThemeToggle = { viewModel.toggleTheme() },
                onLanguageToggle = { viewModel.toggleLanguage() },
                currentLanguage = lang
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Surface(
                modifier = Modifier.fillMaxWidth().weight(1f),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                shape = RoundedCornerShape(16.dp)
            ) {
                Box(contentAlignment = Alignment.BottomEnd, modifier = Modifier.padding(24.dp)) {
                    Text(
                        text = displayText,
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(buttons) { btn ->
                    val color = when (btn) {
                        "C", "DEL" -> AccentRose
                        "÷", "×", "-", "+", "=" -> PrimaryIndigo
                        else -> MaterialTheme.colorScheme.surfaceVariant
                    }
                    val textColor = if (color == MaterialTheme.colorScheme.surfaceVariant) MaterialTheme.colorScheme.onSurface else androidx.compose.ui.graphics.Color.White
                    
                    Surface(
                        shape = CircleShape,
                        color = color,
                        modifier = Modifier.aspectRatio(1f).clickable { onBtnClick(btn) }
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = btn,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = textColor
                            )
                        }
                    }
                }
            }
        }
    }
}
