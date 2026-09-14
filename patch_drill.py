with open("app/src/main/java/com/example/ui/screens/TableDrillScreen.kt", "r") as f:
    content = f.read()

import_str = "import kotlin.random.Random"
new_import = "import kotlin.random.Random\nimport androidx.compose.foundation.lazy.LazyRow\nimport androidx.compose.foundation.lazy.items"

content = content.replace(import_str, new_import)

state_vars = """    val questions = remember { mutableStateListOf<DrillQuestion>() }"""

new_state = """    var difficulty by remember { mutableStateOf("Easy") }
    val questions = remember { mutableStateListOf<DrillQuestion>() }
    
    LaunchedEffect(difficulty) {
        questions.clear()
        for (i in 1..50) {
            questions.add(generateDrillQuestion(i, difficulty))
        }
    }"""

content = content.replace(state_vars, new_state)

remove_block = """    // Generate initial 50
    LaunchedEffect(Unit) {
        if (questions.isEmpty()) {
            for (i in 1..50) {
                questions.add(generateDrillQuestion(i))
            }
        }
    }"""

content = content.replace(remove_block, "")

padding_block = """        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 16.dp),"""

new_padding = """        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("Easy", "Hard").forEach { diff ->
                    FilterChip(
                        selected = difficulty == diff,
                        onClick = { difficulty = diff },
                        label = { Text(diff + " Mode", fontWeight = FontWeight.Bold) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = if(diff == "Easy") AccentEmerald else AccentRose,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),"""

content = content.replace(padding_block, new_padding)
content = content.replace("            }\n        }\n    }\n}", "            }\n        }\n    }\n    }\n}") # adjust braces

gen_func = """fun generateDrillQuestion(id: Int): DrillQuestion {
    val n1 = Random.nextInt(2, 31) // 2 to 30
    val n2 = Random.nextInt(2, 11) // 2 to 10"""

new_gen = """fun generateDrillQuestion(id: Int, difficulty: String = "Easy"): DrillQuestion {
    val n1 = if (difficulty == "Easy") Random.nextInt(2, 16) else Random.nextInt(16, 31)
    val n2 = if (difficulty == "Easy") Random.nextInt(2, 11) else Random.nextInt(11, 21)"""

content = content.replace(gen_func, new_gen)

call1 = "generateDrillQuestion(currentSize + i)"
new_call1 = "generateDrillQuestion(currentSize + i, difficulty)"
content = content.replace(call1, new_call1)

with open("app/src/main/java/com/example/ui/screens/TableDrillScreen.kt", "w") as f:
    f.write(content)
