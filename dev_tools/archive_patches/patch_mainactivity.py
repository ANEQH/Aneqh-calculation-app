import re

with open("app/src/main/java/com/example/MainActivity.kt", "r") as f:
    content = f.read()

old_content = """        setContent {
            MyApplicationTheme {
                val viewModel: CalculationViewModel = viewModel()
                Surface(modifier = Modifier.fillMaxSize()) {
                    MainApp(viewModel = viewModel)
                }
            }
        }"""

new_content = """        setContent {
            val viewModel: CalculationViewModel = viewModel()
            val uiState by viewModel.uiState.androidx.compose.runtime.collectAsState()
            
            MyApplicationTheme(darkTheme = uiState.isDarkMode) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MainApp(viewModel = viewModel)
                }
            }
        }"""
content = content.replace(old_content, new_content)
content = content.replace("import com.example.ui.theme.MyApplicationTheme", "import com.example.ui.theme.MyApplicationTheme\nimport androidx.compose.runtime.collectAsState\nimport androidx.compose.runtime.getValue")

with open("app/src/main/java/com/example/MainActivity.kt", "w") as f:
    f.write(content)
