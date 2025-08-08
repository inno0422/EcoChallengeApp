package com.inno0422.myapp.ui.log

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.inno0422.myapp.ui.dashboard.DashboardViewModel
import com.inno0422.myapp.ui.theme.EcoChallengeTheme

@Composable
fun LogScreen(viewModel: DashboardViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    var transportOption by remember { mutableStateOf("") }
    var distance by remember { mutableStateOf("") }
    var mealOption by remember { mutableStateOf("") }
    var powerUsage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text("활동 기록하기", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))

        LogSection(title = "교통수단") {
            val options = mapOf("자가용" to "0.2kg/km", "버스" to "0.05kg/km", "지하철" to "0.03kg/km", "도보" to "0kg/km")
            SelectableChips(options = options, selectedOption = transportOption, onOptionSelected = { transportOption = it })
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = distance,
                onValueChange = { distance = it },
                label = { Text("이동 거리 (km)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        LogSection(title = "식단") {
            val options = mapOf("육류 식단" to "2.5kg CO₂", "채식 식단" to "1.2kg CO₂")
            SelectableChips(options = options, selectedOption = mealOption, onOptionSelected = { mealOption = it })
        }

        Spacer(modifier = Modifier.height(24.dp))

        LogSection(title = "전력 사용") {
            OutlinedTextField(
                value = powerUsage,
                onValueChange = { powerUsage = it },
                label = { Text("전력 사용량 (kWh)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                val distanceFloat = distance.toFloatOrNull() ?: 0f
                val transportCarbon = when (transportOption) {
                    "자가용" -> distanceFloat * 0.2f
                    "버스" -> distanceFloat * 0.05f
                    "지하철" -> distanceFloat * 0.03f
                    else -> 0f
                }
                val mealCarbon = when (mealOption) {
                    "육류 식단" -> 2.5f
                    "채식 식단" -> 1.2f
                    else -> 0f
                }
                val powerCarbon = powerUsage.toFloatOrNull() ?: 0f

                viewModel.addCarbonFootprint(
                    transportCarbon = transportCarbon,
                    mealCarbon = mealCarbon,
                    powerCarbon = powerCarbon
                )

                transportOption = ""
                distance = ""
                mealOption = ""
                powerUsage = ""
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("활동 기록하기", style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Composable
fun LogSection(title: String, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(16.dp))
            content()
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SelectableChips(
    options: Map<String, String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        options.forEach { (option, detail) ->
            FilterChip(
                selected = selectedOption == option,
                onClick = { onOptionSelected(option) },
                label = {
                    Column {
                        Text(option)
                        Text(detail, style = MaterialTheme.typography.bodySmall)
                    }
                },
                shape = RoundedCornerShape(16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LogScreenPreview() {
    EcoChallengeTheme {
        LogScreen()
    }
}