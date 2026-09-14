package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.Done
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
import com.example.ui.components.NotebookPage
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppDictionary.tr
import com.example.ui.viewmodel.AppLanguage
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.CalculationViewModel

enum class PyqChapter { ALL, ALGEBRA, GEOMETRY, ARITHMETIC, TRIGONOMETRY }

data class PyqQuestion(
    val id: String,
    val chapter: PyqChapter,
    val examTag: String,
    val qEn: String,
    val qHi: String,
    val options: List<String>,
    val correct: String,
    val trickEn: String,
    val trickHi: String
)

val pyqList = listOf(
    PyqQuestion("1", PyqChapter.ALGEBRA, "SSC CGL 2023 Tier-1 (Shift 2)", "If x + 1/x = 5, what is the value of x³ + 1/x³?", "यदि x + 1/x = 5 है, तो x³ + 1/x³ का मान क्या है?", listOf("110", "125", "115", "140"), "110", "Formula: k³ - 3k = 125 - 15 = 110.\nTrick: If plus is given, subtract 3 times.", "सूत्र: k³ - 3k = 125 - 15 = 110.\nट्रिक: अगर प्लस दिया है, तो 3 गुना घटाएं।"),
    PyqQuestion("2", PyqChapter.ARITHMETIC, "SSC CGL 2022 Tier-2 (Mains)", "A dealer marks his goods 20% above cost price and allows a discount of 10%. Find his gain percent.", "एक डीलर अपने माल पर लागत मूल्य से 20% अधिक अंकित करता है और 10% की छूट देता है। उसका लाभ प्रतिशत ज्ञात कीजिए।", listOf("10%", "8%", "12%", "6%"), "8%", "Successive change:\nNet = a + b + (ab/100)\n= 20 - 10 - (20*10)/100\n= 10 - 2\n= 8%.", "क्रमिक परिवर्तन:\nनेट = a + b + (ab/100)\n= +20 - 10 - (20*10)/100\n= 10 - 2\n= 8%."),
    PyqQuestion("3", PyqChapter.ARITHMETIC, "SSC CPO 2023 Shift-1", "The LCM of two numbers is 864 and their HCF is 144. If one number is 288, the other number is?", "दो संख्याओं का LCM 864 है और उनका HCF 144 है। यदि एक संख्या 288 है, तो दूसरी संख्या क्या है?", listOf("432", "576", "144", "384"), "432", "Property: Product of numbers = LCM * HCF.\n\nx * 288 = 864 * 144\nx = (864 * 144) / 288\nx = 432.", "नियम: संख्याओं का गुणनफल = LCM * HCF.\n\nx * 288 = 864 * 144\nx = (864 * 144) / 288\nx = 432."),
    PyqQuestion("4", PyqChapter.ARITHMETIC, "SSC CGL 2021 Tier-1", "A man can row 15 km/hr in still water. It takes him twice as long to row up as to row down the river. Find the rate of stream.", "एक आदमी शांत जल में 15 किमी/घंटा की गति से नाव चला सकता है। उसे नदी में धारा के प्रतिकूल जाने में धारा के अनुकूल जाने से दोगुना समय लगता है। धारा की गति ज्ञात कीजिए।", listOf("3 km/hr", "4 km/hr", "5 km/hr", "6 km/hr"), "5 km/hr", "Let speed of stream = y.\nUpstream time = 2 * Downstream time\nD / (15 - y) = 2 * (D / (15 + y))\n15 + y = 30 - 2y\n3y = 15\ny = 5 km/hr.", "माना धारा की गति = y.\nप्रतिकूल समय = 2 * अनुकूल समय\nD / (15 - y) = 2 * (D / (15 + y))\n15 + y = 30 - 2y\n3y = 15\ny = 5 किमी/घंटा।"),
    PyqQuestion("5", PyqChapter.TRIGONOMETRY, "SSC CHSL 2023 Tier-1", "If the angle of elevation of a tower from a distance of 100m from its foot is 60°, then the height of the tower is:", "यदि किसी टावर के आधार से 100 मीटर की दूरी से उसका उन्नयन कोण 60° है, तो टावर की ऊंचाई क्या है?", listOf("100√3 m", "100/√3 m", "50√3 m", "200 m"), "100√3 m", "tan(60°) = Height / Base\n√3 = h / 100\nh = 100√3 m.\n\nTrick: For 30-60-90 triangle, side opposite 60° is √3 times the base.", "tan(60°) = ऊंचाई / आधार\n√3 = h / 100\nh = 100√3 मीटर।\n\nट्रिक: 30-60-90 त्रिकोण के लिए, 60° के सामने वाली भुजा आधार की √3 गुना होती है।"),
    PyqQuestion("6", PyqChapter.GEOMETRY, "SSC CGL 2022 Tier-1", "If the lengths of the diagonals of a rhombus are 24 cm and 10 cm, what is the perimeter of the rhombus?", "यदि एक समचतुर्भुज के विकर्णों की लंबाई 24 सेमी और 10 सेमी है, तो समचतुर्भुज का परिमाप क्या है?", listOf("52 cm", "60 cm", "68 cm", "48 cm"), "52 cm", "Diagonals of a rhombus bisect at 90°.\nSo, half diagonals are 12 and 5.\nBy Pythagoras, side = √(12² + 5²) = 13 cm.\nPerimeter = 4 * 13 = 52 cm.", "समचतुर्भुज के विकर्ण 90° पर समद्विभाजित करते हैं।\nआधे विकर्ण 12 और 5 हैं।\nपाइथागोरस से, भुजा = √(12² + 5²) = 13 सेमी।\nपरिमाप = 4 * 13 = 52 सेमी।")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PyqTestsScreen(viewModel: CalculationViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val lang = uiState.appLanguage
    var selectedTab by remember { mutableStateOf(PyqChapter.ALL) }
    var answerSheetMode by remember { mutableStateOf(false) }

    val filteredList = if (selectedTab == PyqChapter.ALL) pyqList else pyqList.filter { it.chapter == selectedTab }

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
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { answerSheetMode = !answerSheetMode },
                containerColor = if (answerSheetMode) AccentEmerald else PrimaryIndigo,
                contentColor = Color.White
            ) {
                Icon(if (answerSheetMode) Icons.Default.DoneAll else Icons.Default.CheckCircle, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = if (answerSheetMode) tr("answer_sheet_mode", lang) else tr("submit_test", lang), fontWeight = FontWeight.Bold)
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            
            // Chapter Tabs
            ScrollableTabRow(
                selectedTabIndex = PyqChapter.values().indexOf(selectedTab),
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = PrimaryIndigo,
                edgePadding = 16.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                PyqChapter.values().forEach { tab ->
                    val tabName = when(tab) {
                        PyqChapter.ALL -> "All"
                        PyqChapter.ALGEBRA -> tr("pyq_algebra", lang)
                        PyqChapter.GEOMETRY -> tr("pyq_geometry", lang)
                        PyqChapter.ARITHMETIC -> tr("pyq_arithmetic", lang)
                        PyqChapter.TRIGONOMETRY -> "Trigo"
                    }
                    Tab(
                        selected = selectedTab == tab,
                        onClick = { selectedTab = tab },
                        text = { Text(tabName, fontWeight = FontWeight.Bold) }
                    )
                }
            }
            
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                contentPadding = PaddingValues(top = 16.dp, bottom = 100.dp)
            ) {
                items(filteredList.size) { index ->
                    PyqQuizCard(
                        question = filteredList[index],
                        index = index + 1,
                        lang = lang,
                        answerSheetMode = answerSheetMode
                    )
                }
            }
        }
    }
}

@Composable
fun PyqQuizCard(question: PyqQuestion, index: Int, lang: AppLanguage, answerSheetMode: Boolean) {
    var selectedOption by remember { mutableStateOf<String?>(null) }
    var showConcept by remember { mutableStateOf(false) }
    
    // Auto show concepts if answer sheet mode is on
    val forceShowAnswer = answerSheetMode
    val activeShowConcept = showConcept || forceShowAnswer
    val activeSelected = if (forceShowAnswer) question.correct else selectedOption

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface,
        border = androidx.compose.foundation.BorderStroke(1.dp, PrimaryIndigo.copy(alpha = 0.15f)),
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            
            // Exam Tag (Top Right)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    color = PrimaryIndigo.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = "Q$index", modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp), color = PrimaryIndigo, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
                
                Surface(
                    color = AccentAmber.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = question.examTag, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp), color = AccentOrange, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))

            // Bilingual Question Display
            Text(
                text = question.qEn,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold, fontSize = 16.sp),
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = question.qHi,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 15.sp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Options List
            question.options.forEach { opt ->
                val isSelected = activeSelected == opt
                val isSubmitted = activeSelected != null || forceShowAnswer
                val isCorrect = opt == question.correct
                
                val bgColor = when {
                    forceShowAnswer && isCorrect -> AccentEmerald.copy(alpha = 0.15f)
                    !isSubmitted && isSelected -> PrimaryIndigo.copy(alpha = 0.1f)
                    isSubmitted && isCorrect -> AccentEmerald.copy(alpha = 0.15f)
                    isSubmitted && isSelected && !isCorrect -> AccentRose.copy(alpha = 0.1f)
                    else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                }
                
                val borderColor = when {
                    forceShowAnswer && isCorrect -> AccentEmerald
                    !isSubmitted && isSelected -> PrimaryIndigo
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
                        .clickable(enabled = !isSubmitted && !forceShowAnswer) {
                            selectedOption = opt
                            showConcept = true
                        }
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = if ((isSubmitted && isCorrect) || (forceShowAnswer && isCorrect)) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                            contentDescription = null,
                            tint = if ((isSubmitted && isCorrect) || (forceShowAnswer && isCorrect)) AccentEmerald else if (isSelected) PrimaryIndigo else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = opt,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            if (!forceShowAnswer && activeSelected != null) {
                // Concept Button (Manual Reveal)
                Button(
                    onClick = { showConcept = !showConcept },
                    colors = ButtonDefaults.buttonColors(containerColor = AccentAmber),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = tr("view_concept", lang),
                        color = Color(0xFF5B2200),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            
            // Concept & Trick View (Notebook)
            AnimatedVisibility(visible = activeShowConcept) {
                Column(modifier = Modifier.padding(top = 16.dp)) {
                    val solutionText = if (lang == AppLanguage.HINDI) question.trickHi else question.trickEn
                    NotebookPage(
                        title = "Solution (Ans: ${question.correct})",
                        text = solutionText,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}
