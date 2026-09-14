package com.example.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.AppTopBar
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.CalculationViewModel
import kotlin.random.Random

enum class GymCategory(val label: String) {
    TABLES("Tables (x)"),
    SQUARE("Squares (x²)"),
    CUBE("Cubes (x³)"),
    ADDITION("Addition (+)"),
    SUBTRACTION("Sub (-)"),
    FRACTIONS("Fractions (%)")
}

enum class GymDifficulty(val label: String) {
    EASY("Easy Mode"),
    HARD("Hard Mode")
}

data class GymQuestion(
    val id: Int,
    val text: String,
    val options: List<String>,
    val correct: String,
    val smartTip: String = ""
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MathGymScreen(viewModel: CalculationViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val lang = uiState.appLanguage
    
    var category by remember { mutableStateOf(GymCategory.TABLES) }
    var difficulty by remember { mutableStateOf(GymDifficulty.EASY) }
    
    val questions = remember { mutableStateListOf<GymQuestion>() }
    
    LaunchedEffect(category, difficulty) {
        questions.clear()
        for (i in 1..50) questions.add(generateGymQuestion(i, category, difficulty))
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Math Brain Gym",
                subtitle = "Fast Calculation Practice",
                streak = uiState.userStats.currentStreak,
                showBackButton = true,
                onBackClick = { viewModel.navigateTo(AppScreen.HOME) },
                onThemeToggle = { viewModel.toggleTheme() },
                onLanguageToggle = { viewModel.toggleLanguage() },
                currentLanguage = lang
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            // Filters
            Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)) {
                // Category
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(GymCategory.values().size) { index ->
                        val cat = GymCategory.values()[index]
                        FilterChip(
                            selected = category == cat,
                            onClick = { category = cat },
                            label = { Text(cat.label, fontWeight = FontWeight.Bold) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = PrimaryIndigo,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                // Difficulty
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    GymDifficulty.values().forEach { diff ->
                        FilterChip(
                            selected = difficulty == diff,
                            onClick = { difficulty = diff },
                            label = { Text(diff.label, fontWeight = FontWeight.Bold) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = if (diff == GymDifficulty.EASY) AccentEmerald else AccentRose,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }
            }
            
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                contentPadding = PaddingValues(top = 8.dp, bottom = 100.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                itemsIndexed(questions) { index, q ->
                    GymCard(q, index + 1) { isCorrect ->
                        if (isCorrect) viewModel.submitDrillAnswer(true)
                    }
                    if (index == questions.size - 5) {
                        LaunchedEffect(index) {
                            val currentSize = questions.size
                            for (i in 1..50) questions.add(generateGymQuestion(currentSize + i, category, difficulty))
                        }
                    }
                }
            }
        }
    }
}

fun generateGymQuestion(id: Int, category: GymCategory, difficulty: GymDifficulty): GymQuestion {
    var text = ""
    var correct = ""
    var tip = ""
    val optionsSet = mutableSetOf<String>()

    when (category) {
        GymCategory.SQUARE -> {
            val n = if (difficulty == GymDifficulty.EASY) Random.nextInt(2, 26) else Random.nextInt(26, 100)
            val ans = n * n
            text = "$n² = ?"
            correct = ans.toString()
            optionsSet.add(correct)
            while (optionsSet.size < 4) {
                val fake = ans + Random.nextInt(-15, 15) * n.let { if (it == 0) 5 else it }
                if (fake > 0 && fake != ans) optionsSet.add(fake.toString())
            }
            tip = when {
                n % 10 == 5 -> "Ending in 5: ${n/10} × ${n/10 + 1} = ${(n/10)*(n/10 + 1)}, append 25 → $ans"
                n in 40..60 -> "Base 50: 25 + (${n-50}) = ${25 + n - 50} | (${n-50})² = $ans"
                n in 90..110 -> "Base 100: $n + (${n-100}) = ${n + n - 100} | (${n-100})² = $ans"
                else -> "Identity: ($n)² = ${(n/10)*10}² + 2(${n/10*10})(${n%10}) + ${n%10}² = $ans"
            }
        }
        GymCategory.CUBE -> {
            val n = if (difficulty == GymDifficulty.EASY) Random.nextInt(2, 11) else Random.nextInt(11, 25)
            val ans = n * n * n
            text = "$n³ = ?"
            correct = ans.toString()
            optionsSet.add(correct)
            while (optionsSet.size < 4) {
                val fake = ans + Random.nextInt(-10, 10) * n.let { if (it == 0) 2 else it }
                if (fake > 0 && fake != ans) optionsSet.add(fake.toString())
            }
            tip = "SSC Core Cube: $n × $n = ${n*n}, ${n*n} × $n = $ans. Memorize cubes up to 25!"
        }
        GymCategory.TABLES -> {
            val n1 = if (difficulty == GymDifficulty.EASY) Random.nextInt(2, 16) else Random.nextInt(12, 31)
            val n2 = if (difficulty == GymDifficulty.EASY) Random.nextInt(2, 11) else Random.nextInt(11, 26)
            val ans = n1 * n2
            text = "$n1 × $n2 = ?"
            correct = ans.toString()
            optionsSet.add(correct)
            while (optionsSet.size < 4) {
                val fake = ans + Random.nextInt(-20, 20).let { if (it == 0) 5 else it }
                if (fake > 0) optionsSet.add(fake.toString())
            }
            tip = "Split & Add: ${(n1/10)*10} × $n2 (${(n1/10)*10 * n2}) + ${n1%10} × $n2 (${(n1%10) * n2}) = $ans"
        }
        GymCategory.ADDITION -> {
            val n1 = if (difficulty == GymDifficulty.EASY) Random.nextInt(10, 100) else Random.nextInt(100, 1000)
            val n2 = if (difficulty == GymDifficulty.EASY) Random.nextInt(10, 100) else Random.nextInt(100, 1000)
            val ans = n1 + n2
            text = "$n1 + $n2 = ?"
            correct = ans.toString()
            optionsSet.add(correct)
            while (optionsSet.size < 4) {
                val fake = ans + Random.nextInt(-20, 20).let { if (it == 0) 5 else it }
                if (fake > 0) optionsSet.add(fake.toString())
            }
            tip = "Left-to-Right Addition: Add major place values first, then append unit adjustments."
        }
        GymCategory.SUBTRACTION -> {
            val n1 = if (difficulty == GymDifficulty.EASY) Random.nextInt(20, 100) else Random.nextInt(200, 1000)
            val n2 = if (difficulty == GymDifficulty.EASY) Random.nextInt(10, n1) else Random.nextInt(100, n1)
            val ans = n1 - n2
            text = "$n1 - $n2 = ?"
            correct = ans.toString()
            optionsSet.add(correct)
            while (optionsSet.size < 4) {
                val fake = ans + Random.nextInt(-20, 20).let { if (it == 0) 5 else it }
                if (fake > 0) optionsSet.add(fake.toString())
            }
            tip = "Distance Method: Jump from $n2 to nearest tens/hundreds, then reach $n1."
        }
        GymCategory.FRACTIONS -> {
            val easyFractions = listOf(
                "1/2" to "50%", "1/3" to "33.33%", "1/4" to "25%", "1/5" to "20%",
                "3/4" to "75%", "2/5" to "40%", "1/10" to "10%", "2/3" to "66.66%"
            )
            val hardFractions = listOf(
                "1/6" to "16.66%", "1/7" to "14.28%", "1/8" to "12.5%", "1/9" to "11.11%",
                "1/11" to "9.09%", "1/12" to "8.33%", "3/8" to "37.5%", "5/8" to "62.5%",
                "5/6" to "83.33%", "7/8" to "87.5%", "4/9" to "44.44%"
            )
            val listToUse = if (difficulty == GymDifficulty.EASY) easyFractions else hardFractions
            val pair = listToUse.random()
            text = "${pair.first} = ?"
            correct = pair.second
            optionsSet.add(correct)
            
            val allPercentVals = (easyFractions + hardFractions).map { it.second }.toMutableList()
            allPercentVals.remove(correct)
            allPercentVals.shuffle()
            
            optionsSet.add(allPercentVals[0])
            optionsSet.add(allPercentVals[1])
            optionsSet.add(allPercentVals[2])
            tip = "Direct SSC Tier-1 standard conversion: ${pair.first} = ${pair.second}."
        }
    }
    
    return GymQuestion(id, text, optionsSet.toList().shuffled(), correct, tip)
}

@Composable
fun GymCard(q: GymQuestion, qNumber: Int, onAnswer: (Boolean) -> Unit) {
    var selectedOption by remember { mutableStateOf<String?>(null) }
    
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface,
        border = androidx.compose.foundation.BorderStroke(1.dp, PrimaryIndigo.copy(alpha = 0.15f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Surface(color = PrimaryIndigo.copy(alpha = 0.1f), shape = RoundedCornerShape(8.dp)) {
                    Text("Drill $qNumber", modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp), color = PrimaryIndigo, fontWeight = FontWeight.Bold)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = q.text,
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(16.dp))
            
            q.options.forEach { opt ->
                val isSelected = selectedOption == opt
                val isSubmitted = selectedOption != null
                val isCorrect = opt == q.correct
                
                val bgColor = when {
                    isSubmitted && isCorrect -> AccentEmerald.copy(alpha = 0.15f)
                    isSubmitted && isSelected && !isCorrect -> AccentRose.copy(alpha = 0.1f)
                    else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                }
                
                val borderColor = when {
                    isSubmitted && isCorrect -> AccentEmerald
                    isSubmitted && isSelected && !isCorrect -> AccentRose
                    else -> Color.Transparent
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = bgColor,
                    border = androidx.compose.foundation.BorderStroke(1.dp, borderColor),
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp).clip(RoundedCornerShape(12.dp))
                        .clickable(enabled = !isSubmitted) {
                            selectedOption = opt
                            onAnswer(opt == q.correct)
                        }
                ) {
                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = if (isSubmitted && isCorrect) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                            contentDescription = null,
                            tint = if (isSubmitted && isCorrect) AccentEmerald else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = opt,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            if (selectedOption != null && q.smartTip.isNotEmpty()) {
                Spacer(modifier = Modifier.height(10.dp))
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = AccentAmber.copy(alpha = 0.12f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, AccentAmber.copy(alpha = 0.3f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Lightbulb, contentDescription = null, tint = AccentOrange, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "💡 ${q.smartTip}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = AccentOrange
                        )
                    }
                }
            }
        }
    }
}
