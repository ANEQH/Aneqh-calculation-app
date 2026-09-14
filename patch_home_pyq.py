import re

with open("app/src/main/java/com/example/ui/screens/HomeScreen.kt", "r") as f:
    content = f.read()

old_chapters = """            item {
                Text(
                    text = tr("inspector_chapters", uiState.appLanguage),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }"""

new_pyq_section = """            // PYQ Zone
            item {
                Text(
                    text = tr("pyq_zone_title", uiState.appLanguage),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(horizontal = 4.dp)
                ) {
                    item {
                        QuickActionChip(
                            title = tr("pyq_mock_test", uiState.appLanguage),
                            subtitle = "50 Qs",
                            color = AccentRose,
                            onClick = { viewModel.navigateTo(AppScreen.PYQ_TESTS) }
                        )
                    }
                    item {
                        QuickActionChip(
                            title = tr("pyq_algebra", uiState.appLanguage),
                            subtitle = "TCS Pattern",
                            color = PrimaryIndigo,
                            onClick = { viewModel.navigateTo(AppScreen.PYQ_TESTS) }
                        )
                    }
                    item {
                        QuickActionChip(
                            title = tr("pyq_geometry", uiState.appLanguage),
                            subtitle = "TCS Pattern",
                            color = AccentEmerald,
                            onClick = { viewModel.navigateTo(AppScreen.PYQ_TESTS) }
                        )
                    }
                }
            }
            
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = tr("inspector_chapters", uiState.appLanguage),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }"""

content = content.replace(old_chapters, new_pyq_section)

with open("app/src/main/java/com/example/ui/screens/HomeScreen.kt", "w") as f:
    f.write(content)
