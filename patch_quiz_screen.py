import re

with open("app/src/main/java/com/example/ui/screens/SpeedQuizScreen.kt", "r") as f:
    content = f.read()

old_sol = """                        Text(
                            text = "💡 Solution: ${item.question.explanation}",
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )"""

new_sol = """                        Surface(
                            color = PrimaryIndigo.copy(alpha = 0.05f),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.School, contentDescription = null, tint = AccentOrange, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "Concept & Trick",
                                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                        color = AccentOrange
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = item.question.explanation,
                                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 13.sp, fontWeight = FontWeight.Medium),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }"""
content = content.replace(old_sol, new_sol)

with open("app/src/main/java/com/example/ui/screens/SpeedQuizScreen.kt", "w") as f:
    f.write(content)

