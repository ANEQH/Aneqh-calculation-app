import re

with open("app/src/main/java/com/example/ui/screens/SolversScreen.kt", "r") as f:
    content = f.read()

# 1. Update Enum
old_enum = """enum class SolverType(val title: String, val icon: String) {
    CROSS_MULTIPLY("Cross Multiply", "close"),
    SQUARE_CUBE("Square & Cube", "superscript"),
    NET_PERCENTAGE("Net % Change", "percent"),
    CI_SI_RATES("CI & SI Net Rate", "account_balance"),
    TIME_WORK("Time & Work", "engineering"),
    LCM_HCF("LCM & HCF", "hub"),
    VEDIC_SUBTRACTION("Base 1000 Sub", "remove")
}"""

new_enum = """enum class SolverType(val title: String, val icon: String) {
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
}"""
content = content.replace(old_enum, new_enum)

# 2. Update the `when (selectedSolver)` block
old_when = """            when (selectedSolver) {
                SolverType.CROSS_MULTIPLY -> CrossMultiplySolverView()
                SolverType.SQUARE_CUBE -> SquareCubeSolverView()
                SolverType.NET_PERCENTAGE -> NetPercentageSolverView()
                SolverType.CI_SI_RATES -> CiSiSolverView()
                SolverType.TIME_WORK -> TimeWorkSolverView()
                SolverType.LCM_HCF -> LcmHcfSolverView()
                SolverType.VEDIC_SUBTRACTION -> VedicSubSolverView()
            }"""

new_when = """            when (selectedSolver) {
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
            }"""
content = content.replace(old_when, new_when)

# 3. Add the three new private functions at the bottom
new_functions = """

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
"""

with open("app/src/main/java/com/example/ui/screens/SolversScreen.kt", "a") as f:
    f.write(new_functions)

with open("app/src/main/java/com/example/ui/screens/SolversScreen.kt", "w") as f:
    f.write(content)

with open("app/src/main/java/com/example/ui/screens/SolversScreen.kt", "a") as f:
    f.write(new_functions)

