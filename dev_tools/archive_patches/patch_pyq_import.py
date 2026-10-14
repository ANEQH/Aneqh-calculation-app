import re

with open("app/src/main/java/com/example/ui/screens/PyqTestsScreen.kt", "r") as f:
    content = f.read()

content = content.replace("import androidx.compose.material.icons.filled.DoneAll", "import androidx.compose.material.icons.filled.DoneAll\nimport androidx.compose.material.icons.filled.Done")

with open("app/src/main/java/com/example/ui/screens/PyqTestsScreen.kt", "w") as f:
    f.write(content)
