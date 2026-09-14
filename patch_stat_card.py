import re

with open("app/src/main/java/com/example/ui/screens/HomeScreen.kt", "r") as f:
    content = f.read()

content = content.replace(
    """StatCard(label = tr("solved", lang), value = "${stats.totalSolved}", color = PrimaryIndigo, modifier = Modifier.weight(1f))""",
    """StatCard(label = tr("solved", lang), value = "${stats.totalSolved}", color = PrimaryIndigo, icon = Icons.Default.CheckCircle, modifier = Modifier.weight(1f))"""
)
content = content.replace(
    """StatCard(label = tr("streak", lang), value = "${stats.currentStreak} 🔥", color = AccentAmber, modifier = Modifier.weight(1f))""",
    """StatCard(label = tr("streak", lang), value = "${stats.currentStreak} 🔥", color = AccentAmber, icon = Icons.Default.LocalFireDepartment, modifier = Modifier.weight(1f))"""
)
content = content.replace(
    """StatCard(label = tr("accuracy", lang), value = "$accuracy%", color = AccentEmerald, modifier = Modifier.weight(1f))""",
    """StatCard(label = tr("accuracy", lang), value = "$accuracy%", color = AccentEmerald, icon = Icons.Default.GpsFixed, modifier = Modifier.weight(1f))"""
)

with open("app/src/main/java/com/example/ui/screens/HomeScreen.kt", "w") as f:
    f.write(content)
