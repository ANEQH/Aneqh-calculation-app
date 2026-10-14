package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.repository.SscCglSolvers
import com.example.ui.components.AppTopBar
import com.example.ui.components.DetailedSolverResultCard
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppLanguage
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.CalculationViewModel

enum class PercentageMode(val title: String, val modeId: Int, val desc: String) {
    X_PERCENT_OF_Y("x% of y", 1, "सीधा प्रतिशत मान"),
    X_IS_WHAT_PERCENT_OF_Y("x is what % of y", 2, "प्रतिशत तुलना"),
    PERCENT_INCREASE("Percentage Increase", 3, "प्रतिशत वृद्धि"),
    PERCENT_DECREASE("Percentage Decrease", 4, "प्रतिशत कमी"),
    SUCCESSIVE_INCREASE("Successive Increase", 5, "क्रमागत वृद्धि"),
    SUCCESSIVE_DISCOUNT("Successive Discount", 6, "क्रमागत छूट (बट्टा)"),
    REVERSE_PERCENTAGE("Reverse Percentage", 7, "प्रारंभिक मान ज्ञात करना"),
    INCREASE_THEN_DECREASE("Increase then Decrease", 8, "पहले वृद्धि फिर कमी"),
    PROFIT_PERCENT("Profit %", 9, "लाभ प्रतिशत"),
    LOSS_PERCENT("Loss %", 10, "हानि प्रतिशत"),
    DISCOUNT_PERCENT("Discount %", 11, "छूट प्रतिशत"),
    MARKS_PERCENT("Marks %", 12, "प्राप्तांक प्रतिशत")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PercentageHubView() {
    var selectedMode by remember { mutableStateOf(PercentageMode.X_PERCENT_OF_Y) }
    var inputAText by remember { mutableStateOf("20") }
    var inputBText by remember { mutableStateOf("80") }
    var result by remember { mutableStateOf<SscCglSolvers.DetailedSolverResult?>(null) }

    LaunchedEffect(selectedMode, inputAText, inputBText) {
        val a = inputAText.toDoubleOrNull()
        val b = inputBText.toDoubleOrNull()
        if (a != null && b != null) {
            result = SscCglSolvers.solvePercentageHub(selectedMode.modeId, a, b)
        } else {
            result = null
        }
    }

    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        // Mode Selector Chips
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(PercentageMode.values()) { mode ->
                val isSelected = selectedMode == mode
                FilterChip(
                    selected = isSelected,
                    onClick = {
                        selectedMode = mode
                        // Provide realistic starter numbers based on mode
                        when (mode) {
                            PercentageMode.X_PERCENT_OF_Y -> { inputAText = "20"; inputBText = "80" }
                            PercentageMode.X_IS_WHAT_PERCENT_OF_Y -> { inputAText = "20"; inputBText = "80" }
                            PercentageMode.PERCENT_INCREASE -> { inputAText = "25"; inputBText = "400" }
                            PercentageMode.PERCENT_DECREASE -> { inputAText = "15"; inputBText = "600" }
                            PercentageMode.SUCCESSIVE_INCREASE -> { inputAText = "20"; inputBText = "10" }
                            PercentageMode.SUCCESSIVE_DISCOUNT -> { inputAText = "20"; inputBText = "10" }
                            PercentageMode.REVERSE_PERCENTAGE -> { inputAText = "20"; inputBText = "120" }
                            PercentageMode.INCREASE_THEN_DECREASE -> { inputAText = "20"; inputBText = "10" }
                            PercentageMode.PROFIT_PERCENT -> { inputAText = "500"; inputBText = "650" }
                            PercentageMode.LOSS_PERCENT -> { inputAText = "500"; inputBText = "420" }
                            PercentageMode.DISCOUNT_PERCENT -> { inputAText = "800"; inputBText = "640" }
                            PercentageMode.MARKS_PERCENT -> { inputAText = "165"; inputBText = "200" }
                        }
                    },
                    label = { Text(mode.title, fontSize = 12.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = PrimaryIndigo,
                        selectedLabelColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
            }
        }

        // Input Fields Card
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface,
            border = androidx.compose.foundation.BorderStroke(1.dp, PrimaryIndigo.copy(alpha = 0.25f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = "${selectedMode.title} • ${selectedMode.desc}",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = PrimaryIndigo
                )

                val labelA = when (selectedMode) {
                    PercentageMode.X_PERCENT_OF_Y -> "Percentage x (%)"
                    PercentageMode.X_IS_WHAT_PERCENT_OF_Y -> "Numerator x"
                    PercentageMode.PERCENT_INCREASE, PercentageMode.PERCENT_DECREASE -> "Rate (%)"
                    PercentageMode.SUCCESSIVE_INCREASE, PercentageMode.SUCCESSIVE_DISCOUNT -> "First % (a)"
                    PercentageMode.REVERSE_PERCENTAGE -> "Increase % (x)"
                    PercentageMode.INCREASE_THEN_DECREASE -> "Increase % (+a)"
                    PercentageMode.PROFIT_PERCENT, PercentageMode.LOSS_PERCENT -> "Cost Price (CP)"
                    PercentageMode.DISCOUNT_PERCENT -> "Marked Price (MP)"
                    PercentageMode.MARKS_PERCENT -> "Marks Obtained"
                }

                val labelB = when (selectedMode) {
                    PercentageMode.X_PERCENT_OF_Y -> "Base Number y"
                    PercentageMode.X_IS_WHAT_PERCENT_OF_Y -> "Denominator y"
                    PercentageMode.PERCENT_INCREASE, PercentageMode.PERCENT_DECREASE -> "Original Number"
                    PercentageMode.SUCCESSIVE_INCREASE, PercentageMode.SUCCESSIVE_DISCOUNT -> "Second % (b)"
                    PercentageMode.REVERSE_PERCENTAGE -> "Final Value (after increase)"
                    PercentageMode.INCREASE_THEN_DECREASE -> "Decrease % (-b)"
                    PercentageMode.PROFIT_PERCENT, PercentageMode.LOSS_PERCENT, PercentageMode.DISCOUNT_PERCENT -> "Selling Price (SP)"
                    PercentageMode.MARKS_PERCENT -> "Total Maximum Marks"
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = inputAText,
                        onValueChange = { inputAText = it },
                        label = { Text(labelA, fontSize = 12.sp) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f).testTag("percent_input_a"),
                        shape = RoundedCornerShape(12.dp)
                    )
                    OutlinedTextField(
                        value = inputBText,
                        onValueChange = { inputBText = it },
                        label = { Text(labelB, fontSize = 12.sp) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f).testTag("percent_input_b"),
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            }
        }

        // Detailed Result Card
        if (result != null) {
            DetailedSolverResultCard(result = result!!)
        }
    }
}
