package com.example.data.repository

import com.example.data.model.*
import kotlin.math.roundToInt
import kotlin.math.sqrt
import kotlin.random.Random

object CalculationRepository {

    val chapters: List<Chapter> by lazy {
        listOf(
            createAdditionChapter(),
            createSubtractionChapter(),
            createMultiplicationChapter(),
            createDivisionChapter(),
            createDecimalChapter(),
            createSurdsChapter(),
            createLcmHcfChapter(),
            createFractionChapter(),
            createPercentageChapter(),
            createProfitLossChapter(),
            createSiCiChapter(),
            createTimeWorkChapter(),
            createSpeedTricksChapter(),
            createSscCglEssentialsChapter()
        )
    }

    private fun createAdditionChapter(): Chapter {
        return Chapter(
            id = 1,
            titleEnglish = "Addition",
            titleHindi = "जोड़",
            description = "Master lightning fast addition using tens-first grouping, digit-flow, and column slicing techniques.",
            iconName = "plus",
            pageRange = "1-78",
            quickTip = "एक संख्या का दहाई (Tens) मानकर दिमाग में रखें फिर इकाई (Unit) जोड़ें।",
            types = listOf(
                CalculationType(
                    typeNumber = 1,
                    titleEnglish = "Tens-First Mental Addition (Type 1)",
                    titleHindi = "दहाई का अंक पहले मानकर जोड़",
                    hintHindi = "7 + 67: 67 के दहाई अंक 6 को 60 मानकर दिमाग में रखें, फिर (7 + 7 = 14) को 60 में जोड़ें -> 74।",
                    hintEnglish = "Round to the nearest lower tens digit, add the unit digits, and combine mentally in one step.",
                    formula = "a + (10t + u) = (10t) + (a + u)",
                    steps = listOf(
                        CalculationStep("Step 1", "67 → 60", "दहाई अंक (6) के आगे 0 मानकर 60 दिमाग में रखें", "Keep 60 in mind from tens digit 6"),
                        CalculationStep("Step 2", "7 + 7 = 14", "दोनों संख्याओं के इकाई अंक (7 + 7) जोड़ें", "Add unit digits: 7 + 7 = 14"),
                        CalculationStep("Step 3", "60 + 14 = 74", "दिमाग वाली संख्या (60) में 14 जोड़ें -> उत्तर 74", "Combine 60 + 14 = 74")
                    ),
                    exercises = listOf(
                        ExerciseProblem(1, "7 + 67 =", "75", "74", "81", "71", "b", "60 + (7 + 7) = 60 + 14 = 74"),
                        ExerciseProblem(2, "49 + 8 =", "96", "56", "59", "57", "d", "40 + (9 + 8) = 40 + 17 = 57"),
                        ExerciseProblem(3, "88 + 8 =", "96", "94", "103", "98", "a", "80 + (8 + 8) = 80 + 16 = 96"),
                        ExerciseProblem(4, "6 + 67 =", "51", "57", "73", "59", "c", "60 + (6 + 7) = 60 + 13 = 73"),
                        ExerciseProblem(5, "97 + 8 =", "103", "105", "108", "111", "b", "90 + (7 + 8) = 90 + 15 = 105"),
                        ExerciseProblem(6, "78 + 8 =", "90", "99", "88", "86", "d", "70 + (8 + 8) = 70 + 16 = 86"),
                        ExerciseProblem(7, "75 + 6 =", "88", "94", "87", "81", "d", "70 + (5 + 6) = 70 + 11 = 81"),
                        ExerciseProblem(8, "27 + 9 =", "35", "41", "36", "33", "c", "20 + (7 + 9) = 20 + 16 = 36")
                    )
                ),
                CalculationType(
                    typeNumber = 2,
                    titleEnglish = "Double Digit Tens-Combine (Type 2)",
                    titleHindi = "दो अंकों का सीधा दहाई जोड़",
                    hintHindi = "23 + 47: दोनों दहाई (2+4 = 6) -> 60। दोनों इकाई (3+7 = 10)। 60 + 10 = 70।",
                    hintEnglish = "Sum tens digits first, append zero, sum unit digits, and combine mentally.",
                    formula = "(10t₁ + u₁) + (10t₂ + u₂) = 10(t₁ + t₂) + (u₁ + u₂)",
                    steps = listOf(
                        CalculationStep("Step 1", "2 + 4 = 6 → 60", "दहाई अंक जोड़कर 0 लगाएँ", "Add tens digits (2+4)=6, make it 60"),
                        CalculationStep("Step 2", "3 + 7 = 10", "इकाई अंक जोड़ें", "Add units (3+7)=10"),
                        CalculationStep("Step 3", "60 + 10 = 70", "दोनों को मिलाकर उत्तर प्राप्त करें", "Combine: 60 + 10 = 70")
                    ),
                    exercises = listOf(
                        ExerciseProblem(1, "23 + 47 =", "72", "68", "70", "67", "c", "(20+40) + (3+7) = 60 + 10 = 70"),
                        ExerciseProblem(2, "48 + 55 =", "101", "103", "105", "107", "b", "(40+50) + (8+5) = 90 + 13 = 103"),
                        ExerciseProblem(3, "96 + 37 =", "130", "137", "133", "136", "c", "(90+30) + (6+7) = 120 + 13 = 133"),
                        ExerciseProblem(4, "43 + 86 =", "126", "129", "134", "123", "b", "(40+80) + (3+6) = 120 + 9 = 129"),
                        ExerciseProblem(5, "57 + 74 =", "137", "141", "144", "131", "d", "(50+70) + (7+4) = 120 + 11 = 131"),
                        ExerciseProblem(6, "43 + 39 =", "82", "87", "83", "79", "a", "(40+30) + (3+9) = 70 + 12 = 82"),
                        ExerciseProblem(7, "78 + 87 =", "168", "165", "164", "167", "b", "(70+80) + (8+7) = 150 + 15 = 165"),
                        ExerciseProblem(8, "97 + 79 =", "178", "179", "176", "175", "c", "(90+70) + (7+9) = 160 + 16 = 176")
                    )
                ),
                CalculationType(
                    typeNumber = 3,
                    titleEnglish = "Multi-Number Series Sum (Type 3)",
                    titleHindi = "सैकड़ा + दहाई + इकाई शृंखला जोड़",
                    hintHindi = "173 + 98 + 92 + 78 + 43 + 68 + 65 + 97: पहले 100, फिर दहाई शृंखला 570 (कुल 670), फिर इकाई 44 -> 714।",
                    hintEnglish = "Group hundreds first, sum all tens digits, then sum all single units and combine.",
                    formula = "Sum = Σ(Hundreds) + Σ(Tens×10) + Σ(Units)",
                    steps = listOf(
                        CalculationStep("Step 1", "Hundreds = 100", "सैकड़ों के स्थान को जोड़ें", "Sum hundreds"),
                        CalculationStep("Step 2", "7+9+9+7+4+6+6+9 = 57 → 570 + 100 = 670", "सभी दहाई अंक जोड़ें और 10 से गुणा करें", "Sum tens: 57 -> 570, add to 100 = 670"),
                        CalculationStep("Step 3", "3+8+2+8+3+8+5+7 = 44", "सभी इकाई अंक जोड़ें", "Sum unit digits = 44"),
                        CalculationStep("Step 4", "670 + 44 = 714", "कुल योग प्राप्त करें", "Final sum: 670 + 44 = 714")
                    ),
                    exercises = listOf(
                        ExerciseProblem(1, "173 + 98 + 92 + 78 + 43 + 68 + 65 + 97 =", "714", "814", "658", "865", "a", "100 + 570 + 44 = 714"),
                        ExerciseProblem(2, "84 + 93 + 94 + 73 + 57 + 37 + 41 + 82 =", "663", "461", "561", "462", "c", "Tens sum (8+9+9+7+5+3+4+8)=53 -> 530 + Units (31) = 561"),
                        ExerciseProblem(3, "69 + 96 + 93 + 43 + 47 + 55 + 59 =", "367", "572", "442", "462", "d", "Tens (420) + Units (42) = 462"),
                        ExerciseProblem(4, "73 + 47 + 58 + 68 + 78 + 97 + 80 =", "573", "501", "583", "494", "b", "Tens (470) + Units (31) = 501")
                    )
                ),
                CalculationType(
                    typeNumber = 7,
                    titleEnglish = "Digit Flow Continuous Addition (Type 7)",
                    titleHindi = "डिजिट फ्लो निरंतर जोड़",
                    hintHindi = "226789: सभी अंकों को एक flow में बिना रुके लगातार जोड़ते जाएँ -> 2+2=4+6=10+7=17+8=25+9=34।",
                    hintEnglish = "Add individual digits in a continuous forward flow without pausing.",
                    formula = "Sum = d₁ + d₂ + d₃ + ... + dₙ",
                    steps = listOf(
                        CalculationStep("Step 1", "2 + 2 = 4", "पहले दो अंक", "First two digits"),
                        CalculationStep("Step 2", "4 + 6 = 10, 10 + 7 = 17", "अगले दो अंक", "Next digits in flow"),
                        CalculationStep("Step 3", "17 + 8 = 25, 25 + 9 = 34", "अंतिम योग", "Finish continuous flow: 34")
                    ),
                    exercises = listOf(
                        ExerciseProblem(1, "Sum of digits in 226789 =", "34", "44", "54", "24", "a", "2+2+6+7+8+9 = 34"),
                        ExerciseProblem(2, "Sum of digits in 334567 =", "48", "38", "28", "27", "c", "3+3+4+5+6+7 = 28"),
                        ExerciseProblem(3, "Sum of digits in 897886 =", "56", "36", "66", "46", "d", "8+9+7+8+8+6 = 46"),
                        ExerciseProblem(4, "Sum of digits in 8874567966 =", "76", "66", "86", "77", "b", "8+8+7+4+5+6+7+9+6+6 = 66")
                    )
                )
            )
        )
    }

    private fun createSubtractionChapter(): Chapter {
        return Chapter(
            id = 2,
            titleEnglish = "Subtraction",
            titleHindi = "घटाव",
            description = "Master mental subtraction from 100, 1000, 10000, tens differences, and negative unit adjustments.",
            iconName = "minus",
            pageRange = "79-119",
            quickTip = "100/1000 से घटाते समय इकाई को 10 से और बाकी सभी अंकों को 9 से घटाएँ (All from 9, last from 10)!",
            types = listOf(
                CalculationType(
                    typeNumber = 1,
                    titleEnglish = "Vedic Base 100/1000/10000 Subtraction",
                    titleHindi = "100, 1000, 10000 से त्वरित घटाव",
                    hintHindi = "1000 - 912: 9 में से 9 गया 0, 9 में से 1 गया 8, 10 में से 2 गया 8 -> 88।",
                    hintEnglish = "All from 9 and last from 10 method for instantaneous mental subtraction from base powers.",
                    formula = "10ⁿ - K → Subtract each digit from 9, and the last non-zero digit from 10.",
                    steps = listOf(
                        CalculationStep("Step 1", "9 - 9 = 0", "पहले अंक को 9 से घटाएँ", "Subtract first digit from 9"),
                        CalculationStep("Step 2", "9 - 1 = 8", "दूसरे अंक को 9 से घटाएँ", "Subtract second digit from 9"),
                        CalculationStep("Step 3", "10 - 2 = 8", "इकाई अंक को 10 से घटाएँ", "Subtract unit digit from 10"),
                        CalculationStep("Step 4", "Answer = 88", "परिणाम लिखें", "Final answer: 088 = 88")
                    ),
                    exercises = listOf(
                        ExerciseProblem(1, "100 - 12 =", "78", "67", "83", "88", "d", "9-1=8, 10-2=8 -> 88"),
                        ExerciseProblem(2, "100 - 31 =", "69", "86", "77", "93", "a", "9-3=6, 10-1=9 -> 69"),
                        ExerciseProblem(3, "1000 - 912 =", "99", "78", "88", "86", "c", "9-9=0, 9-1=8, 10-2=8 -> 88"),
                        ExerciseProblem(4, "1000 - 143 =", "767", "857", "787", "954", "b", "9-1=8, 9-4=5, 10-3=7 -> 857"),
                        ExerciseProblem(5, "10000 - 1431 =", "8669", "8759", "8849", "8569", "d", "9-1=8, 9-4=5, 9-3=6, 10-1=9 -> 8569"),
                        ExerciseProblem(6, "100000 - 41607 =", "56788", "57873", "58343", "58393", "d", "9-4=5, 9-1=8, 9-6=3, 9-0=9, 10-7=3 -> 58393")
                    )
                ),
                CalculationType(
                    typeNumber = 2,
                    titleEnglish = "Tens Difference Mental Subtraction",
                    titleHindi = "दहाई अंतर मानसिक घटाव",
                    hintHindi = "73 - 47: दहाई 7 - 4 = 3 -> 30। इकाई 3 - 7 = -4। 30 - 4 = 26।",
                    hintEnglish = "Calculate tens difference, multiply by 10, then add/subtract the unit difference.",
                    formula = "(10t₁ + u₁) - (10t₂ + u₂) = 10(t₁ - t₂) + (u₁ - u₂)",
                    steps = listOf(
                        CalculationStep("Step 1", "7 - 4 = 3 → 30", "दहाई का अंतर", "Tens difference: (7-4)=3 -> 30"),
                        CalculationStep("Step 2", "3 - 7 = -4", "इकाई का अंतर", "Unit difference: 3 - 7 = -4"),
                        CalculationStep("Step 3", "30 - 4 = 26", "30 में से 4 घटाएँ", "Combine: 30 - 4 = 26")
                    ),
                    exercises = listOf(
                        ExerciseProblem(1, "73 - 47 =", "26", "36", "24", "27", "a", "(70-40) + (3-7) = 30 - 4 = 26"),
                        ExerciseProblem(2, "98 - 65 =", "37", "33", "44", "23", "b", "(90-60) + (8-5) = 30 + 3 = 33"),
                        ExerciseProblem(3, "85 - 43 =", "44", "33", "42", "52", "c", "(80-40) + (5-3) = 40 + 2 = 42"),
                        ExerciseProblem(4, "91 - 67 =", "24", "26", "21", "28", "a", "(90-60) + (1-7) = 30 - 6 = 24")
                    )
                )
            )
        )
    }

    private fun createMultiplicationChapter(): Chapter {
        return Chapter(
            id = 3,
            titleEnglish = "Multiplication",
            titleHindi = "गुणा",
            description = "Inspector fast multiplication: 2-digit splits, Vedic cross multiplication (कैंची गुणा), 2-digit & 3-digit Squares and Cubes.",
            iconName = "multiply",
            pageRange = "120-182",
            quickTip = "2-Digit Cross: (Units × Units) -> (Cross sum + carry) -> (Tens × Tens + carry).",
            types = listOf(
                CalculationType(
                    typeNumber = 1,
                    titleEnglish = "2-Digit × 1-Digit Mental Split (Type 1)",
                    titleHindi = "दहाई और इकाई अलग करके गुणा",
                    hintHindi = "19 × 8: 8 × 10 = 80 (दिमाग में रखें) + 8 × 9 = 72। 80 + 72 = 152।",
                    hintEnglish = "Multiply tens first (append 0) and add the unit product.",
                    formula = "(10t + u) × n = 10tn + un",
                    steps = listOf(
                        CalculationStep("Step 1", "8 × 10 = 80", "दहाई से गुणा करें", "Multiply tens: 8 × 10 = 80"),
                        CalculationStep("Step 2", "8 × 9 = 72", "इकाई से गुणा करें", "Multiply units: 8 × 9 = 72"),
                        CalculationStep("Step 3", "80 + 72 = 152", "दोनों को जोड़ें", "Combine: 80 + 72 = 152")
                    ),
                    exercises = listOf(
                        ExerciseProblem(1, "19 × 8 =", "165", "150", "142", "152", "d", "80 + 72 = 152"),
                        ExerciseProblem(2, "48 × 9 =", "443", "342", "355", "432", "d", "360 + 72 = 432"),
                        ExerciseProblem(3, "42 × 5 =", "233", "235", "210", "198", "c", "200 + 10 = 210"),
                        ExerciseProblem(4, "86 × 8 =", "688", "598", "483", "572", "a", "640 + 48 = 688"),
                        ExerciseProblem(5, "78 × 7 =", "577", "588", "546", "466", "c", "490 + 56 = 546"),
                        ExerciseProblem(6, "67 × 6 =", "423", "402", "507", "388", "b", "360 + 42 = 402")
                    )
                ),
                CalculationType(
                    typeNumber = 2,
                    titleEnglish = "2-Digit × 2-Digit Cross Multiplication (Type 2)",
                    titleHindi = "2 अंकों का कैंची गुणा (Cross Method)",
                    hintHindi = "47 × 23: Step 1: 7×3=21 (1 लिखें, 2 हासिल)। Step 2: (4×3)+(7×2)+2 = 12+14+2 = 28 (8 लिखें, 2 हासिल)। Step 3: 4×2+2 = 10 -> 1081।",
                    hintEnglish = "Vedic Criss-Cross algorithm: UnitxUnit -> Cross product sum + carry -> TensxTens + carry.",
                    formula = "(10a + b)(10c + d) = 100(ac) + 10(ad + bc) + bd",
                    steps = listOf(
                        CalculationStep("Step 1", "7 × 3 = 21 → Write 1, Carry 2", "इकाई गुणा", "Unit × Unit: 7 × 3 = 21"),
                        CalculationStep("Step 2", "(4×3) + (2×7) + 2 = 12 + 14 + 2 = 28 → Write 8, Carry 2", "क्रॉस गुणा का जोड़", "Cross: (4×3)+(2×7)+2 = 28"),
                        CalculationStep("Step 3", "(4×2) + 2 = 8 + 2 = 10 → Write 10", "दहाई गुणा + हासिल", "Tens: (4×2)+2 = 10"),
                        CalculationStep("Step 4", "Answer = 1081", "संयुक्त उत्तर", "Result: 1081")
                    ),
                    exercises = listOf(
                        ExerciseProblem(1, "47 × 23 =", "1182", "973", "1271", "1081", "d", "Cross method: 1081"),
                        ExerciseProblem(2, "48 × 55 =", "2465", "2340", "2640", "2751", "c", "Cross: 8x5=40, (20+40)+4=64, 4x5+6=26 -> 2640"),
                        ExerciseProblem(3, "28 × 82 =", "2296", "2473", "3166", "3156", "a", "Cross: 8x2=16, 4+64+1=69, 16+6=22 -> 2296"),
                        ExerciseProblem(4, "52 × 47 =", "2348", "2444", "2548", "3441", "b", "Cross: 2x7=14, 35+8+1=44, 20+4=24 -> 2444"),
                        ExerciseProblem(5, "63 × 31 =", "1953", "1843", "1984", "1875", "a", "Cross: 3x1=3, 6+9=15, 18+1=19 -> 1953")
                    )
                ),
                CalculationType(
                    typeNumber = 3,
                    titleEnglish = "Squares of 2-Digit Numbers (Type 3)",
                    titleHindi = "2 अंकों का वर्ग (Square Shortcut)",
                    hintHindi = "(42)²: Step 1: 2² = 4। Step 2: 4 × 2 × 2 = 16 (6 लिखें, 1 हासिल)। Step 3: 4² + 1 = 16 + 1 = 17 -> 1764।",
                    hintEnglish = "(a+b)² = a² | 2ab | b². Start from unit digit squared, middle is double product, tens squared.",
                    formula = "(10a + b)² = 100a² + 20ab + b²",
                    steps = listOf(
                        CalculationStep("Step 1", "2² = 4", "इकाई का वर्ग", "Square of unit digit: 2² = 4"),
                        CalculationStep("Step 2", "4 × 2 × 2 = 16 → Write 6, Carry 1", "दहाई × इकाई × 2", "2 × tens × unit: 4×2×2 = 16"),
                        CalculationStep("Step 3", "4² + 1 = 16 + 1 = 17", "दहाई का वर्ग + हासिल", "Square of tens + carry: 16+1 = 17"),
                        CalculationStep("Step 4", "Answer = 1764", "उत्तर", "Final: 1764")
                    ),
                    exercises = listOf(
                        ExerciseProblem(1, "21² =", "521", "562", "441", "742", "c", "1²=1, 2x1x2=4, 2²=4 -> 441"),
                        ExerciseProblem(2, "27² =", "893", "826", "729", "927", "c", "7²=49, 2x7x2+4=32, 2²+3=7 -> 729"),
                        ExerciseProblem(3, "32² =", "1522", "1823", "1023", "1024", "d", "2²=4, 3x2x2=12, 3²+1=10 -> 1024"),
                        ExerciseProblem(4, "42² =", "1764", "1965", "1824", "2064", "a", "1764"),
                        ExerciseProblem(5, "55² =", "3812", "4123", "3025", "3472", "c", "5²=25, 5x6=30 -> 3025"),
                        ExerciseProblem(6, "96² =", "8729", "9236", "9216", "8246", "c", "6²=36, 9x6x2+3=111, 9²+11=92 -> 9216")
                    )
                ),
                CalculationType(
                    typeNumber = 5,
                    titleEnglish = "Cubes of 2-Digit Numbers (Type 5)",
                    titleHindi = "2 अंकों का घन (Cube Shortcut)",
                    hintHindi = "(12)³: (a+b)³ = a³ . 3a²b . 3ab² . b³. For 12: 2³=8, 3(1)(2²)=12 (2 लिखें, 1 हासिल), 3(1²)(2)+1=7, 1³=1 -> 1728।",
                    hintEnglish = "Calculate cube using binomial expansion a³ | 3a²b | 3ab² | b³.",
                    formula = "(10a + b)³ = 1000a³ + 300a²b + 30ab² + b³",
                    steps = listOf(
                        CalculationStep("Step 1", "2³ = 8", "इकाई का घन", "Unit cube: 2³ = 8"),
                        CalculationStep("Step 2", "3 × 1 × 2² = 12 → Write 2, Carry 1", "3 × tens × unit²", "3 × 1 × 4 = 12"),
                        CalculationStep("Step 3", "3 × 1² × 2 + 1 = 7", "3 × tens² × unit + carry", "3 × 1 × 2 + 1 = 7"),
                        CalculationStep("Step 4", "1³ = 1", "दहाई का घन", "Tens cube: 1³ = 1"),
                        CalculationStep("Step 5", "Answer = 1728", "उत्तर", "Final: 1728")
                    ),
                    exercises = listOf(
                        ExerciseProblem(1, "5³ =", "225", "125", "145", "147", "b", "5×5×5 = 125"),
                        ExerciseProblem(2, "6³ =", "317", "246", "326", "216", "d", "6×6×6 = 216"),
                        ExerciseProblem(3, "7³ =", "343", "333", "413", "354", "a", "7×7×7 = 343"),
                        ExerciseProblem(4, "8³ =", "614", "512", "412", "611", "b", "8×8×8 = 512"),
                        ExerciseProblem(5, "9³ =", "729", "849", "949", "844", "a", "9×9×9 = 729"),
                        ExerciseProblem(6, "11³ =", "1421", "1331", "1524", "1221", "b", "1331"),
                        ExerciseProblem(7, "12³ =", "1828", "1728", "1978", "1823", "b", "1728"),
                        ExerciseProblem(8, "13³ =", "2241", "2177", "2197", "2457", "c", "2197")
                    )
                )
            )
        )
    }

    private fun createDivisionChapter(): Chapter {
        return Chapter(
            id = 4,
            titleEnglish = "Division",
            titleHindi = "विभाजन",
            description = "Fast division using unit digit inspection, fractional conversions, and the Unitary Multiplier Method.",
            iconName = "divide",
            pageRange = "183-225",
            quickTip = "Unitary Method: 3 -> 12 (Multiplier = 4x). For 16 -> 16 × 4 = 64!",
            types = listOf(
                CalculationType(
                    typeNumber = 1,
                    titleEnglish = "Unit Digit & Option Elimination (Type 1)",
                    titleHindi = "इकाई अंक देखकर विभाजन",
                    hintHindi = "171 / 9: 9 को इकाई अंक (1) से गुणा करने पर 9 आता है। 9×9 = 81 (इकाई 1)। अतः 171/9 = 19।",
                    hintEnglish = "Find the option whose unit digit multiplied by the denominator gives the numerator's unit digit.",
                    formula = "N / D = Q → (Unit digit of D) × (Unit digit of Q) ≡ (Unit digit of N)",
                    steps = listOf(
                        CalculationStep("Step 1", "Numerator Unit = 1, Denominator = 9", "इकाई अंक पहचानें", "Identify unit digits"),
                        CalculationStep("Step 2", "9 × 9 = 81 (Ends in 1)", "विकल्प में 9 वाले अंक देखें", "Check which unit digit gives 1: 9×9=81"),
                        CalculationStep("Step 3", "17 / 9 ≈ 1.8 → 19", "अनुमानित मान से विकल्प चुनें", "Approximate 17/9 -> 19")
                    ),
                    exercises = listOf(
                        ExerciseProblem(1, "120 / 15 =", "6", "9", "8", "7.5", "c", "15 × 8 = 120"),
                        ExerciseProblem(2, "171 / 9 =", "17", "19", "18", "16", "b", "9 × 9 = 81 -> 19"),
                        ExerciseProblem(3, "112 / 16 =", "8", "6", "7", "9", "c", "16 × 7 = 112"),
                        ExerciseProblem(4, "168 / 7 =", "26", "23", "22", "24", "d", "7 × 24 = 168"),
                        ExerciseProblem(5, "117 / 9 =", "12", "13", "14", "16", "b", "9 × 13 = 117"),
                        ExerciseProblem(6, "140 / 28 =", "5", "6", "7", "4.5", "a", "28 × 5 = 140")
                    )
                ),
                CalculationType(
                    typeNumber = 6,
                    titleEnglish = "Unitary Multiplier Method (Type 6)",
                    titleHindi = "ऐकिक नियम (Multiplier Method)",
                    hintHindi = "यदि 3 → 12 (Multiplier = 4), तो 16 → 16 × 4 = 64 होगा। सब कुछ दिमाग में करें।",
                    hintEnglish = "Find the multiplier factor between given pair, then apply it directly to the target number.",
                    formula = "If A → B, then multiplier k = B/A. For C → C × k",
                    steps = listOf(
                        CalculationStep("Step 1", "3 → 12 ⇒ Multiplier = 12/3 = 4", "गुणक ज्ञात करें", "Multiplier = 12 / 3 = 4"),
                        CalculationStep("Step 2", "16 × 4 = 64", "लक्ष्य संख्या में गुणक से गुणा करें", "Multiply: 16 × 4 = 64")
                    ),
                    exercises = listOf(
                        ExerciseProblem(1, "If 3 → 12, then 16 → ?", "48", "64", "80", "96", "b", "Multiplier = 4 -> 16 × 4 = 64"),
                        ExerciseProblem(2, "If 5 → 44, then 15 → ?", "154", "176", "132", "135", "c", "15 is 3×5 -> 44 × 3 = 132"),
                        ExerciseProblem(3, "If 2 → 12, then 27 → ?", "189", "135", "216", "162", "d", "Multiplier = 6 -> 27 × 6 = 162"),
                        ExerciseProblem(4, "If 12 → 48, then 14 → ?", "56", "70", "80", "63", "a", "Multiplier = 4 -> 14 × 4 = 56"),
                        ExerciseProblem(5, "If 4 → 68, then 12 → ?", "192", "180", "204", "216", "c", "12 is 3×4 -> 68 × 3 = 204"),
                        ExerciseProblem(6, "If 25% → 45, then 100% → ?", "270", "90", "540", "180", "d", "100% is 4×25% -> 45 × 4 = 180")
                    )
                )
            )
        )
    }

    private fun createDecimalChapter(): Chapter {
        return Chapter(
            id = 5,
            titleEnglish = "Decimal",
            titleHindi = "दशमलव",
            description = "Multiplication with decimals, fractional shortcuts (0.50=1/2, 0.25=1/4, 0.125=1/8), and mental shifts.",
            iconName = "decimal",
            pageRange = "226-274",
            quickTip = "0.50 = 1/2, 0.25 = 1/4, 0.75 = 3/4, 0.125 = 1/8. 28 × 1.50 = 28 + 14 = 42!",
            types = listOf(
                CalculationType(
                    typeNumber = 1,
                    titleEnglish = "Decimal × Integer Split (Type 1)",
                    titleHindi = "दशमलव का पूर्णांक से गुणा",
                    hintHindi = "5.6 × 8 = (5 + 0.6) × 8: 5 × 8 = 40 (दिमाग में), 6 × 8 = 4.8। 40 + 4.8 = 44.8।",
                    hintEnglish = "Split integer and decimal parts, multiply each, and combine.",
                    formula = "(a + 0.b) × n = an + 0.(b × n)",
                    steps = listOf(
                        CalculationStep("Step 1", "5 × 8 = 40", "पूर्णांक भाग", "5 × 8 = 40"),
                        CalculationStep("Step 2", "0.6 × 8 = 4.8", "दशमलव भाग", "0.6 × 8 = 4.8"),
                        CalculationStep("Step 3", "40 + 4.8 = 44.8", "जोड़ें", "Combine = 44.8")
                    ),
                    exercises = listOf(
                        ExerciseProblem(1, "4.6 × 4 =", "16.4", "17.4", "18.4", "1.84", "c", "16 + 2.4 = 18.4"),
                        ExerciseProblem(2, "2.3 × 9 =", "21.7", "20.7", "18.7", "19.7", "b", "18 + 2.7 = 20.7"),
                        ExerciseProblem(3, "6.4 × 5 =", "32.5", "31", "32", "32.4", "c", "30 + 2.0 = 32"),
                        ExerciseProblem(4, "7.2 × 9 =", "62.8", "60.8", "63.8", "64.8", "d", "63 + 1.8 = 64.8")
                    )
                ),
                CalculationType(
                    typeNumber = 5,
                    titleEnglish = "Fractional Decimal Multipliers (Type 5)",
                    titleHindi = "दशमलव भिन्न शॉर्टकट",
                    hintHindi = "28 × 1.50 = [1 + 0.5] × 28 = 28 + (28 का आधा 14) = 42। (0.25=1/4, 0.125=1/8)।",
                    hintEnglish = "Convert common decimals like 1.5, 2.5, 1.25 into fractional parts to calculate mentally.",
                    formula = "n × (k + f) = kn + n×f",
                    steps = listOf(
                        CalculationStep("Step 1", "28 × 1 = 28", "मूल मान", "Base = 28"),
                        CalculationStep("Step 2", "28 × 0.50 = 14", "आधा मान", "Half = 14"),
                        CalculationStep("Step 3", "28 + 14 = 42", "जोड़ें", "Total = 42")
                    ),
                    exercises = listOf(
                        ExerciseProblem(1, "28 × 1.50 =", "40", "42", "41", "42.5", "b", "28 + 14 = 42"),
                        ExerciseProblem(2, "20 × 9.50 =", "190", "180", "192", "170", "a", "180 + 10 = 190"),
                        ExerciseProblem(3, "7 × 3.50 =", "24.5", "24", "23.5", "25", "a", "21 + 3.5 = 24.5"),
                        ExerciseProblem(4, "88 × 1.50 =", "328", "131.2", "130", "132", "d", "88 + 44 = 132"),
                        ExerciseProblem(5, "80 × 6.25 =", "5000", "400", "500", "50", "c", "80 × 6 + 80/4 = 480 + 20 = 500")
                    )
                )
            )
        )
    }

    private fun createSurdsChapter(): Chapter {
        return Chapter(
            id = 6,
            titleEnglish = "Surds & Roots",
            titleHindi = "करणी",
            description = "Properties of radicals, (√a)², (a√b)², (a√b)³, approximate roots, and cube roots.",
            iconName = "root",
            pageRange = "275-311",
            quickTip = "Root की Power Even (सम) है तो Root हट जाएगा: (3√3)² = 3² × 3 = 27। Odd (विषम) में Root रहेगा: (5√2)³ = 5³ × 2√2 = 250√2।",
            types = listOf(
                CalculationType(
                    typeNumber = 1,
                    titleEnglish = "Radical Powers (Type 1)",
                    titleHindi = "करणी की घात",
                    hintHindi = "Power Even है तो रूट हटेगा, Odd है तो रूट रहेगा। (3√3)² = 9 × 3 = 27। (5√2)³ = 125 × 2√2 = 250√2।",
                    hintEnglish = "Even powers remove the square root, odd powers retain one radical factor.",
                    formula = "(a√b)² = a²b; (a√b)³ = a³b√b",
                    steps = listOf(
                        CalculationStep("Step 1", "(3√3)² = 3² × (√3)²", "अलग-अलग घात करें", "Separate terms"),
                        CalculationStep("Step 2", "= 9 × 3 = 27", "गुणा करें", "9 × 3 = 27")
                    ),
                    exercises = listOf(
                        ExerciseProblem(1, "(√2)² =", "√2", "4", "2", "2√2", "c", "2"),
                        ExerciseProblem(2, "(√13)⁴ =", "26", "13", "13√13", "169", "d", "13² = 169"),
                        ExerciseProblem(3, "(3√3)² =", "27√3", "27", "9√3", "81", "b", "9 × 3 = 27"),
                        ExerciseProblem(4, "(6√6)² =", "216√6", "36√6", "216", "36", "c", "36 × 6 = 216"),
                        ExerciseProblem(5, "(5√2)³ =", "125", "125√2", "250", "250√2", "d", "125 × 2√2 = 250√2"),
                        ExerciseProblem(6, "(3√2)² =", "18", "9", "9√2", "18√2", "a", "9 × 2 = 18")
                    )
                ),
                CalculationType(
                    typeNumber = 2,
                    titleEnglish = "Radical Sums & Simplification (Type 2)",
                    titleHindi = "करणी योग एवं सरलीकरण",
                    hintHindi = "(√7)² + (√4)² = 7 + 4 = 11। (∛125)² + (∛343)² = 5² + 7² = 25 + 49 = 74।",
                    hintEnglish = "Simplify each radical component first, then perform addition or subtraction.",
                    formula = "(√a)² + (√b)² = a + b",
                    steps = listOf(
                        CalculationStep("Step 1", "(√7)² = 7, (√4)² = 4", "वर्गमूल हटाएं", "Remove roots"),
                        CalculationStep("Step 2", "7 + 4 = 11", "योग करें", "Add: 7 + 4 = 11")
                    ),
                    exercises = listOf(
                        ExerciseProblem(1, "(√7)² + (√4)² =", "57", "√11", "10", "11", "d", "7 + 4 = 11"),
                        ExerciseProblem(2, "(√3)² + (√5)² =", "34", "√8", "8", "√19", "c", "3 + 5 = 8"),
                        ExerciseProblem(3, "(√6)² + (2√2)² =", "10", "√40", "14", "28", "c", "6 + 8 = 14"),
                        ExerciseProblem(4, "5² + (√3)² =", "8", "28", "14", "34", "b", "25 + 3 = 28")
                    )
                ),
                CalculationType(
                    typeNumber = 3,
                    titleEnglish = "Approximate Roots (Type 3)",
                    titleHindi = "लगभग मान निकालना",
                    hintHindi = "√5: 5 को 100 से गुणा करें (500)। 22²=484 जो 500 के करीब है, अतः √5 ≈ 2.236।",
                    hintEnglish = "Estimate square root by scaling by 100 and finding nearest integer root, then dividing by 10.",
                    formula = "√x ≈ √(100x)/10",
                    steps = listOf(
                        CalculationStep("Step 1", "5 × 100 = 500", "100 से गुणा करें", "Scale by 100 = 500"),
                        CalculationStep("Step 2", "22² = 484 ≈ 500", "निकटतम वर्ग खोजें", "Nearest square is 22² = 484"),
                        CalculationStep("Step 3", "√5 ≈ 2.236", "दशमलव लगाएँ", "Place decimal: ≈ 2.236")
                    ),
                    exercises = listOf(
                        ExerciseProblem(1, "Approximate value of √2 =", "1.414", "1.824", "2.214", "1.212", "a", "1.414"),
                        ExerciseProblem(2, "Approximate value of √3 =", "1.236", "1.732", "2.244", "1.984", "b", "1.732"),
                        ExerciseProblem(3, "Approximate value of √5 =", "2.236", "2.436", "3.345", "2.021", "a", "2.236"),
                        ExerciseProblem(4, "Approximate value of 1/√2 =", "0.707", "0.52", "0.12", "1.2", "a", "√2/2 = 1.414/2 = 0.707")
                    )
                )
            )
        )
    }

    private fun createLcmHcfChapter(): Chapter {
        return Chapter(
            id = 7,
            titleEnglish = "LCM & HCF",
            titleHindi = "ल.स. / म.स.",
            description = "Inspector rapid LCM (largest number × non-common factor) & HCF (difference method & option elimination).",
            iconName = "lcm",
            pageRange = "312-328",
            quickTip = "LCM: बड़ी संख्या (15) को देखें, जो दूसरी संख्या (10=2×5) में non-common है (2), उसे गुणा करें: 15 × 2 = 30!",
            types = listOf(
                CalculationType(
                    typeNumber = 1,
                    titleEnglish = "LCM by Non-Common Multiplier (Type 1)",
                    titleHindi = "ल.स. शॉर्टकट (बड़ी संख्या विधि)",
                    hintHindi = "10, 15 का LCM: बड़ी संख्या 15 = 3×5। 10 = 2×5। 10 में 2 छूट रहा है। 15 × 2 = 30।",
                    hintEnglish = "Take largest number, identify missing prime factors from smaller numbers, and multiply.",
                    formula = "LCM(a, b) = max(a, b) × (non-common factor)",
                    steps = listOf(
                        CalculationStep("Step 1", "10 = 2 × 5, 15 = 3 × 5", "गुणनखंड देखें", "Factorize"),
                        CalculationStep("Step 2", "Common = 5, Non-common in 10 = 2", "छूटा हुआ अंक", "Missing factor is 2"),
                        CalculationStep("Step 3", "15 × 2 = 30", "उत्तर", "LCM = 15 × 2 = 30")
                    ),
                    exercises = listOf(
                        ExerciseProblem(1, "LCM of 10, 15 =", "50", "30", "15", "20", "b", "15 × 2 = 30"),
                        ExerciseProblem(2, "LCM of 20, 30 =", "120", "40", "60", "100", "c", "30 × 2 = 60"),
                        ExerciseProblem(3, "LCM of 40, 70 =", "420", "240", "280", "210", "c", "70 × 4 = 280"),
                        ExerciseProblem(4, "LCM of 10, 20, 30 =", "40", "60", "30", "90", "b", "30 × 2 = 60"),
                        ExerciseProblem(5, "LCM of 18, 24, 36 =", "120", "36", "72", "84", "c", "36 × 2 = 72")
                    )
                ),
                CalculationType(
                    typeNumber = 2,
                    titleEnglish = "HCF by Option Elimination & Difference (Type 2)",
                    titleHindi = "म.स. विकल्प निष्कासन विधि",
                    hintHindi = "29, 667 का HCF: सबसे बड़ा विकल्प 667 लें, 29 से भाग नहीं होता। 29 से दोनों संख्याएँ विभाजित होती हैं, अतः HCF = 29।",
                    hintEnglish = "Test highest factor from options that divides all given numbers, or find difference between close numbers.",
                    formula = "HCF divides difference |a - b|",
                    steps = listOf(
                        CalculationStep("Step 1", "Check options from largest to smallest", "विकल्प देखें", "Inspect highest option"),
                        CalculationStep("Step 2", "29 divides 29 (1) and 667 (23)", "विभाज्यता जाँच", "29 divides both 29 and 667"),
                        CalculationStep("Step 3", "HCF = 29", "उत्तर", "HCF = 29")
                    ),
                    exercises = listOf(
                        ExerciseProblem(1, "HCF of 29, 667 =", "29", "667", "23", "21", "a", "29"),
                        ExerciseProblem(2, "HCF of 240, 480, 960 =", "120", "240", "480", "125", "b", "240"),
                        ExerciseProblem(3, "HCF of 5, 10 =", "10", "2.5", "5", "15", "c", "5"),
                        ExerciseProblem(4, "HCF of 121, 187, 286 =", "121", "11", "22", "13", "b", "11 divides all three")
                    )
                ),
                CalculationType(
                    typeNumber = 3,
                    titleEnglish = "LCM & HCF of Fractions",
                    titleHindi = "भिन्नों का ल.स. एवं म.स.",
                    hintHindi = "भिन्नों का HCF = (अंशों का HCF) / (हरों का LCM)। भिन्नों का LCM = (अंशों का LCM) / (हरों का HCF)।",
                    hintEnglish = "HCF of fractions = HCF(numerators) / LCM(denominators). LCM of fractions = LCM(numerators) / HCF(denominators).",
                    formula = "HCF(a/b, c/d) = HCF(a,c)/LCM(b,d); LCM(a/b, c/d) = LCM(a,c)/HCF(b,d)",
                    steps = listOf(
                        CalculationStep("Step 1", "For 2/3, 1/2, 3/5 HCF: Numerators HCF(2,1,3) = 1", "अंशों का HCF", "HCF of 2, 1, 3 is 1"),
                        CalculationStep("Step 2", "Denominators LCM(3,2,5) = 30", "हरों का LCM", "LCM of 3, 2, 5 is 30"),
                        CalculationStep("Step 3", "HCF = 1/30", "उत्तर", "Result = 1/30")
                    ),
                    exercises = listOf(
                        ExerciseProblem(1, "HCF of 2/3, 1/2, 3/5 =", "2/5", "1/30", "3/10", "7/30", "b", "HCF(2,1,3)/LCM(3,2,5) = 1/30"),
                        ExerciseProblem(2, "LCM of 7/2, 9/4, 8/3 =", "72/7", "504", "504/12", "252", "b", "LCM(7,9,8)/HCF(2,4,3) = 504/1 = 504")
                    )
                )
            )
        )
    }

    private fun createFractionChapter(): Chapter {
        return Chapter(
            id = 8,
            titleEnglish = "Fraction",
            titleHindi = "भिन्न",
            description = "Mixed fraction mental arithmetic, cross division, percentage shortcuts (e.g. 14 2/7 ÷ 5 = 100/7 ÷ 5 = 20/7).",
            iconName = "fraction",
            pageRange = "329-419",
            quickTip = "1 + 2/3 = 5/3. 8 1/5 × 7 = (8×7) + (7/5 = 1 2/5) = 57 2/5!",
            types = listOf(
                CalculationType(
                    typeNumber = 1,
                    titleEnglish = "Whole + Proper Fraction (Type 1)",
                    titleHindi = "पूर्णांक और भिन्न का योग/घटाव",
                    hintHindi = "1 + 2/3 = (3×1 + 2)/3 = 5/3। 9/8 = 1 + 1/8।",
                    hintEnglish = "Quick conversion between improper fraction and mixed number.",
                    formula = "a + b/c = (ac + b)/c",
                    steps = listOf(
                        CalculationStep("Step 1", "1 + 2/3 → Denominator is 3", "हर 3 रहेगा", "Denominator is 3"),
                        CalculationStep("Step 2", "3 × 1 + 2 = 5", "अंश 5 बनेगा", "Numerator = 3(1)+2 = 5"),
                        CalculationStep("Step 3", "Answer = 5/3", "उत्तर", "Result = 5/3")
                    ),
                    exercises = listOf(
                        ExerciseProblem(1, "1 + 2/3 =", "5/2", "5/3", "2/5", "3/5", "b", "5/3"),
                        ExerciseProblem(2, "3 + 1/4 =", "13/3", "11/4", "13/4", "11/3", "c", "13/4"),
                        ExerciseProblem(3, "7/3 = 1 + ?", "4/3", "3/4", "5/3", "3/2", "a", "7/3 - 1 = 4/3"),
                        ExerciseProblem(4, "9/8 = 1 + ?", "8/9", "1/9", "7/8", "1/8", "d", "9/8 - 1 = 1/8")
                    )
                ),
                CalculationType(
                    typeNumber = 3,
                    titleEnglish = "Mixed Fraction × Integer (Type 3)",
                    titleHindi = "मिश्रित भिन्न का पूर्णांक से गुणा",
                    hintHindi = "8 1/5 × 7: 8 × 7 = 56। 1/5 × 7 = 7/5 = 1 2/5। 56 + 1 2/5 = 57 2/5।",
                    hintEnglish = "Multiply integer part and fraction part separately, convert improper fraction to mixed number, and sum.",
                    formula = "(I + a/b) × n = (I×n) + (an/b)",
                    steps = listOf(
                        CalculationStep("Step 1", "8 × 7 = 56", "पूर्णांक गुणा", "Integer: 8 × 7 = 56"),
                        CalculationStep("Step 2", "1/5 × 7 = 7/5 = 1 2/5", "भिन्न गुणा", "Fraction: 7/5 = 1 2/5"),
                        CalculationStep("Step 3", "56 + 1 2/5 = 57 2/5", "संयुक्त मान", "Combine: 57 2/5")
                    ),
                    exercises = listOf(
                        ExerciseProblem(1, "8 1/5 × 7 =", "57 2/5", "56 1/5", "42 2/5", "48 1/5", "a", "56 + 1 2/5 = 57 2/5"),
                        ExerciseProblem(2, "6 1/4 × 8 =", "75", "60", "40", "50", "d", "48 + 2 = 50"),
                        ExerciseProblem(3, "7 1/6 × 7 =", "48 1/6", "49 1/6", "50 1/6", "51 1/6", "c", "49 + 1 1/6 = 50 1/6"),
                        ExerciseProblem(4, "9 1/4 × 12 =", "112", "110", "116", "111", "d", "108 + 3 = 111")
                    )
                ),
                CalculationType(
                    typeNumber = 4,
                    titleEnglish = "Fraction Division & Cross Simplification (Type 4)",
                    titleHindi = "भिन्नों का विभाजन एवं कैंची हल",
                    hintHindi = "1 1/4 ÷ 3/7 = 5/4 ÷ 3/7 = (5×7)/(4×3) = 35/12 = 2 11/12।",
                    hintEnglish = "Convert mixed to improper, multiply by reciprocal: (a/b) ÷ (c/d) = (a×d)/(b×c).",
                    formula = "(a/b) ÷ (c/d) = (a×d) / (b×c)",
                    steps = listOf(
                        CalculationStep("Step 1", "1 1/4 = 5/4", "विषम भिन्न बनाएँ", "1 1/4 = 5/4"),
                        CalculationStep("Step 2", "5/4 × 7/3 = 35/12", "व्युत्क्रम से गुणा", "5/4 × 7/3 = 35/12"),
                        CalculationStep("Step 3", "35/12 = 2 11/12", "मिश्रित भिन्न", "2 11/12")
                    ),
                    exercises = listOf(
                        ExerciseProblem(1, "1 1/4 ÷ 3/7 =", "5/7", "2 11/12", "12/35", "2 1/7", "b", "5/4 × 7/3 = 35/12 = 2 11/12"),
                        ExerciseProblem(2, "9 1/2 ÷ 4/7 =", "15 5/8", "16 5/8", "17 3/8", "5/8", "b", "19/2 × 7/4 = 133/8 = 16 5/8"),
                        ExerciseProblem(3, "4 1/5 ÷ 8/3 =", "1 3/40", "23/40", "40/63", "1 23/40", "d", "21/5 × 3/8 = 63/40 = 1 23/40")
                    )
                )
            )
        )
    }

    private fun createPercentageChapter(): Chapter {
        return Chapter(
            id = 9,
            titleEnglish = "Percentage",
            titleHindi = "प्रतिशत",
            description = "Standard percentage benchmarks, complete Fraction ↔ % table, % increase/decrease multipliers, and net successive change (x + y + xy/100).",
            iconName = "percent",
            pageRange = "420-475",
            quickTip = "1/3=33.33%, 1/7=14.28%, 1/8=12.5%, 1/9=11.11%, 1/11=9.09%, 1/12=8.33%, 1/16=6.25%. Net increase = x + y + xy/100.",
            types = listOf(
                CalculationType(
                    typeNumber = 1,
                    titleEnglish = "Benchmark Percentages (Type 1)",
                    titleHindi = "मानक प्रतिशत मान",
                    hintHindi = "1% = 2 अंक पहले दशमलव। 10% = 1 अंक पहले दशमलव। 100% = 1 गुना। 150% = 1.5 गुना। 200% = 2 गुना।",
                    hintEnglish = "Calculate 1% (divide by 100), 10% (divide by 10), 50% (half), 150% (1.5x) mentally.",
                    formula = "1% of N = N/100; 10% of N = N/10; 50% of N = N/2",
                    steps = listOf(
                        CalculationStep("Step 1", "1% of 19 = 0.19", "2 स्थान पहले दशमलव", "Shift decimal 2 places left"),
                        CalculationStep("Step 2", "50% of 19 = 9.5", "19 का आधा", "Half of 19 = 9.5"),
                        CalculationStep("Step 3", "150% of 19 = 19 + 9.5 = 28.5", "100% + 50%", "19 + 9.5 = 28.5")
                    ),
                    exercises = listOf(
                        ExerciseProblem(1, "1% of 19 =", "0.19", "1.9", "0.019", "19", "a", "0.19"),
                        ExerciseProblem(2, "50% of 19 =", "19", "9.5", "38", "95", "b", "9.5"),
                        ExerciseProblem(3, "150% of 19 =", "19", "38", "28.5", "22.5", "c", "28.5"),
                        ExerciseProblem(4, "10% of 118 =", "1.18", "11.8", "18.1", "8.11", "b", "11.8"),
                        ExerciseProblem(5, "150% of 118 =", "236", "118", "59", "177", "d", "118 + 59 = 177")
                    )
                ),
                CalculationType(
                    typeNumber = 2,
                    titleEnglish = "Fraction to Percentage Equivalents (Type 2)",
                    titleHindi = "भिन्न से प्रतिशत रूपांतरण",
                    hintHindi = "1/3 = 33.33%, 2/3 = 66.66%. 1/7 = 14.28%, 2/7 = 28.57%, 3/7 = 42.85%, 4/7 = 57.14%. 1/8 = 12.5%, 3/8 = 37.5%, 5/8 = 62.5%.",
                    hintEnglish = "Memorize and apply fundamental fraction-to-percentage values.",
                    formula = "Fraction f → f × 100%",
                    steps = listOf(
                        CalculationStep("Step 1", "1/3 = 33.33%", "मूल भिन्न", "1/3 = 33.33%"),
                        CalculationStep("Step 2", "2/3 = 2 × 33.33% = 66.66%", "दोगुना", "2 × 33.33% = 66.66%")
                    ),
                    exercises = listOf(
                        ExerciseProblem(1, "1/3 in percentage =", "66.66%", "33.33%", "63.33%", "36.66%", "b", "33.33%"),
                        ExerciseProblem(2, "2/3 in percentage =", "66.66%", "33.33%", "63.33%", "36.66%", "a", "66.66%"),
                        ExerciseProblem(3, "1/7 in percentage =", "7.28%", "14.28%", "28.56%", "16.56%", "b", "14.28%"),
                        ExerciseProblem(4, "3/8 in percentage =", "35.7%", "37.5%", "38.5%", "36.5%", "b", "3 × 12.5% = 37.5%"),
                        ExerciseProblem(5, "5/8 in percentage =", "62.5%", "60%", "67.5%", "72%", "a", "5 × 12.5% = 62.5%"),
                        ExerciseProblem(6, "1/11 in percentage =", "18.18%", "9.09%", "10.10%", "11.11%", "b", "9.09%")
                    )
                ),
                CalculationType(
                    typeNumber = 6,
                    titleEnglish = "Net Percentage Change (Type 6)",
                    titleHindi = "कुल प्रतिशत परिवर्तन सूत्र",
                    hintHindi = "2%↑ & 3%↑: (2 + 3) = 5। 2 × 3 / 100 = 0.06। कुल = 5.06%। (x + y + xy/100)।",
                    hintEnglish = "Net successive percentage change formula: x + y + (xy/100). Use negative signs for decrease.",
                    formula = "Net % = x + y + (x × y)/100",
                    steps = listOf(
                        CalculationStep("Step 1", "x + y = 2 + 3 = 5", "योग", "Sum: 2 + 3 = 5"),
                        CalculationStep("Step 2", "xy / 100 = (2 × 3) / 100 = 0.06", "गुणनफल / 100", "Product / 100 = 0.06"),
                        CalculationStep("Step 3", "5 + 0.06 = 5.06%", "कुल प्रतिशत", "Net = 5.06%")
                    ),
                    exercises = listOf(
                        ExerciseProblem(1, "2%↑ & 3%↑ =", "5.50%", "5.05%", "6.06%", "5.06%", "d", "2 + 3 + 6/100 = 5.06%"),
                        ExerciseProblem(2, "4%↑ & 5%↑ =", "20.09%", "9.20%", "9.02%", "9.09%", "b", "4 + 5 + 20/100 = 9.20%"),
                        ExerciseProblem(3, "10%↑ & 20%↑ =", "32.2%", "32%", "31.20%", "30.2%", "b", "10 + 20 + 200/100 = 32%"),
                        ExerciseProblem(4, "20%↑ & 30%↑ =", "54%", "50%", "56%", "66%", "c", "20 + 30 + 600/100 = 56%"),
                        ExerciseProblem(5, "8%↓ & 5%↓ =", "12%", "13.40%", "12.6%", "11.6%", "c", "-8 - 5 + 40/100 = -12.60%"),
                        ExerciseProblem(6, "25%↑ & 20%↓ =", "+5%", "-5%", "+4.5%", "No change", "d", "25 - 20 - 500/100 = 0%")
                    )
                )
            )
        )
    }

    private fun createProfitLossChapter(): Chapter {
        return Chapter(
            id = 10,
            titleEnglish = "Profit & Loss",
            titleHindi = "लाभ और हानि",
            description = "Markup, Discount, CP/MP relationships, and successive pricing calculations.",
            iconName = "profit",
            pageRange = "476-483",
            quickTip = "MP / CP = (100 + P) / (100 - D) = (1 + P) / (1 - D).",
            types = listOf(
                CalculationType(
                    typeNumber = 1,
                    titleEnglish = "Markup, Discount & Profit/Loss Relation (Type 1)",
                    titleHindi = "अंकित मूल्य, छूट एवं लाभ/हानि",
                    hintHindi = "यदि 28 4/7% (2/7) Markup और 11 1/9% (1/9) Discount हो: 7 की वस्तु पर MP=9, 9 पर 1 छूट से SP=8। लाभ = 1/7 = 14.28%।",
                    hintEnglish = "Use ratio method: CP → MP using markup fraction, then MP → SP using discount fraction.",
                    formula = "MP/CP = (100 + P%)/(100 - D%)",
                    steps = listOf(
                        CalculationStep("Step 1", "Markup 28 4/7% = 2/7 → CP = 7, MP = 9", "CP से MP", "CP = 7, MP = 9"),
                        CalculationStep("Step 2", "Discount 11 1/9% = 1/9 → 9 पर 1 छूट ⇒ SP = 8", "MP से SP", "Discount 1 on 9 -> SP = 8"),
                        CalculationStep("Step 3", "Profit = (8 - 7)/7 = 1/7 = 14.28%", "लाभ प्रतिशत", "Profit = 1/7 = 14.28%")
                    ),
                    exercises = listOf(
                        ExerciseProblem(1, "Markup = 28 4/7% and Discount = 11 1/9%. Profit or Loss% =", "42.85%", "14.28%", "16.66%", "12.5%", "b", "CP 7 -> MP 9 -> SP 8 -> Profit = 1/7 = 14.28%"),
                        ExerciseProblem(2, "Markup = 16 2/3% and Discount = 28 4/7%. Profit or Loss% =", "12.5%", "14.28%", "20%", "16.66%", "d", "CP 6 -> MP 7 -> SP 5 -> Loss = 1/6 = 16.66%"),
                        ExerciseProblem(3, "Markup = 20% and Discount = 10%. Profit or Loss% =", "5%", "4%", "8%", "6%", "c", "100 -> 120 -> 108 -> Profit = 8%")
                    )
                )
            )
        )
    }

    private fun createSiCiChapter(): Chapter {
        return Chapter(
            id = 11,
            titleEnglish = "SI and CI",
            titleHindi = "साधारण और चक्रवृद्धि ब्याज",
            description = "Compound Interest Net Rates (2a.a²/100 for 2 yrs, 3a.3a²a³ for 3 yrs), CI - SI Differences, and irregular compounding periods (5, 7, 8, 10 months).",
            iconName = "interest",
            pageRange = "484-512",
            quickTip = "2 Years CI Net Rate = 2a + a²/100. 3 Years CI Net Rate = 3a . 3a² a³. CI-SI Diff for 2 yrs = R²/100%.",
            types = listOf(
                CalculationType(
                    typeNumber = 1,
                    titleEnglish = "CI Net Rate for 2 & 3 Years (Type 1)",
                    titleHindi = "चक्रवृद्धि ब्याज की कुल दर",
                    hintHindi = "2% @ CI for 2 yrs: 2(2) + 2²/100 = 4.04%। 5% for 2 yrs = 10.25%। 10% for 2 yrs = 21%। 10% for 3 yrs = 33.1%।",
                    hintEnglish = "Calculate effective compound interest rates directly.",
                    formula = "2 yrs: 2R + R²/100; 3 yrs: 3R + 3R²/100 + R³/10000",
                    steps = listOf(
                        CalculationStep("Step 1", "For 2%: 2 × 2 = 4", "2a भाग", "2a = 4"),
                        CalculationStep("Step 2", "2² / 100 = 0.04", "a²/100 भाग", "a²/100 = 0.04"),
                        CalculationStep("Step 3", "Net CI Rate = 4.04%", "कुल दर", "Total Rate = 4.04%")
                    ),
                    exercises = listOf(
                        ExerciseProblem(1, "2% @ CI for 2 years =", "4%", "4.04%", "4.16%", "4.4%", "b", "2(2) + 4/100 = 4.04%"),
                        ExerciseProblem(2, "3% @ CI for 2 years =", "6%", "6.06%", "6.09%", "6.12%", "c", "2(3) + 9/100 = 6.09%"),
                        ExerciseProblem(3, "5% @ CI for 2 years =", "10%", "10.10%", "10.50%", "10.25%", "d", "10 + 25/100 = 10.25%"),
                        ExerciseProblem(4, "10% @ CI for 2 years =", "20%", "20.20%", "21%", "20.40%", "c", "20 + 1 = 21%"),
                        ExerciseProblem(5, "10% @ CI for 3 years =", "30%", "33%", "33.1%", "33.33%", "c", "33.1%")
                    )
                ),
                CalculationType(
                    typeNumber = 2,
                    titleEnglish = "Difference between CI and SI (Type 2)",
                    titleHindi = "CI और SI का अंतर",
                    hintHindi = "2 वर्ष का अंतर = (R²/100)%। जैसे 10% के लिए 10²/100 = 1%। 2% के लिए 2²/100 = 0.04%।",
                    hintEnglish = "CI - SI difference for 2 years is P(R/100)² or (R²/100)%.",
                    formula = "Difference% (2 yrs) = R²/100 %",
                    steps = listOf(
                        CalculationStep("Step 1", "R = 10%", "दर", "Rate R = 10%"),
                        CalculationStep("Step 2", "R² / 100 = 100 / 100 = 1%", "अंतर सूत्र", "R²/100 = 1%"),
                        CalculationStep("Step 3", "Difference = 1%", "उत्तर", "Result = 1%")
                    ),
                    exercises = listOf(
                        ExerciseProblem(1, "Difference between CI & SI for 2% @ 2 years =", "0.2%", "0.04%", "0.02%", "0.4%", "b", "2²/100 = 0.04%"),
                        ExerciseProblem(2, "Difference between CI & SI for 3% @ 2 years =", "0.6%", "0.09%", "0.06%", "0.9%", "b", "3²/100 = 0.09%"),
                        ExerciseProblem(3, "Difference between CI & SI for 10% @ 2 years =", "1%", "2%", "1.5%", "2.5%", "a", "10²/100 = 1%")
                    )
                ),
                CalculationType(
                    typeNumber = 3,
                    titleEnglish = "Non-Standard Compounding Cycles (Type 3)",
                    titleHindi = "5-मासिक, 8-मासिक, 10-मासिक ब्याज चक्र",
                    hintHindi = "12% p.a. @ CI compounded 5-monthly in 10 months: 12 महीने = 12%, 5 महीने = 5%। 10 महीने = 2 चक्र। 5% का 2 चक्र = 10.25%।",
                    hintEnglish = "Calculate adjusted rate per cycle = (R/12) × cycle_months, and number of cycles = total_months / cycle_months.",
                    formula = "Cycle Rate = (Annual Rate / 12) × Cycle Months",
                    steps = listOf(
                        CalculationStep("Step 1", "Cycle Rate = (12% / 12) × 5 = 5%", "प्रति चक्र दर", "Cycle Rate = 5%"),
                        CalculationStep("Step 2", "Number of Cycles = 10 / 5 = 2", "चक्रों की संख्या", "Number of Cycles = 2"),
                        CalculationStep("Step 3", "CI Net Rate for 2 cycles of 5% = 10.25%", "कुल ब्याज दर", "Net Rate = 10.25%")
                    ),
                    exercises = listOf(
                        ExerciseProblem(1, "12% p.a. @ CI compounded 5-monthly in 10 months =", "11%", "11.25%", "12.25%", "10.25%", "d", "5% for 2 cycles = 10.25%"),
                        ExerciseProblem(2, "10% p.a. @ CI half-yearly in 1 year =", "5.25%", "6.25%", "11.25%", "10.25%", "d", "5% for 2 cycles = 10.25%"),
                        ExerciseProblem(3, "20% p.a. @ CI half-yearly in 1 year =", "22%", "21%", "22.5%", "21.50%", "b", "10% for 2 cycles = 21%")
                    )
                )
            )
        )
    }

    private fun createTimeWorkChapter(): Chapter {
        return Chapter(
            id = 12,
            titleEnglish = "Time & Work",
            titleHindi = "समय एवं कार्य",
            description = "Fractional work to full days converter, percentage work conversion, and relative workload days.",
            iconName = "work",
            pageRange = "513-532",
            quickTip = "यदि A 2/5 भाग 10 दिनों में करता है: 2 का मान 10 (5 गुना) -> 5 का मान 5 × 5 = 25 दिन!",
            types = listOf(
                CalculationType(
                    typeNumber = 1,
                    titleEnglish = "Fractional & Percentage Work to Total Days (Type 1)",
                    titleHindi = "कार्य भाग से कुल दिन निकालना",
                    hintHindi = "A does 2/5 work in 10 days: 2 unit = 10 days (5 गुना) ⇒ 5 unit = 5 × 5 = 25 days. 33 1/3% (1/3) in 17 days ⇒ 17 × 3 = 51 days.",
                    hintEnglish = "If (a/b) work is done in D days, total days = D × (b/a).",
                    formula = "Total Days = D × (b / a)",
                    steps = listOf(
                        CalculationStep("Step 1", "2/5 work = 10 days", "दिया गया मान", "2 units = 10 days"),
                        CalculationStep("Step 2", "1 unit = 10 / 2 = 5 days", "1 इकाई का मान", "1 unit = 5 days"),
                        CalculationStep("Step 3", "Total (5 units) = 5 × 5 = 25 days", "पूर्ण कार्य के दिन", "5 units = 25 days")
                    ),
                    exercises = listOf(
                        ExerciseProblem(1, "If A does 2/5 part of the work in 10 days, whole work will be completed in =", "20 days", "25 days", "30 days", "40 days", "b", "10 × (5/2) = 25 days"),
                        ExerciseProblem(2, "If A does 3/4 part of the work in 27 days, whole work completed in =", "20 days", "24 days", "36 days", "30 days", "c", "27 × (4/3) = 36 days"),
                        ExerciseProblem(3, "If A does 5/17 part of work in 25 days, whole work completed in =", "68 days", "85 days", "102 days", "91 days", "b", "25 × (17/5) = 85 days"),
                        ExerciseProblem(4, "If A does 33 1/3% of work in 17 days, whole work completed in =", "50 days", "51 days", "68 days", "34 days", "b", "33 1/3% = 1/3 -> 17 × 3 = 51 days"),
                        ExerciseProblem(5, "If A does 4/5 of work in 10 days, then 2/3 of work completed in =", "6 1/3 days", "8 1/3 days", "6 2/3 days", "9 1/3 days", "b", "Total = 10 × (5/4) = 12.5 days. 2/3 work = 12.5 × 2/3 = 25/3 = 8 1/3 days")
                    )
                )
            )
        )
    }

    private fun createSpeedTricksChapter(): Chapter {
        return Chapter(
            id = 13,
            titleEnglish = "Vedic Speed Tricks",
            titleHindi = "वैदिक स्पीड ट्रिक्स",
            description = "Master extra speed math tricks: multiplication by 11, squaring numbers ending in 5, base multiplication, and digit sum.",
            iconName = "flash_on",
            pageRange = "Extra",
            quickTip = "कैलकुलेशन में वैदिक गणित के ये तरीके आपका बहुत समय बचाएंगे।",
            types = listOf(
                CalculationType(
                    typeNumber = 1,
                    titleEnglish = "Multiply any number by 11",
                    titleHindi = "किसी भी संख्या को 11 से गुणा करना",
                    hintHindi = "35 × 11: 3 को शुरुआत में और 5 को अंत में लिखें। बीच में दोनों अंकों का योग (3+5=8) लिखें -> 385।",
                    hintEnglish = "Write the first and last digits on the ends, and place the sum of adjacent digits in the middle.",
                    formula = "ab × 11 = a | (a+b) | b",
                    steps = listOf(
                        CalculationStep("Step 1", "3 _ 5", "पहला और आखिरी अंक लिखें", "Write first and last digit"),
                        CalculationStep("Step 2", "3 + 5 = 8", "अंकों को जोड़ें", "Add the digits"),
                        CalculationStep("Step 3", "385", "बीच में योग रखें", "Place the sum in the middle")
                    ),
                    exercises = listOf(
                        ExerciseProblem(1, "45 × 11 =", "495", "485", "455", "505", "a", "4 | 4+5 | 5 -> 495"),
                        ExerciseProblem(2, "72 × 11 =", "792", "722", "812", "782", "a", "7 | 7+2 | 2 -> 792"),
                        ExerciseProblem(3, "85 × 11 =", "935", "925", "855", "955", "a", "8 | 8+5=13 (carry 1) | 5 -> (8+1)35 = 935")
                    )
                ),
                CalculationType(
                    typeNumber = 2,
                    titleEnglish = "Squaring numbers ending in 5",
                    titleHindi = "5 पर समाप्त होने वाली संख्या का वर्ग",
                    hintHindi = "65²: 5 का वर्ग 25 अंत में लिखें। 6 को उसके अगले अंक 7 से गुणा करें (6×7=42)। उत्तर 4225।",
                    hintEnglish = "Multiply the tens digit by (tens digit + 1) and append 25 at the end.",
                    formula = "(n5)² = [n × (n+1)] | 25",
                    steps = listOf(
                        CalculationStep("Step 1", "6 × (6 + 1) = 42", "दहाई अंक को उसके अगले अंक से गुणा करें", "Multiply tens digit by its next number"),
                        CalculationStep("Step 2", "5² = 25", "5 का वर्ग लिखें", "Square of 5"),
                        CalculationStep("Step 3", "4225", "दोनों को मिला दें", "Combine them")
                    ),
                    exercises = listOf(
                        ExerciseProblem(1, "45² =", "2025", "2525", "1625", "2425", "a", "4×5 = 20, 5² = 25 -> 2025"),
                        ExerciseProblem(2, "85² =", "7225", "6425", "7425", "8125", "a", "8×9 = 72, 5² = 25 -> 7225"),
                        ExerciseProblem(3, "105² =", "11025", "10025", "11525", "10525", "a", "10×11 = 110, 5² = 25 -> 11025")
                    )
                ),
                CalculationType(
                    typeNumber = 3,
                    titleEnglish = "Base Multiplication (Close to 100)",
                    titleHindi = "बेस 100 गुणा",
                    hintHindi = "104 × 106: दोनों 100 से 4 और 6 ज्यादा हैं। 104+6 = 110 और 4×6 = 24 -> 11024।",
                    hintEnglish = "Cross add/subtract the deviation from 100, then append the product of the deviations.",
                    formula = "(100+a)(100+b) = (100+a+b) | (a×b)",
                    steps = listOf(
                        CalculationStep("Step 1", "104 + 6 = 110", "क्रॉस जोड़ें (या घटाएं)", "Cross add deviations"),
                        CalculationStep("Step 2", "4 × 6 = 24", "अंतर (deviations) का गुणा करें", "Multiply deviations"),
                        CalculationStep("Step 3", "11024", "दोनों को मिला दें (दो अंक)", "Combine them (keep 2 digits)")
                    ),
                    exercises = listOf(
                        ExerciseProblem(1, "103 × 107 =", "11021", "11010", "12121", "10721", "a", "103+7 = 110, 3×7 = 21 -> 11021"),
                        ExerciseProblem(2, "96 × 98 =", "9408", "9208", "9508", "9608", "a", "96-2 = 94, (-4)×(-2) = 08 -> 9408"),
                        ExerciseProblem(3, "105 × 109 =", "11445", "11545", "11345", "11645", "a", "105+9 = 114, 5×9 = 45 -> 11445")
                    )
                ),
                CalculationType(
                    typeNumber = 4,
                    titleEnglish = "Multiplication by 9, 99, 999",
                    titleHindi = "9, 99, 999 से त्वरित गुणा",
                    hintHindi = "345 × 999: 345-1 = 344। 999-344 = 655। दोनों को मिला दें -> 344655।",
                    hintEnglish = "Subtract 1 from the number, then subtract the result from 9s.",
                    formula = "N × (9...9) = (N-1) | (9...9 - (N-1))",
                    steps = listOf(
                        CalculationStep("Step 1", "345 - 1 = 344", "संख्या में से 1 घटाएं", "Subtract 1 from the number"),
                        CalculationStep("Step 2", "999 - 344 = 655", "परिणाम को 999 में से घटाएं", "Subtract result from 9s"),
                        CalculationStep("Step 3", "344655", "दोनों को मिला दें", "Combine the two parts")
                    ),
                    exercises = listOf(
                        ExerciseProblem(1, "78 × 99 =", "7722", "7822", "7712", "7622", "a", "78-1=77, 99-77=22 -> 7722"),
                        ExerciseProblem(2, "456 × 999 =", "455544", "456544", "454544", "455444", "a", "456-1=455, 999-455=544 -> 455544"),
                        ExerciseProblem(3, "83 × 99 =", "8217", "8317", "8117", "8417", "a", "83-1=82, 99-82=17 -> 8217")
                    )
                )
            )
        )
    }

    private fun createSscCglEssentialsChapter(): Chapter {
        return Chapter(
            id = 14,
            titleEnglish = "SSC CGL Essentials",
            titleHindi = "SSC CGL विशेष",
            description = "Crucial tables, squares, cubes, Pythagorean triplets, and digital sum methods specifically required for SSC CGL.",
            iconName = "star",
            pageRange = "Formula",
            quickTip = "SSC CGL में समय बचाने के लिए Digital Sum (C9) और Pythagorean Triplets याद रखना बहुत जरूरी है!",
            types = listOf(
                CalculationType(
                    typeNumber = 1,
                    titleEnglish = "Digital Sum (C9 Method)",
                    titleHindi = "डिजिटल सम (C9 विधि)",
                    hintHindi = "किसी भी संख्या के अंकों का योग तब तक करें जब तक 1 अंक न बचे। 9 को 0 मान लें। विकल्प छाँटने में बहुत उपयोगी।",
                    hintEnglish = "Sum the digits until you get a single digit. Treat 9 as 0. Great for eliminating options in MCQs.",
                    formula = "DS(a × b) = DS(a) × DS(b)",
                    steps = listOf(
                        CalculationStep("Step 1", "456 → 4+5+6 = 15 → 6", "अंकों का योग करें (9 को छोड़ें)", "Sum digits (ignore 9s)"),
                        CalculationStep("Step 2", "73 × 456 → DS(1) × DS(6) = 6", "समीकरण पर लागू करें", "Apply to equation"),
                        CalculationStep("Step 3", "Option with DS 6 is answer", "विकल्पों से मिलान करें", "Match with option DS")
                    ),
                    exercises = listOf(
                        ExerciseProblem(1, "What is the DS of 8745?", "6", "5", "8", "9", "a", "8+7+4+5 = 24 -> 2+4 = 6 (or ignore 4+5=9, 8+7=15->6)"),
                        ExerciseProblem(2, "Find DS of 342 × 71", "0 or 9", "1", "2", "3", "a", "342 (3+4+2=9=0). 0 × anything = 0")
                    )
                ),
                CalculationType(
                    typeNumber = 2,
                    titleEnglish = "Pythagorean Triplets",
                    titleHindi = "पाइथागोरियन ट्रिपलेट्स",
                    hintHindi = "SSC Geometry और Trigonometry में सीधा उत्तर देने के लिए इन ट्रिपलेट्स को रट लें।",
                    hintEnglish = "Memorize these to instantly solve Right Angled Triangles in SSC.",
                    formula = "a² + b² = c² (e.g., 3-4-5, 5-12-13, 8-15-17, 7-24-25, 9-40-41)",
                    steps = listOf(
                        CalculationStep("Step 1", "3, 4, 5", "Basic triplet", "Base triplet"),
                        CalculationStep("Step 2", "6, 8, 10", "Multiply by 2", "Scale by 2"),
                        CalculationStep("Step 3", "5, 12, 13", "Another common triplet", "Another base")
                    ),
                    exercises = listOf(
                        ExerciseProblem(1, "If sides are 7 and 24, hypotenuse is?", "25", "26", "23", "27", "a", "Triplet: 7, 24, 25"),
                        ExerciseProblem(2, "If sides are 9 and 40, hypotenuse is?", "41", "42", "40", "39", "a", "Triplet: 9, 40, 41")
                    )
                ),
                CalculationType(
                    typeNumber = 3,
                    titleEnglish = "Base 50 Squaring",
                    titleHindi = "50 के आधार पर वर्ग",
                    hintHindi = "54²: 50 से 4 ज्यादा है। 25 में 4 जोड़ें = 29। 4 का वर्ग 16। उत्तर 2916।",
                    hintEnglish = "Compare with 50. Add/subtract deviation from 25. Append square of deviation.",
                    formula = "(50 ± x)² = (25 ± x) | x²",
                    steps = listOf(
                        CalculationStep("Step 1", "54 = 50 + 4", "50 से अंतर", "Deviation from 50"),
                        CalculationStep("Step 2", "25 + 4 = 29", "25 में अंतर जोड़ें", "Add deviation to 25"),
                        CalculationStep("Step 3", "4² = 16 → 2916", "अंतर का वर्ग साथ में लिखें", "Append square of deviation")
                    ),
                    exercises = listOf(
                        ExerciseProblem(1, "56² =", "3136", "3036", "3236", "2936", "a", "25+6 = 31, 6² = 36 -> 3136"),
                        ExerciseProblem(2, "47² =", "2209", "2109", "2309", "2409", "a", "25-3 = 22, (-3)² = 09 -> 2209"),
                        ExerciseProblem(3, "42² =", "1764", "1664", "1864", "1964", "a", "25-8 = 17, (-8)² = 64 -> 1764")
                    )
                )
            )
        )
    }

    val fractionPercentTable: List<FractionPercentEntry> = listOf(
        FractionPercentEntry("1/1", "1.00", "100%", "100%"),
        FractionPercentEntry("1/2", "0.50", "50%", "50%"),
        FractionPercentEntry("1/3", "0.3333", "33.33%", "33 1/3%"),
        FractionPercentEntry("2/3", "0.6666", "66.66%", "66 2/3%"),
        FractionPercentEntry("1/4", "0.25", "25%", "25%"),
        FractionPercentEntry("3/4", "0.75", "75%", "75%"),
        FractionPercentEntry("1/5", "0.20", "20%", "20%"),
        FractionPercentEntry("2/5", "0.40", "40%", "40%"),
        FractionPercentEntry("3/5", "0.60", "60%", "60%"),
        FractionPercentEntry("4/5", "0.80", "80%", "80%"),
        FractionPercentEntry("1/6", "0.1666", "16.66%", "16 2/3%"),
        FractionPercentEntry("5/6", "0.8333", "83.33%", "83 1/3%"),
        FractionPercentEntry("1/7", "0.1428", "14.28%", "14 2/7%"),
        FractionPercentEntry("2/7", "0.2857", "28.57%", "28 4/7%"),
        FractionPercentEntry("3/7", "0.4285", "42.85%", "42 6/7%"),
        FractionPercentEntry("4/7", "0.5714", "57.14%", "57 1/7%"),
        FractionPercentEntry("5/7", "0.7142", "71.42%", "71 3/7%"),
        FractionPercentEntry("6/7", "0.8571", "85.71%", "85 5/7%"),
        FractionPercentEntry("1/8", "0.125", "12.50%", "12 1/2%"),
        FractionPercentEntry("3/8", "0.375", "37.50%", "37 1/2%"),
        FractionPercentEntry("5/8", "0.625", "62.50%", "62 1/2%"),
        FractionPercentEntry("7/8", "0.875", "87.50%", "87 1/2%"),
        FractionPercentEntry("1/9", "0.1111", "11.11%", "11 1/9%"),
        FractionPercentEntry("2/9", "0.2222", "22.22%", "22 2/9%"),
        FractionPercentEntry("4/9", "0.4444", "44.44%", "44 4/9%"),
        FractionPercentEntry("5/9", "0.5555", "55.55%", "55 5/9%"),
        FractionPercentEntry("7/9", "0.7777", "77.77%", "77 7/9%"),
        FractionPercentEntry("8/9", "0.8888", "88.88%", "88 8/9%"),
        FractionPercentEntry("1/10", "0.10", "10%", "10%"),
        FractionPercentEntry("1/11", "0.0909", "9.09%", "9 1/11%"),
        FractionPercentEntry("1/12", "0.0833", "8.33%", "8 1/3%"),
        FractionPercentEntry("1/13", "0.0769", "7.69%", "7 9/13%"),
        FractionPercentEntry("1/14", "0.0714", "7.14%", "7 1/7%"),
        FractionPercentEntry("1/15", "0.0666", "6.66%", "6 2/3%"),
        FractionPercentEntry("1/16", "0.0625", "6.25%", "6 1/4%"),
        FractionPercentEntry("1/20", "0.05", "5%", "5%"),
        FractionPercentEntry("1/25", "0.04", "4%", "4%"),
        FractionPercentEntry("1/30", "0.0333", "3.33%", "3 1/3%")
    )

    fun generateSpeedQuizQuestions(count: Int = 10, chapterFilter: Int? = null): List<QuizQuestion> {
        val pool = mutableListOf<QuizQuestion>()
        val targetChapters = if (chapterFilter != null) {
            chapters.filter { it.id == chapterFilter }
        } else {
            chapters
        }

        for (ch in targetChapters) {
            for (type in ch.types) {
                for (ex in type.exercises) {
                    pool.add(
                        QuizQuestion(
                            chapterId = ch.id,
                            chapterTitle = ch.titleHindi + " (" + ch.titleEnglish + ")",
                            questionText = ex.question,
                            optionA = ex.optionA,
                            optionB = ex.optionB,
                            optionC = ex.optionC,
                            optionD = ex.optionD,
                            correctOption = ex.correctOption,
                            explanation = ex.solutionSteps
                        )
                    )
                }
            }
        }

        // Also add dynamically generated questions for variety
        pool.addAll(generateDynamicQuestions(count * 5 + 500))

        return pool.shuffled().take(count)
    }

    private fun generateDynamicQuestions(count: Int): List<QuizQuestion> {
        val list = mutableListOf<QuizQuestion>()
        repeat(count) {
            when (Random.nextInt(12)) {
                0 -> {
                    // 2-digit addition
                    val a = Random.nextInt(20, 99)
                    val b = Random.nextInt(10, 99)
                    val ans = a + b
                    val wrong1 = ans + 10
                    val wrong2 = ans - 2
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
                1 -> {
                    // 1000 - x subtraction
                    val x = Random.nextInt(111, 989)
                    val ans = 1000 - x
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
                            questionText = "1000 - $x =",
                            optionA = options[0],
                            optionB = options[1],
                            optionC = options[2],
                            optionD = options[3],
                            correctOption = letter,
                            explanation = "All from 9, last from 10: $ans"
                        )
                    )
                }
                2 -> {
                    // 2-digit Square
                    val num = Random.nextInt(15, 60)
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
                3 -> {
                    // Net % increase
                    val r1 = listOf(10, 20, 30, 25, 15).random()
                    val r2 = listOf(10, 20, 30, 5, 15).random()
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
                4 -> {
                    // Time and Work
                    val aFrac = listOf(2 to 5, 3 to 4, 1 to 3, 4 to 5, 5 to 6).random()
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
                5 -> {
                    // CI 2 years
                    val r = listOf(2, 3, 4, 5, 6, 7, 8, 9, 10, 12).random()
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
                6 -> {
                    // Vedic Multiply by 11
                    val num = Random.nextInt(21, 89)
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
                }
                7 -> {
                    // Base 50 Square
                    val num = Random.nextInt(41, 59)
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
                }
                8 -> {
                    // Tables 12-25
                    val a = Random.nextInt(12, 26)
                    val b = Random.nextInt(5, 10)
                    val ans = a * b
                    val wrong1 = ans + a
                    val wrong2 = ans - a
                    val wrong3 = ans + 10
                    val options = listOf(ans.toString(), wrong1.toString(), wrong2.toString(), wrong3.toString()).shuffled()
                    val correctIdx = options.indexOf(ans.toString())
                    val letter = listOf("a", "b", "c", "d")[correctIdx]
                    list.add(
                        QuizQuestion(
                            chapterId = 14,
                            chapterTitle = "SSC CGL Essentials",
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
                9 -> {
                    // Digital Sum Concept
                    val a = Random.nextInt(11, 45)
                    val b = Random.nextInt(11, 45)
                    val trueAns = a * b
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
                10 -> {
                    // Squares ending in 5
                    val tens = Random.nextInt(2, 12)
                    val num = tens * 10 + 5
                    val ans = num * num
                    val wrong1 = (tens * (tens+2) * 100) + 25
                    val wrong2 = (tens * tens * 100) + 25
                    val wrong3 = ans + 1000
                    val options = listOf(ans.toString(), wrong1.toString(), wrong2.toString(), wrong3.toString()).shuffled()
                    val correctIdx = options.indexOf(ans.toString())
                    val letter = listOf("a", "b", "c", "d")[correctIdx]
                    list.add(
                        QuizQuestion(
                            chapterId = 13,
                            chapterTitle = "Vedic Speed Tricks",
                            questionText = "$num² =",
                            optionA = options[0],
                            optionB = options[1],
                            optionC = options[2],
                            optionD = options[3],
                            correctOption = letter,
                            explanation = "($tens × ${tens+1}) | 25 = $ans"
                        )
                    )
                }
                else -> {
                    // Base 100 Multiplication
                    val a = Random.nextInt(101, 109)
                    val b = Random.nextInt(101, 109)
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
        }
        return list
    }
}
