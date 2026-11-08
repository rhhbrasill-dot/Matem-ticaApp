package com.exemplo.matematicaapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exemplo.matematicaapp.data.Exercise
import com.exemplo.matematicaapp.data.ExerciseGenerator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseScreen(
    categoryId: String,
    onBackClick: () -> Unit
) {
    var exercise by remember { mutableStateOf(getExercise(categoryId)) }
    var userAnswer by remember { mutableStateOf("") }
    var score by remember { mutableIntStateOf(0) }
    var attempts by remember { mutableIntStateOf(0) }
    var showFeedback by remember { mutableStateOf(false) }
    var isCorrect by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Praticar") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Pontos: $score",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(
                        text = exercise.question,
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    OutlinedTextField(
                        value = userAnswer,
                        onValueChange = { userAnswer = it },
                        label = { Text("Sua resposta") },
                        singleLine = true,
                        keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Button(
                onClick = {
                    attempts++
                    val normalizedUser = userAnswer.trim().replace(",", ".").replace(" ", "")
                    val normalizedCorrect = exercise.correctAnswer.split("=").lastOrNull()?.trim() ?: ""

                    isCorrect = normalizedUser == normalizedCorrect

                    if (isCorrect) score += 10
                    showFeedback = true
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Verificar")
            }

            if (showFeedback) {
                val backgroundColor = if (isCorrect) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.errorContainer
                val contentColor = if (isCorrect) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onErrorContainer

                Card(
                    colors = CardDefaults.cardColors(containerColor = backgroundColor),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = if (isCorrect) "Correto! 🎉" else "Errado. Tente de novo ou veja a explicação.",
                            color = contentColor,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                        )

                        if (!isCorrect) {
                            Text(
                                text = "Resposta certa: ${exercise.correctAnswer}",
                                color = contentColor,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }

                        Text(
                            text = exercise.explanation,
                            color = contentColor,
                            modifier = Modifier.padding(top = 8.dp),
                            fontSize = 14.sp
                        )

                        Button(
                            onClick = {
                                exercise = getExercise(categoryId)
                                userAnswer = ""
                                showFeedback = false
                            },
                            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
                        ) {
                            Text("Próximo Exercício")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun getExercise(categoryId: String): Exercise {
    return when (categoryId) {
        "arithmetic" -> ExerciseGenerator.generateArithmetic()
        "fractions" -> ExerciseGenerator.generateFractions()
        "equations" -> ExerciseGenerator.generateLinearEquation()
        "geometry" -> ExerciseGenerator.generateGeometry()
        else -> ExerciseGenerator.generateArithmetic()
    }
}
