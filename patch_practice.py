import re

with open("app/src/main/java/com/example/ui/screens/PracticeQuestionScreen.kt", "r") as f:
    content = f.read()

old_solution = """            // Solution Step Breakdown Card
            AnimatedVisibility(
                visible = uiState.showExerciseSolution || uiState.isExerciseAnswerSubmitted,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = AccentAmber.copy(alpha = 0.1f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, AccentAmber.copy(alpha = 0.4f)),
                    modifier = Modifier.fillMaxWidth().testTag("practice_solution_card")
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = AccentEmerald,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Inspector's Mental Solution (Correct: (${currentProblem.correctOption}))",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = AccentOrange
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = currentProblem.solutionSteps,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontFamily = FontFamily.Monospace,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            ),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "💡 Tip: ${type.hintHindi}",
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }"""

new_solution = """            // Solution Step Breakdown Card (Notebook Style)
            AnimatedVisibility(
                visible = uiState.showExerciseSolution || uiState.isExerciseAnswerSubmitted,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Column {
                    com.example.ui.components.NotebookPage(
                        title = "Inspector's Solution (Ans: ${currentProblem.correctOption})",
                        text = currentProblem.solutionSteps,
                        modifier = Modifier.fillMaxWidth().testTag("practice_solution_card")
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "💡 Tip: ${type.hintHindi}",
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                        color = AccentOrange,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }"""
content = content.replace(old_solution, new_solution)

with open("app/src/main/java/com/example/ui/screens/PracticeQuestionScreen.kt", "w") as f:
    f.write(content)
