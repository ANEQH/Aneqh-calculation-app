import re

with open("app/src/main/java/com/example/ui/screens/HomeScreen.kt", "r") as f:
    content = f.read()
    
# Text function parameter issue on line 194
# "text = chapter.id" -> chapter.id is probably not a String, or something else is wrong.
content = content.replace("Text(text = chapter.id,", "Text(text = chapter.id.toString(),")

with open("app/src/main/java/com/example/ui/screens/HomeScreen.kt", "w") as f:
    f.write(content)
