package com.example.ui.viewmodel

object AppDictionary {
    fun tr(key: String, lang: AppLanguage): String {
        return if (lang == AppLanguage.HINDI) {
            hindiMap[key] ?: key
        } else {
            englishMap[key] ?: key
        }
    }

    private val englishMap = mapOf(
        "app_title" to "Anek Calculation",
        "app_subtitle" to "SSC CGL Math Speed Maker",
        "start_speed_drill" to "Start Speed Drill",
        "speed_drill_desc" to "10 Qs • Mixed Concepts",
        "mental_solvers" to "Mental Solvers",
        "mental_solvers_desc" to "10x PYQ Formula Engine",
        "quick_tools" to "Quick Tools",
        "ci_si_rates" to "📈 CI & SI Rate Sheet",
        "visual_math" to "🧊 3D Visual Math",
        "pyq_tests" to "📝 PYQ Math Tests",
        "pyq_tests_sub" to "Real SSC Qs",
        "calculator" to "📱 Pro Calculator",
        "calculator_sub" to "Smart Tool",
        "inspector_chapters" to "Inspector Chalisa Chapters",
        "solved" to "Solved",
        "streak" to "Streak",
        "accuracy" to "Accuracy",
        "chapters" to "Chapters",
        "speed_math_tables" to "Speed Math Tables",
        "formulas_perc_pow" to "Formulas, Percentages & Powers",
        "10x_solvers" to "10x Mental Solvers",
        "step_by_step" to "Step-by-step Inspector method",
        "my_progress" to "My Progress",
        "stats_analytics" to "Stats & Analytics",
        "3d_visualizer" to "3D Algebra Visualizer",
        "pyq_test_title" to "SSC PYQ Real Tests",
        "pyq_test_subtitle" to "Previous Year Questions with Notebook Solutions",
        "pro_calculator" to "Pro Math Calculator",
        "pro_calculator_sub" to "Perform instant calculations"
    )

    private val hindiMap = mapOf(
        "app_title" to "अनेक कैलकुलेशन",
        "app_subtitle" to "SSC CGL मैथ्स स्पीड मेकर",
        "start_speed_drill" to "स्पीड ड्रिल शुरू करें",
        "speed_drill_desc" to "10 प्रश्न • मिश्रित अवधारणाएं",
        "mental_solvers" to "मेंटल सॉल्वर्स",
        "mental_solvers_desc" to "10x PYQ फॉर्मूला इंजन",
        "quick_tools" to "क्विक टूल्स",
        "ci_si_rates" to "📈 CI & SI रेट शीट",
        "visual_math" to "🧊 3D विज़ुअल गणित",
        "pyq_tests" to "📝 PYQ गणित टेस्ट",
        "pyq_tests_sub" to "असली SSC प्रश्न",
        "calculator" to "📱 प्रो कैलकुलेटर",
        "calculator_sub" to "स्मार्ट टूल",
        "inspector_chapters" to "इंस्पेक्टर चालीसा चैप्टर्स",
        "solved" to "हल किए",
        "streak" to "स्ट्रीक",
        "accuracy" to "सटीकता",
        "chapters" to "चैप्टर्स",
        "speed_math_tables" to "स्पीड मैथ टेबल्स",
        "formulas_perc_pow" to "सूत्र, प्रतिशत और घात",
        "10x_solvers" to "10x मेंटल सॉल्वर्स",
        "step_by_step" to "इंस्पेक्टर की स्टेप-बाय-स्टेप विधि",
        "my_progress" to "मेरी प्रगति",
        "stats_analytics" to "आंकड़े और एनालिटिक्स",
        "3d_visualizer" to "3D बीजगणित विज़ुअलाइज़र",
        "pyq_test_title" to "SSC PYQ असली टेस्ट",
        "pyq_test_subtitle" to "नोटबुक समाधान के साथ पिछले वर्ष के प्रश्न",
        "pro_calculator" to "प्रो गणित कैलकुलेटर",
        "pro_calculator_sub" to "तुरंत गणना करें"
    )
}
