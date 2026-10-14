package com.example.util

import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Robust, production-grade calculation engine for SSC CGL Quantitative Aptitude.
 * Supports BODMAS, parentheses, decimals, negative values, powers, roots, and clear percentage operations.
 */
object SscCalculatorEngine {

    sealed class CalcResult {
        data class Success(val value: Double, val formatted: String, val details: String? = null) : CalcResult()
        data class Error(val message: String) : CalcResult()
    }

    /**
     * Evaluates standard arithmetic expressions with strict BODMAS parsing.
     * Supports +, -, *, /, %, ^, sqrt, and nested parentheses ( ).
     */
    fun evaluate(expression: String): CalcResult {
        if (expression.isBlank()) return CalcResult.Error("Empty expression")
        return try {
            val sanitized = sanitize(expression)
            val parser = BodmasParser(sanitized)
            val result = parser.parse()
            if (result.isNaN() || result.isInfinite()) {
                CalcResult.Error("Cannot divide by zero")
            } else {
                CalcResult.Success(result, formatResult(result))
            }
        } catch (e: ArithmeticException) {
            CalcResult.Error("Math error: ${e.message}")
        } catch (e: Exception) {
            CalcResult.Error("Invalid expression: ${e.message}")
        }
    }

    private fun sanitize(expr: String): String {
        return expr
            .replace("×", "*")
            .replace("÷", "/")
            .replace("√", "sqrt ")
            .replace(" ", "")
    }

    fun formatResult(value: Double): String {
        if (value.isNaN() || value.isInfinite()) return "Error"
        return if (value == value.toLong().toDouble()) {
            value.toLong().toString()
        } else {
            val bd = BigDecimal.valueOf(value).setScale(6, RoundingMode.HALF_UP).stripTrailingZeros()
            bd.toPlainString()
        }
    }

    // --- High-Precision Dedicated Percentage Modes for SSC CGL ---

    /**
     * Mode 1: x% of y  -> (x / 100) * y
     * e.g., 20% of 100 = 20
     */
    fun percentOf(x: Double, y: Double): Double = (x / 100.0) * y

    /**
     * Mode 2: x is what percentage of y? -> (x / y) * 100
     * e.g., 20 is what % of 100? = 20%
     */
    fun isWhatPercentOf(x: Double, y: Double): Double {
        if (y == 0.0) throw ArithmeticException("Division by zero in percentage calculation")
        return (x / y) * 100.0
    }

    /**
     * Mode 3: y increased by x% -> y * (1 + x/100)
     * e.g., 100 increased by 20% = 120
     */
    fun increaseByPercent(y: Double, x: Double): Double = y * (1.0 + (x / 100.0))

    /**
     * Mode 4: y decreased by x% -> y * (1 - x/100)
     * e.g., 100 decreased by 20% = 80
     */
    fun decreaseByPercent(y: Double, x: Double): Double = y * (1.0 - (x / 100.0))

    /**
     * Mode 5: Successive percentage changes (a% and b%) -> a + b + (a * b) / 100
     * e.g., +20% and +10% -> 20 + 10 + 2 = 32%
     */
    fun successiveNetPercent(a: Double, b: Double): Double = a + b + ((a * b) / 100.0)

    /**
     * Mode 6: Reverse percentage: If a number after x% increase is Y, what was original X?
     * original = Y / (1 + x/100)
     */
    fun reversePercentIncrease(y: Double, x: Double): Double {
        if (1.0 + (x / 100.0) == 0.0) throw ArithmeticException("Invalid reverse percentage rate")
        return y / (1.0 + (x / 100.0))
    }

    /**
     * Mode 7: Reverse percentage: If a number after x% decrease is Y, what was original X?
     * original = Y / (1 - x/100)
     */
    fun reversePercentDecrease(y: Double, x: Double): Double {
        if (1.0 - (x / 100.0) == 0.0) throw ArithmeticException("Invalid reverse percentage rate")
        return y / (1.0 - (x / 100.0))
    }

    // --- Recursive Descent BODMAS Parser with Unary - and Functions ---
    private class BodmasParser(private val expr: String) {
        private var pos = -1
        private var ch = 0

        private fun nextChar() {
            ch = if (++pos < expr.length) expr[pos].code else -1
        }

        private fun eat(charToEat: Int): Boolean {
            while (ch == ' '.code) nextChar()
            if (ch == charToEat) {
                nextChar()
                return true
            }
            return false
        }

        fun parse(): Double {
            nextChar()
            val x = parseExpression()
            if (pos < expr.length) throw IllegalArgumentException("Unexpected character: " + ch.toChar())
            return x
        }

        // Addition and Subtraction
        private fun parseExpression(): Double {
            var x = parseTerm()
            while (true) {
                if (eat('+'.code)) x += parseTerm()
                else if (eat('-'.code)) x -= parseTerm()
                else return x
            }
        }

        // Multiplication and Division and Modulo
        private fun parseTerm(): Double {
            var x = parseFactor()
            while (true) {
                if (eat('*'.code)) x *= parseFactor()
                else if (eat('/'.code)) {
                    val divisor = parseFactor()
                    if (divisor == 0.0) throw ArithmeticException("Division by zero")
                    x /= divisor
                } else if (eat('%'.code)) {
                    x /= 100.0
                } else return x
            }
        }

        // Unary signs, Exponentiation, Square Roots, Parentheses, Numbers
        private fun parseFactor(): Double {
            if (eat('+'.code)) return parseFactor()
            if (eat('-'.code)) return -parseFactor()

            var x: Double
            val startPos = pos

            if (eat('('.code)) {
                x = parseExpression()
                if (!eat(')'.code)) throw IllegalArgumentException("Missing closing parenthesis")
            } else if ((ch in '0'.code..'9'.code) || ch == '.'.code) {
                while ((ch in '0'.code..'9'.code) || ch == '.'.code) nextChar()
                val token = expr.substring(startPos, pos)
                x = token.toDoubleOrNull() ?: throw IllegalArgumentException("Invalid number: $token")
            } else if (ch in 'a'.code..'z'.code || ch in 'A'.code..'Z'.code) {
                while (ch in 'a'.code..'z'.code || ch in 'A'.code..'Z'.code) nextChar()
                val func = expr.substring(startPos, pos).lowercase()
                if (eat('('.code)) {
                    x = parseExpression()
                    if (!eat(')'.code)) throw IllegalArgumentException("Missing closing parenthesis after $func")
                } else {
                    x = parseFactor()
                }
                x = when (func) {
                    "sqrt" -> {
                        if (x < 0) throw ArithmeticException("Square root of negative number")
                        sqrt(x)
                    }
                    else -> throw IllegalArgumentException("Unknown function: $func")
                }
            } else {
                throw IllegalArgumentException("Unexpected token at position $pos: " + ch.toChar())
            }

            // Exponentiation (Power ^)
            if (eat('^'.code)) {
                val exponent = parseFactor()
                x = x.pow(exponent)
            }

            return x
        }
    }
}
