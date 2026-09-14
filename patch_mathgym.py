with open("app/src/main/java/com/example/ui/screens/MathGymScreen.kt", "r") as f:
    content = f.read()

# Update Enum
old_enum = """enum class GymCategory(val label: String) {
    TABLES("Tables (x)"),
    ADDITION("Addition (+)"),
    SUBTRACTION("Sub (-)"),
    FRACTIONS("Fractions (%)")
}"""

new_enum = """enum class GymCategory(val label: String) {
    TABLES("Tables (x)"),
    SQUARE("Squares (x²)"),
    CUBE("Cubes (x³)"),
    ADDITION("Addition (+)"),
    SUBTRACTION("Sub (-)"),
    FRACTIONS("Fractions (%)")
}"""
content = content.replace(old_enum, new_enum)

# Update generator
old_gen = """    when (category) {
        GymCategory.TABLES -> {"""

new_gen = """    when (category) {
        GymCategory.SQUARE -> {
            val n = if (difficulty == GymDifficulty.EASY) Random.nextInt(2, 26) else Random.nextInt(26, 100)
            val ans = n * n
            text = "$n² = ?"
            correct = ans.toString()
            optionsSet.add(correct)
            while (optionsSet.size < 4) {
                val fake = ans + Random.nextInt(-15, 15) * n.let { if (it == 0) 5 else it }
                if (fake > 0 && fake != ans) optionsSet.add(fake.toString())
            }
        }
        GymCategory.CUBE -> {
            val n = if (difficulty == GymDifficulty.EASY) Random.nextInt(2, 11) else Random.nextInt(11, 25)
            val ans = n * n * n
            text = "$n³ = ?"
            correct = ans.toString()
            optionsSet.add(correct)
            while (optionsSet.size < 4) {
                val fake = ans + Random.nextInt(-10, 10) * n.let { if (it == 0) 2 else it }
                if (fake > 0 && fake != ans) optionsSet.add(fake.toString())
            }
        }
        GymCategory.TABLES -> {"""
content = content.replace(old_gen, new_gen)

with open("app/src/main/java/com/example/ui/screens/MathGymScreen.kt", "w") as f:
    f.write(content)
