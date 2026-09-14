import re
import os

files = [
    "app/src/main/java/com/example/ui/screens/SpeedQuizScreen.kt",
    "app/src/main/java/com/example/ui/screens/PracticeQuestionScreen.kt",
]

for file in files:
    with open(file, "r") as f:
        content = f.read()
    
    # We want to replace AppTopBar calls that might not have onThemeToggle
    # But it's easier to just rebuild it all
