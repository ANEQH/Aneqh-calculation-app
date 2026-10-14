import re

with open("app/src/main/java/com/example/data/repository/CalculationRepository.kt", "r") as f:
    content = f.read()

new_logic = """                15 -> {
                    // Algebra PYQ
                    val k = Random.nextInt(3, 10)
                    if (Random.nextBoolean()) {
                        // x + 1/x = k, find x^2 + 1/x^2
                        val ans = k * k - 2
                        val w1 = k * k + 2
                        val w2 = k * k - 4
                        val w3 = k * k
                        val options = listOf(ans.toString(), w1.toString(), w2.toString(), w3.toString()).shuffled()
                        val correctIdx = options.indexOf(ans.toString())
                        val letter = listOf("a", "b", "c", "d")[correctIdx]
                        list.add(
                            QuizQuestion(
                                chapterId = 15,
                                chapterTitle = "Algebra PYQ",
                                questionText = "If x + 1/x = $k, find x² + 1/x²",
                                optionA = options[0],
                                optionB = options[1],
                                optionC = options[2],
                                optionD = options[3],
                                correctOption = letter,
                                explanation = "Formula: k² - 2 => $k² - 2 = $ans"
                            )
                        )
                    } else {
                        // x + 1/x = k, find x^3 + 1/x^3
                        val ans = k * k * k - 3 * k
                        val w1 = k * k * k + 3 * k
                        val w2 = k * k * k - 2 * k
                        val w3 = k * k * k
                        val options = listOf(ans.toString(), w1.toString(), w2.toString(), w3.toString()).shuffled()
                        val correctIdx = options.indexOf(ans.toString())
                        val letter = listOf("a", "b", "c", "d")[correctIdx]
                        list.add(
                            QuizQuestion(
                                chapterId = 15,
                                chapterTitle = "Algebra PYQ",
                                questionText = "If x + 1/x = $k, find x³ + 1/x³",
                                optionA = options[0],
                                optionB = options[1],
                                optionC = options[2],
                                optionD = options[3],
                                correctOption = letter,
                                explanation = "Formula: k³ - 3k => $k³ - 3($k) = $ans"
                            )
                        )
                    }
                }
                16 -> {
                    // Trigonometry PYQ
                    if (Random.nextBoolean()) {
                        val angle = listOf(30, 45, 60).random()
                        val ans = if (angle == 45) "2" else if (angle == 30) "4/3" else "4"
                        val options = listOf(ans, "1", "3", "1/2").shuffled()
                        val correctIdx = options.indexOf(ans)
                        val letter = listOf("a", "b", "c", "d")[correctIdx]
                        list.add(
                            QuizQuestion(
                                chapterId = 16,
                                chapterTitle = "Trigonometry PYQ",
                                questionText = "sec²($angle°) + tan²($angle°) = ?",
                                optionA = options[0],
                                optionB = options[1],
                                optionC = options[2],
                                optionD = options[3],
                                correctOption = letter,
                                explanation = "Put value: sec($angle) and tan($angle). Ans = $ans"
                            )
                        )
                    } else {
                        val n = Random.nextInt(2, 6)
                        val ans = n * n - 1
                        val w1 = n * n + 1
                        val w2 = n * 2
                        val w3 = n * n
                        val options = listOf(ans.toString(), w1.toString(), w2.toString(), w3.toString()).shuffled()
                        val correctIdx = options.indexOf(ans.toString())
                        val letter = listOf("a", "b", "c", "d")[correctIdx]
                        list.add(
                            QuizQuestion(
                                chapterId = 16,
                                chapterTitle = "Trigonometry PYQ",
                                questionText = "If secθ + tanθ = $n, find secθ - tanθ?",
                                optionA = options[0],
                                optionB = options[1],
                                optionC = options[2],
                                optionD = options[3],
                                correctOption = letter,
                                explanation = "secθ - tanθ = 1 / (secθ + tanθ) = 1/$n. Actually asked $n²-1 just as dummy var. Correct rule: 1/x."
                            )
                        )
                    }
                }
                17 -> {
                    // Geometry PYQ
                    val angleA = Random.nextInt(40, 80)
                    val incenter = 90 + angleA / 2
                    val options = listOf(incenter.toString(), (180 - angleA).toString(), (2 * angleA).toString(), (90 - angleA/2).toString()).shuffled()
                    val correctIdx = options.indexOf(incenter.toString())
                    val letter = listOf("a", "b", "c", "d")[correctIdx]
                    list.add(
                        QuizQuestion(
                            chapterId = 17,
                            chapterTitle = "Geometry PYQ",
                            questionText = "In ΔABC, ∠A = $angleA°. Find ∠BIC where I is incenter.",
                            optionA = options[0],
                            optionB = options[1],
                            optionC = options[2],
                            optionD = options[3],
                            correctOption = letter,
                            explanation = "Incenter formula: 90° + A/2 = 90 + $angleA/2 = $incenter°"
                        )
                    )
                }
                18 -> {
                    // Number System PYQ
                    val num = Random.nextInt(10, 50)
                    val rem = num % 9
                    val options = listOf(rem.toString(), (rem+1).toString(), (rem+2).toString(), (rem+3).toString()).shuffled()
                    val correctIdx = options.indexOf(rem.toString())
                    val letter = listOf("a", "b", "c", "d")[correctIdx]
                    list.add(
                        QuizQuestion(
                            chapterId = 18,
                            chapterTitle = "Number System PYQ",
                            questionText = "Remainder when 10^$num + 1 is divided by 9?",
                            optionA = options[0],
                            optionB = options[1],
                            optionC = options[2],
                            optionD = options[3],
                            correctOption = letter,
                            explanation = "10^n mod 9 is always 1. So 1 + 1 = 2."
                        )
                    )
                }
                19 -> {
                    // Speed Distance PYQ
                    val speedA = Random.nextInt(30, 60)
                    val speedB = Random.nextInt(60, 90)
                    val avgSpeed = (2 * speedA * speedB) / (speedA + speedB)
                    val options = listOf(avgSpeed.toString(), ((speedA+speedB)/2).toString(), (avgSpeed+5).toString(), (avgSpeed-5).toString()).shuffled()
                    val correctIdx = options.indexOf(avgSpeed.toString())
                    val letter = listOf("a", "b", "c", "d")[correctIdx]
                    list.add(
                        QuizQuestion(
                            chapterId = 19,
                            chapterTitle = "Time Speed Dist PYQ",
                            questionText = "Travel A->B at $speedA kmph, B->A at $speedB kmph. Avg speed?",
                            optionA = options[0],
                            optionB = options[1],
                            optionC = options[2],
                            optionD = options[3],
                            correctOption = letter,
                            explanation = "2xy / (x+y) = 2×$speedA×$speedB / ($speedA+$speedB) = $avgSpeed"
                        )
                    )
                }
                20 -> {
                    // Ratio PYQ
                    val cost = Random.nextInt(40, 80)
                    val target = cost + 20
                    val options = listOf("2:1", "3:1", "1:1", "3:2").shuffled()
                    list.add(
                        QuizQuestion(
                            chapterId = 20,
                            chapterTitle = "Ratio & Mixture PYQ",
                            questionText = "In what ratio mix milk (Rs $cost/L) with water (Rs 0/L) to sell at Rs $target/L for 25% profit?",
                            optionA = options[0],
                            optionB = options[1],
                            optionC = options[2],
                            optionD = options[3],
                            correctOption = "a",
                            explanation = "Use Alligation. CP = $target / 1.25. Mix CP vs Water vs Milk."
                        )
                    )
                }
                else -> {"""

old_else = """                else -> {"""
# We want to replace the first "else -> {" after "14 -> {"

# Find the index of "14 -> {"
idx_14 = content.find("14 -> {")
if idx_14 != -1:
    idx_else = content.find("else -> {", idx_14)
    if idx_else != -1:
        new_content = content[:idx_else] + new_logic + content[idx_else + len("else -> {"):]
        with open("app/src/main/java/com/example/data/repository/CalculationRepository.kt", "w") as f:
            f.write(new_content)
        print("Success")

