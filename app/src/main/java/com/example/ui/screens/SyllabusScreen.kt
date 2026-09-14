package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

data class SyllabusTopic(val name: String, val weightage: String, val type: String)

val sscSyllabus = listOf(
    SyllabusTopic("Number System", "2-3 Qs", "Arithmetic"),
    SyllabusTopic("Percentage", "1-2 Qs", "Arithmetic"),
    SyllabusTopic("Ratio & Proportion", "1-2 Qs", "Arithmetic"),
    SyllabusTopic("Average", "1-2 Qs", "Arithmetic"),
    SyllabusTopic("Simple & Compound Interest", "2 Qs", "Arithmetic"),
    SyllabusTopic("Profit, Loss & Discount", "2-3 Qs", "Arithmetic"),
    SyllabusTopic("Time & Work", "1-2 Qs", "Arithmetic"),
    SyllabusTopic("Time, Speed & Distance", "1-2 Qs", "Arithmetic"),
    SyllabusTopic("Mixture & Alligation", "1 Qs", "Arithmetic"),
    SyllabusTopic("Algebra", "3-4 Qs", "Advanced"),
    SyllabusTopic("Geometry", "3-4 Qs", "Advanced"),
    SyllabusTopic("Mensuration (2D & 3D)", "2-3 Qs", "Advanced"),
    SyllabusTopic("Trigonometry", "2-3 Qs", "Advanced"),
    SyllabusTopic("Data Interpretation (DI)", "3-4 Qs", "Advanced"),
    SyllabusTopic("Statistics & Probability", "1-2 Qs", "Advanced (Mains)")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SyllabusScreen(viewModel: CalculationViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val lang = uiState.appLanguage

    Scaffold(
        topBar = {
            AppTopBar(
                title = "SSC Math Syllabus",
                subtitle = "Complete Chapter-wise Breakdown",
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
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 100.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    text = "Arithmetic & Advanced Topics",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            
            items(sscSyllabus) { topic ->
                val isAdvanced = topic.type.contains("Advanced")
                val tint = if (isAdvanced) AccentRose else PrimaryIndigo
                
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surface,
                    border = androidx.compose.foundation.BorderStroke(1.dp, tint.copy(alpha = 0.2f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = tint.copy(alpha = 0.1f),
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Book,
                                contentDescription = null,
                                tint = tint,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = topic.name,
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Expected: ${topic.weightage} • ${topic.type}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = AccentEmerald.copy(alpha = 0.5f)
                        )
                    }
                }
            }
        }
    }
}
