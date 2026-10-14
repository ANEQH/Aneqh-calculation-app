import re

with open("app/src/main/java/com/example/ui/screens/HomeScreen.kt", "r") as f:
    content = f.read()

# Add icon parameter to ChipCard
old_chip_def = """@Composable
fun ChipCard(title: String, subtitle: String, color: Color, onClick: () -> Unit) {"""

new_chip_def = """@Composable
fun ChipCard(title: String, subtitle: String, color: Color, icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {"""
content = content.replace(old_chip_def, new_chip_def)

# Find ChipCard calls and inject icon
content = content.replace(
    """ChipCard(title = tr("pyq_mock_test", lang), subtitle = "Mixed Qs", color = AccentRose)""",
    """ChipCard(title = tr("pyq_mock_test", lang), subtitle = "Mixed Qs", color = AccentRose, icon = Icons.Default.Quiz)"""
)
content = content.replace(
    """ChipCard(title = tr("pyq_algebra", lang), subtitle = "250+ Qs", color = PrimaryIndigo)""",
    """ChipCard(title = tr("pyq_algebra", lang), subtitle = "250+ Qs", color = PrimaryIndigo, icon = Icons.Default.Functions)"""
)
content = content.replace(
    """ChipCard(title = tr("pyq_geometry", lang), subtitle = "180+ Qs", color = AccentEmerald)""",
    """ChipCard(title = tr("pyq_geometry", lang), subtitle = "180+ Qs", color = AccentEmerald, icon = Icons.Default.Category)"""
)
content = content.replace(
    """ChipCard(title = tr("pyq_arithmetic", lang), subtitle = "400+ Qs", color = AccentCyan)""",
    """ChipCard(title = tr("pyq_arithmetic", lang), subtitle = "400+ Qs", color = AccentCyan, icon = Icons.Default.Calculate)"""
)

# Fix Advanced Tools section
content = content.replace(
    """ChipCard(title = tr("10x_solvers", lang), subtitle = "Instant Ans", color = AccentOrange)""",
    """ChipCard(title = tr("10x_solvers", lang), subtitle = "Instant Ans", color = AccentOrange, icon = Icons.Default.AutoAwesome)"""
)
content = content.replace(
    """ChipCard(title = tr("calculator", lang), subtitle = "Math Pro", color = AccentCyan)""",
    """ChipCard(title = tr("calculator", lang), subtitle = "Math Pro", color = AccentCyan, icon = Icons.Default.Calculate)"""
)
content = content.replace(
    """ChipCard(title = tr("ci_si_rates", lang), subtitle = "Fast %", color = AccentPurple)""",
    """ChipCard(title = tr("ci_si_rates", lang), subtitle = "Fast %", color = AccentPurple, icon = Icons.Default.Percent)"""
)
content = content.replace(
    """ChipCard(title = tr("visual_math", lang), subtitle = "3D Graphs", color = PrimaryIndigo)""",
    """ChipCard(title = tr("visual_math", lang), subtitle = "3D Graphs", color = PrimaryIndigo, icon = Icons.Default.3dRotation)"""
)
content = content.replace(
    """ChipCard(title = tr("concept_book", lang), subtitle = "1000x Theory", color = AccentEmerald)""",
    """ChipCard(title = tr("concept_book", lang), subtitle = "1000x Theory", color = AccentEmerald, icon = Icons.Default.MenuBook)"""
)

# Update the chip implementation to show the icon and fix the text parameter issue
old_chip_impl = """        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = title, style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold), color = MaterialTheme.colorScheme.onSurface, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = subtitle, style = MaterialTheme.typography.labelSmall, color = color)
        }"""
        
new_chip_impl = """        Column(modifier = Modifier.padding(12.dp)) {
            Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = title, style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold), color = MaterialTheme.colorScheme.onSurface, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = subtitle, style = MaterialTheme.typography.labelSmall, color = color, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }"""
content = content.replace(old_chip_impl, new_chip_impl)

with open("app/src/main/java/com/example/ui/screens/HomeScreen.kt", "w") as f:
    f.write(content)
