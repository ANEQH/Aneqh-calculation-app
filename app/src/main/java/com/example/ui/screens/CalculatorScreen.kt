package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.AppTopBar
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppDictionary.tr
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.CalculationViewModel
import com.example.util.SmartMathAnalysis
import com.example.util.SmartMathAssistant
import kotlin.math.sqrt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorScreen(viewModel: CalculationViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val lang = uiState.appLanguage

    var expression by remember { mutableStateOf("") }
    var displayText by remember { mutableStateOf("0") }
    var historyText by remember { mutableStateOf("") }
    var isResultShown by remember { mutableStateOf(false) }
    var activeSmartAnalysis by remember { mutableStateOf<SmartMathAnalysis?>(null) }

    val buttons = listOf(
        "AC", "(", ")", "÷",
        "7", "8", "9", "×",
        "4", "5", "6", "-",
        "1", "2", "3", "+",
        "%", "0", ".", "="
    )

    fun onSpecialFunc(func: String) {
        val num = displayText.toDoubleOrNull() ?: return
        when (func) {
            "x²" -> {
                val res = num * num
                historyText = "(${displayText})² ="
                displayText = if (res == res.toLong().toDouble()) res.toLong().toString() else String.format("%.4f", res).trimEnd('0').trimEnd('.')
                isResultShown = true
                activeSmartAnalysis = SmartMathAssistant.analyze("${num.toLong()}^2")
            }
            "√x" -> {
                if (num >= 0) {
                    val res = sqrt(num)
                    historyText = "√(${displayText}) ="
                    displayText = if (res == res.toLong().toDouble()) res.toLong().toString() else String.format("%.4f", res).trimEnd('0').trimEnd('.')
                    isResultShown = true
                    activeSmartAnalysis = SmartMathAssistant.analyze("sqrt ${num.toLong()}")
                }
            }
            "x³" -> {
                val res = num * num * num
                historyText = "(${displayText})³ ="
                displayText = if (res == res.toLong().toDouble()) res.toLong().toString() else String.format("%.4f", res).trimEnd('0').trimEnd('.')
                isResultShown = true
                activeSmartAnalysis = SmartMathAssistant.analyze("${num.toLong()}^3")
            }
            "1/x" -> {
                if (num != 0.0) {
                    val res = 1.0 / num
                    historyText = "1/(${displayText}) ="
                    displayText = if (res == res.toLong().toDouble()) res.toLong().toString() else String.format("%.4f", res).trimEnd('0').trimEnd('.')
                    isResultShown = true
                    activeSmartAnalysis = SmartMathAssistant.analyze("1/${num.toLong()}")
                }
            }
            "DR" -> {
                val v = num.toLong()
                val dr = SmartMathAssistant.digitalRoot(v)
                historyText = "Digital Root($v) ="
                displayText = dr.toString()
                isResultShown = true
                activeSmartAnalysis = SmartMathAnalysis(
                    query = "Digital Root of $v",
                    result = "$dr",
                    title = "🔍 Digital Root (बीजांक): $dr",
                    method = "Sum of Digits mod 9",
                    explanationSteps = listOf(
                        "Add all digits of $v repeatedly until a single digit remains.",
                        "If sum = 9 or multiple of 9, the digital root is 9.",
                        "Use this to verify any addition, multiplication, or square in 1 second!"
                    ),
                    mentalShortcut = "Digital sum of LHS must equal digital sum of RHS in SSC calculations."
                )
            }
        }
    }

    fun onBtnClick(btn: String) {
        when (btn) {
            "AC" -> {
                displayText = "0"
                expression = ""
                historyText = ""
                isResultShown = false
                activeSmartAnalysis = null
            }
            "=" -> {
                try {
                    val fullExpr = if (expression.isNotEmpty() && !isResultShown) expression + displayText else displayText
                    historyText = "$fullExpr ="
                    val evalExpr = fullExpr
                        .replace("×", "*")
                        .replace("÷", "/")
                        .replace("%", "/100.0")

                    val result = calculateBasic(evalExpr)
                    displayText = if (result == result.toLong().toDouble()) {
                        result.toLong().toString()
                    } else {
                        String.format("%.4f", result).trimEnd('0').trimEnd('.')
                    }
                    expression = ""
                    isResultShown = true

                    // Smart Assistant Hook
                    activeSmartAnalysis = SmartMathAssistant.analyze(fullExpr)
                        ?: SmartMathAssistant.analyze("$fullExpr = $displayText")
                } catch (e: Exception) {
                    displayText = "Error"
                    isResultShown = true
                }
            }
            "+", "-", "×", "÷" -> {
                if (isResultShown) {
                    expression = "$displayText $btn "
                    isResultShown = false
                    displayText = "0"
                } else {
                    expression += if (displayText != "0") "$displayText $btn " else "$btn "
                    displayText = "0"
                }
                historyText = expression
            }
            "%" -> {
                try {
                    val v = displayText.toDouble() / 100.0
                    displayText = if (v == v.toLong().toDouble()) v.toLong().toString() else v.toString()
                } catch (e: Exception) {
                    displayText = "0"
                }
            }
            "(" -> {
                if (displayText == "0" || isResultShown) {
                    displayText = "("
                    isResultShown = false
                } else {
                    displayText += "("
                }
            }
            ")" -> {
                displayText += ")"
            }
            "." -> {
                if (isResultShown) {
                    displayText = "0."
                    isResultShown = false
                } else if (!displayText.contains(".")) {
                    displayText += "."
                }
            }
            else -> { // Numbers 0 to 9
                if (displayText == "0" || isResultShown || displayText == "Error") {
                    displayText = btn
                    isResultShown = false
                } else {
                    displayText += btn
                }
            }
        }
    }

    fun onBackspace() {
        if (!isResultShown && displayText.length > 1) {
            displayText = displayText.dropLast(1)
        } else {
            displayText = "0"
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
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            // Display Screen with Glassmorphism & High-Contrast
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(24.dp),
                border = androidx.compose.foundation.BorderStroke(1.2.dp, PrimaryIndigo.copy(alpha = 0.25f))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f),
                                    PrimaryIndigo.copy(alpha = 0.06f)
                                )
                            )
                        )
                        .padding(20.dp)
                ) {
                    // Top History & Backspace
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.TopCenter),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = historyText,
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                            maxLines = 1
                        )
                        IconButton(onClick = { onBackspace() }) {
                            Icon(
                                imageVector = Icons.Default.Backspace,
                                contentDescription = "Backspace",
                                tint = AccentRose,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }

                    // Main Numerical Value
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomEnd),
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = displayText,
                            fontSize = if (displayText.length > 9) 34.sp else 46.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.End,
                            maxLines = 2
                        )
                    }
                }
            }

            // Quick Exam Functions Toolbar
            Spacer(modifier = Modifier.height(10.dp))
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val specialFuncs = listOf("x²", "√x", "x³", "1/x", "DR")
                items(specialFuncs) { func ->
                    AssistChip(
                        onClick = { onSpecialFunc(func) },
                        label = {
                            Text(
                                text = func,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = PrimaryIndigo
                            )
                        },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = PrimaryIndigo.copy(alpha = 0.1f)
                        ),
                        border = androidx.compose.foundation.BorderStroke(1.dp, PrimaryIndigo.copy(alpha = 0.3f)),
                        modifier = Modifier.testTag("calc_func_$func")
                    )
                }
            }

            // Active Inspector Smart Math Breakdown Card
            AnimatedVisibility(visible = activeSmartAnalysis != null) {
                val analysis = activeSmartAnalysis
                if (analysis != null) {
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = MaterialTheme.colorScheme.surface,
                        border = androidx.compose.foundation.BorderStroke(1.2.dp, PrimaryIndigo.copy(alpha = 0.4f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .testTag("calc_smart_breakdown_card")
                    ) {
                        Column(
                            modifier = Modifier
                                .background(
                                    Brush.verticalGradient(
                                        listOf(
                                            PrimaryIndigo.copy(alpha = 0.1f),
                                            Color.Transparent
                                        )
                                    )
                                )
                                .padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "⚡ Inspector Shortcut",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = PrimaryIndigo
                                )
                                IconButton(
                                    onClick = { activeSmartAnalysis = null },
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Icon(Icons.Default.Close, contentDescription = "Close", modifier = Modifier.size(16.dp))
                                }
                            }

                            Text(
                                text = analysis.title,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            Text(
                                text = "💡 ${analysis.mentalShortcut}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = AccentOrange
                            )

                            if (analysis.digitalRootCheck != null) {
                                Text(
                                    text = "🔍 ${analysis.digitalRootCheck}",
                                    fontSize = 11.sp,
                                    color = AccentCyan,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Keypad Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(buttons) { btn ->
                    val isOperator = btn in listOf("÷", "×", "-", "+")
                    val isSpecial = btn in listOf("AC", "%", "(", ")")
                    val isEquals = btn == "="

                    val btnBg = when {
                        isEquals -> AccentEmerald
                        isOperator -> PrimaryIndigo
                        isSpecial -> AccentAmber.copy(alpha = 0.18f)
                        else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    }

                    val textColor = when {
                        isEquals || isOperator -> Color.White
                        isSpecial -> AccentOrange
                        else -> MaterialTheme.colorScheme.onSurface
                    }

                    Surface(
                        shape = RoundedCornerShape(18.dp),
                        color = btnBg,
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (isEquals || isOperator) Color.Transparent else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
                        ),
                        modifier = Modifier
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(18.dp))
                            .clickable { onBtnClick(btn) }
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = btn,
                                fontSize = if (btn.length > 1) 20.sp else 24.sp,
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
