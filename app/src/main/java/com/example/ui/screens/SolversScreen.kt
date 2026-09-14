package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.repository.CalculationSolvers
import com.example.ui.components.AppTopBar
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.CalculationViewModel

enum class SolverType(val title: String, val icon: String) {
    PYQ_SUGAR_PRICE("PYQ: Price & Cons.", "trending_down"),
    PYQ_ELECTION("PYQ: Election", "how_to_vote"),
    PYQ_PASS_FAIL("PYQ: Pass/Fail", "school"),
    CROSS_MULTIPLY("Cross Multiply", "close"),
    SQUARE_CUBE("Square & Cube", "superscript"),
    NET_PERCENTAGE("Net % Change", "percent"),
    CI_SI_RATES("CI & SI Net Rate", "account_balance"),
    TIME_WORK("Time & Work", "engineering"),
    LCM_HCF("LCM & HCF", "hub"),
    VEDIC_SUBTRACTION("Base 1000 Sub", "remove")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SolversScreen(
    viewModel: CalculationViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedSolver by remember { mutableStateOf(SolverType.CROSS_MULTIPLY) }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "10x Mental Solvers",
                subtitle = "Step-by-step Inspector method calculator",
                streak = uiState.userStats.currentStreak,
                showBackButton = true,
                onBackClick = { viewModel.navigateTo(AppScreen.HOME) },
                onStatsClick = { viewModel.navigateTo(AppScreen.STATS) }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(top = 8.dp, bottom = 100.dp)
        ) {
            // Solver Selector Chips
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(vertical = 4.dp)
                ) {
                    items(SolverType.values()) { solver ->
                        val isSelected = selectedSolver == solver
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedSolver = solver },
                            label = { Text(solver.title, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = PrimaryIndigo,
                                selectedLabelColor = Color.White
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.testTag("solver_chip_${solver.name}")
                        )
                    }
                }
            }

            // Active Solver Card
            item {
                when (selectedSolver) {
                    SolverType.PYQ_SUGAR_PRICE -> PyqSugarPriceSolverView()
                    SolverType.PYQ_ELECTION -> PyqElectionSolverView()
                    SolverType.PYQ_PASS_FAIL -> PyqPassFailSolverView()
                    SolverType.CROSS_MULTIPLY -> CrossMultiplySolverView()
                    SolverType.SQUARE_CUBE -> SquareCubeSolverView()
                    SolverType.NET_PERCENTAGE -> NetPercentageSolverView()
                    SolverType.CI_SI_RATES -> CiSiSolverView()
                    SolverType.TIME_WORK -> TimeWorkSolverView()
                    SolverType.LCM_HCF -> LcmHcfSolverView()
                    SolverType.VEDIC_SUBTRACTION -> VedicSubSolverView()
                }
            }
        }
    }
}

@Composable
private fun CrossMultiplySolverView() {
    var num1Text by remember { mutableStateOf("47") }
    var num2Text by remember { mutableStateOf("23") }
    var result by remember { mutableStateOf<CalculationSolvers.SolverResult?>(null) }

    LaunchedEffect(num1Text, num2Text) {
        val n1 = num1Text.toIntOrNull()
        val n2 = num2Text.toIntOrNull()
        if (n1 != null && n2 != null && n1 in 10..99 && n2 in 10..99) {
            result = CalculationSolvers.solveCrossMultiplication(n1, n2)
        } else {
            result = null
        }
    }

    SolverCardWrapper(
        title = "2-Digit Cross Multiplication (कैंची गुणा)",
        description = "Instant Vedic algorithm for any 2-digit by 2-digit multiplication."
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = num1Text,
                onValueChange = { if (it.length <= 2) num1Text = it },
                label = { Text("Number 1") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f).testTag("cross_num1_input"),
                shape = RoundedCornerShape(12.dp)
            )
            OutlinedTextField(
                value = num2Text,
                onValueChange = { if (it.length <= 2) num2Text = it },
                label = { Text("Number 2") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f).testTag("cross_num2_input"),
                shape = RoundedCornerShape(12.dp)
            )
        }

        if (result != null) {
            SolverResultDisplay(result = result!!)
        }
    }
}

@Composable
private fun SquareCubeSolverView() {
    var numText by remember { mutableStateOf("42") }
    var isCube by remember { mutableStateOf(false) }
    var result by remember { mutableStateOf<CalculationSolvers.SolverResult?>(null) }

    LaunchedEffect(numText, isCube) {
        val n = numText.toIntOrNull()
        if (n != null && n in 1..99) {
            result = if (isCube) CalculationSolvers.solveCube(n) else CalculationSolvers.solveSquare(n)
        } else {
            result = null
        }
    }

    SolverCardWrapper(
        title = if (isCube) "2-Digit Cube Solver (घन)" else "2-Digit Square Solver (वर्ग)",
        description = "Calculate powers mentally using polynomial expansions."
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = numText,
                onValueChange = { if (it.length <= 2) numText = it },
                label = { Text("Number (1-99)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f).testTag("sq_num_input"),
                shape = RoundedCornerShape(12.dp)
            )

            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                FilterChip(
                    selected = !isCube,
                    onClick = { isCube = false },
                    label = { Text("x²") },
                    shape = RoundedCornerShape(10.dp)
                )
                FilterChip(
                    selected = isCube,
                    onClick = { isCube = true },
                    label = { Text("x³") },
                    shape = RoundedCornerShape(10.dp)
                )
            }
        }

        if (result != null) {
            SolverResultDisplay(result = result!!)
        }
    }
}

@Composable
private fun NetPercentageSolverView() {
    var xText by remember { mutableStateOf("10") }
    var yText by remember { mutableStateOf("20") }
    var xIncrease by remember { mutableStateOf(true) }
    var yIncrease by remember { mutableStateOf(true) }
    var result by remember { mutableStateOf<CalculationSolvers.SolverResult?>(null) }

    LaunchedEffect(xText, yText, xIncrease, yIncrease) {
        val x = xText.toDoubleOrNull()
        val y = yText.toDoubleOrNull()
        if (x != null && y != null) {
            result = CalculationSolvers.solveNetPercentage(x, y, xIncrease, yIncrease)
        } else {
            result = null
        }
    }

    SolverCardWrapper(
        title = "Net Percentage Change (x + y + xy/100)",
        description = "Calculate combined successive percentage increase/decrease."
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                OutlinedTextField(
                    value = xText,
                    onValueChange = { xText = it },
                    label = { Text("First %") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = xIncrease, onClick = { xIncrease = true })
                    Text("↑ Inc", fontSize = 12.sp)
                    Spacer(modifier = Modifier.width(4.dp))
                    RadioButton(selected = !xIncrease, onClick = { xIncrease = false })
                    Text("↓ Dec", fontSize = 12.sp)
                }
            }

            Column(modifier = Modifier.weight(1f)) {
                OutlinedTextField(
                    value = yText,
                    onValueChange = { yText = it },
                    label = { Text("Second %") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = yIncrease, onClick = { yIncrease = true })
                    Text("↑ Inc", fontSize = 12.sp)
                    Spacer(modifier = Modifier.width(4.dp))
                    RadioButton(selected = !yIncrease, onClick = { yIncrease = false })
                    Text("↓ Dec", fontSize = 12.sp)
                }
            }
        }

        if (result != null) {
            SolverResultDisplay(result = result!!)
        }
    }
}

@Composable
private fun CiSiSolverView() {
    var rateText by remember { mutableStateOf("10") }
    var yearsText by remember { mutableStateOf("2") }
    var result by remember { mutableStateOf<CalculationSolvers.SolverResult?>(null) }

    LaunchedEffect(rateText, yearsText) {
        val r = rateText.toDoubleOrNull()
        val y = yearsText.toDoubleOrNull()
        if (r != null && y != null && y > 0) {
            result = CalculationSolvers.solveCompoundInterest(r, y)
        } else {
            result = null
        }
    }

    SolverCardWrapper(
        title = "CI Net Rate & (CI - SI) Difference",
        description = "Effective CI rate formulas (2 yrs: 2a+a²/100, 3 yrs: 3a.3a²a³)."
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedTextField(
                value = rateText,
                onValueChange = { rateText = it },
                label = { Text("Rate % p.a.") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = yearsText,
                onValueChange = { yearsText = it },
                label = { Text("Years (e.g. 2 or 3)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1f)
            )
        }

        if (result != null) {
            SolverResultDisplay(result = result!!)
        }
    }
}

@Composable
private fun TimeWorkSolverView() {
    var numText by remember { mutableStateOf("2") }
    var denText by remember { mutableStateOf("5") }
    var daysText by remember { mutableStateOf("10") }
    var result by remember { mutableStateOf<CalculationSolvers.SolverResult?>(null) }

    LaunchedEffect(numText, denText, daysText) {
        val n = numText.toIntOrNull()
        val d = denText.toIntOrNull()
        val days = daysText.toDoubleOrNull()
        if (n != null && d != null && days != null && n > 0 && d > 0) {
            result = CalculationSolvers.solveTimeWork(n, d, days)
        } else {
            result = null
        }
    }

    SolverCardWrapper(
        title = "Time & Work Fractional Days Converter",
        description = "If (a/b) work takes D days, find total days to finish complete work."
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = numText,
                onValueChange = { numText = it },
                label = { Text("Num (a)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = denText,
                onValueChange = { denText = it },
                label = { Text("Den (b)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = daysText,
                onValueChange = { daysText = it },
                label = { Text("Days (D)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1.2f)
            )
        }

        if (result != null) {
            SolverResultDisplay(result = result!!)
        }
    }
}

@Composable
private fun LcmHcfSolverView() {
    var aText by remember { mutableStateOf("24") }
    var bText by remember { mutableStateOf("36") }
    var result by remember { mutableStateOf<CalculationSolvers.SolverResult?>(null) }

    LaunchedEffect(aText, bText) {
        val a = aText.toLongOrNull()
        val b = bText.toLongOrNull()
        if (a != null && b != null && a > 0 && b > 0) {
            result = CalculationSolvers.solveLcmHcf(a, b)
        } else {
            result = null
        }
    }

    SolverCardWrapper(
        title = "LCM & HCF Step-by-Step Solver",
        description = "Find Highest Common Factor and Least Common Multiple instantly."
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedTextField(
                value = aText,
                onValueChange = { aText = it },
                label = { Text("Number A") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = bText,
                onValueChange = { bText = it },
                label = { Text("Number B") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1f)
            )
        }

        if (result != null) {
            SolverResultDisplay(result = result!!)
        }
    }
}

@Composable
private fun VedicSubSolverView() {
    var baseText by remember { mutableStateOf("1000") }
    var subText by remember { mutableStateOf("343") }
    var resultSteps by remember { mutableStateOf<List<String>>(emptyList()) }
    var ans by remember { mutableStateOf("") }

    LaunchedEffect(baseText, subText) {
        val base = baseText.toLongOrNull()
        val sub = subText.toLongOrNull()
        if (base != null && sub != null && base > sub) {
            val diff = base - sub
            ans = diff.toString()
            resultSteps = listOf(
                "Vedic Sutra: निखिलं नवतश्चरमं दशतः (All from 9 and last from 10)",
                "Subtract every digit of $sub from 9 except the last unit digit from 10",
                "Answer: $base - $sub = $diff"
            )
        } else {
            resultSteps = emptyList()
            ans = ""
        }
    }

    SolverCardWrapper(
        title = "Base Subtraction (100 / 1000 / 10000 - N)",
        description = "Subtract from power of 10 instantly without borrowing."
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedTextField(
                value = baseText,
                onValueChange = { baseText = it },
                label = { Text("Base (e.g. 1000)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = subText,
                onValueChange = { subText = it },
                label = { Text("Subtract (N)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1f)
            )
        }

        if (ans.isNotEmpty()) {
            SolverResultDisplay(
                result = CalculationSolvers.SolverResult(
                    finalAnswer = ans,
                    methodTitle = "All from 9, last from 10",
                    steps = resultSteps,
                    mentalNote = "सीधा बाईं से दाईं ओर लिखें।"
                )
            )
        }
    }
}

@Composable
private fun SolverCardWrapper(
    title: String,
    description: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.surface,
        border = androidx.compose.foundation.BorderStroke(1.dp, PrimaryIndigo.copy(alpha = 0.25f)),
        tonalElevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            content()
        }
    }
}

@Composable
private fun SolverResultDisplay(result: CalculationSolvers.SolverResult) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = PrimaryIndigo.copy(alpha = 0.08f),
        border = androidx.compose.foundation.BorderStroke(1.dp, PrimaryIndigo.copy(alpha = 0.3f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = result.methodTitle,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = PrimaryIndigo
                )
                Surface(
                    color = AccentEmerald.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "= ${result.finalAnswer}",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = FontFamily.Monospace,
                            color = AccentEmerald
                        ),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
            }

            Divider(color = PrimaryIndigo.copy(alpha = 0.2f), thickness = 1.dp)

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                result.steps.forEach { step ->
                    Text(
                        text = step,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 13.sp,
                            fontFamily = FontFamily.Monospace
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Surface(
                color = AccentAmber.copy(alpha = 0.15f),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Bolt,
                        contentDescription = null,
                        tint = AccentOrange,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = result.mentalNote,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Medium,
                            fontSize = 12.sp
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}


@Composable
private fun PyqSugarPriceSolverView() {
    var rateText by remember { mutableStateOf("25") }
    var isIncrease by remember { mutableStateOf(true) }
    var result by remember { mutableStateOf<CalculationSolvers.SolverResult?>(null) }

    LaunchedEffect(rateText, isIncrease) {
        val rate = rateText.toDoubleOrNull()
        if (rate != null && rate > 0) {
            result = CalculationSolvers.solvePyqPriceConsumption(rate, isIncrease)
        } else {
            result = null
        }
    }

    SolverCardWrapper(
        title = "SSC CGL PYQ: Price & Consumption",
        description = "Change the % rate dynamically to solve expenditure questions."
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = rateText,
                onValueChange = { rateText = it },
                label = { Text("Rate Change %") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1f)
            )
            
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = isIncrease,
                        onClick = { isIncrease = true },
                        colors = RadioButtonDefaults.colors(selectedColor = AccentEmerald)
                    )
                    Text("Increase (वृद्धि)", fontSize = 12.sp)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = !isIncrease,
                        onClick = { isIncrease = false },
                        colors = RadioButtonDefaults.colors(selectedColor = AccentRose)
                    )
                    Text("Decrease (कमी)", fontSize = 12.sp)
                }
            }
        }
        if (result != null) {
            SolverResultDisplay(result = result!!)
        }
    }
}

@Composable
private fun PyqElectionSolverView() {
    var winnerText by remember { mutableStateOf("60") }
    var majorityText by remember { mutableStateOf("400") }
    var result by remember { mutableStateOf<CalculationSolvers.SolverResult?>(null) }

    LaunchedEffect(winnerText, majorityText) {
        val wPercent = winnerText.toDoubleOrNull()
        val mVotes = majorityText.toDoubleOrNull()
        if (wPercent != null && mVotes != null && wPercent > 0) {
            result = CalculationSolvers.solvePyqElection(wPercent, mVotes)
        } else {
            result = null
        }
    }

    SolverCardWrapper(
        title = "SSC CGL PYQ: Election Votes",
        description = "Input Winner % and winning margin to find Total Votes."
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedTextField(
                value = winnerText,
                onValueChange = { winnerText = it },
                label = { Text("Winner %") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = majorityText,
                onValueChange = { majorityText = it },
                label = { Text("Majority Votes") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1f)
            )
        }
        if (result != null) {
            SolverResultDisplay(result = result!!)
        }
    }
}

@Composable
private fun PyqPassFailSolverView() {
    var failPercentText by remember { mutableStateOf("30") }
    var failMarksText by remember { mutableStateOf("15") }
    var passPercentText by remember { mutableStateOf("40") }
    var extraMarksText by remember { mutableStateOf("35") }
    var result by remember { mutableStateOf<CalculationSolvers.SolverResult?>(null) }

    LaunchedEffect(failPercentText, failMarksText, passPercentText, extraMarksText) {
        val fPct = failPercentText.toDoubleOrNull()
        val fMk = failMarksText.toDoubleOrNull()
        val pPct = passPercentText.toDoubleOrNull()
        val pMk = extraMarksText.toDoubleOrNull()
        if (fPct != null && fMk != null && pPct != null && pMk != null) {
            result = CalculationSolvers.solvePyqPassFail(fPct, fMk, pPct, pMk)
        } else {
            result = null
        }
    }

    SolverCardWrapper(
        title = "SSC CGL PYQ: Pass/Fail Marks",
        description = "Student 1 fails by X marks. Student 2 gets more than pass marks."
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedTextField(
                    value = failPercentText,
                    onValueChange = { failPercentText = it },
                    label = { Text("Student 1 %") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = failMarksText,
                    onValueChange = { failMarksText = it },
                    label = { Text("Fails By Marks") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.weight(1f)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedTextField(
                    value = passPercentText,
                    onValueChange = { passPercentText = it },
                    label = { Text("Student 2 %") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = extraMarksText,
                    onValueChange = { extraMarksText = it },
                    label = { Text("Passes By Marks") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.weight(1f)
                )
            }
        }
        if (result != null) {
            SolverResultDisplay(result = result!!)
        }
    }
}
