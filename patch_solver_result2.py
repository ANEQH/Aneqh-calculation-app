import re

with open("app/src/main/java/com/example/ui/screens/SolversScreen.kt", "r") as f:
    content = f.read()

# Let's try to match it dynamically in case there's slight variations
idx_start = content.find("private fun SolverResultDisplay(result: CalculationSolvers.SolverResult) {")
if idx_start != -1:
    # Find the matching closing brace for this function.
    # Count braces.
    count = 0
    in_function = False
    idx_end = -1
    for i in range(idx_start, len(content)):
        if content[i] == '{':
            count += 1
            in_function = True
        elif content[i] == '}':
            count -= 1
        
        if in_function and count == 0:
            idx_end = i + 1
            break
            
    if idx_end != -1:
        new_display = """private fun SolverResultDisplay(result: CalculationSolvers.SolverResult) {
    val stepsText = result.steps.mapIndexed { index, step -> "${index + 1}. $step" }.joinToString("\\n")
    val fullText = stepsText + if (result.mentalNote.isNotEmpty()) "\\n\\n💡 Note: ${result.mentalNote}" else ""
    
    com.example.ui.components.NotebookPage(
        title = "${result.methodTitle}\\n(Ans: ${result.finalAnswer})",
        text = fullText,
        modifier = Modifier.fillMaxWidth()
    )
}"""
        content = content[:idx_start] + new_display + content[idx_end:]
        with open("app/src/main/java/com/example/ui/screens/SolversScreen.kt", "w") as f:
            f.write(content)
        print("Patched SolverResultDisplay")
    else:
        print("Could not find end of function")
else:
    print("Could not find SolverResultDisplay")
