import re

with open("app/src/main/java/com/example/ui/screens/PyqTestsScreen.kt", "r") as f:
    content = f.read()

# Replace the static pyqList block completely
pattern = re.compile(r"val pyqList = listOf\([\s\S]*?\n\)", re.MULTILINE)

generator_code = """import kotlin.random.Random

// --- Added for PYQ Generator ---
fun generatePyqQuestions(chapter: PyqChapter, count: Int, startId: Int): List<PyqQuestion> {
    val list = mutableListOf<PyqQuestion>()
    val exams = listOf("SSC CGL 2023 Tier-1", "SSC CHSL 2022", "SSC CPO 2023", "SSC CGL 2022 Tier-2", "SSC MTS 2023")
    
    for (i in 0 until count) {
        val type = if (chapter == PyqChapter.ALL) PyqChapter.values().filter { it != PyqChapter.ALL }.random() else chapter
        val exam = exams.random()
        val id = (startId + i).toString()
        
        when (type) {
            PyqChapter.ALGEBRA -> {
                val k = Random.nextInt(3, 10)
                val isPlus = Random.nextBoolean()
                if (isPlus) {
                    val ans = k * k * k - 3 * k
                    val options = mutableSetOf(ans, ans + 6, ans - 6, ans + 2)
                    while(options.size < 4) options.add(ans + Random.nextInt(1, 10))
                    val optList = options.toList().shuffled()
                    list.add(PyqQuestion(id, type, exam,
                        "If x + 1/x = $k, what is the value of x³ + 1/x³?",
                        "यदि x + 1/x = $k है, तो x³ + 1/x³ का मान क्या है?",
                        optList.map { it.toString() }, ans.toString(),
                        "Formula: k³ - 3k = $k³ - 3($k) = $ans.\\nTrick: If plus is given, subtract 3 times.",
                        "सूत्र: k³ - 3k = $k³ - 3($k) = $ans.\\nट्रिक: अगर प्लस दिया है, तो 3 गुना घटाएं।"
                    ))
                } else {
                    val ans = k * k + 2
                    val options = mutableSetOf(ans, ans - 4, ans + 4, k*k - 2)
                    while(options.size < 4) options.add(ans + Random.nextInt(1, 10))
                    val optList = options.toList().shuffled()
                    list.add(PyqQuestion(id, type, exam,
                        "If x - 1/x = $k, what is the value of x² + 1/x²?",
                        "यदि x - 1/x = $k है, तो x² + 1/x² का मान क्या है?",
                        optList.map { it.toString() }, ans.toString(),
                        "Formula: k² + 2 = $k² + 2 = $ans.\\nTrick: If minus is given, add 2.",
                        "सूत्र: k² + 2 = $k² + 2 = $ans.\\nट्रिक: अगर माइनस दिया है, तो 2 जोड़ें।"
                    ))
                }
            }
            PyqChapter.ARITHMETIC -> {
                val cp = Random.nextInt(10, 50) * 100
                val markup = Random.nextInt(2, 6) * 10
                val discount = Random.nextInt(1, 3) * 10
                val net = markup - discount - (markup * discount) / 100
                
                val options = mutableSetOf(net, net + 2, net - 2, net + 4)
                while(options.size < 4) options.add(net + Random.nextInt(1, 5))
                val optList = options.toList().shuffled()
                
                list.add(PyqQuestion(id, type, exam,
                    "A dealer marks his goods $markup% above cost price and allows a discount of $discount%. Find his gain percent.",
                    "एक डीलर अपने माल पर लागत मूल्य से $markup% अधिक अंकित करता है और $discount% की छूट देता है। उसका लाभ प्रतिशत ज्ञात कीजिए।",
                    optList.map { "$it%" }, "$net%",
                    "Successive change: a + b + (ab/100)\\n= $markup - $discount - ($markup*$discount)/100 = $net%.",
                    "क्रमिक परिवर्तन: a + b + (ab/100)\\n= +$markup - $discount - ($markup*$discount)/100 = $net%."
                ))
            }
            PyqChapter.GEOMETRY -> {
                val sides = Random.nextInt(5, 12)
                val ans = (sides - 2) * 180
                val options = mutableSetOf(ans, ans + 180, ans - 180, ans + 360)
                while(options.size < 4) options.add(ans + Random.nextInt(1, 5)*180)
                val optList = options.toList().shuffled()
                
                list.add(PyqQuestion(id, type, exam,
                    "What is the sum of the interior angles of a polygon with $sides sides?",
                    "$sides भुजाओं वाले बहुभुज के आंतरिक कोणों का योग क्या है?",
                    optList.map { "$it°" }, "$ans°",
                    "Formula: (n-2) × 180°\\n= ($sides - 2) × 180° = $ans°.",
                    "सूत्र: (n-2) × 180°\\n= ($sides - 2) × 180° = $ans°."
                ))
            }
            PyqChapter.TRIGONOMETRY -> {
                val dist = Random.nextInt(2, 10) * 10
                val angles = listOf(30, 45, 60)
                val angle = angles.random()
                val (ans, trickEn) = when (angle) {
                    30 -> Pair("$dist/√3 m", "For 30°, height = base/√3")
                    45 -> Pair("$dist m", "For 45°, height = base")
                    else -> Pair("$dist√3 m", "For 60°, height = base * √3")
                }
                
                val optList = listOf("$dist m", "$dist√3 m", "$dist/√3 m", "${dist*2} m").shuffled()
                
                list.add(PyqQuestion(id, type, exam,
                    "If the angle of elevation of a tower from a distance of ${dist}m from its foot is $angle°, then the height of the tower is:",
                    "यदि किसी टावर के आधार से ${dist}m की दूरी से उसका उन्नयन कोण $angle° है, तो टावर की ऊंचाई क्या है?",
                    optList, ans,
                    "tan($angle°) = Height / Base\\nHeight = $dist × tan($angle°) = $ans.\\nTrick: $trickEn.",
                    "tan($angle°) = ऊंचाई / आधार\\nऊंचाई = $dist × tan($angle°) = $ans.\\nट्रिक: $trickEn."
                ))
            }
            else -> {}
        }
    }
    return list
}
"""

content = pattern.sub(generator_code, content)

with open("app/src/main/java/com/example/ui/screens/PyqTestsScreen.kt", "w") as f:
    f.write(content)
