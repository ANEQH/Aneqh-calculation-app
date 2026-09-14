package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.TextStyle

@Composable
fun NotebookPage(
    text: String,
    title: String = "Step-by-Step Solution",
    modifier: Modifier = Modifier
) {
    val paperColor = Color(0xFFFDFBF7) // White-ish paper color
    val blueLineColor = Color(0xFF90CAF9).copy(alpha = 0.5f)
    val redMarginColor = Color(0xFFEF9A9A).copy(alpha = 0.8f)
    val inkColor = Color(0xFF0F52BA) // Classic blue pen ink
    
    // We assume roughly 24.sp line height translates to about 28.dp height visually depending on density.
    // To keep it simple, we draw lines every 28.dp and set the text line height to 28.sp.
    val lineSpacingDp = 28.dp
    
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(paperColor)
            .fillMaxWidth()
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            
            // Draw horizontal ruled lines
            var y = lineSpacingDp.toPx()
            while (y < canvasHeight) {
                drawLine(
                    color = blueLineColor,
                    start = Offset(0f, y),
                    end = Offset(canvasWidth, y),
                    strokeWidth = 1f
                )
                y += lineSpacingDp.toPx()
            }
            
            // Draw vertical red margins
            drawLine(
                color = redMarginColor,
                start = Offset(40.dp.toPx(), 0f),
                end = Offset(40.dp.toPx(), canvasHeight),
                strokeWidth = 2f
            )
            drawLine(
                color = redMarginColor,
                start = Offset(44.dp.toPx(), 0f),
                end = Offset(44.dp.toPx(), canvasHeight),
                strokeWidth = 1f
            )
        }
        
        Column(
            modifier = Modifier.padding(start = 56.dp, top = 16.dp, end = 16.dp, bottom = 24.dp)
        ) {
            Text(
                text = title,
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color(0xFFB71C1C) // Red ink for title
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = text,
                style = TextStyle(
                    fontFamily = FontFamily.Cursive, // Using cursive for handwriting feel
                    fontSize = 20.sp,
                    color = inkColor,
                    lineHeight = 28.sp,
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}
