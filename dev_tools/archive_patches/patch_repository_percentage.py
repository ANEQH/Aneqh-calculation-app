with open("app/src/main/java/com/example/data/repository/CalculationRepository.kt", "r") as f:
    content = f.read()

import re

old_percent = """                9 -> {
                    // Percentage
                    val r1 = if (difficulty == 0) listOf(10, 20).random() else if (difficulty == 1) listOf(15, 25, 30).random() else listOf(12, 18, 22).random()
                    val r2 = if (difficulty == 0) listOf(10, 20).random() else if (difficulty == 1) listOf(5, 15).random() else listOf(8, 12, 18).random()
                    val ansVal = r1 + r2 + (r1 * r2) / 100.0
                    val ansStr = "%.2f%%".format(ansVal)
                    val w1 = "%.2f%%".format(ansVal + 2)
                    val w2 = "%.2f%%".format(ansVal - 1)
                    val w3 = "%.2f%%".format(r1 + r2.toDouble())
                    val options = listOf(ansStr, w1, w2, w3).shuffled()
                    val correctIdx = options.indexOf(ansStr)
                    val letter = listOf("a", "b", "c", "d")[correctIdx]
                    list.add(
                        QuizQuestion(
                            chapterId = 9,
                            chapterTitle = "प्रतिशत (Percentage)",
                            questionText = "$r1%↑ & $r2%↑ = ?",
                            optionA = options[0],
                            optionB = options[1],
                            optionC = options[2],
                            optionD = options[3],
                            correctOption = letter,
                            explanation = "$r1 + $r2 + ($r1×$r2)/100 = $ansStr"
                        )
                    )
                }"""

new_percent = """                9 -> {
                    // Percentage (Mix of Successive & PYQ Concepts)
                    val randType = Random.nextInt(3)
                    if (randType == 0) {
                        // Successive
                        val r1 = if (difficulty == 0) listOf(10, 20).random() else if (difficulty == 1) listOf(15, 25, 30).random() else listOf(12, 18, 22).random()
                        val r2 = if (difficulty == 0) listOf(10, 20).random() else if (difficulty == 1) listOf(5, 15).random() else listOf(8, 12, 18).random()
                        val ansVal = r1 + r2 + (r1 * r2) / 100.0
                        val ansStr = "%.2f%%".format(ansVal)
                        val w1 = "%.2f%%".format(ansVal + 2)
                        val w2 = "%.2f%%".format(ansVal - 1)
                        val w3 = "%.2f%%".format(r1 + r2.toDouble())
                        val options = listOf(ansStr, w1, w2, w3).shuffled()
                        val correctIdx = options.indexOf(ansStr)
                        val letter = listOf("a", "b", "c", "d")[correctIdx]
                        list.add(
                            QuizQuestion(
                                chapterId = 9,
                                chapterTitle = "प्रतिशत (Percentage)",
                                questionText = "$r1%↑ & $r2%↑ = ?",
                                optionA = options[0],
                                optionB = options[1],
                                optionC = options[2],
                                optionD = options[3],
                                correctOption = letter,
                                explanation = "$r1 + $r2 + ($r1×$r2)/100 = $ansStr"
                            )
                        )
                    } else if (randType == 1) {
                        // Sugar Price PYQ
                        val r = if (difficulty == 0) 25 else if (difficulty == 1) 20 else 50
                        val isInc = Random.nextBoolean()
                        val num = r.toDouble()
                        val den = if (isInc) 100.0 + r else 100.0 - r
                        val ansVal = (num / den) * 100
                        val ansStr = "%.2f%%".format(ansVal)
                        val w1 = "%.2f%%".format(ansVal + 5)
                        val w2 = "%.2f%%".format(ansVal - 5)
                        val w3 = "%.2f%%".format(r.toDouble())
                        val options = listOf(ansStr, w1, w2, w3).shuffled()
                        val correctIdx = options.indexOf(ansStr)
                        val letter = listOf("a", "b", "c", "d")[correctIdx]
                        val qText = if (isInc) "Price +$r%. Consump. -%?" else "Price -$r%. Consump. +%?"
                        list.add(
                            QuizQuestion(
                                chapterId = 9,
                                chapterTitle = "SSC CGL PYQ: Price",
                                questionText = qText,
                                optionA = options[0],
                                optionB = options[1],
                                optionC = options[2],
                                optionD = options[3],
                                correctOption = letter,
                                explanation = "$r / (100${if(isInc) "+" else "-"}$r) × 100 = $ansStr"
                            )
                        )
                    } else {
                        // Election PYQ
                        val w = if (difficulty == 0) 60 else if (difficulty == 1) 55 else 65
                        val l = 100 - w
                        val diff = w - l
                        val majority = diff * Random.nextInt(10, 50)
                        val total = (majority / diff) * 100
                        val w1 = total + 1000
                        val w2 = total - 500
                        val w3 = total * 2
                        val options = listOf(total.toString(), w1.toString(), w2.toString(), w3.toString()).shuffled()
                        val correctIdx = options.indexOf(total.toString())
                        val letter = listOf("a", "b", "c", "d")[correctIdx]
                        list.add(
                            QuizQuestion(
                                chapterId = 9,
                                chapterTitle = "SSC CGL PYQ: Election",
                                questionText = "Win:$w%, Margin:$majority. Total?",
                                optionA = options[0],
                                optionB = options[1],
                                optionC = options[2],
                                optionD = options[3],
                                correctOption = letter,
                                explanation = "Diff = $w% - $l% = $diff%. $diff% = $majority, so 100% = $total"
                            )
                        )
                    }
                }"""

if old_percent in content:
    with open("app/src/main/java/com/example/data/repository/CalculationRepository.kt", "w") as f:
        f.write(content.replace(old_percent, new_percent))
    print("Replaced successfully")
else:
    print("Could not find old text")

