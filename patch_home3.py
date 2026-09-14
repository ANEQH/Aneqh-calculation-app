with open("app/src/main/java/com/example/ui/screens/HomeScreen.kt", "r") as f:
    content = f.read()

old_hero = """private fun HeroDrillBanner(
    onStartSpeedDrill: () -> Unit,
    onOpenSolvers: () -> Unit,
    onLogoClick: () -> Unit = {}
) {"""

new_hero = """private fun HeroDrillBanner(
    onStartSpeedDrill: () -> Unit,
    onOpenSolvers: () -> Unit,
    onLogoClick: () -> Unit = {},
    lang: com.example.ui.viewmodel.AppLanguage = com.example.ui.viewmodel.AppLanguage.ENGLISH
) {"""

content = content.replace(old_hero, new_hero)

with open("app/src/main/java/com/example/ui/screens/HomeScreen.kt", "w") as f:
    f.write(content)
