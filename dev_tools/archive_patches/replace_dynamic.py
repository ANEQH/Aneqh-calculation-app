import re

with open("app/src/main/java/com/example/data/repository/CalculationRepository.kt", "r") as f:
    content = f.read()

# Find the start of the function
start_str = "    private fun generateDynamicQuestions(count: Int, chapterFilter: Int? = null): List<QuizQuestion> {"
start_idx = content.find(start_str)

new_func = """    private fun generateDynamicQuestions(count: Int, chapterFilter: Int? = null): List<QuizQuestion> {
        val list = mutableListOf<QuizQuestion>()
        repeat(count) {
            val difficulty = Random.nextInt(3) // 0 = Easy, 1 = Hard, 2 = Very Hard
            val typeId = chapterFilter ?: Random.nextInt(1, 15) // If chapterFilter is present, use it. Else random.
            
            when (typeId) {
                1 -> {
                    // Addition
                    val a = if (difficulty == 0) Random.nextInt(20, 99) else if (difficulty == 1) Random.nextInt(100, 999) else Random.nextInt(1000, 9999)
                    val b = if (difficulty == 0) Random.nextInt(10, 99) else if (difficulty == 1) Random.nextInt(100, 999) else Random.nextInt(1000, 9999)
                    val ans = a + b
                    val wrong1 = ans + (if (difficulty == 0) 10 else 100)
                    val wrong2 = ans - (if (difficulty == 0) 2 else 12)
                    val wrong3 = ans + 2
                    val options = listOf(ans.toString(), wrong1.toString(), wrong2.toString(), wrong3.toString()).shuffled()
                    val correctIdx = options.indexOf(ans.toString())
                    val letter = listOf("a", "b", "c", "d")[correctIdx]
                    list.add(
                        QuizQuestion(
                            chapterId = 1,
                            chapterTitle = "जोड़ (Addition)",
                            questionText = "$a + $b =",
                            optionA = options[0],
                            optionB = options[1],
                            optionC = options[2],
                            optionD = options[3],
                            correctOption = letter,
                            explanation = "Tens: ${(a/10)*10 + (b/10)*10} + Units: ${(a%10)+(b%10)} = $ans"
                        )
                    )
                }
                2 -> {
                    // Subtraction
                    val base = if (difficulty == 0) 100 else if (difficulty == 1) 1000 else 10000
                    val x = if (difficulty == 0) Random.nextInt(11, 89) else if (difficulty == 1) Random.nextInt(111, 989) else Random.nextInt(1111, 9889)
                    val ans = base - x
                    val wrong1 = ans + 10
                    val wrong2 = ans - 10
                    val wrong3 = ans + 2
                    val options = listOf(ans.toString(), wrong1.toString(), wrong2.toString(), wrong3.toString()).shuffled()
                    val correctIdx = options.indexOf(ans.toString())
                    val letter = listOf("a", "b", "c", "d")[correctIdx]
                    list.add(
                        QuizQuestion(
                            chapterId = 2,
                            chapterTitle = "घटाव (Subtraction)",
                            questionText = "$base - $x =",
                            optionA = options[0],
                            optionB = options[1],
                            optionC = options[2],
                            optionD = options[3],
                            correctOption = letter,
                            explanation = "All from 9, last from 10: $ans"
                        )
                    )
                }
                3 -> {
                    // Multiplication
                    val num = if (difficulty == 0) Random.nextInt(15, 30) else if (difficulty == 1) Random.nextInt(31, 60) else Random.nextInt(61, 120)
                    val ans = num * num
                    val wrong1 = ans + 20
                    val wrong2 = ans - 10
                    val wrong3 = ans + 100
                    val options = listOf(ans.toString(), wrong1.toString(), wrong2.toString(), wrong3.toString()).shuffled()
                    val correctIdx = options.indexOf(ans.toString())
                    val letter = listOf("a", "b", "c", "d")[correctIdx]
                    list.add(
                        QuizQuestion(
                            chapterId = 3,
                            chapterTitle = "गुणा (Multiplication)",
                            questionText = "$num² =",
                            optionA = options[0],
                            optionB = options[1],
                            optionC = options[2],
                            optionD = options[3],
                            correctOption = letter,
                            explanation = "($num)² = $ans using a² | 2ab | b²"
                        )
                    )
                }
                4 -> {
                    // Division
                    val divisor = if (difficulty == 0) Random.nextInt(2, 9) else if (difficulty == 1) Random.nextInt(11, 19) else Random.nextInt(21, 99)
                    val quotient = if (difficulty == 0) Random.nextInt(11, 99) else if (difficulty == 1) Random.nextInt(21, 99) else Random.nextInt(11, 49)
                    val dividend = divisor * quotient
                    val wrong1 = quotient + 2
                    val wrong2 = quotient - 1
                    val wrong3 = quotient + 10
                    val options = listOf(quotient.toString(), wrong1.toString(), wrong2.toString(), wrong3.toString()).shuffled()
                    val correctIdx = options.indexOf(quotient.toString())
                    val letter = listOf("a", "b", "c", "d")[correctIdx]
                    list.add(
                        QuizQuestion(
                            chapterId = 4,
                            chapterTitle = "भाग (Division)",
                            questionText = "$dividend ÷ $divisor =",
                            optionA = options[0],
                            optionB = options[1],
                            optionC = options[2],
                            optionD = options[3],
                            correctOption = letter,
                            explanation = "$dividend / $divisor = $quotient"
                        )
                    )
                }
                5 -> {
                    // Decimals
                    val a = if (difficulty == 0) Random.nextInt(11, 99) / 10.0 else if (difficulty == 1) Random.nextInt(111, 999) / 100.0 else Random.nextInt(1111, 9999) / 1000.0
                    val b = if (difficulty == 0) Random.nextInt(11, 99) / 10.0 else if (difficulty == 1) Random.nextInt(111, 999) / 100.0 else Random.nextInt(1111, 9999) / 1000.0
                    val ans = ((a + b) * 1000).toInt() / 1000.0
                    val wrong1 = ans + 1.1
                    val wrong2 = ans - 0.2
                    val wrong3 = ans + 0.5
                    val options = listOf(ans.toString(), wrong1.toString(), wrong2.toString(), wrong3.toString()).shuffled()
                    val correctIdx = options.indexOf(ans.toString())
                    val letter = listOf("a", "b", "c", "d")[correctIdx]
                    list.add(
                        QuizQuestion(
                            chapterId = 5,
                            chapterTitle = "दशमलव (Decimals)",
                            questionText = "$a + $b =",
                            optionA = options[0],
                            optionB = options[1],
                            optionC = options[2],
                            optionD = options[3],
                            correctOption = letter,
                            explanation = "Align decimals: $ans"
                        )
                    )
                }
                6 -> {
                    // Surds & Indices
                    val base = if (difficulty == 0) Random.nextInt(2, 5) else if (difficulty == 1) Random.nextInt(6, 9) else Random.nextInt(11, 15)
                    val power = if (difficulty == 0) 2 else if (difficulty == 1) 3 else 4
                    var ans = 1
                    repeat(power) { ans *= base }
                    val wrong1 = ans + base
                    val wrong2 = ans - base
                    val wrong3 = ans * 2
                    val options = listOf(ans.toString(), wrong1.toString(), wrong2.toString(), wrong3.toString()).shuffled()
                    val correctIdx = options.indexOf(ans.toString())
                    val letter = listOf("a", "b", "c", "d")[correctIdx]
                    list.add(
                        QuizQuestion(
                            chapterId = 6,
                            chapterTitle = "घातांक एवं करणी (Surds)",
                            questionText = "$base^$power =",
                            optionA = options[0],
                            optionB = options[1],
                            optionC = options[2],
                            optionD = options[3],
                            correctOption = letter,
                            explanation = "$base multiplied $power times = $ans"
                        )
                    )
                }
                7 -> {
                    // LCM & HCF
                    val a = if (difficulty == 0) Random.nextInt(4, 12) else if (difficulty == 1) Random.nextInt(12, 30) else Random.nextInt(20, 50)
                    val b = if (difficulty == 0) Random.nextInt(4, 12) else if (difficulty == 1) Random.nextInt(12, 30) else Random.nextInt(20, 50)
                    // HCF
                    var hcf = 1
                    for (i in 1..minOf(a, b)) {
                        if (a % i == 0 && b % i == 0) hcf = i
                    }
                    val lcm = (a * b) / hcf
                    val wrong1 = lcm + a
                    val wrong2 = lcm - b
                    val wrong3 = a * b
                    val options = listOf(lcm.toString(), wrong1.toString(), wrong2.toString(), wrong3.toString()).shuffled()
                    val correctIdx = options.indexOf(lcm.toString())
                    val letter = listOf("a", "b", "c", "d")[correctIdx]
                    list.add(
                        QuizQuestion(
                            chapterId = 7,
                            chapterTitle = "LCM & HCF",
                            questionText = "LCM of $a and $b is?",
                            optionA = options[0],
                            optionB = options[1],
                            optionC = options[2],
                            optionD = options[3],
                            correctOption = letter,
                            explanation = "HCF is $hcf. LCM = ($a × $b) / $hcf = $lcm"
                        )
                    )
                }
                8 -> {
                    // Fractions
                    val num = if (difficulty == 0) Random.nextInt(1, 5) else if (difficulty == 1) Random.nextInt(5, 12) else Random.nextInt(11, 25)
                    val den = if (difficulty == 0) Random.nextInt(6, 10) else if (difficulty == 1) Random.nextInt(13, 20) else Random.nextInt(26, 50)
                    val num2 = if (difficulty == 0) Random.nextInt(1, 5) else if (difficulty == 1) Random.nextInt(5, 12) else Random.nextInt(11, 25)
                    val isGreater = (num.toDouble() / den) > (num2.toDouble() / den)
                    val ans = if (isGreater) "$num/$den" else "$num2/$den"
                    val wrong = if (!isGreater) "$num/$den" else "$num2/$den"
                    val options = listOf(ans, wrong, "Equal", "None").shuffled()
                    val correctIdx = options.indexOf(ans)
                    val letter = listOf("a", "b", "c", "d")[correctIdx]
                    list.add(
                        QuizQuestion(
                            chapterId = 8,
                            chapterTitle = "भिन्न (Fractions)",
                            questionText = "Which is greater: $num/$den or $num2/$den?",
                            optionA = options[0],
                            optionB = options[1],
                            optionC = options[2],
                            optionD = options[3],
                            correctOption = letter,
                            explanation = "Compare numerators when denominators are same. $ans is greater."
                        )
                    )
                }
                9 -> {
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
                }
                10 -> {
                    // Profit & Loss
                    val cp = if (difficulty == 0) Random.nextInt(10, 50) * 10 else if (difficulty == 1) Random.nextInt(50, 150) * 10 else Random.nextInt(150, 500) * 10
                    val profitPercent = if (difficulty == 0) listOf(10, 20, 25).random() else if (difficulty == 1) listOf(15, 30, 40).random() else listOf(12, 18, 22).random()
                    val sp = cp + (cp * profitPercent / 100)
                    val wrong1 = sp + 50
                    val wrong2 = sp - 20
                    val wrong3 = cp - (cp * profitPercent / 100)
                    val options = listOf("₹$sp", "₹$wrong1", "₹$wrong2", "₹$wrong3").shuffled()
                    val correctIdx = options.indexOf("₹$sp")
                    val letter = listOf("a", "b", "c", "d")[correctIdx]
                    list.add(
                        QuizQuestion(
                            chapterId = 10,
                            chapterTitle = "लाभ एवं हानि (Profit/Loss)",
                            questionText = "CP = ₹$cp, Profit = $profitPercent%, SP = ?",
                            optionA = options[0],
                            optionB = options[1],
                            optionC = options[2],
                            optionD = options[3],
                            correctOption = letter,
                            explanation = "SP = CP + Profit = $cp + ($cp × $profitPercent / 100) = ₹$sp"
                        )
                    )
                }
                11 -> {
                    // CI 2 years
                    val r = if (difficulty == 0) listOf(5, 10).random() else if (difficulty == 1) listOf(4, 6, 8).random() else listOf(3, 7, 9, 12).random()
                    val ciRate = 2 * r + (r * r) / 100.0
                    val ansStr = "%.2f%%".format(ciRate)
                    val w1 = "%.2f%%".format(ciRate + 1)
                    val w2 = "%.2f%%".format(2.0 * r)
                    val w3 = "%.2f%%".format(ciRate - 0.5)
                    val options = listOf(ansStr, w1, w2, w3).shuffled()
                    val correctIdx = options.indexOf(ansStr)
                    val letter = listOf("a", "b", "c", "d")[correctIdx]
                    list.add(
                        QuizQuestion(
                            chapterId = 11,
                            chapterTitle = "ब्याज (SI & CI)",
                            questionText = "$r% @ CI for 2 years =",
                            optionA = options[0],
                            optionB = options[1],
                            optionC = options[2],
                            optionD = options[3],
                            correctOption = letter,
                            explanation = "2($r) + $r²/100 = $ansStr"
                        )
                    )
                }
                12 -> {
                    // Time and Work
                    val aFrac = if (difficulty == 0) listOf(1 to 2, 1 to 3).random() else if (difficulty == 1) listOf(2 to 3, 3 to 4).random() else listOf(2 to 5, 3 to 7).random()
                    val days = aFrac.first * Random.nextInt(4, 10)
                    val totalDays = days * aFrac.second / aFrac.first
                    val w1 = totalDays + 5
                    val w2 = totalDays - 4
                    val w3 = totalDays * 2
                    val options = listOf("$totalDays days", "$w1 days", "$w2 days", "$w3 days").shuffled()
                    val correctIdx = options.indexOf("$totalDays days")
                    val letter = listOf("a", "b", "c", "d")[correctIdx]
                    list.add(
                        QuizQuestion(
                            chapterId = 12,
                            chapterTitle = "समय एवं कार्य (Time & Work)",
                            questionText = "If A does ${aFrac.first}/${aFrac.second} work in $days days, total work completed in?",
                            optionA = options[0],
                            optionB = options[1],
                            optionC = options[2],
                            optionD = options[3],
                            correctOption = letter,
                            explanation = "Total = $days × (${aFrac.second}/${aFrac.first}) = $totalDays days"
                        )
                    )
                }
                13 -> {
                    // Vedic Multiply by 11 or Base 100
                    if (Random.nextBoolean()) {
                        val num = if (difficulty == 0) Random.nextInt(21, 45) else if (difficulty == 1) Random.nextInt(46, 89) else Random.nextInt(111, 499)
                        val ans = num * 11
                        val wrong1 = ans + 11
                        val wrong2 = ans - 11
                        val wrong3 = ans + 100
                        val options = listOf(ans.toString(), wrong1.toString(), wrong2.toString(), wrong3.toString()).shuffled()
                        val correctIdx = options.indexOf(ans.toString())
                        val letter = listOf("a", "b", "c", "d")[correctIdx]
                        list.add(
                            QuizQuestion(
                                chapterId = 13,
                                chapterTitle = "Vedic Speed Tricks",
                                questionText = "$num × 11 =",
                                optionA = options[0],
                                optionB = options[1],
                                optionC = options[2],
                                optionD = options[3],
                                correctOption = letter,
                                explanation = "Split ${num/10} and ${num%10}, insert sum: ${num/10 + num%10}. Ans: $ans"
                            )
                        )
                    } else {
                        val a = if (difficulty == 0) Random.nextInt(101, 105) else if (difficulty == 1) Random.nextInt(106, 112) else Random.nextInt(91, 99)
                        val b = if (difficulty == 0) Random.nextInt(101, 105) else if (difficulty == 1) Random.nextInt(106, 112) else Random.nextInt(91, 99)
                        val ans = a * b
                        val wrong1 = ans + 100
                        val wrong2 = ans - 100
                        val wrong3 = ans + 10
                        val options = listOf(ans.toString(), wrong1.toString(), wrong2.toString(), wrong3.toString()).shuffled()
                        val correctIdx = options.indexOf(ans.toString())
                        val letter = listOf("a", "b", "c", "d")[correctIdx]
                        list.add(
                            QuizQuestion(
                                chapterId = 13,
                                chapterTitle = "Vedic Speed Tricks",
                                questionText = "$a × $b =",
                                optionA = options[0],
                                optionB = options[1],
                                optionC = options[2],
                                optionD = options[3],
                                correctOption = letter,
                                explanation = "(100 + ${a-100} + ${b-100}) | (${a-100} × ${b-100}) = $ans"
                            )
                        )
                    }
                }
                14 -> {
                    // SSC CGL Essentials (Base 50 Square or Digital Sum)
                    if (Random.nextBoolean()) {
                        val num = if (difficulty == 0) Random.nextInt(48, 52) else if (difficulty == 1) Random.nextInt(41, 59) else Random.nextInt(35, 65)
                        val ans = num * num
                        val wrong1 = ans + 100
                        val wrong2 = ans - 100
                        val wrong3 = ans + 10
                        val options = listOf(ans.toString(), wrong1.toString(), wrong2.toString(), wrong3.toString()).shuffled()
                        val correctIdx = options.indexOf(ans.toString())
                        val letter = listOf("a", "b", "c", "d")[correctIdx]
                        list.add(
                            QuizQuestion(
                                chapterId = 14,
                                chapterTitle = "SSC CGL Essentials",
                                questionText = "$num² =",
                                optionA = options[0],
                                optionB = options[1],
                                optionC = options[2],
                                optionD = options[3],
                                correctOption = letter,
                                explanation = "25 + (${num - 50}) | (${num - 50})² = $ans"
                            )
                        )
                    } else {
                        val a = if (difficulty == 0) Random.nextInt(11, 45) else if (difficulty == 1) Random.nextInt(111, 456) else Random.nextInt(1111, 4567)
                        val b = if (difficulty == 0) Random.nextInt(11, 45) else if (difficulty == 1) Random.nextInt(111, 456) else Random.nextInt(1111, 4567)
                        val dsA = a.toString().map { it.digitToInt() }.sum() % 9
                        val dsB = b.toString().map { it.digitToInt() }.sum() % 9
                        val dsAns = (dsA * dsB) % 9
                        val correctDS = if (dsAns == 0) 9 else dsAns
                        val wrong1 = (correctDS % 9) + 1
                        val wrong2 = (wrong1 % 9) + 1
                        val wrong3 = (wrong2 % 9) + 1
                        val options = listOf(correctDS.toString(), wrong1.toString(), wrong2.toString(), wrong3.toString()).shuffled()
                        val correctIdx = options.indexOf(correctDS.toString())
                        val letter = listOf("a", "b", "c", "d")[correctIdx]
                        list.add(
                            QuizQuestion(
                                chapterId = 14,
                                chapterTitle = "SSC CGL Essentials",
                                questionText = "Digital Sum of ($a × $b) is?",
                                optionA = options[0],
                                optionB = options[1],
                                optionC = options[2],
                                optionD = options[3],
                                correctOption = letter,
                                explanation = "DS($a) × DS($b) = $dsA × $dsB = $dsAns => $correctDS"
                            )
                        )
                    }
                }
                else -> {
                    // Fallback should ideally never happen now, but just in case
                    val a = Random.nextInt(2, 20)
                    val b = Random.nextInt(2, 20)
                    val ans = a * b
                    val wrong1 = ans + a
                    val wrong2 = ans - a
                    val wrong3 = ans + 10
                    val options = listOf(ans.toString(), wrong1.toString(), wrong2.toString(), wrong3.toString()).shuffled()
                    val correctIdx = options.indexOf(ans.toString())
                    val letter = listOf("a", "b", "c", "d")[correctIdx]
                    list.add(
                        QuizQuestion(
                            chapterId = typeId,
                            chapterTitle = "Chapter $typeId",
                            questionText = "$a × $b =",
                            optionA = options[0],
                            optionB = options[1],
                            optionC = options[2],
                            optionD = options[3],
                            correctOption = letter,
                            explanation = "Table memory: $a × $b = $ans"
                        )
                    )
                }
            }
        }
        return list
    }
}
"""

with open("app/src/main/java/com/example/data/repository/CalculationRepository.kt", "w") as f:
    f.write(content[:start_idx] + new_func)

