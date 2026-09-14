import re

with open("app/src/main/java/com/example/data/repository/CalculationRepository.kt", "r") as f:
    content = f.read()

# Fix Number system
old_num = """                18 -> {
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
                }"""

new_num = """                18 -> {
                    // Number System PYQ
                    val num = Random.nextInt(10, 100)
                    val rem = 2
                    val options = listOf("2", "1", "0", "8").shuffled()
                    val correctIdx = options.indexOf("2")
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
                }"""
content = content.replace(old_num, new_num)

old_trigo = """                        val n = Random.nextInt(2, 6)
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
                        )"""

new_trigo = """                        val n = Random.nextInt(2, 6)
                        val ans = "1/$n"
                        val w1 = "$n"
                        val w2 = "${n * n - 1}"
                        val w3 = "-$n"
                        val options = listOf(ans, w1, w2, w3).shuffled()
                        val correctIdx = options.indexOf(ans)
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
                                explanation = "sec²θ - tan²θ = 1 => secθ - tanθ = 1 / (secθ + tanθ) = 1/$n"
                            )
                        )"""

content = content.replace(old_trigo, new_trigo)

with open("app/src/main/java/com/example/data/repository/CalculationRepository.kt", "w") as f:
    f.write(content)
