package com.example.inspectionapp.ui.tabs

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.inspectionapp.R
import com.example.inspectionapp.model.ConditionResponse
import com.example.inspectionapp.ui.components.CustomTextField
import com.example.inspectionapp.viewmodel.InspectionViewModel
import com.example.inspectionapp.viewmodel.MainViewModel

@Composable
fun ConditionDetailsTab(titleText: String, viewModel: MainViewModel, inspectionViewModel: InspectionViewModel) {
    val questions = listOf(
        "1. Check QDS Horns for excessive wear or signs of damage.",
        "2. Inspect wheel bearings for wear.",
        "3. Verify the braking system functionality.",
        "4. Examine hydraulic hoses for leaks.",
        "5. Assess tire pressure and tread depth."
    )

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            titleText,
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 16.dp)
        )

        LazyColumn(
            modifier = Modifier.weight(1f),
        ) {
            items(questions) { question ->
                ConditionQuestion(question, viewModel, inspectionViewModel)
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { inspectionViewModel.clearConditionResponses(questions) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .weight(1f)
                    .padding(horizontal = 8.dp)
                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.white),
                ),
            ) {
                Text("Clear", color = Color.Gray)
            }
            Button(
                onClick = {
                    val responses = questions.map { question ->
                        inspectionViewModel.conditionResponses.value[question] ?: ConditionResponse(
                            question,
                            "",
                            "A",
                            "",
                            "A",
                            ""
                        )
                    }
                    Log.d("ConditionResponses", responses.toString())
                    inspectionViewModel.saveConditionResponses(responses)
                    viewModel.nextTab()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.primary_color),
                ),
            ) {
                Text("Next ->")
            }
        }
    }
}

@Composable
fun ConditionQuestion(
    question: String,
    viewModel: MainViewModel,
    inspectionViewModel: InspectionViewModel
) {
    val conditionResponses by inspectionViewModel.conditionResponses.collectAsState()
    val response = conditionResponses[question] ?: ConditionResponse(question, "", "A", "", "A", "")

    var comment by remember { mutableStateOf(response.comment) }
    var preCost by remember { mutableStateOf(response.preCost) }
    var postCost by remember { mutableStateOf(response.postCost) }
    var preSelectedOption by remember { mutableStateOf(response.preCondition) }
    var postSelectedOption by remember { mutableStateOf(response.postCondition) }

    LaunchedEffect(conditionResponses) {
        val updatedResponse = conditionResponses[question] ?: ConditionResponse(question, "", "A", "", "A", "")
        comment = updatedResponse.comment
        preCost = updatedResponse.preCost
        postCost = updatedResponse.postCost
        preSelectedOption = updatedResponse.preCondition
        postSelectedOption = updatedResponse.postCondition
    }

    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = question, style = MaterialTheme.typography.labelLarge, color = Color.Black)

        CustomTextField(
            value = comment,
            onValueChange = {
                comment = it
                inspectionViewModel.updateConditionResponse(question, comment, preSelectedOption, preCost, postSelectedOption, postCost)
            },
            label = "Comment",
            isSingleLined = false
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.weight(3f)) {
                Text("Pre Condition", style = MaterialTheme.typography.titleSmall, color = Color.Black)
                CustomRadioGroup(
                    selectedOption = preSelectedOption,
                    onOptionSelected = {
                        preSelectedOption = it
                        inspectionViewModel.updateConditionResponse(question, comment, preSelectedOption, preCost, postSelectedOption, postCost)
                    }
                )
            }
            Column(modifier = Modifier.weight(2f)) {
                Text("Cost", style = MaterialTheme.typography.titleSmall, color = Color.Black)
                CustomTextField(
                    value = preCost,
                    onValueChange = {
                        preCost = it
                        inspectionViewModel.updateConditionResponse(question, comment, preSelectedOption, preCost, postSelectedOption, postCost)
                    },
                    label = "Enter cost",
                    keyboardType = KeyboardType.Number
                )
            }
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.weight(3f)) {
                Text("Post Condition", style = MaterialTheme.typography.titleSmall, color = Color.Black)
                CustomRadioGroup(
                    selectedOption = postSelectedOption,
                    onOptionSelected = {
                        postSelectedOption = it
                        inspectionViewModel.updateConditionResponse(question, comment, preSelectedOption, preCost, postSelectedOption, postCost)
                    }
                )
            }
            Column(modifier = Modifier.weight(2f)) {
                Text("Cost", style = MaterialTheme.typography.titleSmall, color = Color.Black)
                CustomTextField(
                    value = postCost,
                    onValueChange = {
                        postCost = it
                        inspectionViewModel.updateConditionResponse(question, comment, preSelectedOption, preCost, postSelectedOption, postCost)
                    },
                    label = "Enter cost",
                    keyboardType = KeyboardType.Number
                )
            }
        }
    }
}

@Composable
fun CustomRadioGroup(selectedOption: String, onOptionSelected: (String) -> Unit) {
    Row {
        listOf("A", "B", "C").forEach { option ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = selectedOption == option,
                    onClick = { onOptionSelected(option) },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = colorResource(id = R.color.green),
                        unselectedColor = Color.Gray
                    )
                )
                Text(text = option)
            }
        }
    }
}