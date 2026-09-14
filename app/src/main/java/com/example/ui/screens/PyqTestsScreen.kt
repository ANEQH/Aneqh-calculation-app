package com.example.ui.screens

import kotlin.random.Random
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

// --- Added for PYQ Generator ---
fun generatePyqQuestions(chapter: PyqChapter, count: Int, startId: Int): List<PyqQuestion> {
    val list = mutableListOf<PyqQuestion>()
    val exams = listOf("SSC CGL 2023 Tier-1", "SSC CHSL 2022", "SSC CPO 2023", "SSC CGL 2022 Tier-2", "SSC MTS 2023")
    
    for (i in 0 until count) {
        val type = if (chapter == PyqChapter.ALL) PyqChapter.values().filter { it != PyqChapter.ALL }.random() else chapter
        val exam = exams.random()
        val id = (startId + i).toString()
        
        when (type) {
            PyqChapter.ALGEBRA -> {
                val k = Random.nextInt(3, 10)
                val isPlus = Random.nextBoolean()
                if (isPlus) {
                    val ans = k * k * k - 3 * k
                    val options = mutableSetOf(ans, ans + 6, ans - 6, ans + 2)
                    while(options.size < 4) options.add(ans + Random.nextInt(1, 10))
                    val optList = options.toList().shuffled()
                    list.add(PyqQuestion(id, type, exam,
                        "If x + 1/x = $k, what is the value of x³ + 1/x³?",
                        "यदि x + 1/x = $k है, तो x³ + 1/x³ का मान क्या है?",
                        optList.map { it.toString() }, ans.toString(),
                        "Formula: k³ - 3k = $k³ - 3($k) = $ans. Trick: If plus is given, subtract 3 times.",
                        "सूत्र: k³ - 3k = $k³ - 3($k) = $ans. ट्रिक: अगर प्लस दिया है, तो 3 गुना घटाएं।"
                    ))
                } else {
                    val ans = k * k + 2
                    val options = mutableSetOf(ans, ans - 4, ans + 4, k*k - 2)
                    while(options.size < 4) options.add(ans + Random.nextInt(1, 10))
                    val optList = options.toList().shuffled()
                    list.add(PyqQuestion(id, type, exam,
                        "If x - 1/x = $k, what is the value of x² + 1/x²?",
                        "यदि x - 1/x = $k है, तो x² + 1/x² का मान क्या है?",
                        optList.map { it.toString() }, ans.toString(),
                        "Formula: k² + 2 = $k² + 2 = $ans. Trick: If minus is given, add 2.",
                        "सूत्र: k² + 2 = $k² + 2 = $ans. ट्रिक: अगर माइनस दिया है, तो 2 जोड़ें।"
                    ))
                }
            }
            PyqChapter.ARITHMETIC -> {
                val cp = Random.nextInt(10, 50) * 100
                val markup = Random.nextInt(2, 6) * 10
                val discount = Random.nextInt(1, 3) * 10
                val net = markup - discount - (markup * discount) / 100
                
                val options = mutableSetOf(net, net + 2, net - 2, net + 4)
                while(options.size < 4) options.add(net + Random.nextInt(1, 5))
                val optList = options.toList().shuffled()
                
                list.add(PyqQuestion(id, type, exam,
                    "A dealer marks his goods $markup% above cost price and allows a discount of $discount%. Find his gain percent.",
                    "एक डीलर अपने माल पर लागत मूल्य से $markup% अधिक अंकित करता है और $discount% की छूट देता है। उसका लाभ प्रतिशत ज्ञात कीजिए।",
                    optList.map { "$it%" }, "$net%",
                    "Successive change: a + b + (ab/100) = $markup - $discount - ($markup*$discount)/100 = $net%.",
                    "क्रमिक परिवर्तन: a + b + (ab/100) = +$markup - $discount - ($markup*$discount)/100 = $net%."
                ))
            }
            PyqChapter.GEOMETRY -> {
                val sides = Random.nextInt(5, 12)
                val ans = (sides - 2) * 180
                val options = mutableSetOf(ans, ans + 180, ans - 180, ans + 360)
                while(options.size < 4) options.add(ans + Random.nextInt(1, 5)*180)
                val optList = options.toList().shuffled()
                
                list.add(PyqQuestion(id, type, exam,
                    "What is the sum of the interior angles of a polygon with $sides sides?",
                    "$sides भुजाओं वाले बहुभुज के आंतरिक कोणों का योग क्या है?",
                    optList.map { "$it°" }, "$ans°",
                    "Formula: (n-2) × 180° = ($sides - 2) × 180° = $ans°.",
                    "सूत्र: (n-2) × 180° = ($sides - 2) × 180° = $ans°."
                ))
            }
            PyqChapter.TRIGONOMETRY -> {
                val dist = Random.nextInt(2, 10) * 10
                val angles = listOf(30, 45, 60)
                val angle = angles.random()
                val (ans, trickEn) = when (angle) {
                    30 -> Pair("$dist/√3 m", "For 30°, height = base/√3")
                    45 -> Pair("$dist m", "For 45°, height = base")
                    else -> Pair("$dist√3 m", "For 60°, height = base * √3")
                }
                
                val optList = listOf("$dist m", "$dist√3 m", "$dist/√3 m", "${dist*2} m").shuffled()
                
                list.add(PyqQuestion(id, type, exam,
                    "If the angle of elevation of a tower from a distance of ${dist}m from its foot is $angle°, then the height of the tower is:",
                    "यदि किसी टावर के आधार से ${dist}m की दूरी से उसका उन्नयन कोण $angle° है, तो टावर की ऊंचाई क्या है?",
                    optList, ans,
                    "tan($angle°) = Height / Base, Height = $dist × tan($angle°) = $ans. Trick: $trickEn.",
                    "tan($angle°) = ऊंचाई / आधार, ऊंचाई = $dist × tan($angle°) = $ans. ट्रिक: $trickEn."
                ))
            }
            else -> {}
        }
    }
    return list
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PyqTestsScreen(viewModel: CalculationViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val lang = uiState.appLanguage
    var selectedTab by remember { mutableStateOf(PyqChapter.ALL) }
    var answerSheetMode by remember { mutableStateOf(false) }
    
    // Hold endless pyq list
    val pyqList = remember { mutableStateListOf<PyqQuestion>() }
    
    // Load initial batch
    LaunchedEffect(selectedTab) {
        pyqList.clear()
        pyqList.addAll(generatePyqQuestions(selectedTab, 50, 1))
    }

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
                items(pyqList.size) { index ->
                    PyqQuizCard(
                        question = pyqList[index],
                        index = index + 1,
                        lang = lang,
                        answerSheetMode = answerSheetMode
                    )
                    
                    // Endless generation when reaching near bottom
                    if (index == pyqList.size - 5) {
                        LaunchedEffect(index) {
                            val more = generatePyqQuestions(selectedTab, 50, pyqList.size + 1)
                            pyqList.addAll(more)
                        }
                    }
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
