import re

with open("app/src/main/java/com/example/ui/screens/SolversScreen.kt", "r") as f:
    content = f.read()

old_display = """@Composable
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
            HorizontalDivider(color = PrimaryIndigo.copy(alpha = 0.2f), thickness = 1.dp)
            result.steps.forEachIndexed { index, step ->
                Row(crossAxisAlignment = Alignment.Top) {
                    Text(
                        text = "${index + 1}.",
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                        color = PrimaryIndigo,
                        modifier = Modifier.width(20.dp)
                    )
                    Text(
                        text = step,
                        style = MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily.Monospace),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            if (result.mentalNote.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = AccentAmber.copy(alpha = 0.1f)
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Lightbulb, contentDescription = null, tint = AccentAmber, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = result.mentalNote,
                            style = MaterialTheme.typography.labelSmall,
                            color = AccentOrange
                        )
                    }
                }
            }
        }
    }
}"""

new_display = """@Composable
private fun SolverResultDisplay(result: CalculationSolvers.SolverResult) {
    val stepsText = result.steps.mapIndexed { index, step -> "${index + 1}. $step" }.joinToString("\\n")
    val fullText = stepsText + if (result.mentalNote.isNotEmpty()) "\\n\\n💡 Note: ${result.mentalNote}" else ""
    
    com.example.ui.components.NotebookPage(
        title = "${result.methodTitle} (Ans: ${result.finalAnswer})",
        text = fullText,
        modifier = Modifier.fillMaxWidth()
    )
}"""

content = content.replace("Divider(", "HorizontalDivider(") # First fix the deprecated Divider if it exists
content = content.replace(old_display, new_display)

with open("app/src/main/java/com/example/ui/screens/SolversScreen.kt", "w") as f:
    f.write(content)
