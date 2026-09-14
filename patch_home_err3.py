import re

with open("app/src/main/java/com/example/ui/screens/HomeScreen.kt", "r") as f:
    content = f.read()

content = content.replace(
    """ChipCard(title = tr("pyq_mock_test", lang), subtitle = "Mixed Qs", color = AccentRose) { viewModel.navigateTo(AppScreen.PYQ_TESTS) }""",
    """ChipCard(title = tr("pyq_mock_test", lang), subtitle = "Mixed Qs", color = AccentRose, icon = Icons.Default.Quiz) { viewModel.navigateTo(AppScreen.PYQ_TESTS) }"""
)
content = content.replace(
    """ChipCard(title = tr("pyq_algebra", lang), subtitle = "250+ Qs", color = PrimaryIndigo) { viewModel.navigateTo(AppScreen.PYQ_TESTS) }""",
    """ChipCard(title = tr("pyq_algebra", lang), subtitle = "250+ Qs", color = PrimaryIndigo, icon = Icons.Default.Calculate) { viewModel.navigateTo(AppScreen.PYQ_TESTS) }"""
)
content = content.replace(
    """ChipCard(title = tr("pyq_geometry", lang), subtitle = "180+ Qs", color = AccentEmerald) { viewModel.navigateTo(AppScreen.PYQ_TESTS) }""",
    """ChipCard(title = tr("pyq_geometry", lang), subtitle = "180+ Qs", color = AccentEmerald, icon = Icons.Default.Category) { viewModel.navigateTo(AppScreen.PYQ_TESTS) }"""
)
content = content.replace(
    """ChipCard(title = tr("pyq_arithmetic", lang), subtitle = "400+ Qs", color = AccentCyan) { viewModel.navigateTo(AppScreen.PYQ_TESTS) }""",
    """ChipCard(title = tr("pyq_arithmetic", lang), subtitle = "400+ Qs", color = AccentCyan, icon = Icons.Default.TrendingUp) { viewModel.navigateTo(AppScreen.PYQ_TESTS) }"""
)

with open("app/src/main/java/com/example/ui/screens/HomeScreen.kt", "w") as f:
    f.write(content)
