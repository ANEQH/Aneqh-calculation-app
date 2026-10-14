with open("app/src/main/java/com/example/ui/viewmodel/AppDictionary.kt", "r") as f:
    content = f.read()

new_eng = """        "pro_calculator_sub" to "Perform instant calculations",
        "pyq_zone_title" to "SSC CGL PYQ Zone",
        "pyq_mock_test" to "PYQ Mock Test",
        "pyq_algebra" to "Algebra PYQ (22-23)",
        "pyq_geometry" to "Geometry PYQ (22-23)",
        "pyq_arithmetic" to "Arithmetic PYQ",
        "view_concept" to "💡 View Concept & Trick"
    )"""

new_hin = """        "pro_calculator_sub" to "तुरंत गणना करें",
        "pyq_zone_title" to "SSC CGL PYQ ज़ोन",
        "pyq_mock_test" to "PYQ मॉक टेस्ट",
        "pyq_algebra" to "बीजगणित PYQ (22-23)",
        "pyq_geometry" to "ज्यामिति PYQ (22-23)",
        "pyq_arithmetic" to "अंकगणित PYQ",
        "view_concept" to "💡 कांसेप्ट और ट्रिक देखें"
    )"""

content = content.replace("""        "pro_calculator_sub" to "Perform instant calculations"
    )""", new_eng)

content = content.replace("""        "pro_calculator_sub" to "तुरंत गणना करें"
    )""", new_hin)

with open("app/src/main/java/com/example/ui/viewmodel/AppDictionary.kt", "w") as f:
    f.write(content)
