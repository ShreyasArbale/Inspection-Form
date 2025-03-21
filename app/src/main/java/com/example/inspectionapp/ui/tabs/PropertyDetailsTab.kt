package com.example.inspectionapp.ui.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.inspectionapp.R
import com.example.inspectionapp.ui.components.CustomTextField
import com.example.inspectionapp.viewmodel.InspectionViewModel
import com.example.inspectionapp.viewmodel.MainViewModel
import java.util.Calendar

@Composable
fun PropertyDetailsTab(titleText: String, mainViewModel: MainViewModel, inspectionViewModel: InspectionViewModel) {

    val customerName = remember { mutableStateOf("") }
    val siteName = remember { mutableStateOf("") }
    val plantNumber = remember { mutableStateOf("") }
    val serialNumber = remember { mutableStateOf("") }

    var selectedDate by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        Text(
            titleText,
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )

        Text(
            "Customer Name",
            style = MaterialTheme.typography.labelLarge,
            color = Color.Black,
        )

        CustomTextField(
            value = customerName.value,
            onValueChange = { customerName.value = it },
            label = "Enter customer name",
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )

        Text(
            "Site Name",
            style = MaterialTheme.typography.labelLarge,
            color = Color.Black,
        )

        CustomTextField(
            value = siteName.value,
            onValueChange = { siteName.value = it },
            label = "Enter site name",
            isSingleLined = false
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )

        Text(
            "Plant Number",
            style = MaterialTheme.typography.labelLarge,
            color = Color.Black,
        )

        CustomTextField(
            value = plantNumber.value,
            onValueChange = { plantNumber.value = it },
            label = "Enter plant number",
            keyboardType = KeyboardType.Number
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )

        Text(
            "Serial Number",
            style = MaterialTheme.typography.labelLarge,
            color = Color.Black
        )

        CustomTextField(
            value = serialNumber.value,
            onValueChange = { serialNumber.value = it },
            label = "Enter serial number",
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )

        Text(
            "Date",
            style = MaterialTheme.typography.labelLarge,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showDialog = true }
                .height(48.dp)
                .background(
                    color = colorResource(id = R.color.light_gray),
                    shape = RoundedCornerShape(8.dp)
                )
                .clip(RoundedCornerShape(8.dp)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "Calendar Icon",
                tint = Color.Gray,
                modifier = Modifier.size(24.dp)
            )

            Text(
                text = if (selectedDate.isNotEmpty()) selectedDate else "Pick a date",
                color = Color.Black,
                modifier = Modifier.padding(start = 16.dp)
            )

            if (showDialog) {
                DatePickerDialog(
                    onDateSelected = { date ->
                        selectedDate = date
                        showDialog = false
                    },
                    onDismiss = { showDialog = false }
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    customerName.value = ""
                    siteName.value = ""
                    plantNumber.value = ""
                    serialNumber.value = ""
                    selectedDate = ""
                    mainViewModel.previousTab()
                },
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
                    inspectionViewModel.savePropertyDetails(
                        customerName.value,
                        siteName.value,
                        plantNumber.value,
                        serialNumber.value,
                        selectedDate
                    )
                    mainViewModel.nextTab()
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
fun DatePickerDialog(
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    LaunchedEffect(Unit) {
        val datePickerDialog = android.app.DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val date = "$dayOfMonth-${month + 1}-$year"
                onDateSelected(date)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.setOnCancelListener {
            onDismiss()
        }

        datePickerDialog.show()
    }
}