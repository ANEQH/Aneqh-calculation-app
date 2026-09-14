import re
with open("app/src/main/java/com/example/ui/screens/PyqTestsScreen.kt", "r") as f:
    lines = f.readlines()

new_lines = []
has_random_import = False
for line in lines:
    if line.strip() == "import kotlin.random.Random":
        if not has_random_import:
            # We will insert it at the top later
            has_random_import = True
    else:
        new_lines.append(line)

# insert at top
for i, line in enumerate(new_lines):
    if line.startswith("import "):
        new_lines.insert(i, "import kotlin.random.Random\n")
        break

with open("app/src/main/java/com/example/ui/screens/PyqTestsScreen.kt", "w") as f:
    f.writelines(new_lines)

