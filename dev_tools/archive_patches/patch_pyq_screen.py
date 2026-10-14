import re

with open("app/src/main/java/com/example/ui/screens/PyqTestsScreen.kt", "r") as f:
    content = f.read()

# Replace the pyqList definition with an endless generator

new_pyq_code = """import kotlin.random.Random

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
                        "Formula: k³ - 3k = $k³ - 3($k) = $ans.\nTrick: If plus is given, subtract 3 times.",
                        "सूत्र: k³ - 3k = $k³ - 3($k) = $ans.\nट्रिक: अगर प्लस दिया है, तो 3 गुना घटाएं।"
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
                        "Formula: k² + 2 = $k² + 2 = $ans.\nTrick: If minus is given, add 2.",
                        "सूत्र: k² + 2 = $k² + 2 = $ans.\nट्रिक: अगर माइनस दिया है, तो 2 जोड़ें।"
                    ))
                }
            }
            PyqChapter.ARITHMETIC -> {
                val cp = Random.nextInt(10, 50) * 100 // 1000 to 5000
                val markup = Random.nextInt(2, 6) * 10 // 20 to 50
                val discount = Random.nextInt(1, 3) * 10 // 10 to 20
                val net = markup - discount - (markup * discount) / 100
                
                val options = mutableSetOf(net, net + 2, net - 2, net + 4)
                while(options.size < 4) options.add(net + Random.nextInt(1, 5))
                val optList = options.toList().shuffled()
                
                list.add(PyqQuestion(id, type, exam,
                    "A dealer marks his goods $markup% above cost price and allows a discount of $discount%. Find his gain percent.",
                    "एक डीलर अपने माल पर लागत मूल्य से $markup% अधिक अंकित करता है और $discount% की छूट देता है। उसका लाभ प्रतिशत ज्ञात कीजिए।",
                    optList.map { "$it%" }, "$net%",
                    "Successive change: a + b + (ab/100)\n= $markup - $discount - ($markup*$discount)/100 = $net%.",
                    "क्रमिक परिवर्तन: a + b + (ab/100)\n= +$markup - $discount - ($markup*$discount)/100 = $net%."
                ))
            }
            PyqChapter.GEOMETRY -> {
                val d1 = Random.nextInt(3, 8) * 2 // 6 to 14 even
                val d2 = Random.nextInt(4, 10) * 2 // 8 to 18 even
                // make sure they form a pythagorean triplet if possible, or just calculate hypotenuse
                // to keep it simple, let's just generate a question about sum of angles of a polygon
                val sides = Random.nextInt(5, 12)
                val ans = (sides - 2) * 180
                val options = mutableSetOf(ans, ans + 180, ans - 180, ans + 360)
                while(options.size < 4) options.add(ans + Random.nextInt(1, 5)*180)
                val optList = options.toList().shuffled()
                
                list.add(PyqQuestion(id, type, exam,
                    "What is the sum of the interior angles of a polygon with $sides sides?",
                    "$sides भुजाओं वाले बहुभुज के आंतरिक कोणों का योग क्या है?",
                    optList.map { "$it°" }, "$ans°",
                    "Formula: (n-2) × 180°\n= ($sides - 2) × 180° = $ans°.",
                    "सूत्र: (n-2) × 180°\n= ($sides - 2) × 180° = $ans°."
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
                    "tan($angle°) = Height / Base\nHeight = $dist × tan($angle°) = $ans.\nTrick: $trickEn.",
                    "tan($angle°) = ऊंचाई / आधार\nऊंचाई = $dist × tan($angle°) = $ans.\nट्रिक: $trickEn."
                ))
            }
            else -> {}
        }
    }
    return list
}
"""

old_pyq_list = """val pyqList = listOf(
    PyqQuestion("1", PyqChapter.ALGEBRA, "SSC CGL 2023 Tier-1 (Shift 2)", "If x + 1/x = 5, what is the value of x³ + 1/x³?", "यदि x + 1/x = 5 है, तो x³ + 1/x³ का मान क्या है?", listOf("110", "125", "115", "140"), "110", "Formula: k³ - 3k = 125 - 15 = 110.\nTrick: If plus is given, subtract 3 times.", "सूत्र: k³ - 3k = 125 - 15 = 110.\nट्रिक: अगर प्लस दिया है, तो 3 गुना घटाएं।"),
    PyqQuestion("2", PyqChapter.ARITHMETIC, "SSC CGL 2022 Tier-2 (Mains)", "A dealer marks his goods 20% above cost price and allows a discount of 10%. Find his gain percent.", "एक डीलर अपने माल पर लागत मूल्य से 20% अधिक अंकित करता है और 10% की छूट देता है। उसका लाभ प्रतिशत ज्ञात कीजिए।", listOf("10%", "8%", "12%", "6%"), "8%", "Successive change:\nNet = a + b + (ab/100)\n= 20 - 10 - (20*10)/100\n= 10 - 2\n= 8%.", "क्रमिक परिवर्तन:\nनेट = a + b + (ab/100)\n= +20 - 10 - (20*10)/100\n= 10 - 2\n= 8%."),
    PyqQuestion("3", PyqChapter.ARITHMETIC, "SSC CPO 2023 Shift-1", "The LCM of two numbers is 864 and their HCF is 144. If one number is 288, the other number is?", "दो संख्याओं का LCM 864 है और उनका HCF 144 है। यदि एक संख्या 288 है, तो दूसरी संख्या क्या है?", listOf("432", "576", "144", "384"), "432", "Property: Product of numbers = LCM * HCF.\n\nx * 288 = 864 * 144\nx = (864 * 144) / 288\nx = 432.", "नियम: संख्याओं का गुणनफल = LCM * HCF.\n\nx * 288 = 864 * 144\nx = (864 * 144) / 288\nx = 432."),
    PyqQuestion("4", PyqChapter.ARITHMETIC, "SSC CGL 2021 Tier-1", "A man can row 15 km/hr in still water. It takes him twice as long to row up as to row down the river. Find the rate of stream.", "एक आदमी शांत जल में 15 किमी/घंटा की गति से नाव चला सकता है। उसे नदी में धारा के प्रतिकूल जाने में धारा के अनुकूल जाने से दोगुना समय लगता है। धारा की गति ज्ञात कीजिए।", listOf("3 km/hr", "4 km/hr", "5 km/hr", "6 km/hr"), "5 km/hr", "Let speed of stream = y.\nUpstream time = 2 * Downstream time\nD / (15 - y) = 2 * (D / (15 + y))\n15 + y = 30 - 2y\n3y = 15\ny = 5 km/hr.", "माना धारा की गति = y.\nप्रतिकूल समय = 2 * अनुकूल समय\nD / (15 - y) = 2 * (D / (15 + y))\n15 + y = 30 - 2y\n3y = 15\ny = 5 किमी/घंटा।"),
    PyqQuestion("5", PyqChapter.TRIGONOMETRY, "SSC CHSL 2023 Tier-1", "If the angle of elevation of a tower from a distance of 100m from its foot is 60°, then the height of the tower is:", "यदि किसी टावर के आधार से 100 मीटर की दूरी से उसका उन्नयन कोण 60° है, तो टावर की ऊंचाई क्या है?", listOf("100√3 m", "100/√3 m", "50√3 m", "200 m"), "100√3 m", "tan(60°) = Height / Base\n√3 = h / 100\nh = 100√3 m.\n\nTrick: For 30-60-90 triangle, side opposite 60° is √3 times the base.", "tan(60°) = ऊंचाई / आधार\n√3 = h / 100\nh = 100√3 मीटर।\n\nट्रिक: 30-60-90 त्रिकोण के लिए, 60° के सामने वाली भुजा आधार की √3 गुना होती है।"),
    PyqQuestion("6", PyqChapter.GEOMETRY, "SSC CGL 2022 Tier-1", "If the lengths of the diagonals of a rhombus are 24 cm and 10 cm, what is the perimeter of the rhombus?", "यदि एक समचतुर्भुज के विकर्णों की लंबाई 24 सेमी और 10 सेमी है, तो समचतुर्भुज का परिमाप क्या है?", listOf("52 cm", "60 cm", "68 cm", "48 cm"), "52 cm", "Diagonals of a rhombus bisect at 90°.\nSo, half diagonals are 12 and 5.\nBy Pythagoras, side = √(12² + 5²) = 13 cm.\nPerimeter = 4 * 13 = 52 cm.", "समचतुर्भुज के विकर्ण 90° पर समद्विभाजित करते हैं।\nआधे विकर्ण 12 और 5 हैं।\nपाइथागोरस से, भुजा = √(12² + 5²) = 13 सेमी।\nपरिमाप = 4 * 13 = 52 सेमी।")
)"""

content = content.replace(old_pyq_list, new_pyq_code)

old_state = """    var selectedTab by remember { mutableStateOf(PyqChapter.ALL) }
    var answerSheetMode by remember { mutableStateOf(false) }

    val filteredList = if (selectedTab == PyqChapter.ALL) pyqList else pyqList.filter { it.chapter == selectedTab }"""

new_state = """    var selectedTab by remember { mutableStateOf(PyqChapter.ALL) }
    var answerSheetMode by remember { mutableStateOf(false) }
    
    // Hold endless pyq list
    val pyqList = remember { mutableStateListOf<PyqQuestion>() }
    
    // Load initial batch
    LaunchedEffect(selectedTab) {
        pyqList.clear()
        pyqList.addAll(generatePyqQuestions(selectedTab, 50, 1))
    }"""

content = content.replace(old_state, new_state)

# Replace items(filteredList.size) { index -> ... filteredList[index] ... }
# with items(pyqList.size) { index -> ... pyqList[index] ... add more on scroll ... }

old_list = """                items(filteredList.size) { index ->
                    PyqQuizCard(
                        question = filteredList[index],
                        index = index + 1,
                        lang = lang,
                        answerSheetMode = answerSheetMode
                    )
                }"""

new_list = """                items(pyqList.size) { index ->
                    PyqQuizCard(
                        question = pyqList[index],
                        index = index + 1,
                        lang = lang,
                        answerSheetMode = answerSheetMode
                    )
                    
                    // Endless generation when reaching near bottom
                    if (index == pyqList.size - 5) {
                        LaunchedEffect(index) {
                            val more = generatePyqQuestions(selectedTab, 50, pyqList.size + 1)
                            pyqList.addAll(more)
                        }
                    }
                }"""

content = content.replace(old_list, new_list)

with open("app/src/main/java/com/example/ui/screens/PyqTestsScreen.kt", "w") as f:
    f.write(content)
