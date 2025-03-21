package com.example.inspectionapp.ui.tabs

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.inspectionapp.R
import com.example.inspectionapp.ui.components.CustomTextField
import com.example.inspectionapp.viewmodel.InspectionViewModel
import com.example.inspectionapp.viewmodel.MainViewModel
import java.util.Calendar

@Composable
fun AdditionalInfoTab(
    titleText: String,
    viewModel: MainViewModel,
    inspectionViewModel : InspectionViewModel,
    navController: NavController,
    context: Context,
) {

    val cost = remember { mutableStateOf("") }
    val selectedImageUri = remember { mutableStateOf<Uri?>(null) }

    val preSignImageUri = remember { mutableStateOf<Uri?>(null) }
    val preCustomerSignImageUri = remember { mutableStateOf<Uri?>(null) }

    val postSignImageUri = remember { mutableStateOf<Uri?>(null) }
    val postCustomerSignImageUri = remember { mutableStateOf<Uri?>(null) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        selectedImageUri.value = uri
    }

    val preSignImagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        preSignImageUri.value = uri
    }

    val preCustomerSignImagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        preCustomerSignImageUri.value = uri
    }

    val postSignImagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        postSignImageUri.value = uri
    }

    val postCustomerSignImagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        postCustomerSignImageUri.value = uri
    }

    var preHireSelectedDate by remember { mutableStateOf("") }
    var preHireShowDialog by remember { mutableStateOf(false) }

    var postHireSelectedDate by remember { mutableStateOf("") }
    var postHireShowDialog by remember { mutableStateOf(false) }

    val isSaving by inspectionViewModel.isSaving.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        ProgressDialog(isShowing = isSaving)

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            titleText,
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(modifier = Modifier.weight(1f)) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Comments",
                                style = MaterialTheme.typography.titleSmall,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.weight(1f))

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .clickable { inspectionViewModel.addComment(context) }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AddCircle,
                                    contentDescription = "Add Icon",
                                    tint = colorResource(id = R.color.primary_color),
                                    modifier = Modifier.size(24.dp).padding(end = 8.dp)
                                )

                                Text(
                                    "Add more",
                                    style = MaterialTheme.typography.titleSmall,
                                    color = colorResource(id = R.color.primary_color)
                                )
                            }
                        }

                        inspectionViewModel.commentsList.forEach { comment ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                CustomTextField(
                                    value = comment,
                                    onValueChange = { newValue ->
                                        inspectionViewModel.updateComment(
                                            inspectionViewModel.commentsList.indexOf(comment),
                                            newValue
                                        )
                                    },
                                    label = "Enter your comment",
                                    isSingleLined = false,
                                    modifier = Modifier.weight(1f)
                                )

                                Spacer(modifier = Modifier.size(8.dp))

                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete Comment",
                                    tint = Color.Red,
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clickable {
                                            inspectionViewModel.deleteComment(
                                                inspectionViewModel.commentsList.indexOf(comment)
                                            )
                                        }
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    "Upload Photo",
                                    style = MaterialTheme.typography.titleSmall,
                                    color = Color.Black
                                )
                                Spacer(modifier = Modifier.height(8.dp))

                                Box(
                                    modifier = Modifier
                                        .height(56.dp)
                                        .fillMaxWidth()
                                        .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                                        .clickable { imagePickerLauncher.launch("image/*") },
                                    contentAlignment = Alignment.Center
                                ) {
                                    selectedImageUri.value?.let { uri ->
                                        AsyncImage(
                                            model = uri,
                                            contentDescription = "Selected Image",
                                            modifier = Modifier.size(48.dp)
                                        )
                                    } ?: run {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.icon_uploade),
                                                contentDescription = "UploadImage",
                                                tint = colorResource(id = R.color.primary_color),
                                                modifier = Modifier
                                                    .size(24.dp)
                                                    .padding(end = 8.dp)
                                            )

                                            Text(
                                                "Select Photos",
                                                style = MaterialTheme.typography.titleSmall,
                                                color = colorResource(id = R.color.primary_color)
                                            )
                                        }
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    "Cost",
                                    style = MaterialTheme.typography.titleSmall,
                                    color = Color.Black
                                )
                                CustomTextField(
                                    value = cost.value,
                                    onValueChange = { cost.value = it },
                                    label = "Enter cost",
                                    keyboardType = KeyboardType.Number
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }

                item {
                    Column {
                        Text(
                            "Pre-Hire Acceptance",
                            style = MaterialTheme.typography.titleSmall,
                            color = Color.Black,
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(Alignment.CenterHorizontally)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(modifier = Modifier.fillMaxWidth()) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                            ) {
                                Text(
                                    "24/7 Rep (Print & Sign)",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.Black,
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Box(
                                    modifier = Modifier
                                        .height(48.dp)
                                        .fillMaxWidth()
                                        .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                                        .clickable { preSignImagePickerLauncher.launch("image/*") },
                                    contentAlignment = Alignment.Center
                                ) {
                                    preSignImageUri.value?.let { uri ->
                                        AsyncImage(
                                            model = uri,
                                            contentDescription = "Selected Image",
                                            modifier = Modifier.size(48.dp)
                                        )
                                    } ?: run {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.icon_uploade),
                                                contentDescription = "UploadImage",
                                                tint = colorResource(id = R.color.primary_color),
                                                modifier = Modifier
                                                    .size(24.dp)
                                                    .padding(end = 8.dp)
                                            )

                                            Text(
                                                "Select Sign",
                                                style = MaterialTheme.typography.titleSmall,
                                                color = colorResource(id = R.color.primary_color)
                                            )
                                        }
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                            ) {
                                Text(
                                    "Customer Rep (Print & Sign)",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.Black,
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Box(
                                    modifier = Modifier
                                        .height(48.dp)
                                        .fillMaxWidth()
                                        .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                                        .clickable { preCustomerSignImagePickerLauncher.launch("image/*") },
                                    contentAlignment = Alignment.Center
                                ) {
                                    preCustomerSignImageUri.value?.let { uri ->
                                        AsyncImage(
                                            model = uri,
                                            contentDescription = "Selected Image",
                                            modifier = Modifier.size(48.dp)
                                        )
                                    } ?: run {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.icon_uploade),
                                                contentDescription = "UploadImage",
                                                tint = colorResource(id = R.color.primary_color),
                                                modifier = Modifier
                                                    .size(24.dp)
                                                    .padding(end = 8.dp)
                                            )

                                            Text(
                                                "Select Sign",
                                                style = MaterialTheme.typography.titleSmall,
                                                color = colorResource(id = R.color.primary_color)
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            "Pre-Hire Date",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { preHireShowDialog = true }
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
                                text = if (preHireSelectedDate.isNotEmpty()) preHireSelectedDate else "Pick a date",
                                color = Color.Black,
                                modifier = Modifier.padding(start = 16.dp)
                            )

                            if (preHireShowDialog) {
                                PreHireDatePickerDialog(
                                    onDateSelected = { date ->
                                        preHireSelectedDate = date
                                        preHireShowDialog = false
                                    },
                                    onDismiss = { preHireShowDialog = false }
                                )
                            }
                        }

                    }

                    Spacer(modifier = Modifier.height(24.dp))
                }

                item {
                    Column {
                        Text(
                            "Post-Hire Acceptance",
                            style = MaterialTheme.typography.titleSmall,
                            color = Color.Black,
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(Alignment.CenterHorizontally)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(modifier = Modifier.fillMaxWidth()) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                            ) {
                                Text(
                                    "24/7 Rep (Print & Sign)",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.Black,
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Box(
                                    modifier = Modifier
                                        .height(48.dp)
                                        .fillMaxWidth()
                                        .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                                        .clickable { postSignImagePickerLauncher.launch("image/*") },
                                    contentAlignment = Alignment.Center
                                ) {
                                    postSignImageUri.value?.let { uri ->
                                        AsyncImage(
                                            model = uri,
                                            contentDescription = "Selected Image",
                                            modifier = Modifier.size(48.dp)
                                        )
                                    } ?: run {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.icon_uploade),
                                                contentDescription = "UploadImage",
                                                tint = colorResource(id = R.color.primary_color),
                                                modifier = Modifier
                                                    .size(24.dp)
                                                    .padding(end = 8.dp)
                                            )

                                            Text(
                                                "Select Sign",
                                                style = MaterialTheme.typography.titleSmall,
                                                color = colorResource(id = R.color.primary_color)
                                            )
                                        }
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                            ) {
                                Text(
                                    "Customer Rep (Print & Sign)",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.Black,
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Box(
                                    modifier = Modifier
                                        .height(48.dp)
                                        .fillMaxWidth()
                                        .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                                        .clickable { postCustomerSignImagePickerLauncher.launch("image/*") },
                                    contentAlignment = Alignment.Center
                                ) {
                                    postCustomerSignImageUri.value?.let { uri ->
                                        AsyncImage(
                                            model = uri,
                                            contentDescription = "Selected Image",
                                            modifier = Modifier.size(48.dp)
                                        )
                                    } ?: run {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.icon_uploade),
                                                contentDescription = "UploadImage",
                                                tint = colorResource(id = R.color.primary_color),
                                                modifier = Modifier
                                                    .size(24.dp)
                                                    .padding(end = 8.dp)
                                            )

                                            Text(
                                                "Select Sign",
                                                style = MaterialTheme.typography.titleSmall,
                                                color = colorResource(id = R.color.primary_color)
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            "Post-Hire Date",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { postHireShowDialog = true }
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
                                text = if (postHireSelectedDate.isNotEmpty()) postHireSelectedDate else "Pick a date",
                                color = Color.Black,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                            if (postHireShowDialog) {
                                PostHireDatePickerDialog(
                                    onDateSelected = { date ->
                                        postHireSelectedDate = date
                                        postHireShowDialog = false
                                    },
                                    onDismiss = { postHireShowDialog = false }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    cost.value = ""
                    preHireSelectedDate = ""
                    postHireSelectedDate = ""
                    selectedImageUri.value = null
                    preSignImageUri.value = null
                    preCustomerSignImageUri.value = null
                    postSignImageUri.value = null
                    postCustomerSignImageUri.value = null
                    inspectionViewModel.clearAdditionalDetailsTab()
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
                    inspectionViewModel.saveAdditionalInfo(
                        context = context,
                        selectedImageUri = selectedImageUri.value.toString(),
                        cost = cost.value,
                        preSignImageUri = preSignImageUri.value.toString(),
                        preCustomerSignImageUri = preCustomerSignImageUri.value.toString(),
                        preHireSelectedDate = preHireSelectedDate,
                        postSignImageUri = postSignImageUri.value.toString(),
                        postCustomerSignImageUri = postCustomerSignImageUri.value.toString(),
                        postHireSelectedDate = postHireSelectedDate,
                        onSuccess = {
                            viewModel.setSelectedTabIndex(0)
                            navController.popBackStack()
                        }
                    )
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
                Text("Submit")
            }
        }
    }
}

@Composable
fun PreHireDatePickerDialog(
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

@Composable
fun PostHireDatePickerDialog(
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

@Composable
fun ProgressDialog(isShowing: Boolean) {
    if (isShowing) {
        Dialog(onDismissRequest = {}) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(64.dp)
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                CircularProgressIndicator(
                    color = colorResource(id = R.color.primary_color)
                )
            }
        }
    }
}