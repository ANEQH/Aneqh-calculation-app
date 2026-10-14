with open("app/src/main/java/com/example/data/repository/CalculationSolvers.kt", "r") as f:
    content = f.read()

# The file currently has a trailing '}' from the original file, followed by my new functions, followed by another '}'
# We need to remove the first '}' that closed the object, and keep the last one.
# Let's find the string "// --- SSC CGL PYQ Dynamic Concept Solvers (Percentage) ---"
import re

new_functions_start = content.find("// --- SSC CGL PYQ Dynamic Concept Solvers (Percentage) ---")
if new_functions_start != -1:
    # Before this index, find the last '}'
    before = content[:new_functions_start]
    last_brace_idx = before.rfind('}')
    
    if last_brace_idx != -1:
        # Remove that brace
        before = before[:last_brace_idx] + before[last_brace_idx+1:]
        
        with open("app/src/main/java/com/example/data/repository/CalculationSolvers.kt", "w") as f:
            f.write(before + content[new_functions_start:])
