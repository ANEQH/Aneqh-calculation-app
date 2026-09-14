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
            "=" -> {
                try {
                    val exp = displayText.replace("×", "*").replace("÷", "/")
                    val res = calculateBasic(exp)
                    displayText = if (res == res.toLong().toDouble()) res.toLong().toString() else res.toString()
                } catch (e: Exception) {
                    displayText = "Error"
                }
            }
            else -> {
                if (displayText == "0" || displayText == "Done" || displayText == "Error") displayText = btn
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

fun calculateBasic(expr: String): Double {
    return object : Any() {
        var pos = -1
        var ch = 0
        fun nextChar() {
            ch = if (++pos < expr.length) expr[pos].code else -1
        }
        fun eat(charToEat: Int): Boolean {
            while (ch == ' '.code) nextChar()
            if (ch == charToEat) {
                nextChar()
                return true
            }
            return false
        }
        fun parse(): Double {
            nextChar()
            val x = parseExpression()
            if (pos < expr.length) throw RuntimeException("Unexpected: " + ch.toChar())
            return x
        }
        fun parseExpression(): Double {
            var x = parseTerm()
            while (true) {
                if (eat('+'.code)) x += parseTerm()
                else if (eat('-'.code)) x -= parseTerm()
                else return x
            }
        }
        fun parseTerm(): Double {
            var x = parseFactor()
            while (true) {
                if (eat('*'.code)) x *= parseFactor()
                else if (eat('/'.code)) x /= parseFactor()
                else return x
            }
        }
        fun parseFactor(): Double {
            if (eat('+'.code)) return parseFactor()
            if (eat('-'.code)) return -parseFactor()
            var x: Double
            val startPos = pos
            if (eat('('.code)) {
                x = parseExpression()
                eat(')'.code)
            } else if (ch >= '0'.code && ch <= '9'.code || ch == '.'.code) {
                while (ch >= '0'.code && ch <= '9'.code || ch == '.'.code) nextChar()
                x = expr.substring(startPos, pos).toDouble()
            } else {
                throw RuntimeException("Unexpected: " + ch.toChar())
            }
            return x
        }
    }.parse()
}
