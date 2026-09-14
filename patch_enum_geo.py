with open("app/src/main/java/com/example/ui/viewmodel/CalculationViewModel.kt", "r") as f:
    content = f.read()

old_enum = """    VEDIC_TRICKS,
    FORMULA_CARDS
}"""

new_enum = """    VEDIC_TRICKS,
    FORMULA_CARDS,
    GEOMETRY
}"""
content = content.replace(old_enum, new_enum)

with open("app/src/main/java/com/example/ui/viewmodel/CalculationViewModel.kt", "w") as f:
    f.write(content)
