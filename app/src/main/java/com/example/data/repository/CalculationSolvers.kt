package com.example.data.repository

import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt

object CalculationSolvers {

    data class SolverResult(
        val finalAnswer: String,
        val methodTitle: String,
        val steps: List<String>,
        val mentalNote: String
    )

    // 1. Cross Multiplication (2-digit x 2-digit)
    fun solveCrossMultiplication(num1: Int, num2: Int): SolverResult {
        val a = num1 / 10
        val b = num1 % 10
        val c = num2 / 10
        val d = num2 % 10

        val step1 = b * d
        val step1Write = step1 % 10
        val step1Carry = step1 / 10

        val step2 = (a * d) + (b * c) + step1Carry
        val step2Write = step2 % 10
        val step2Carry = step2 / 10

        val step3 = (a * c) + step2Carry

        val ans = num1 * num2

        val steps = listOf(
            "Step 1: इकाई × इकाई (Unit digits product) → $b × $d = $step1. इकाई पर $step1Write लिखें, हासिल (carry) = $step1Carry",
            "Step 2: कैंची गुणा (Cross multiply & add) → ($a×$d) + ($b×$c) + $step1Carry = ${(a*d)} + ${(b*c)} + $step1Carry = $step2. दहाई पर $step2Write लिखें, हासिल = $step2Carry",
            "Step 3: दहाई × दहाई (Tens digits product + carry) → ($a × $c) + $step2Carry = ${(a*c)} + $step2Carry = $step3.",
            "Step 4: संयुक्त मान → $step3$step2Write$step1Write = $ans"
        )

        return SolverResult(
            finalAnswer = ans.toString(),
            methodTitle = "Inspector कैंची गुणा विधि (Vedic Cross Multiplication)",
            steps = steps,
            mentalNote = "बिना पेन-कॉपी के दिमाग में 3 हिस्सों को जोड़ते हुए सीधा उत्तर लिखें!"
        )
    }

    // 2. Square Solver (2-digit)
    fun solveSquare(num: Int): SolverResult {
        val tens = num / 10
        val unit = num % 10

        val uSquare = unit * unit
        val uWrite = uSquare % 10
        val uCarry = uSquare / 10

        val middle = (2 * tens * unit) + uCarry
        val mWrite = middle % 10
        val mCarry = middle / 10

        val tSquare = (tens * tens) + mCarry
        val ans = num * num

        val steps = listOf(
            "Step 1: इकाई का वर्ग (Unit²) → $unit² = $uSquare. अंक $uWrite लिखें, हासिल = $uCarry",
            "Step 2: 2 × दहाई × इकाई + हासिल → 2 × $tens × $unit + $uCarry = ${(2 * tens * unit)} + $uCarry = $middle. अंक $mWrite लिखें, हासिल = $mCarry",
            "Step 3: दहाई का वर्ग + हासिल → $tens² + $mCarry = ${(tens * tens)} + $mCarry = $tSquare.",
            "Step 4: Answer = $tSquare$mWrite$uWrite = $ans"
        )

        return SolverResult(
            finalAnswer = ans.toString(),
            methodTitle = "Inspector Square Shortcut ((a+b)² = a² | 2ab | b²)",
            steps = steps,
            mentalNote = "दिमाग में $tens² और 2×$tens×$unit को अलग रखें और इकाई वर्ग को जोड़ें।"
        )
    }

    // 3. Cube Solver (2-digit)
    fun solveCube(num: Int): SolverResult {
        val a = num / 10
        val b = num % 10

        val b3 = b * b * b
        val step1Write = b3 % 10
        val step1Carry = b3 / 10

        val term2 = (3 * a * b * b) + step1Carry
        val step2Write = term2 % 10
        val step2Carry = term2 / 10

        val term3 = (3 * a * a * b) + step2Carry
        val step3Write = term3 % 10
        val step3Carry = term3 / 10

        val term4 = (a * a * a) + step3Carry
        val ans = num * num * num

        val steps = listOf(
            "Step 1: b³ (इकाई का घन) → $b³ = $b3. लिखें $step1Write, हासिल = $step1Carry",
            "Step 2: 3ab² + हासिल → 3 × $a × $b² + $step1Carry = ${(3*a*b*b)} + $step1Carry = $term2. लिखें $step2Write, हासिल = $step2Carry",
            "Step 3: 3a²b + हासिल → 3 × $a² × $b + $step2Carry = ${(3*a*a*b)} + $step2Carry = $term3. लिखें $step3Write, हासिल = $step3Carry",
            "Step 4: a³ + हासिल → $a³ + $step3Carry = ${(a*a*a)} + $step3Carry = $term4.",
            "Step 5: Answer = $ans"
        )

        return SolverResult(
            finalAnswer = ans.toString(),
            methodTitle = "Inspector Cube Formula ((a+b)³ = a³ | 3a²b | 3ab² | b³)",
            steps = steps,
            mentalNote = "चारों स्तम्भों को क्रमिक रूप से दिमाग में हल करें।"
        )
    }

    // 4. Net Percentage Change
    fun solveNetPercentage(x: Double, y: Double, xIncrease: Boolean, yIncrease: Boolean): SolverResult {
        val signX = if (xIncrease) x else -x
        val signY = if (yIncrease) y else -y

        val sumPart = signX + signY
        val productPart = (signX * signY) / 100.0
        val net = sumPart + productPart

        val steps = listOf(
            "Step 1: x + y = ${if (signX >= 0) "+$x" else "-$x"} ${if (signY >= 0) "+ $y" else "- $y"} = %.2f%%".format(sumPart),
            "Step 2: (x × y) / 100 = (%.2f × %.2f) / 100 = %+.4f%%".format(signX, signY, productPart),
            "Step 3: Net %% = %.2f + (%+.4f) = %+.2f%%".format(sumPart, productPart, net)
        )

        val direction = if (net >= 0) "कुल वृद्धि (Net Increase)" else "कुल कमी (Net Decrease)"

        return SolverResult(
            finalAnswer = "%.2f%%".format(net),
            methodTitle = "Net Percentage Change (x + y + xy/100)",
            steps = steps,
            mentalNote = "$direction: %.2f%%".format(abs(net))
        )
    }

    // 5. CI Net Rate & Difference
    fun solveCompoundInterest(rate: Double, years: Double): SolverResult {
        val r = rate
        return if (years == 2.0) {
            val simpleInterest = 2 * r
            val ciPart = (r * r) / 100.0
            val netCi = simpleInterest + ciPart
            val diff = ciPart

            SolverResult(
                finalAnswer = "CI Rate: %.2f%% | Diff (CI-SI): %.4f%%".format(netCi, diff),
                methodTitle = "2-Year CI Net Rate & Difference",
                steps = listOf(
                    "Step 1: 2 वर्ष का SI = 2 × $r = %.2f%%".format(simpleInterest),
                    "Step 2: ब्याज पर ब्याज (R²/100) = $r² / 100 = %.4f%%".format(ciPart),
                    "Step 3: 2 वर्ष की कुल CI दर = 2R + R²/100 = %.4f%%".format(netCi),
                    "Step 4: CI और SI का अंतर = (R²/100)%% = %.4f%%".format(diff)
                ),
                mentalNote = "2 वर्ष के लिए हमेशा 2a.a²/100 याद रखें!"
            )
        } else if (years == 3.0) {
            val netCi = 3 * r + 3 * (r * r) / 100.0 + (r * r * r) / 10000.0
            val si = 3 * r
            val diff = netCi - si

            SolverResult(
                finalAnswer = "CI Rate: %.4f%% | Diff (CI-SI): %.4f%%".format(netCi, diff),
                methodTitle = "3-Year CI Net Rate (3a . 3a² a³)",
                steps = listOf(
                    "Step 1: 3a भाग = 3 × $r = %.2f%%".format(3 * r),
                    "Step 2: 3a²/100 भाग = 3 × $r² / 100 = %.4f%%".format(3 * r * r / 100.0),
                    "Step 3: a³/10000 भाग = $r³ / 10000 = %.6f%%".format(r.pow(3) / 10000.0),
                    "Step 4: कुल CI दर = %.4f%% | अंतर (CI-SI) = %.4f%%".format(netCi, diff)
                ),
                mentalNote = "3 वर्ष के लिए 3a.3a²a³ विधि सबसे तेज है!"
            )
        } else {
            val cycles = years
            val netCi = ((1.0 + r / 100.0).pow(cycles) - 1.0) * 100.0
            SolverResult(
                finalAnswer = "%.4f%%".format(netCi),
                methodTitle = "Compounded Multi-Period ($years cycles)",
                steps = listOf(
                    "Effective rate for $years periods at $r% per period = ((1 + $r/100)^$years - 1) × 100 = %.4f%%".format(netCi)
                ),
                mentalNote = "प्रति चक्र दर और चक्रों की कुल संख्या का गुणा।"
            )
        }
    }

    // 6. Time & Work Converter
    fun solveTimeWork(fractionNum: Int, fractionDen: Int, daysGiven: Double): SolverResult {
        val totalDays = daysGiven * fractionDen / fractionNum.toDouble()

        val steps = listOf(
            "Step 1: दिया गया कार्य = $fractionNum / $fractionDen भाग = $daysGiven दिन",
            "Step 2: 1 भाग (1 unit) का समय = $daysGiven ÷ $fractionNum = %.2f दिन".format(daysGiven / fractionNum),
            "Step 3: कुल कार्य ($fractionDen units) = %.2f × $fractionDen = %.2f दिन".format(daysGiven / fractionNum, totalDays)
        )

        return SolverResult(
            finalAnswer = if (totalDays % 1.0 == 0.0) "${totalDays.toInt()} Days" else "%.2f Days".format(totalDays),
            methodTitle = "Time & Work Fractional Converter",
            steps = steps,
            mentalNote = "सीधा गुणा करें: दिन × (हर / अंश) = कुल दिन!"
        )
    }

    // 7. LCM & HCF Solver
    fun solveLcmHcf(a: Long, b: Long): SolverResult {
        val hcf = calculateHcf(a, b)
        val lcm = (a * b) / hcf

        val steps = listOf(
            "Step 1: पहली संख्या = $a, दूसरी संख्या = $b",
            "Step 2: HCF (महत्तम समापवर्तक) = $hcf (दोनों को विभाजित करने वाली सबसे बड़ी संख्या)",
            "Step 3: LCM सूत्र = (संख्या 1 × संख्या 2) / HCF = ($a × $b) / $hcf = $lcm"
        )

        return SolverResult(
            finalAnswer = "HCF = $hcf | LCM = $lcm",
            methodTitle = "Inspector LCM & HCF Engine",
            steps = steps,
            mentalNote = "LCM = बड़ी संख्या × (छोटी संख्या का non-common factor)"
        )
    }

    private fun calculateHcf(n1: Long, n2: Long): Long {
        var x = n1
        var y = n2
        while (y != 0L) {
            val t = y
            y = x % y
            x = t
        }
        return x
    }


    // --- SSC CGL PYQ Dynamic Concept Solvers (Percentage) ---

    // 8. Sugar Price / Expenditure Concept
    fun solvePyqPriceConsumption(rateChange: Double, isIncrease: Boolean): SolverResult {
        val numerator = rateChange
        val denominator = if (isIncrease) 100.0 + rateChange else 100.0 - rateChange
        val ansPercent = (numerator / denominator) * 100.0
        val increaseOrDecrease = if (isIncrease) "कमी (Decrease)" else "वृद्धि (Increase)"
        
        val steps = listOf(
            "SSC CGL PYQ Concept: चीनी / पेट्रोल की कीमत पर आधारित",
            "सूत्र: [ R / (100 ± R) ] × 100",
            "Step 1: R = $rateChange%",
            "Step 2: चूँकि कीमत में ${if(isIncrease) "वृद्धि" else "कमी"} हुई है, इसलिए हर (Denominator) = 100 ${if(isIncrease) "+" else "-"} $rateChange = $denominator",
            "Step 3: खपत में $increaseOrDecrease = ($numerator / $denominator) × 100",
            "Step 4: = %.2f%%".format(ansPercent)
        )
        return SolverResult(
            finalAnswer = "%.2f%% $increaseOrDecrease".format(ansPercent),
            methodTitle = "Price-Consumption Concept (SSC PYQ)",
            steps = steps,
            mentalNote = "R / (100±R) × 100: अंश समान रखें, हर में R जोड़ें/घटाएं।"
        )
    }

    // 9. Election Concept
    fun solvePyqElection(winnerPercent: Double, majorityVotes: Double): SolverResult {
        val loserPercent = 100.0 - winnerPercent
        val diffPercent = winnerPercent - loserPercent
        
        if (diffPercent <= 0) {
             return SolverResult(
                finalAnswer = "Invalid Input",
                methodTitle = "Election Concept (SSC PYQ)",
                steps = listOf("विजेता का प्रतिशत 50% से अधिक होना चाहिए।"),
                mentalNote = "Error"
            )
        }
        
        val totalVotes = (majorityVotes / diffPercent) * 100.0
        
        val steps = listOf(
            "SSC CGL PYQ Concept: चुनाव (विजेता और हारने वाला)",
            "Step 1: कुल वोट हमेशा 100% होते हैं।",
            "Step 2: विजेता को मिले = $winnerPercent%",
            "Step 3: हारने वाले को मिले = 100% - $winnerPercent% = $loserPercent%",
            "Step 4: जीत का अंतर = $winnerPercent% - $loserPercent% = $diffPercent%",
            "Step 5: प्रश्न अनुसार $diffPercent% = $majorityVotes वोट",
            "Step 6: कुल वोट (100%) = ($majorityVotes / $diffPercent) × 100 = ${totalVotes.toLong()} वोट"
        )
        return SolverResult(
            finalAnswer = "${totalVotes.toLong()} Votes",
            methodTitle = "Election Concept (SSC PYQ)",
            steps = steps,
            mentalNote = "अंतर % = बहुमत वोट ➔ 100% निकालें।"
        )
    }

    // 10. Pass / Fail Marks Concept
    fun solvePyqPassFail(failPercent: Double, failMarks: Double, passPercent: Double, extraMarks: Double): SolverResult {
        val diffPercent = passPercent - failPercent
        val diffMarks = failMarks + extraMarks
        
        if (diffPercent <= 0) {
             return SolverResult(
                finalAnswer = "Invalid Input",
                methodTitle = "Pass/Fail Marks Concept (SSC PYQ)",
                steps = listOf("पास प्रतिशत, फेल प्रतिशत से अधिक होना चाहिए।"),
                mentalNote = "Error"
            )
        }
        
        val totalMaxMarks = (diffMarks / diffPercent) * 100.0
        val passingMarks = (failPercent / 100.0) * totalMaxMarks + failMarks
        
        val steps = listOf(
            "SSC CGL PYQ Concept: पास और फेल अंकों पर आधारित",
            "Step 1: दोनों छात्रों के अंकों का अंतर = फेल अंक ($failMarks) + अतिरिक्त अंक ($extraMarks) = $diffMarks",
            "Step 2: दोनों छात्रों के प्रतिशत का अंतर = $passPercent% - $failPercent% = $diffPercent%",
            "Step 3: इसलिए, $diffPercent% = $diffMarks अंक",
            "Step 4: कुल अधिकतम अंक (100%) = ($diffMarks / $diffPercent) × 100 = ${totalMaxMarks.toLong()}",
            "Step 5: पास होने के लिए आवश्यक अंक = (कुल का $failPercent%) + $failMarks = ${passingMarks.toLong()}"
        )
        
        return SolverResult(
            finalAnswer = "Max: ${totalMaxMarks.toLong()}, Pass: ${passingMarks.toLong()}",
            methodTitle = "Pass/Fail Marks Concept (SSC PYQ)",
            steps = steps,
            mentalNote = "अंकों का योग (Opposite sides) / प्रतिशत का अंतर × 100"
        )
    }
}
