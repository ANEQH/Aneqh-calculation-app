import re

with open("app/src/main/java/com/example/ui/screens/HomeScreen.kt", "r") as f:
    content = f.read()

content = content.replace(
    "import androidx.compose.material.icons.filled.ViewInAr", 
    "import androidx.compose.material.icons.filled.ViewInAr\nimport androidx.compose.material.icons.filled.CheckCircle\nimport androidx.compose.material.icons.filled.LocalFireDepartment\nimport androidx.compose.material.icons.filled.GpsFixed"
)

with open("app/src/main/java/com/example/ui/screens/HomeScreen.kt", "w") as f:
    f.write(content)
