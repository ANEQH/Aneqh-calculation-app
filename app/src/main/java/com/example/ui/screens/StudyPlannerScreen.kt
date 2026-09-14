package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppLanguage
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.CalculationViewModel
import java.util.Calendar

data class ExamInfo(
    val name: String,
    val targetScore: String,
    val examMonth: String,
    val daysLeft: Int,
    val badgeColor: Color
)

data class RapidRevisionFormula(
    val title: String,
    val category: String,
    val formula: String,
    val example: String,
    val shortcutTip: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudyPlannerScreen(
    viewModel: CalculationViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val lang = uiState.appLanguage
    val isHindi = lang == AppLanguage.HINDI

    var showAddTargetDialog by remember { mutableStateOf(false) }
    var newTargetTitle by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Practice") }

    val exams = remember {
        listOf(
            ExamInfo("SSC CGL Tier-1", "Target: 160+/200", "Sept/Oct", 42, PrimaryIndigo),
            ExamInfo("SSC CHSL Tier-1", "Target: 155+/200", "July/Aug", 28, AccentEmerald),
            ExamInfo("SSC CPO (SI)", "Target: 145+/200", "Nov", 78, AccentAmber),
            ExamInfo("RRB NTPC (Railway)", "Target: 85+/100", "Dec", 110, AccentCyan),
            ExamInfo("IBPS PO / Clerk", "Target: 75+/100", "Oct", 56, AccentRose)
        )
    }

    val rapidFormulas = remember {
        listOf(
            RapidRevisionFormula(
                title = "Successive Percentage Change (क्रमागत प्रतिशत)",
                category = "Arithmetic",
                formula = "Net Change = (a + b + ab/100) %",
                example = "Increase 20% then 10% = 20 + 10 + 200/100 = 32% increase",
                shortcutTip = "For two discounts: d1 + d2 - (d1 * d2)/100"
            ),
            RapidRevisionFormula(
                title = "Alligation Rule (मिश्रण अनुपात)",
                category = "Arithmetic",
                formula = "Ratio = (Cheaper - Mean) / (Mean - Dearer)",
                example = "Rice at ₹40 and ₹60 mixed to sell at ₹54 => Ratio = (60-54)/(54-40) = 6:14 = 3:7",
                shortcutTip = "Use for Speed-Time-Distance, Profit-Loss, and Simple Interest!"
            ),
            RapidRevisionFormula(
                title = "Pythagorean Triplets (पाइथागोरस त्रिक)",
                category = "Geometry",
                formula = "a² + b² = c² (Key pairs: 3-4-5, 5-12-13, 7-24-25, 8-15-17, 9-40-41, 11-60-61, 20-21-29)",
                example = "If hypotenuse = 65 and one side = 39 => Multiplier of 5-12-13 x 3 => other side = 60",
                shortcutTip = "90% of SSC mensuration right triangle questions use these triplets directly."
            ),
            RapidRevisionFormula(
                title = "Euler's Cubic Identity (यूलर का सर्वसमिका)",
                category = "Algebra",
                formula = "a³ + b³ + c³ - 3abc = (a + b + c)(a² + b² + c² - ab - bc - ca)",
                example = "If a + b + c = 0, then a³ + b³ + c³ = 3abc!",
                shortcutTip = "Alternate form: 1/2(a+b+c)[(a-b)² + (b-c)² + (c-a)²]"
            ),
            RapidRevisionFormula(
                title = "Tangent-Secant Theorem (स्पर्शरेखा प्रमेय)",
                category = "Geometry",
                formula = "PT² = PA × PB",
                example = "PT is tangent from P to circle, P-A-B is a secant line cutting circle at A and B.",
                shortcutTip = "If PA = 4 cm and AB = 5 cm, then PB = 9 cm, PT² = 4 × 9 = 36 => PT = 6 cm."
            )
        )
    }

    // Add Target Dialog
    if (showAddTargetDialog) {
        AlertDialog(
            onDismissRequest = { showAddTargetDialog = false },
            title = {
                Text(
                    text = if (isHindi) "नया दैनिक लक्ष्य जोड़ें" else "Add Daily Study Target",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = newTargetTitle,
                        onValueChange = { newTargetTitle = it },
                        label = { Text(if (isHindi) "लक्ष्य विवरण (उदा. 20 प्रतिशत प्रश्न)" else "Target Description") },
                        placeholder = { Text("e.g. Solve 25 Geometry Qs") },
                        modifier = Modifier.fillMaxWidth().testTag("new_target_title_input")
                    )

                    Text(
                        text = if (isHindi) "श्रेणी चुनें:" else "Select Category:",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.SemiBold
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        listOf("Practice", "Speed", "Focus", "Revision").forEach { cat ->
                            val isSel = selectedCategory == cat
                            FilterChip(
                                selected = isSel,
                                onClick = { selectedCategory = cat },
                                label = { Text(cat, fontSize = 11.sp) }
                            )
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (newTargetTitle.isNotBlank()) {
                            viewModel.addDailyTarget(newTargetTitle, selectedCategory)
                            newTargetTitle = ""
                            showAddTargetDialog = false
                        }
                    },
                    modifier = Modifier.testTag("confirm_add_target_button")
                ) {
                    Text(if (isHindi) "जोड़ें" else "Add Goal")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddTargetDialog = false }) {
                    Text(if (isHindi) "रद्द करें" else "Cancel")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = if (isHindi) "स्टडी प्लानर व काउंटडाउन" else "Study Planner & Countdown",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = if (isHindi) "दैनिक लक्ष्य व पॉकेट नोट्स" else "Daily Targets & Aspirant Tracker",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = { viewModel.navigateTo(AppScreen.HOME) },
                        modifier = Modifier.testTag("planner_back_button")
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.navigateTo(AppScreen.FOCUS_CLOCK) },
                        modifier = Modifier.testTag("planner_open_clock_button")
                    ) {
                        Icon(Icons.Default.Timer, contentDescription = "Focus Clock", tint = PrimaryIndigo)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        },
        modifier = modifier
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(top = 12.dp, bottom = 80.dp)
        ) {

            // Section: Upcoming Exam Countdown
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (isHindi) "⏳ परीक्षा काउंटडाउन (Days Left)" else "⏳ Exam Countdown",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = if (isHindi) "SSC / Railways 2024-25" else "Target Exam Dates",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(exams) { exam ->
                            Surface(
                                shape = RoundedCornerShape(18.dp),
                                color = MaterialTheme.colorScheme.surface,
                                border = androidx.compose.foundation.BorderStroke(1.2.dp, exam.badgeColor.copy(alpha = 0.35f)),
                                modifier = Modifier
                                    .width(180.dp)
                                    .clip(RoundedCornerShape(18.dp))
                            ) {
                                Column(
                                    modifier = Modifier
                                        .background(
                                            Brush.verticalGradient(
                                                listOf(
                                                    exam.badgeColor.copy(alpha = 0.12f),
                                                    Color.Transparent
                                                )
                                            )
                                        )
                                        .padding(14.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = exam.name,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp,
                                            maxLines = 1
                                        )
                                    }

                                    Row(
                                        verticalAlignment = Alignment.Bottom,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Text(
                                            text = "${exam.daysLeft}",
                                            fontSize = 28.sp,
                                            fontWeight = FontWeight.Black,
                                            color = exam.badgeColor
                                        )
                                        Text(
                                            text = if (isHindi) "दिन शेष" else "days left",
                                            fontSize = 12.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            modifier = Modifier.padding(bottom = 4.dp)
                                        )
                                    }

                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = exam.badgeColor.copy(alpha = 0.15f)
                                    ) {
                                        Text(
                                            text = exam.targetScore,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = exam.badgeColor,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Section: Daily Study Targets Checklist
            item {
                val targets = uiState.dailyTargets
                val completedCount = targets.count { it.isCompleted }
                val totalCount = targets.size
                val progressFraction = if (totalCount > 0) completedCount.toFloat() / totalCount.toFloat() else 0f

                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = MaterialTheme.colorScheme.surface,
                    border = androidx.compose.foundation.BorderStroke(1.2.dp, PrimaryIndigo.copy(alpha = 0.35f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = if (isHindi) "🎯 आज के अध्ययन लक्ष्य" else "🎯 Today's Study Targets",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                                )
                                Text(
                                    text = if (isHindi) "$completedCount में से $totalCount पूरे हुए (${(progressFraction * 100).toInt()}%)" else "$completedCount of $totalCount Completed (${(progressFraction * 100).toInt()}%)",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = if (completedCount == totalCount && totalCount > 0) AccentEmerald else PrimaryIndigo,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }

                            FilledTonalButton(
                                onClick = { showAddTargetDialog = true },
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                                modifier = Modifier.testTag("add_target_fab")
                            ) {
                                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(if (isHindi) "नया लक्ष्य" else "Add Goal", fontSize = 12.sp)
                            }
                        }

                        // Progress Indicator
                        LinearProgressIndicator(
                            progress = progressFraction,
                            color = if (progressFraction >= 1f) AccentEmerald else PrimaryIndigo,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(CircleShape)
                        )

                        // Target Item Checklist
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            targets.forEach { target ->
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = if (target.isCompleted) AccentEmerald.copy(alpha = 0.08f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { viewModel.toggleDailyTarget(target.id) }
                                ) {
                                    Row(
                                        modifier = Modifier.padding(10.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Checkbox(
                                                checked = target.isCompleted,
                                                onCheckedChange = { viewModel.toggleDailyTarget(target.id) },
                                                colors = CheckboxDefaults.colors(checkedColor = AccentEmerald),
                                                modifier = Modifier.testTag("target_checkbox_${target.id}")
                                            )
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Column {
                                                Text(
                                                    text = target.title,
                                                    style = MaterialTheme.typography.bodyMedium.copy(
                                                        fontWeight = if (target.isCompleted) FontWeight.Normal else FontWeight.SemiBold,
                                                        textDecoration = if (target.isCompleted) TextDecoration.LineThrough else TextDecoration.None
                                                    ),
                                                    color = if (target.isCompleted) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface
                                                )
                                                Surface(
                                                    shape = RoundedCornerShape(4.dp),
                                                    color = PrimaryIndigo.copy(alpha = 0.1f)
                                                ) {
                                                    Text(
                                                        text = target.category,
                                                        fontSize = 10.sp,
                                                        fontWeight = FontWeight.Medium,
                                                        color = PrimaryIndigo,
                                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                                    )
                                                }
                                            }
                                        }

                                        IconButton(
                                            onClick = { viewModel.removeDailyTarget(target.id) },
                                            modifier = Modifier.size(32.dp).testTag("delete_target_${target.id}")
                                        ) {
                                            Icon(
                                                Icons.Default.Close,
                                                contentDescription = "Delete",
                                                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Section: Launch Focus Session Banner
            item {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = MaterialTheme.colorScheme.surface,
                    border = androidx.compose.foundation.BorderStroke(1.dp, AccentAmber.copy(alpha = 0.4f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .clickable { viewModel.navigateTo(AppScreen.FOCUS_CLOCK) }
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                Brush.horizontalGradient(
                                    listOf(
                                        AccentAmber.copy(alpha = 0.15f),
                                        PrimaryIndigo.copy(alpha = 0.15f)
                                    )
                                )
                            )
                            .padding(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(14.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Surface(
                                    shape = CircleShape,
                                    color = AccentAmber.copy(alpha = 0.2f),
                                    modifier = Modifier.size(46.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(Icons.Default.HourglassTop, contentDescription = null, tint = AccentAmber)
                                    }
                                }
                                Column {
                                    Text(
                                        text = if (isHindi) "फोकस मोड क्लॉक शुरू करें" else "Start Pomodoro Focus Session",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp
                                    )
                                    Text(
                                        text = if (isHindi) "अल्फा वेव्स ऑडियो व डीप अध्ययन" else "Alpha waves audio & zero distraction",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                            Icon(Icons.Default.ArrowForward, contentDescription = null, tint = AccentAmber)
                        }
                    }
                }
            }

            // Section: High-Yield Rapid Revision Pocketbook (स्मृति पत्र)
            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (isHindi) "📖 रैपिड रिवीजन फॉर्मूला पॉकेटबुक" else "📖 Rapid Revision Formulas",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = if (isHindi) "हाई-यील्ड ट्रिक्स" else "Top SSC Hacks",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    rapidFormulas.forEach { formula ->
                        var isExpanded by remember { mutableStateOf(false) }

                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = MaterialTheme.colorScheme.surface,
                            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { isExpanded = !isExpanded }
                        ) {
                            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Surface(
                                            shape = RoundedCornerShape(6.dp),
                                            color = PrimaryIndigo.copy(alpha = 0.12f)
                                        ) {
                                            Text(
                                                text = formula.category,
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = PrimaryIndigo,
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                        }
                                        Text(
                                            text = formula.title,
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 14.sp
                                        )
                                    }

                                    Icon(
                                        imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }

                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = formula.formula,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = PrimaryIndigo,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                    )
                                }

                                AnimatedVisibility(visible = isExpanded) {
                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(6.dp),
                                        modifier = Modifier.padding(top = 6.dp)
                                    ) {
                                        Text(
                                            text = "💡 उदाहरण: ${formula.example}",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Text(
                                            text = "⚡ शॉर्टकट: ${formula.shortcutTip}",
                                            style = MaterialTheme.typography.bodySmall,
                                            fontWeight = FontWeight.SemiBold,
                                            color = AccentEmerald
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
