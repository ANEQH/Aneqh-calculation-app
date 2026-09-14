with open("app/src/main/java/com/example/ui/screens/CalculatorScreen.kt", "r") as f:
    content = f.read()

import_lines = "import com.example.ui.viewmodel.CalculationViewModel"
new_import = """import com.example.ui.viewmodel.CalculationViewModel
import java.util.Stack"""

content = content.replace(import_lines, new_import)

eval_code = """
fun evaluate(expression: String): Double {
    val expr = expression.replace("×", "*").replace("÷", "/").replace(" ", "")
    var it = 0
    fun parse(): Double {
        var a = parseFactor()
        while (true) {
            if (it < expr.length && expr[it] == '+') { it++; a += parseFactor() }
            else if (it < expr.length && expr[it] == '-') { it++; a -= parseFactor() }
            else return a
        }
    }

    fun parseFactor(): Double {
        var a = parseTerm()
        while (true) {
            if (it < expr.length && expr[it] == '*') { it++; a *= parseTerm() }
            else if (it < expr.length && expr[it] == '/') { it++; a /= parseTerm() }
            else return a
        }
    }

    fun parseTerm(): Double {
        if (it < expr.length && expr[it] == '-') {
            it++
            return -parseTerm()
        }
        if (it < expr.length && expr[it] == '(') {
            it++
            val a = parse()
            if (it < expr.length && expr[it] == ')') it++
            return a
        }
        var start = it
        while (it < expr.length && (expr[it].isDigit() || expr[it] == '.')) it++
        if (start == it) throw RuntimeException("Invalid")
        return expr.substring(start, it).toDouble()
    }
    return parse()
}
"""

content = content.replace("fun onBtnClick(btn: String) {", eval_code + "\n    fun onBtnClick(btn: String) {")

old_click = """        when (btn) {
            "C" -> displayText = "0"
            "DEL" -> if (displayText.length > 1) displayText = displayText.dropLast(1) else displayText = "0"
            "=" -> displayText = "Done" // simplified
            else -> {
                if (displayText == "0" || displayText == "Done") displayText = btn
                else displayText += btn
            }
        }"""

new_click = """        when (btn) {
            "C" -> displayText = "0"
            "DEL" -> if (displayText.length > 1) displayText = displayText.dropLast(1) else displayText = "0"
            "=" -> {
                try {
                    val res = evaluate(displayText)
                    displayText = if (res == res.toLong().toDouble()) res.toLong().toString() else res.toString()
                } catch (e: Exception) {
                    displayText = "Error"
                }
            }
            else -> {
                if (displayText == "0" || displayText == "Done" || displayText == "Error") displayText = btn
                else displayText += btn
            }
        }"""

content = content.replace(old_click, new_click)

with open("app/src/main/java/com/example/ui/screens/CalculatorScreen.kt", "w") as f:
    f.write(content)
