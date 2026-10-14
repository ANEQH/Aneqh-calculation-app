import re

with open("app/src/main/java/com/example/ui/screens/HomeScreen.kt", "r") as f:
    content = f.read()

content = content.replace(
    "import androidx.compose.material.icons.filled.*", 
    "import androidx.compose.material.icons.filled.*\nimport androidx.compose.material.icons.filled.AutoAwesome\nimport androidx.compose.material.icons.filled.Calculate\nimport androidx.compose.material.icons.filled.Category\nimport androidx.compose.material.icons.filled.MenuBook\nimport androidx.compose.material.icons.filled.Quiz\nimport androidx.compose.material.icons.filled.TrendingUp\nimport androidx.compose.material.icons.filled.ViewInAr"
)

with open("app/src/main/java/com/example/ui/screens/HomeScreen.kt", "w") as f:
    f.write(content)
