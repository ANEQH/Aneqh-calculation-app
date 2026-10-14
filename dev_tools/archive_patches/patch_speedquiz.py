import re

with open("app/src/main/java/com/example/ui/screens/SpeedQuizScreen.kt", "r") as f:
    content = f.read()

old_explanation = """            // Explanation Reveal
            if (quiz.isAnswerSubmitted) {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                    modifier = Modifier.fillMaxWidth().testTag("quiz_explanation_box")
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = if (quiz.isCorrect) "🎉 Correct! (+${10 + quiz.streak * 2} pts)" else "❌ Incorrect (Correct answer is (${question.correctOption}))",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = if (quiz.isCorrect) AccentEmerald else AccentRose
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = question.explanation,
                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }"""

new_explanation = """            // Explanation Reveal (Notebook Style)
            if (quiz.isAnswerSubmitted) {
                val header = if (quiz.isCorrect) "🎉 Correct! (+${10 + quiz.streak * 2} pts)" else "❌ Incorrect (Correct: ${question.correctOption})"
                com.example.ui.components.NotebookPage(
                    title = header,
                    text = question.explanation,
                    modifier = Modifier.fillMaxWidth().testTag("quiz_explanation_box")
                )
            }"""
content = content.replace(old_explanation, new_explanation)

with open("app/src/main/java/com/example/ui/screens/SpeedQuizScreen.kt", "w") as f:
    f.write(content)
