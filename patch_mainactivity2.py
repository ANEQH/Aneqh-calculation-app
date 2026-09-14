import re

with open("app/src/main/java/com/example/MainActivity.kt", "r") as f:
    content = f.read()

content = content.replace("val uiState by viewModel.uiState.androidx.compose.runtime.collectAsState()", "val uiState by viewModel.uiState.collectAsState()")

with open("app/src/main/java/com/example/MainActivity.kt", "w") as f:
    f.write(content)
