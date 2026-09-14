import re

with open("app/src/main/java/com/example/data/repository/CalculationRepository.kt", "r") as f:
    content = f.read()

# 1. Update the chapters list
old_chapters = """    val chapters: List<Chapter> by lazy {
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
    }"""

new_chapters = """    val chapters: List<Chapter> by lazy {
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
            createSscCglEssentialsChapter(),
            createAlgebraPyqChapter(),
            createTrigoPyqChapter(),
            createGeometryPyqChapter(),
            createNumberSystemPyqChapter(),
            createSpeedDistancePyqChapter(),
            createRatioProportionPyqChapter()
        )
    }"""
content = content.replace(old_chapters, new_chapters)

new_chapter_functions = """

    private fun createAlgebraPyqChapter(): Chapter {
        return Chapter(
            id = 15,
            titleEnglish = "Algebra (PYQ Concepts)",
            titleHindi = "बीजगणित (PYQ)",
            description = "Unlimited auto-generating Algebra questions based on SSC CGL Tier 1 & 2 PYQs.",
            iconName = "superscript",
            pageRange = "PYQ DB",
            quickTip = "Use formulas directly. (x+1/x=k)",
            types = emptyList()
        )
    }
    
    private fun createTrigoPyqChapter(): Chapter {
        return Chapter(
            id = 16,
            titleEnglish = "Trigonometry (PYQ)",
            titleHindi = "त्रिकोणमिति (PYQ)",
            description = "Unlimited auto-generating Trigonometry identity and value-putting questions.",
            iconName = "change_history",
            pageRange = "PYQ DB",
            quickTip = "Put θ = 45° or 30° to solve quickly.",
            types = emptyList()
        )
    }

    private fun createGeometryPyqChapter(): Chapter {
        return Chapter(
            id = 17,
            titleEnglish = "Geometry & 2D/3D",
            titleHindi = "ज्यामिति और क्षेत्रमिति",
            description = "Angles, Centers, Circles and Volumes. Pure SSC CGL PYQ logic.",
            iconName = "architecture",
            pageRange = "PYQ DB",
            quickTip = "Remember ratio of Equilateral and Right Angle triangles.",
            types = emptyList()
        )
    }

    private fun createNumberSystemPyqChapter(): Chapter {
        return Chapter(
            id = 18,
            titleEnglish = "Number System (PYQ)",
            titleHindi = "संख्या पद्धति",
            description = "Divisibility, Remainders, Unit Digits & Factors.",
            iconName = "pin",
            pageRange = "PYQ DB",
            quickTip = "For divisibility of 72, check for 8 and 9.",
            types = emptyList()
        )
    }

    private fun createSpeedDistancePyqChapter(): Chapter {
        return Chapter(
            id = 19,
            titleEnglish = "Speed, Time & Distance",
            titleHindi = "चाल, समय और दूरी",
            description = "Trains, Boats, Streams and Average Speed PYQs.",
            iconName = "directions_run",
            pageRange = "PYQ DB",
            quickTip = "Relative speed: Opposite=Add, Same=Subtract.",
            types = emptyList()
        )
    }

    private fun createRatioProportionPyqChapter(): Chapter {
        return Chapter(
            id = 20,
            titleEnglish = "Ratio & Mixture (PYQ)",
            titleHindi = "अनुपात और मिश्रण",
            description = "Income/Exp, Coins, Mixtures & Replacement.",
            iconName = "balance",
            pageRange = "PYQ DB",
            quickTip = "Use Alligation method for two mixed qualities.",
            types = emptyList()
        )
    }
"""
# insert before "fun generateDynamicQuestions"
gen_idx = content.find("    private fun generateDynamicQuestions(")
if gen_idx != -1:
    content = content[:gen_idx] + new_chapter_functions + content[gen_idx:]
else:
    print("Could not find generateDynamicQuestions")

with open("app/src/main/java/com/example/data/repository/CalculationRepository.kt", "w") as f:
    f.write(content)

