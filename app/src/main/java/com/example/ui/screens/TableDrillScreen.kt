package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
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
import com.example.ui.theme.AccentEmerald
import com.example.ui.theme.AccentRose
import com.example.ui.theme.PrimaryIndigo
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.CalculationViewModel
import kotlinx.coroutines.delay
import kotlin.random.Random

data class DrillQuestion(
    val id: Int,
    val num1: Int,
    val num2: Int,
    val options: List<Int>,
    val correct: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TableDrillScreen(viewModel: CalculationViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val lang = uiState.appLanguage
    
    var difficulty by remember { mutableStateOf("Easy") }
    val questions = remember { mutableStateListOf<DrillQuestion>() }
    
    LaunchedEffect(difficulty) {
        questions.clear()
        for (i in 1..50) {
            questions.add(generateDrillQuestion(i, difficulty))
        }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Table Drill",
                subtitle = "Infinite Multiplication Test",
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
            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("Easy", "Hard").forEach { diff ->
                    FilterChip(
                        selected = difficulty == diff,
                        onClick = { difficulty = diff },
                        label = { Text(diff + " Mode", fontWeight = FontWeight.Bold) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = if(diff == "Easy") AccentEmerald else AccentRose,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                contentPadding = PaddingValues(top = 16.dp, bottom = 100.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                itemsIndexed(questions) { index, q ->
                    DrillCard(q, index + 1) { isCorrect ->
                        if (isCorrect) viewModel.submitDrillAnswer(true)
                    }
                    
                    if (index == questions.size - 5) {
                        LaunchedEffect(index) {
                            val currentSize = questions.size
                            for (i in 1..50) {
                                questions.add(generateDrillQuestion(currentSize + i, difficulty))
                            }
                        }
                    }
                }
            }
        }
    }
}

fun generateDrillQuestion(id: Int, difficulty: String = "Easy"): DrillQuestion {
    val n1 = if (difficulty == "Easy") Random.nextInt(2, 16) else Random.nextInt(16, 31)
    val n2 = if (difficulty == "Easy") Random.nextInt(2, 11) else Random.nextInt(11, 21)
    val correct = n1 * n2
    
    val optionsSet = mutableSetOf(correct)
    while (optionsSet.size < 4) {
        val fakeN1 = Random.nextInt(2, 31)
        val fakeN2 = Random.nextInt(2, 21)
        val fakeAns = fakeN1 * fakeN2
        if (fakeAns != correct && fakeAns > 0) {
            optionsSet.add(fakeAns)
        }
    }
    
    return DrillQuestion(
        id = id,
        num1 = n1,
        num2 = n2,
        options = optionsSet.toList().shuffled(),
        correct = correct
    )
}

@Composable
fun DrillCard(q: DrillQuestion, qNumber: Int, onAnswer: (Boolean) -> Unit) {
    var selectedOption by remember { mutableStateOf<Int?>(null) }
    
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface,
        border = androidx.compose.foundation.BorderStroke(1.dp, PrimaryIndigo.copy(alpha = 0.15f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Surface(color = PrimaryIndigo.copy(alpha = 0.1f), shape = RoundedCornerShape(8.dp)) {
                    Text("Q$qNumber", modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp), color = PrimaryIndigo, fontWeight = FontWeight.Bold)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "${q.num1} × ${q.num2} = ?",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable(enabled = !isSubmitted) {
                            selectedOption = opt
                            onAnswer(opt == q.correct)
                        }
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = if (isSubmitted && isCorrect) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                            contentDescription = null,
                            tint = if (isSubmitted && isCorrect) AccentEmerald else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = opt.toString(),
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}
