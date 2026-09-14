package com.example.util

import com.example.ui.viewmodel.AppScreen
import kotlin.math.abs
import kotlin.math.roundToInt
import kotlin.math.sqrt

data class SmartMathAnalysis(
    val query: String,
    val result: String,
    val title: String,
    val method: String,
    val explanationSteps: List<String>,
    val mentalShortcut: String,
    val digitalRootCheck: String? = null,
    val targetScreen: AppScreen? = null
)

object SmartMathAssistant {

    fun digitalRoot(n: Long): Int {
        val absN = abs(n)
        if (absN == 0L) return 0
        val rem = (absN % 9).toInt()
        return if (rem == 0) 9 else rem
    }

    fun analyze(input: String): SmartMathAnalysis? {
        val q = input.trim().lowercase()
        if (q.isBlank()) return null

        // 1. Navigation / Keyword Queries
        when {
            q.contains("focus") || q.contains("clock") || q.contains("pomodoro") || q.contains("zen") -> {
                return SmartMathAnalysis(
                    query = input,
                    result = "Launch Focus Clock",
                    title = "⏱️ Focus Mode Clock",
                    method = "Deep Study & Pomodoro 25m/50m Timer",
                    explanationSteps = listOf(
                        "Track uninterrupted focus sprints with Alpha Waves (432Hz) audio.",
                        "Scientifically proven to boost calculation retention by 300%."
                    ),
                    mentalShortcut = "Tip: 25 minutes of deep math drills beats 4 hours of distracted study.",
                    targetScreen = AppScreen.FOCUS_CLOCK
                )
            }
            q.contains("plan") || q.contains("countdown") || q.contains("exam") || q.contains("target") -> {
                return SmartMathAnalysis(
                    query = input,
                    result = "Open Study Planner",
                    title = "📅 Study Planner & Exam Countdown",
                    method = "Aspirant Goal Tracker & Days Left",
                    explanationSteps = listOf(
                        "View days left for SSC CGL, CHSL, CPO, and RRB NTPC.",
                        "Track daily checklist goals and rapid revision pocketbook."
                    ),
                    mentalShortcut = "Consistency in daily targets guarantees high accuracy in Tier-1.",
                    targetScreen = AppScreen.STUDY_PLANNER
                )
            }
            q.contains("geom") || q.contains("cylinder") || q.contains("cone") || q.contains("sphere") || q.contains("3d") -> {
                return SmartMathAnalysis(
                    query = input,
                    result = "Open 3D Geometry Studio",
                    title = "📐 3D Geometry Visualizer",
                    method = "Interactive Dimension Sliders & Mensuration Hacks",
                    explanationSteps = listOf(
                        "Live 3D interactive shapes with real-time formulas.",
                        "Examine volume, curved surface area (CSA), and total surface area (TSA)."
                    ),
                    mentalShortcut = "Remember: In right circular cone, l² = r² + h² (Pythagoras rule).",
                    targetScreen = AppScreen.GEOMETRY
                )
            }
            q.contains("graph") || q.contains("algebra") -> {
                return SmartMathAnalysis(
                    query = input,
                    result = "Open Algebra Studio",
                    title = "📈 3D Algebra Visualizer",
                    method = "Polynomial Curve Plotting & Identities",
                    explanationSteps = listOf(
                        "Visualize y = ax² + bx + c, roots, and symmetry.",
                        "Master symmetric relations and Euler identities."
                    ),
                    mentalShortcut = "If a + b + c = 0, then a³ + b³ + c³ = 3abc.",
                    targetScreen = AppScreen.ALGEBRA_GRAPH
                )
            }
            q.contains("gym") || q.contains("brain") -> {
                return SmartMathAnalysis(
                    query = input,
                    result = "Open Math Brain Gym",
                    title = "🏋️ Math Brain Gym",
                    method = "Unlimited Rapid Calculation Drills",
                    explanationSteps = listOf(
                        "Practice Tables, Squares, Cubes, Additions, and Fractions.",
                        "Choose Easy or Hard difficulty mode."
                    ),
                    mentalShortcut = "Daily 10-minute Gym drill sharpens mental arithmetic.",
                    targetScreen = AppScreen.MATH_GYM
                )
            }
            q.contains("vedic") || q.contains("trick") -> {
                return SmartMathAnalysis(
                    query = input,
                    result = "Open Vedic Tricks",
                    title = "🕉️ Vedic Math Vault",
                    method = "16 Sutras for 10x Mental Speed",
                    explanationSteps = listOf(
                        "Ekadhikena Purvena, Nikhilam Navatashcaramam, Urdhva Tiryagbhyam.",
                        "Instant 2-second answers for huge calculations."
                    ),
                    mentalShortcut = "Vedic math turns pen-paper problems into pure mental visualization.",
                    targetScreen = AppScreen.VEDIC_TRICKS
                )
            }
        }

        // 2. Table Queries (e.g. "table 19", "19 table", "19 x 7")
        val tableMatch = Regex("""(?:table\s*(?:of)?\s*(\d{1,3}))|(\d{1,3})\s*table""").find(q)
        if (tableMatch != null) {
            val num = (tableMatch.groupValues[1].ifEmpty { tableMatch.groupValues[2] }).toIntOrNull() ?: 19
            val rows = (1..10).map { i -> "$num × $i = ${num * i}" }
            return SmartMathAnalysis(
                query = input,
                result = "$num Table (1 to 10)",
                title = "🔢 Multiplication Table of $num",
                method = "Split & Add Mental Strategy",
                explanationSteps = rows.take(5) + listOf("...", "$num × 10 = ${num * 10}"),
                mentalShortcut = "Split method: For $num × k, calculate ${(num/10)*10} × k + ${(num%10)} × k mentally.",
                targetScreen = AppScreen.TABLES
            )
        }

        // 3. Fraction to Percentage (e.g. "3/8", "1/7", "5/6", "fraction 3/8")
        val fractionMatch = Regex("""(?:fraction\s*)?(\d{1,2})\s*/\s*(\d{1,2})""").find(q)
        if (fractionMatch != null) {
            val num = fractionMatch.groupValues[1].toDoubleOrNull() ?: 1.0
            val den = fractionMatch.groupValues[2].toDoubleOrNull() ?: 1.0
            if (den != 0.0) {
                val percent = (num / den) * 100.0
                val percentStr = String.format("%.2f%%", percent).trimEnd('0').trimEnd('.') + "%"
                val decimalStr = String.format("%.4f", num / den).trimEnd('0').trimEnd('.')
                return SmartMathAnalysis(
                    query = input,
                    result = "$percentStr (Decimal: $decimalStr)",
                    title = "📊 Fraction to Percentage: ${num.toInt()}/${den.toInt()}",
                    method = "Reciprocal Multiplier Method",
                    explanationSteps = listOf(
                        "Base 1/${den.toInt()} = ${String.format("%.2f", 100.0 / den)}%",
                        "Multiply by numerator ${num.toInt()}: ${num.toInt()} × ${String.format("%.2f", 100.0 / den)}% = $percentStr"
                    ),
                    mentalShortcut = "Memorize prime fractions: 1/7 = 14.28%, 1/8 = 12.5%, 1/9 = 11.11%, 1/11 = 9.09%."
                )
            }
        }

        // 4. Percentage of Number (e.g. "15% of 840", "15 % 840")
        val percentOfMatch = Regex("""(\d+(?:\.\d+)?)\s*%\s*(?:of\s*)?(\d+(?:\.\d+)?)""").find(q)
        if (percentOfMatch != null) {
            val p = percentOfMatch.groupValues[1].toDoubleOrNull() ?: 10.0
            val n = percentOfMatch.groupValues[2].toDoubleOrNull() ?: 100.0
            val ans = (p * n) / 100.0
            val ansStr = if (ans == ans.toLong().toDouble()) ans.toLong().toString() else String.format("%.2f", ans)
            return SmartMathAnalysis(
                query = input,
                result = ansStr,
                title = "⚡ Percentage Value: $p% of $n",
                method = "10% & 1% Chunking Method",
                explanationSteps = listOf(
                    "Step 1: 10% of $n = ${n / 10.0}",
                    "Step 2: 1% of $n = ${n / 100.0}",
                    "Step 3: Combine chunks to get $p% = $ansStr"
                ),
                mentalShortcut = "Always break percentages into 50%, 10%, 5%, 1% for mental speed without pen!",
                digitalRootCheck = "DR($ansStr) = ${digitalRoot(ans.toLong())}"
            )
        }

        // 5. Square of Number (e.g. "75^2", "square 75", "75*75", "75 x 75")
        val squareMatch = Regex("""(?:(?:square\s*(?:of)?\s*(\d{1,4}))|(\d{1,4})\s*(?:\^2|²)|(\d{1,4})\s*[x*×]\s*\3)""").find(q)
        if (squareMatch != null) {
            val nStr = squareMatch.groupValues.drop(1).firstOrNull { it.isNotEmpty() } ?: "25"
            val n = nStr.toLongOrNull() ?: 25L
            val ans = n * n

            val steps = mutableListOf<String>()
            val trick: String

            if (n % 10 == 5L) {
                // Ending in 5
                val prefix = n / 10
                val product = prefix * (prefix + 1)
                steps.add("Unit digit is 5: Use 'Ekadhikena Purvena' (One more than previous)")
                steps.add("Prefix = $prefix → $prefix × ($prefix + 1) = $product")
                steps.add("Append 25: $product | 25 = $ans")
                trick = "For any number ending in 5, multiply tens by (tens + 1) and append 25!"
            } else if (n in 40..60) {
                // Near 50
                val diff = n - 50
                val base25 = 25 + diff
                val sqDiff = diff * diff
                val sqDiffStr = String.format("%02d", sqDiff)
                steps.add("Base 50 Strategy: Difference from 50 = $diff")
                steps.add("Left part = 25 + ($diff) = $base25")
                steps.add("Right part = ($diff)² = $sqDiffStr")
                steps.add("Combine: $base25 | $sqDiffStr = $ans")
                trick = "For numbers near 50, compare with 50: (25 ± d) | d²"
            } else if (n in 90..110) {
                // Near 100
                val diff = n - 100
                val base = n + diff
                val sqDiff = diff * diff
                val sqDiffStr = String.format("%02d", sqDiff)
                steps.add("Base 100 Strategy: Difference from 100 = $diff")
                steps.add("Left part = $n + ($diff) = $base")
                steps.add("Right part = ($diff)² = $sqDiffStr")
                steps.add("Combine: $base | $sqDiffStr = $ans")
                trick = "For numbers near 100: Add difference to number, append (diff)²"
            } else {
                val a = n / 10
                val b = n % 10
                steps.add("(a + b)² Expansion: ($n = ${a*10} + $b)")
                steps.add("a² = ${(a*a)*100}, 2ab = ${2*a*b*10}, b² = ${b*b}")
                steps.add("Sum = ${(a*a)*100} + ${2*a*b*10} + ${b*b} = $ans")
                trick = "Use (a + b)² = a² + 2ab + b² mentally in left-to-right chunks."
            }

            return SmartMathAnalysis(
                query = input,
                result = "$ans",
                title = "⚡ Square of $n: $n² = $ans",
                method = "Inspector Smart Square Engine",
                explanationSteps = steps,
                mentalShortcut = trick,
                digitalRootCheck = "DR($ans) = ${digitalRoot(ans)} (Digital root of squares must be 1, 4, 7, or 9)"
            )
        }

        // 6. Cube of Number (e.g. "12^3", "cube 12", "12³")
        val cubeMatch = Regex("""(?:(?:cube\s*(?:of)?\s*(\d{1,3}))|(\d{1,3})\s*(?:\^3|³))""").find(q)
        if (cubeMatch != null) {
            val nStr = cubeMatch.groupValues.drop(1).firstOrNull { it.isNotEmpty() } ?: "12"
            val n = nStr.toLongOrNull() ?: 12L
            val ans = n * n * n
            return SmartMathAnalysis(
                query = input,
                result = "$ans",
                title = "🎲 Cube of $n: $n³ = $ans",
                method = "Vedic Ratio Cube Expansion",
                explanationSteps = listOf(
                    "Identity: (a + b)³ = a³ + 3a²b + 3ab² + b³",
                    "For $n: $n × $n = ${n*n}, then ${n*n} × $n = $ans",
                    "High yield SSC cube: memorize 1³ to 25³ for instant Tier-1 accuracy."
                ),
                mentalShortcut = "Cubes 1 to 15 are tested directly in CI & Compound Growth questions.",
                digitalRootCheck = "DR($ans) = ${digitalRoot(ans)}"
            )
        }

        // 7. Square Root (e.g. "sqrt 7056", "root 7056", "√7056")
        val sqrtMatch = Regex("""(?:(?:sqrt|root|√)\s*(\d+))""").find(q)
        if (sqrtMatch != null) {
            val n = sqrtMatch.groupValues[1].toLongOrNull() ?: 100L
            val root = sqrt(n.toDouble())
            val isExact = root == root.toLong().toDouble()
            val ansStr = if (isExact) root.toLong().toString() else String.format("%.3f", root)

            val steps = if (isExact) {
                val lastDigit = n % 10
                val possibleUnits = when (lastDigit.toInt()) {
                    1 -> "1 or 9"
                    4 -> "2 or 8"
                    5 -> "5"
                    6 -> "4 or 6"
                    9 -> "3 or 7"
                    0 -> "0"
                    else -> "Non-perfect square"
                }
                listOf(
                    "Unit digit is $lastDigit → Root must end in: $possibleUnits",
                    "Exclude last 2 digits (${n.toString().takeLast(2)}), remaining base is ${n / 100}",
                    "Largest square <= ${n / 100} determines the tens digit.",
                    "Exact Root = $ansStr"
                )
            } else {
                listOf(
                    "Nearest lower perfect square: ${(root.toLong() * root.toLong())} (Root ${root.toLong()})",
                    "Approx: ${root.toLong()} + ${(n - root.toLong()*root.toLong())} / (2 × ${root.toLong()}) ≈ $ansStr"
                )
            }

            return SmartMathAnalysis(
                query = input,
                result = ansStr,
                title = "📐 Square Root: √$n = $ansStr",
                method = "Unit Digit & Nearest Base Extraction",
                explanationSteps = steps,
                mentalShortcut = if (isExact) "Perfect square unit digits rule: 1->(1,9), 4->(2,8), 5->5, 6->(4,6), 9->(3,7)." else "Approximation rule: √x ≈ √A + (x - A)/(2√A)",
                digitalRootCheck = "DR($n) = ${digitalRoot(n)}"
            )
        }

        // 8. Multiplication (e.g. "48 x 52", "63 * 67", "104 * 107", "45 * 99")
        val multMatch = Regex("""(\d+)\s*[*x×]\s*(\d+)""").find(q)
        if (multMatch != null) {
            val a = multMatch.groupValues[1].toLongOrNull() ?: 10L
            val b = multMatch.groupValues[2].toLongOrNull() ?: 10L
            val ans = a * b

            val steps = mutableListOf<String>()
            val methodTitle: String
            val trick: String

            // Check (x - d)(x + d) = x² - d²
            val sum = a + b
            if (sum % 2 == 0L) {
                val mid = sum / 2
                val diff = abs(a - mid)
                if (diff in 1..10) {
                    methodTitle = "Difference of Squares Formula: (x - d)(x + d) = x² - d²"
                    steps.add("Midpoint between $a and $b is $mid, difference is $diff")
                    steps.add("Rewrite: ($mid - $diff) × ($mid + $diff) = $mid² - $diff²")
                    steps.add("Calculation: ${mid * mid} - ${diff * diff} = $ans")
                    trick = "Whenever two numbers have an easy even midpoint (e.g. 50, 40, 100), square the midpoint and subtract d²!"
                    return SmartMathAnalysis(
                        query = input,
                        result = "$ans",
                        title = "⚡ Mental Product: $a × $b = $ans",
                        method = methodTitle,
                        explanationSteps = steps,
                        mentalShortcut = trick,
                        digitalRootCheck = "DR($a)×DR($b) = ${digitalRoot(a)}×${digitalRoot(b)} → DR(${digitalRoot(a)*digitalRoot(b)}) == DR($ans): ${digitalRoot(digitalRoot(a).toLong()*digitalRoot(b)) == digitalRoot(ans)}"
                    )
                }
            }

            // Check multiplying by 99 / 999
            if (b == 99L || a == 99L) {
                val other = if (b == 99L) a else b
                methodTitle = "Vedic Multiply by 99: Base 100 Shift"
                steps.add("$other × (100 - 1) = ${other * 100} - $other = $ans")
                trick = "Multiply by 100 and subtract the number itself! Instant in 2 seconds."
                return SmartMathAnalysis(
                    query = input,
                    result = "$ans",
                    title = "⚡ Mental Product: $a × $b = $ans",
                    method = methodTitle,
                    explanationSteps = steps,
                    mentalShortcut = trick,
                    digitalRootCheck = "Digital root of product with 9/99 is always 9: DR($ans) = ${digitalRoot(ans)}"
                )
            }

            // Check Base 100 (e.g. 104 x 107)
            if (a in 101..120 && b in 101..120) {
                val d1 = a - 100
                val d2 = b - 100
                val left = a + d2
                val right = d1 * d2
                val rightStr = String.format("%02d", right)
                methodTitle = "Vedic Nikhilam Base 100 Sutra"
                steps.add("Surplus from 100: +$d1 and +$d2")
                steps.add("Left Part: $a + $d2 = $left")
                steps.add("Right Part: $d1 × $d2 = $rightStr")
                steps.add("Combine: $left$rightStr = $ans")
                trick = "Base 100: (100 + a)(100 + b) = (100 + a + b) | ab"
                return SmartMathAnalysis(
                    query = input,
                    result = "$ans",
                    title = "⚡ Mental Product: $a × $b = $ans",
                    method = methodTitle,
                    explanationSteps = steps,
                    mentalShortcut = trick,
                    digitalRootCheck = "DR($ans) = ${digitalRoot(ans)}"
                )
            }

            // Default Cross Multiplication
            methodTitle = "Urdhva Tiryagbhyam (Cross-Multiplication)"
            steps.add("Breakdown: ($a) × ($b) = $ans")
            steps.add("Digital root verification: DR($a)×DR($b) = ${digitalRoot(a)*digitalRoot(b)} → ${digitalRoot(ans)}")
            trick = "Inspector Cross Multiply: Unit×Unit, Cross-Sum, Tens×Tens."
            return SmartMathAnalysis(
                query = input,
                result = "$ans",
                title = "⚡ Mental Product: $a × $b = $ans",
                method = methodTitle,
                explanationSteps = steps,
                mentalShortcut = trick,
                digitalRootCheck = "DR($ans) = ${digitalRoot(ans)}"
            )
        }

        // 9. Pythagorean Triplet (e.g. "triplet 7", "triplet 8", "pythagoras 5")
        val tripletMatch = Regex("""(?:triplet|pythagoras)\s*(\d+)""").find(q)
        if (tripletMatch != null) {
            val n = tripletMatch.groupValues[1].toIntOrNull() ?: 3
            val triplet = when (n) {
                3, 4, 5 -> "3 - 4 - 5 (Hypotenuse: 5)"
                5, 12, 13 -> "5 - 12 - 13 (Hypotenuse: 13)"
                7, 24, 25 -> "7 - 24 - 25 (Hypotenuse: 25)"
                8, 15, 17 -> "8 - 15 - 17 (Hypotenuse: 17)"
                9, 40, 41 -> "9 - 40 - 41 (Hypotenuse: 41)"
                11, 60, 61 -> "11 - 60 - 61 (Hypotenuse: 61)"
                12, 35, 37 -> "12 - 35 - 37 (Hypotenuse: 37)"
                20, 21, 29 -> "20 - 21 - 29 (Hypotenuse: 29)"
                else -> {
                    if (n % 2 != 0) {
                        val n2 = n * n
                        val b = (n2 - 1) / 2
                        val c = (n2 + 1) / 2
                        "$n - $b - $c (Formula: (n²-1)/2, (n²+1)/2)"
                    } else {
                        val half = n / 2
                        val b = half * half - 1
                        val c = half * half + 1
                        "$n - $b - $c (Formula: (n/2)²-1, (n/2)²+1)"
                    }
                }
            }
            return SmartMathAnalysis(
                query = input,
                result = triplet,
                title = "📐 Pythagorean Triplet for $n",
                method = "Odd/Even Triplet Generator Rule",
                explanationSteps = listOf(
                    "Triplet: $triplet",
                    "Verification: a² + b² = c² holds true for right-angled triangles."
                ),
                mentalShortcut = "Over 80% of SSC mensuration right triangle questions use these triplets directly without calculating square roots."
            )
        }

        return null
    }
}
