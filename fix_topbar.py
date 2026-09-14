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

if "onThemeToggle" not in content:
    content = content.replace(old_fun, new_fun)
    with open("app/src/main/java/com/example/ui/components/CommonComponents.kt", "w") as f:
        f.write(content)
