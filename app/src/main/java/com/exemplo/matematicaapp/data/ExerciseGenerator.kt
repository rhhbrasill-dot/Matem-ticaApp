package com.exemplo.matematicaapp.data

import kotlin.random.Random

data class Exercise(
    val question: String,
    val correctAnswer: String,
    val explanation: String
)

object ExerciseGenerator {

    fun generateArithmetic(): Exercise {
        val a = Random.nextInt(1, 20)
        val b = Random.nextInt(1, 20)
        val ops = listOf("+", "-", "×", "÷")
        val op = ops.random()

        return when (op) {
            "+" -> Exercise("$a + $b = ?", "$a + $b = ${a + b}", "Soma de $a e $b.")
            "-" -> Exercise("$a - $b = ?", "$a - $b = ${a - b}", "Subtração de $b de $a.")
            "×" -> Exercise("$a × $b = ?", "$a × $b = ${a * b}", "Multiplicação de $a por $b.")
            else -> {
                val divisor = Random.nextInt(1, 10)
                val quotient = Random.nextInt(1, 10)
                val dividend = divisor * quotient
                Exercise("$dividend ÷ $divisor = ?", "$dividend ÷ $divisor = $quotient", "Divisão exata.")
            }
        }
    }

    fun generateFractions(): Exercise {
        val denom = Random.nextInt(2, 10)
        val num1 = Random.nextInt(1, denom)
        val num2 = Random.nextInt(1, denom)

        val totalNum = num1 + num2
        val simplifiedNum = totalNum / gcd(totalNum, denom)
        val simplifiedDenom = denom / gcd(totalNum, denom)

        val answerStr = if (simplifiedDenom == 1) "$simplifiedNum" else "$simplifiedNum/$simplifiedDenom"

        return Exercise(
            "$num1/$denom + $num2/$denom = ?",
            answerStr,
            "Some os numeradores: ${num1} + ${num2} = $totalNum. O denominador permanece $denom. Simplificando: $answerStr."
        )
    }

    fun generateLinearEquation(): Exercise {
        val x = Random.nextInt(-10, 10)
        val a = Random.nextInt(1, 10)
        val b = Random.nextInt(-10, 10)
        val result = a * x + b

        return Exercise(
            "$ax + $b = $result (onde x é a incógnita)",
            "x = $x",
            "Subtraia $b de ambos os lados: $ax = ${result - b}. Divida por $a: x = ${result - b}/$a = $x."
        )
    }

    fun generateGeometry(): Exercise {
        val type = Random.nextInt(3)
        return when (type) {
            0 -> {
                val l = Random.nextInt(1, 10)
                Exercise("Área de um quadrado com lado $l?", "${l * l}", "Área = lado² = $l * $l.")
            }
            1 -> {
                val b = Random.nextInt(1, 10)
                val h = Random.nextInt(1, 10)
                Exercise("Área de um triângulo com base $b e altura $h?", "${(b * h) / 2}", "Área = (base * altura) / 2 = ($b * $h) / 2.")
            }
            else -> {
                val r = Random.nextInt(1, 10)
                val area = Math.round(Math.PI * r * r * 100.0) / 100.0
                Exercise("Área de um círculo com raio $r? (use π ≈ 3.14)", "$area", "Área = π * r² ≈ 3.14 * $r * $r.")
            }
        }
    }

    private fun gcd(a: Int, b: Int): Int {
        return if (b == 0) a else gcd(b, a % b)
    }
}
