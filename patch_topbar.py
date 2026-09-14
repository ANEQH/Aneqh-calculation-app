import re

with open("app/src/main/java/com/example/ui/components/CommonComponents.kt", "r") as f:
    content = f.read()

old_fun = """fun AppTopBar(
    title: String,
    subtitle: String,
    streak: Int,
    showBackButton: Boolean = false,
    currentLanguage: AppLanguage? = null,
    onBackClick: () -> Unit = {},
    onLanguageToggle: () -> Unit = {},
    onStatsClick: () -> Unit = {}
) {"""

new_fun = """fun AppTopBar(
    title: String,
    subtitle: String,
    streak: Int,
    showBackButton: Boolean = false,
    currentLanguage: AppLanguage? = null,
    onBackClick: () -> Unit = {},
    onLanguageToggle: () -> Unit = {},
    onStatsClick: () -> Unit = {},
    onThemeToggle: (() -> Unit)? = null
) {"""
content = content.replace(old_fun, new_fun)

old_stats = """            Surface(
                shape = CircleShape,
                color = AccentAmber.copy(alpha = 0.2f),
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable { onStatsClick() }
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocalFireDepartment,
                        contentDescription = "Streak",
                        tint = AccentAmber,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "$streak",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = AccentAmber
                    )
                }
            }"""

new_stats = """            if (onThemeToggle != null) {
                IconButton(onClick = onThemeToggle) {
                    Icon(imageVector = Icons.Default.DarkMode, contentDescription = "Toggle Theme", tint = PrimaryIndigo)
                }
            }
            Surface(
                shape = CircleShape,
                color = AccentAmber.copy(alpha = 0.2f),
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable { onStatsClick() }
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocalFireDepartment,
                        contentDescription = "Streak",
                        tint = AccentAmber,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "$streak",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = AccentAmber
                    )
                }
            }"""
content = content.replace(old_stats, new_stats)
content = content.replace("import androidx.compose.material.icons.filled.Translate", "import androidx.compose.material.icons.filled.Translate\nimport androidx.compose.material.icons.filled.DarkMode")

with open("app/src/main/java/com/example/ui/components/CommonComponents.kt", "w") as f:
    f.write(content)
