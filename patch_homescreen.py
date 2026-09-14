import re

with open("app/src/main/java/com/example/ui/screens/HomeScreen.kt", "r") as f:
    content = f.read()

# Replace "Calculation Chapters (12)" with "Calculation Chapters"
content = content.replace('text = "Calculation Chapters (12)",', 'text = "Calculation Mastery (Foundation)",')

old_items = """            // Chapters Grid / List
            items(filteredChapters) { chapter ->
                ChapterCard(
                    chapter = chapter,
                    language = uiState.appLanguage,
                    onClick = { viewModel.selectChapter(chapter) },
                    onStartQuiz = { viewModel.startSpeedQuiz(questionCount = 8, chapterId = chapter.id) }
                )
            }"""

new_items = """            // Foundation Chapters
            val foundationChapters = filteredChapters.filter { it.id <= 14 }
            val advancedChapters = filteredChapters.filter { it.id > 14 }
            
            items(foundationChapters) { chapter ->
                ChapterCard(
                    chapter = chapter,
                    language = uiState.appLanguage,
                    onClick = { viewModel.selectChapter(chapter) },
                    onStartQuiz = { viewModel.startSpeedQuiz(questionCount = 8, chapterId = chapter.id) }
                )
            }
            
            if (advancedChapters.isNotEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Advanced SSC PYQ Engine (6500+ Qs)",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
                            color = AccentOrange
                        )
                    }
                }
                
                items(advancedChapters) { chapter ->
                    ChapterCard(
                        chapter = chapter,
                        language = uiState.appLanguage,
                        onClick = { viewModel.selectChapter(chapter) },
                        onStartQuiz = { viewModel.startSpeedQuiz(questionCount = 10, chapterId = chapter.id) }
                    )
                }
            }"""

content = content.replace(old_items, new_items)

with open("app/src/main/java/com/example/ui/screens/HomeScreen.kt", "w") as f:
    f.write(content)
