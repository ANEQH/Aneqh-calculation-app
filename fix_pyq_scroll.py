with open("app/src/main/java/com/example/ui/screens/PyqTestsScreen.kt", "r") as f:
    content = f.read()

# Replace the initial load from 50 to 500, and scroll trigger to 200
content = content.replace("generatePyqQuestions(selectedTab, 50, 1)", "generatePyqQuestions(selectedTab, 500, 1)")
content = content.replace("if (index == pyqList.size - 5)", "if (index == pyqList.size - 10)")
content = content.replace("generatePyqQuestions(selectedTab, 50, pyqList.size + 1)", "generatePyqQuestions(selectedTab, 200, pyqList.size + 1)")

with open("app/src/main/java/com/example/ui/screens/PyqTestsScreen.kt", "w") as f:
    f.write(content)
