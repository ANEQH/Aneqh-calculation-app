package com.example.data.repository

import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt

/**
 * SSC CGL Specialized Quantitative Aptitude Solvers.
 * Provides structured input-formula-substitution-calculation-result outputs
 * for key Tier-1 & Tier-2 question types.
 */
object SscCglSolvers {

    data class DetailedSolverResult(
        val finalAnswer: String,
        val methodTitle: String,
        val topicHindi: String,
        val inputSummary: String,
        val formula: String,
        val substitution: String,
        val calculationSteps: List<String>,
        val examShortcut: String,
        val mentalNoteHindi: String
    )

    // 1. Percentage Hub - Comprehensive 12 Modes
    fun solvePercentageHub(
        modeIndex: Int,
        valA: Double,
        valB: Double,
        valC: Double = 0.0
    ): DetailedSolverResult {
        return when (modeIndex) {
            1 -> { // x% of y
                val ans = (valA / 100.0) * valB
                DetailedSolverResult(
                    finalAnswer = formatNum(ans),
                    methodTitle = "$valA% of $valB",
                    topicHindi = "प्रतिशत मान निकालना",
                    inputSummary = "x = $valA%, y = $valB",
                    formula = "Result = (x / 100) × y",
                    substitution = "Result = ($valA / 100) × $valB",
                    calculationSteps = listOf(
                        "Step 1: प्रतिशत को भिन्न में बदलें → $valA / 100 = ${valA / 100.0}",
                        "Step 2: संख्या $valB से गुणा करें → ${valA / 100.0} × $valB = $ans"
                    ),
                    examShortcut = "Split method: 10% = ${valB * 0.1}, 1% = ${valB * 0.01}. Break $valA into tens and units.",
                    mentalNoteHindi = "दहाई और इकाई में तोड़कर 10% और 1% की मदद से बिना पेन निकाले हल करें।"
                )
            }
            2 -> { // x is what % of y
                if (valB == 0.0) return invalidResult("Denominator y cannot be 0")
                val ans = (valA / valB) * 100.0
                DetailedSolverResult(
                    finalAnswer = "${formatNum(ans)}%",
                    methodTitle = "$valA is what % of $valB",
                    topicHindi = "संख्या का प्रतिशत अंश",
                    inputSummary = "x = $valA, y = $valB",
                    formula = "Percentage (%) = (x / y) × 100",
                    substitution = "Percentage = ($valA / $valB) × 100",
                    calculationSteps = listOf(
                        "Step 1: अंश (Numerator) x = $valA, हर (Denominator) y = $valB",
                        "Step 2: अनुपात बनाएं → $valA / $valB = ${valA / valB}",
                        "Step 3: 100 से गुणा करें → ${valA / valB} × 100 = ${formatNum(ans)}%"
                    ),
                    examShortcut = "Simplifying fraction $valA/$valB to standard reciprocal percentage tables gives answer in 2s.",
                    mentalNoteHindi = "तुलना हमेशा आधार संख्या (y) से होती है जिसे हर (denominator) में रखा जाता है।"
                )
            }
            3 -> { // Percentage Increase (y increased by x%)
                val increase = (valA / 100.0) * valB
                val ans = valB + increase
                DetailedSolverResult(
                    finalAnswer = formatNum(ans),
                    methodTitle = "$valB increased by $valA%",
                    topicHindi = "प्रतिशत वृद्धि",
                    inputSummary = "मूल मान = $valB, वृद्धि दर = $valA%",
                    formula = "New Value = y × (1 + x / 100)",
                    substitution = "New Value = $valB × (1 + $valA / 100) = $valB × ${(100 + valA) / 100.0}",
                    calculationSteps = listOf(
                        "Step 1: Multiplier = 100% + $valA% = ${(100 + valA)}% = ${(100 + valA) / 100.0}",
                        "Step 2: $valB × ${(100 + valA) / 100.0} = $ans",
                        "Step 3: शुद्ध वृद्धि = $increase"
                    ),
                    examShortcut = "Direct multiplying factor: × ${(100 + valA) / 100.0}",
                    mentalNoteHindi = "100 में प्रतिशत जोड़कर मल्टीप्लायर निकालें और सीधे गुणा करें।"
                )
            }
            4 -> { // Percentage Decrease (y decreased by x%)
                val decrease = (valA / 100.0) * valB
                val ans = valB - decrease
                DetailedSolverResult(
                    finalAnswer = formatNum(ans),
                    methodTitle = "$valB decreased by $valA%",
                    topicHindi = "प्रतिशत कमी",
                    inputSummary = "मूल मान = $valB, कमी दर = $valA%",
                    formula = "New Value = y × (1 - x / 100)",
                    substitution = "New Value = $valB × (1 - $valA / 100) = $valB × ${(100 - valA) / 100.0}",
                    calculationSteps = listOf(
                        "Step 1: Multiplier = 100% - $valA% = ${(100 - valA)}% = ${(100 - valA) / 100.0}",
                        "Step 2: $valB × ${(100 - valA) / 100.0} = $ans",
                        "Step 3: शुद्ध कमी = $decrease"
                    ),
                    examShortcut = "Direct multiplying factor: × ${(100 - valA) / 100.0}",
                    mentalNoteHindi = "100 में से प्रतिशत घटाकर मल्टीप्लायर से गुणा करें।"
                )
            }
            5 -> { // Successive Increase (a% and b%)
                val net = valA + valB + (valA * valB) / 100.0
                DetailedSolverResult(
                    finalAnswer = "${formatNum(net)}%",
                    methodTitle = "Successive Increase of $valA% and $valB%",
                    topicHindi = "क्रमागत प्रतिशत वृद्धि",
                    inputSummary = "प्रथम वृद्धि a = $valA%, द्वितीय वृद्धि b = $valB%",
                    formula = "Net % = a + b + (a × b) / 100",
                    substitution = "Net % = $valA + $valB + ($valA × $valB) / 100",
                    calculationSteps = listOf(
                        "Step 1: दोनों का सीधा योग → $valA + $valB = ${valA + valB}",
                        "Step 2: गुणनफल का 1% → ($valA × $valB) / 100 = ${(valA * valB) / 100.0}",
                        "Step 3: कुल प्रभावी दर = ${valA + valB} + ${(valA * valB) / 100.0} = ${formatNum(net)}%"
                    ),
                    examShortcut = "Universal formula for CI 2-year net rate and successive area expansion.",
                    mentalNoteHindi = "दोनों को जोड़ें और दोनों के गुणनफल में 2 अंक बाद दशमलव लगाकर जोड़ दें।"
                )
            }
            6 -> { // Successive Discount / Decrease (-a% and -b%)
                val netDisc = valA + valB - (valA * valB) / 100.0
                DetailedSolverResult(
                    finalAnswer = "${formatNum(netDisc)}% Discount",
                    methodTitle = "Successive Discount of $valA% and $valB%",
                    topicHindi = "क्रमागत छूट (बट्टा)",
                    inputSummary = "बट्टा 1 = $valA%, बट्टा 2 = $valB%",
                    formula = "Single Equivalent Discount = a + b - (a × b) / 100",
                    substitution = "Equivalent = $valA + $valB - ($valA × $valB) / 100",
                    calculationSteps = listOf(
                        "Step 1: दोनों छूट का योग → $valA + $valB = ${valA + valB}%",
                        "Step 2: संयुक्त छूट समायोजन घटाएं → ($valA × $valB)/100 = ${(valA * valB) / 100.0}%",
                        "Step 3: एकल समतुल्य बट्टा = ${valA + valB} - ${(valA * valB) / 100.0} = ${formatNum(netDisc)}%"
                    ),
                    examShortcut = "Direct standard SSC formula for equivalent single discount.",
                    mentalNoteHindi = "दोनों को जोड़कर उनके गुणनफल / 100 को घटाया जाता है।"
                )
            }
            7 -> { // Reverse Percentage: Final is Y after X% increase, find original
                if (100.0 + valA == 0.0) return invalidResult("Invalid percentage")
                val orig = (valB / (100.0 + valA)) * 100.0
                DetailedSolverResult(
                    finalAnswer = formatNum(orig),
                    methodTitle = "Original Value (after $valA% increase to $valB)",
                    topicHindi = "रिवर्स प्रतिशत (प्रारंभिक मान)",
                    inputSummary = "अंतिम मान = $valB, वृद्धि = $valA%",
                    formula = "Original Value = [ Final / (100 + x) ] × 100",
                    substitution = "Original Value = [ $valB / (100 + $valA) ] × 100 = [ $valB / ${(100 + valA)} ] × 100",
                    calculationSteps = listOf(
                        "Step 1: अंतिम मान 100 + $valA = ${(100 + valA)}% का प्रतिनिधित्व करता है",
                        "Step 2: 1% का मान = $valB / ${(100 + valA)} = ${valB / (100 + valA)}",
                        "Step 3: 100% का मान (मूल मान) = ${valB / (100 + valA)} × 100 = ${formatNum(orig)}"
                    ),
                    examShortcut = "Never calculate x% on final value! Divide final value by (1 + x/100).",
                    mentalNoteHindi = "बढ़ी हुई संख्या पर वापस प्रतिशत मत लगाइए, (100+x) से भाग देकर 100 निकालें।"
                )
            }
            8 -> { // Increase then Decrease (+a% then -b%)
                val net = valA - valB - (valA * valB) / 100.0
                DetailedSolverResult(
                    finalAnswer = "${formatNum(net)}% ${if (net >= 0) "Net Increase" else "Net Decrease"}",
                    methodTitle = "Increase by $valA% then Decrease by $valB%",
                    topicHindi = "पहले वृद्धि फिर कमी",
                    inputSummary = "वृद्धि a = +$valA%, कमी b = -$valB%",
                    formula = "Net % = a - b - (a × b) / 100",
                    substitution = "Net % = $valA - $valB - ($valA × $valB) / 100",
                    calculationSteps = listOf(
                        "Step 1: सीधा अंतर → $valA - $valB = ${valA - valB}%",
                        "Step 2: गुणनफल / 100 घटाएं → ($valA × $valB)/100 = ${(valA * valB) / 100.0}%",
                        "Step 3: कुल शुद्ध बदलाव = ${valA - valB} - ${(valA * valB) / 100.0} = ${formatNum(net)}%"
                    ),
                    examShortcut = "Successive change formula with signs: (+a) + (-b) + (+a)(-b)/100.",
                    mentalNoteHindi = "समान वृद्धि व कमी (x% increase, x% decrease) पर हमेशा x²/100% की कमी होती है।"
                )
            }
            9 -> { // Profit % (CP = valA, SP = valB)
                val cp = valA
                val sp = valB
                val profit = sp - cp
                val pct = if (cp > 0) (profit / cp) * 100.0 else 0.0
                DetailedSolverResult(
                    finalAnswer = "${formatNum(pct)}% Profit",
                    methodTitle = "Profit % (CP = $cp, SP = $sp)",
                    topicHindi = "लाभ प्रतिशत (Profit %)",
                    inputSummary = "क्रय मूल्य (CP) = $cp, विक्रय मूल्य (SP) = $sp",
                    formula = "Profit % = [ (SP - CP) / CP ] × 100",
                    substitution = "Profit % = [ ($sp - $cp) / $cp ] × 100 = [ $profit / $cp ] × 100",
                    calculationSteps = listOf(
                        "Step 1: लाभ = SP - CP = $sp - $cp = $profit",
                        "Step 2: आधार हमेशा क्रय मूल्य (CP = $cp) होता है",
                        "Step 3: प्रतिशत = ($profit / $cp) × 100 = ${formatNum(pct)}%"
                    ),
                    examShortcut = "Ratio SP:CP determines profit multiplier directly.",
                    mentalNoteHindi = "लाभ हमेशा क्रय मूल्य (CP) पर ही निकाला जाता है।"
                )
            }
            10 -> { // Loss % (CP = valA, SP = valB)
                val cp = valA
                val sp = valB
                val loss = cp - sp
                val pct = if (cp > 0) (loss / cp) * 100.0 else 0.0
                DetailedSolverResult(
                    finalAnswer = "${formatNum(pct)}% Loss",
                    methodTitle = "Loss % (CP = $cp, SP = $sp)",
                    topicHindi = "हानि प्रतिशत (Loss %)",
                    inputSummary = "क्रय मूल्य (CP) = $cp, विक्रय मूल्य (SP) = $sp",
                    formula = "Loss % = [ (CP - SP) / CP ] × 100",
                    substitution = "Loss % = [ ($cp - $sp) / $cp ] × 100 = [ $loss / $cp ] × 100",
                    calculationSteps = listOf(
                        "Step 1: हानि = CP - SP = $cp - $sp = $loss",
                        "Step 2: आधार क्रय मूल्य (CP = $cp) है",
                        "Step 3: प्रतिशत = ($loss / $cp) × 100 = ${formatNum(pct)}%"
                    ),
                    examShortcut = "Loss % = (Loss / CP) × 100.",
                    mentalNoteHindi = "हानि होने पर CP हमेशा SP से बड़ा होता है।"
                )
            }
            11 -> { // Discount % (MP = valA, SP = valB)
                val mp = valA
                val sp = valB
                val disc = mp - sp
                val pct = if (mp > 0) (disc / mp) * 100.0 else 0.0
                DetailedSolverResult(
                    finalAnswer = "${formatNum(pct)}% Discount",
                    methodTitle = "Discount % (MP = $mp, SP = $sp)",
                    topicHindi = "छूट / बट्टा प्रतिशत",
                    inputSummary = "अंकित मूल्य (MP) = $mp, विक्रय मूल्य (SP) = $sp",
                    formula = "Discount % = [ (MP - SP) / MP ] × 100",
                    substitution = "Discount % = [ ($mp - $sp) / $mp ] × 100 = [ $disc / $mp ] × 100",
                    calculationSteps = listOf(
                        "Step 1: छूट = अंकित मूल्य (MP) - विक्रय मूल्य (SP) = $mp - $sp = $disc",
                        "Step 2: आधार हमेशा अंकित मूल्य (MP = $mp) होता है",
                        "Step 3: छूट % = ($disc / $mp) × 100 = ${formatNum(pct)}%"
                    ),
                    examShortcut = "Discount is always calculated on Marked Price (MP), never on CP or SP.",
                    mentalNoteHindi = "छूट हमेशा अंकित मूल्य (Marked Price / सूची मूल्य) पर दी जाती है।"
                )
            }
            else -> { // 12 -> Marks % (Obtained = valA, Total = valB)
                val obt = valA
                val total = valB
                val pct = if (total > 0) (obt / total) * 100.0 else 0.0
                DetailedSolverResult(
                    finalAnswer = "${formatNum(pct)}%",
                    methodTitle = "Marks Percentage ($obt / $total)",
                    topicHindi = "प्राप्तांक प्रतिशत (Marks %)",
                    inputSummary = "प्राप्तांक = $obt, कुल पूर्णांक = $total",
                    formula = "Marks % = (Obtained Marks / Total Maximum Marks) × 100",
                    substitution = "Marks % = ($obt / $total) × 100",
                    calculationSteps = listOf(
                        "Step 1: प्राप्तांक = $obt, कुल पूर्णांक = $total",
                        "Step 2: अनुपात = $obt / $total = ${if (total > 0) obt/total else 0.0}",
                        "Step 3: प्रतिशत = (${if (total > 0) obt/total else 0.0}) × 100 = ${formatNum(pct)}%"
                    ),
                    examShortcut = "CGL Score = Raw Score / 200 × 100.",
                    mentalNoteHindi = "प्राप्तांक / कुल पूर्णांक × 100"
                )
            }
        }
    }

    // 2. Simple Interest (SI = P*R*T/100) & Amount
    fun solveSimpleInterest(p: Double, r: Double, t: Double): DetailedSolverResult {
        val si = (p * r * t) / 100.0
        val amount = p + si
        return DetailedSolverResult(
            finalAnswer = "SI = ${formatNum(si)}, Amount = ${formatNum(amount)}",
            methodTitle = "Simple Interest (साधारण ब्याज)",
            topicHindi = "साधारण ब्याज (SI)",
            inputSummary = "मूलधन (P) = $p, दर (R) = $r%, समय (T) = $t वर्ष",
            formula = "SI = (P × R × T) / 100 | Amount = P + SI",
            substitution = "SI = ($p × $r × $t) / 100 = ${(p * r * t)} / 100",
            calculationSteps = listOf(
                "Step 1: कुल प्रभावी दर = R × T = $r × $t = ${r * t}%",
                "Step 2: SI = मूलधन का ${r * t}% = $p × ${(r * t) / 100.0} = $si",
                "Step 3: मिश्रधन (Amount) = P + SI = $p + $si = $amount"
            ),
            examShortcut = "Effective rate is simply R × T. Calculate (R × T)% of P in 1 step.",
            mentalNoteHindi = "साधारण ब्याज में हर साल ब्याज बराबर रहता है, सीधे R×T% निकालें।"
        )
    }

    // 3. Compound Interest (CI) & 2-year / 3-year difference with SI
    fun solveCompoundInterest(p: Double, r: Double, t: Int): DetailedSolverResult {
        val amount = p * (1.0 + r / 100.0).pow(t.toDouble())
        val ci = amount - p
        val si = (p * r * t.toDouble()) / 100.0
        val diff = ci - si
        return DetailedSolverResult(
            finalAnswer = "CI = ${formatNum(ci)}, Amount = ${formatNum(amount)}",
            methodTitle = "Compound Interest (चक्रवृद्धि ब्याज)",
            topicHindi = "चक्रवृद्धि ब्याज (CI)",
            inputSummary = "P = $p, R = $r%, T = $t वर्ष",
            formula = "Amount = P(1 + R/100)^T | CI = Amount - P",
            substitution = "Amount = $p × (1 + $r/100)^$t = $p × ${(1.0 + r / 100.0).pow(t.toDouble())}",
            calculationSteps = listOf(
                "Step 1: 1 वर्ष का गुणक = ${(100 + r) / 100.0}",
                "Step 2: $t वर्षों का गुणक = ${(1.0 + r / 100.0).pow(t.toDouble())}",
                "Step 3: मिश्रधन = $p × ${(1.0 + r / 100.0).pow(t.toDouble())} = ${formatNum(amount)}",
                "Step 4: CI = $amount - $p = ${formatNum(ci)}",
                "Step 5: CI - SI अंतर = ${formatNum(diff)}"
            ),
            examShortcut = if (t == 2) "For 2 years: CI - SI = P × (R/100)² = ${formatNum(diff)}" else "Successive rate application.",
            mentalNoteHindi = "2 वर्षों के लिए CI - SI = P(R/100)² सूत्र SSC का सबसे पसंदीदा प्रश्न है।"
        )
    }

    // 4. Time and Work (A in d1 days, B in d2 days)
    fun solveTimeAndWork(d1: Double, d2: Double, d3: Double = 0.0): DetailedSolverResult {
        if (d1 <= 0 || d2 <= 0) return invalidResult("Days must be positive")
        val ans = if (d3 <= 0.0) {
            (d1 * d2) / (d1 + d2)
        } else {
            1.0 / ((1.0 / d1) + (1.0 / d2) + (1.0 / d3))
        }
        return DetailedSolverResult(
            finalAnswer = "${formatNum(ans)} Days",
            methodTitle = "Time & Work (कार्य और समय)",
            topicHindi = "कार्य और समय (Time & Work)",
            inputSummary = if (d3 <= 0.0) "A = $d1 दिन, B = $d2 दिन" else "A = $d1 दिन, B = $d2 दिन, C = $d3 दिन",
            formula = if (d3 <= 0.0) "Together Time = (A × B) / (A + B)" else "1/T = 1/A + 1/B + 1/C",
            substitution = if (d3 <= 0.0) "Time = ($d1 × $d2) / ($d1 + $d2) = ${(d1 * d2)} / ${(d1 + d2)}" else "Sum of 1-day work",
            calculationSteps = listOf(
                "Step 1: A की 1 दिन की कार्यक्षमता = 1/$d1",
                "Step 2: B की 1 दिन की कार्यक्षमता = 1/$d2",
                "Step 3: कुल मिलकर 1 दिन का कार्य = ${(1.0/d1) + (1.0/d2)}",
                "Step 4: कुल दिन = 1 / संयुक्त कार्य = ${formatNum(ans)} दिन"
            ),
            examShortcut = "LCM Method: Assume total work = LCM($d1, $d2). Find efficiencies and divide.",
            mentalNoteHindi = "कुल कार्य = दिनों का LCM। कार्यक्षमता जोड़कर कुल काम में भाग दें।"
        )
    }

    // 5. Pipes and Cisterns (Pipe A fills in t1, Pipe B fills/empties in t2)
    fun solvePipesAndCistern(t1: Double, t2: Double, isBEmptying: Boolean): DetailedSolverResult {
        val sign = if (isBEmptying) -1.0 else 1.0
        val netRate = (1.0 / t1) + (sign / t2)
        if (netRate <= 0.0) return invalidResult("Tank will never fill (emptying rate >= filling rate)")
        val ans = 1.0 / netRate
        return DetailedSolverResult(
            finalAnswer = "${formatNum(ans)} Hours / Minutes",
            methodTitle = "Pipes & Cistern (नल और टंकी)",
            topicHindi = "नल और टंकी",
            inputSummary = "Pipe A = $t1 hr (भरने वाला), Pipe B = $t2 hr (${if (isBEmptying) "खाली करने वाला" else "भरने वाला"})",
            formula = "1/T = 1/A ${if (isBEmptying) "-" else "+"} 1/B",
            substitution = "Net rate = (1/$t1) ${if (isBEmptying) "-" else "+"} (1/$t2) = $netRate per hr",
            calculationSteps = listOf(
                "Step 1: A की भरने की दर = +1/$t1",
                "Step 2: B की दर = ${if (isBEmptying) "-" else "+"}1/$t2",
                "Step 3: संयुक्त शुद्ध प्रवाह = $netRate टंकी प्रति घंटा",
                "Step 4: भरने में लगा समय = 1 / $netRate = ${formatNum(ans)} घंटे"
            ),
            examShortcut = "Time = (A × B) / (B - A) if emptying, (A × B) / (A + B) if filling.",
            mentalNoteHindi = "खाली करने वाले नल की कार्यक्षमता ऋणात्मक (-) ली जाती है।"
        )
    }

    // 6. Time, Speed and Distance (Distance = Speed × Time) & Relative Speed
    fun solveSpeedDistance(distanceKm: Double, speedKmph: Double): DetailedSolverResult {
        if (speedKmph <= 0.0) return invalidResult("Speed must be positive")
        val timeHours = distanceKm / speedKmph
        val timeMin = timeHours * 60.0
        val speedMs = speedKmph * (5.0 / 18.0)
        return DetailedSolverResult(
            finalAnswer = "${formatNum(timeHours)} hrs (${formatNum(timeMin)} mins)",
            methodTitle = "Speed, Distance & Time (चाल, दूरी और समय)",
            topicHindi = "चाल, दूरी और समय",
            inputSummary = "दूरी (D) = $distanceKm km, चाल (S) = $speedKmph km/h",
            formula = "Time = Distance / Speed | km/h to m/s = × (5/18)",
            substitution = "Time = $distanceKm / $speedKmph = ${formatNum(timeHours)} hrs",
            calculationSteps = listOf(
                "Step 1: समय (घंटे) = दूरी / चाल = $distanceKm / $speedKmph = ${formatNum(timeHours)} घंटे",
                "Step 2: समय (मिनट) = ${formatNum(timeHours)} × 60 = ${formatNum(timeMin)} मिनट",
                "Step 3: चाल (m/s में) = $speedKmph × 5/18 = ${formatNum(speedMs)} m/s"
            ),
            examShortcut = "To convert km/h to m/s: multiply by 5/18. From m/s to km/h: multiply by 18/5.",
            mentalNoteHindi = "km/h से m/s के लिए 5/18 से गुणा, और m/s से km/h के लिए 18/5 से गुणा।"
        )
    }

    // 7. Train crossing platform or pole
    fun solveTrainProblem(
        trainLengthMeters: Double,
        platformLengthMeters: Double,
        speedKmph: Double
    ): DetailedSolverResult {
        if (speedKmph <= 0.0) return invalidResult("Speed must be positive")
        val totalDistM = trainLengthMeters + platformLengthMeters
        val speedMs = speedKmph * (5.0 / 18.0)
        val timeSec = totalDistM / speedMs
        return DetailedSolverResult(
            finalAnswer = "${formatNum(timeSec)} Seconds",
            methodTitle = "Train Crossing Problem (रेलगाड़ी संबंधी प्रश्न)",
            topicHindi = "रेलगाड़ी और प्लेटफ़ॉर्म",
            inputSummary = "ट्रेन लंबाई = $trainLengthMeters m, प्लेटफ़ॉर्म = $platformLengthMeters m, चाल = $speedKmph km/h",
            formula = "Time (sec) = (Train Length + Platform Length) / [ Speed × 5/18 ]",
            substitution = "Time = ($trainLengthMeters + $platformLengthMeters) / [ $speedKmph × 5/18 ] = $totalDistM / ${formatNum(speedMs)}",
            calculationSteps = listOf(
                "Step 1: कुल तय दूरी = ट्रेन की लंबाई + प्लेटफ़ॉर्म की लंबाई = $trainLengthMeters + $platformLengthMeters = $totalDistM m",
                "Step 2: चाल को m/s में बदलें = $speedKmph × 5/18 = ${formatNum(speedMs)} m/s",
                "Step 3: पार करने में समय = $totalDistM / ${formatNum(speedMs)} = ${formatNum(timeSec)} सेकंड"
            ),
            examShortcut = "Khamba / Man crossing requires 0 platform length. Bridge / Platform adds directly.",
            mentalNoteHindi = "खंभा पार करने में केवल अपनी लंबाई, प्लेटफ़ॉर्म में दोनों की लंबाइयाँ जुड़ेंगी।"
        )
    }

    // 8. Boat and Stream (Downstream & Upstream)
    fun solveBoatStream(boatSpeed: Double, streamSpeed: Double): DetailedSolverResult {
        if (boatSpeed <= streamSpeed) return invalidResult("Boat speed must exceed stream speed")
        val down = boatSpeed + streamSpeed
        val up = boatSpeed - streamSpeed
        return DetailedSolverResult(
            finalAnswer = "Downstream: ${formatNum(down)} km/h | Upstream: ${formatNum(up)} km/h",
            methodTitle = "Boat & Stream (नाव और धारा)",
            topicHindi = "नाव और धारा",
            inputSummary = "शांत जल में नाव चाल (B) = $boatSpeed km/h, धारा चाल (S) = $streamSpeed km/h",
            formula = "Downstream (D) = B + S | Upstream (U) = B - S | B = (D+U)/2 | S = (D-U)/2",
            substitution = "D = $boatSpeed + $streamSpeed = $down km/h | U = $boatSpeed - $streamSpeed = $up km/h",
            calculationSteps = listOf(
                "Step 1: अनुकूल चाल (धारा के साथ Downstream) = B + S = $boatSpeed + $streamSpeed = $down km/h",
                "Step 2: प्रतिकूल चाल (धारा के विपरीत Upstream) = B - S = $boatSpeed - $streamSpeed = $up km/h",
                "Step 3: औसत चाल (समान दूरी पर) = 2DU / (D+U) = ${formatNum((2.0 * down * up) / (down + up))} km/h"
            ),
            examShortcut = "If D and U are given: Boat = (D+U)/2, Current = (D-U)/2.",
            mentalNoteHindi = "धारा के साथ चालें जुड़ती हैं, विपरीत दिशा में घटती हैं।"
        )
    }

    // 9. Mixture and Alligation (Rule of Alligation)
    fun solveAlligation(c1: Double, c2: Double, mean: Double): DetailedSolverResult {
        if ((mean < c1 && mean < c2) || (mean > c1 && mean > c2)) {
            return invalidResult("Mean price must lie strictly between the two component prices")
        }
        val ratio1 = abs(c2 - mean)
        val ratio2 = abs(mean - c1)
        val gcd = gcdLong(ratio1.toLong(), ratio2.toLong()).toDouble()
        val sim1 = if (gcd > 0) ratio1 / gcd else ratio1
        val sim2 = if (gcd > 0) ratio2 / gcd else ratio2

        return DetailedSolverResult(
            finalAnswer = "Ratio: ${formatNum(sim1)} : ${formatNum(sim2)}",
            methodTitle = "Mixture & Alligation (मिश्रण और पृथक्करण)",
            topicHindi = "मिश्रण और पृथक्करण (Alligation)",
            inputSummary = "पहला भाव (C1) = $c1, दूसरा भाव (C2) = $c2, औसत भाव (Mean) = $mean",
            formula = "Quantity₁ / Quantity₂ = |C2 - Mean| / |Mean - C1|",
            substitution = "Ratio = |$c2 - $mean| : |$mean - $c1| = $ratio1 : $ratio2",
            calculationSteps = listOf(
                "Step 1: पहले प्रकार की मात्रा = दूसरे का अंतर = |$c2 - $mean| = $ratio1",
                "Step 2: दूसरे प्रकार की मात्रा = पहले का अंतर = |$mean - $c1| = $ratio2",
                "Step 3: अनुपात = $ratio1 : $ratio2 → सरल अनुपात = ${formatNum(sim1)} : ${formatNum(sim2)}"
            ),
            examShortcut = "Cross difference rule applies to price, percentages, average marks, and speeds.",
            mentalNoteHindi = "क्रॉस अंतर विधि: बीच के भाव से दोनों किनारों का अंतर निकालें।"
        )
    }

    // 10. Number System: LCM, HCF & Product Relation
    fun solveLcmHcf(a: Long, b: Long): DetailedSolverResult {
        if (a <= 0 || b <= 0) return invalidResult("Numbers must be positive")
        val hcf = gcdLong(a, b)
        val lcm = (a / hcf) * b
        return DetailedSolverResult(
            finalAnswer = "HCF = $hcf, LCM = $lcm",
            methodTitle = "LCM & HCF (ल.स.प. एवं म.स.प.)",
            topicHindi = "संख्या पद्धति - ल.स. एवं म.स.",
            inputSummary = "प्रथम संख्या a = $a, द्वितीय संख्या b = $b",
            formula = "HCF × LCM = a × b | HCF = GCD(a, b)",
            substitution = "HCF = GCD($a, $b) = $hcf | LCM = ($a × $b) / $hcf = $lcm",
            calculationSteps = listOf(
                "Step 1: यूक्लिड विभाजन विधि से HCF = $hcf",
                "Step 2: LCM = (संख्या 1 × संख्या 2) / HCF = ($a × $b) / $hcf = $lcm",
                "Step 3: गुणनफल सत्यापन: $hcf × $lcm = ${hcf * lcm} == $a × $b = ${a * b}"
            ),
            examShortcut = "Two numbers can always be written as Hx and Hy where x,y are coprime.",
            mentalNoteHindi = "दो संख्याओं का गुणनफल हमेशा उनके HCF और LCM के गुणनफल के बराबर होता है।"
        )
    }

    // Helper functions
    private fun formatNum(d: Double): String {
        return if (d == d.toLong().toDouble()) d.toLong().toString() else "%.2f".format(d)
    }

    private fun gcdLong(a: Long, b: Long): Long {
        var x = abs(a)
        var y = abs(b)
        while (y != 0L) {
            val t = y
            y = x % y
            x = t
        }
        return x
    }

    private fun invalidResult(msg: String): DetailedSolverResult {
        return DetailedSolverResult(
            finalAnswer = "Invalid Input",
            methodTitle = "Error",
            topicHindi = "अमान्य इनपुट",
            inputSummary = msg,
            formula = "-",
            substitution = "-",
            calculationSteps = listOf("Error: $msg"),
            examShortcut = "-",
            mentalNoteHindi = "कृपया सही संख्यात्मक मान दर्ज करें।"
        )
    }
}
