package com.example.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.AppTopBar
import com.example.ui.components.NotebookPage
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppDictionary.tr
import com.example.ui.viewmodel.AppLanguage
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.CalculationViewModel

data class PyqQuestion(
    val qEn: String,
    val qHi: String,
    val options: List<String>,
    val correct: String,
    val trickEn: String,
    val trickHi: String
)

val pyqList = listOf(
    PyqQuestion(
        "If x + 1/x = 5, what is the value of x³ + 1/x³?",
        "यदि x + 1/x = 5 है, तो x³ + 1/x³ का मान क्या है?",
        listOf("110", "125", "115", "140"),
        "110",
        "Formula: k³ - 3k = 125 - 15 = 110. Trick: If plus, subtract 3 times.",
        "सूत्र: k³ - 3k = 125 - 15 = 110. ट्रिक: अगर प्लस है, तो 3 गुना घटाएं।"
    ),
    PyqQuestion(
        "A dealer marks his goods 20% above cost price and allows a discount of 10%. Find his gain percent.",
        "एक डीलर अपने माल पर लागत मूल्य से 20% अधिक अंकित करता है और 10% की छूट देता है। उसका लाभ प्रतिशत ज्ञात कीजिए।",
        listOf("10%", "8%", "12%", "6%"),
        "8%",
        "Successive change: +20 - 10 - (20*10)/100 = 10 - 2 = 8%.",
        "क्रमिक परिवर्तन: +20 - 10 - (20*10)/100 = 10 - 2 = 8%."
    ),
    PyqQuestion(
        "The LCM of two numbers is 864 and their HCF is 144. If one number is 288, the other number is?",
        "दो संख्याओं का LCM 864 है और उनका HCF 144 है। यदि एक संख्या 288 है, तो दूसरी संख्या क्या है?",
        listOf("432", "576", "144", "384"),
        "432",
        "Product of numbers = LCM * HCF. -> x = (864 * 144) / 288 = 432.",
        "संख्याओं का गुणनफल = LCM * HCF. -> x = (864 * 144) / 288 = 432."
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PyqTestsScreen(viewModel: CalculationViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val lang = uiState.appLanguage
    var expandedIndex by remember { mutableStateOf<Int?>(null) }

    Scaffold(
        topBar = {
            AppTopBar(
                title = tr("pyq_test_title", lang),
                subtitle = tr("pyq_test_subtitle", lang),
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
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(pyqList.size) { index ->
                val q = pyqList[index]
                val isExpanded = expandedIndex == index
                
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surface,
                    border = androidx.compose.foundation.BorderStroke(1.dp, PrimaryIndigo.copy(alpha = 0.2f)),
                    modifier = Modifier.fillMaxWidth().clickable {
                        expandedIndex = if (isExpanded) null else index
                    }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Q${index + 1}. " + if (lang == AppLanguage.HINDI) q.qHi else q.qEn,
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            q.options.forEach { opt ->
                                Surface(
                                    color = MaterialTheme.colorScheme.surfaceVariant,
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = opt,
                                        modifier = Modifier.padding(8.dp),
                                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                        
                        if (isExpanded) {
                            Spacer(modifier = Modifier.height(16.dp))
                            NotebookPage(
                                title = "Solution (Ans: ${q.correct})",
                                text = if (lang == AppLanguage.HINDI) q.trickHi else q.trickEn
                            )
                        }
                    }
                }
            }
        }
    }
}
