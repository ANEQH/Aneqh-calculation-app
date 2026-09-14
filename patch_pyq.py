import re

with open("app/src/main/java/com/example/ui/screens/PyqTestsScreen.kt", "r") as f:
    content = f.read()

content = content.replace("contentPadding = PaddingValues(vertical = 16.dp, bottom = 80.dp)", "contentPadding = PaddingValues(top = 16.dp, bottom = 80.dp)")

with open("app/src/main/java/com/example/ui/screens/PyqTestsScreen.kt", "w") as f:
    f.write(content)
