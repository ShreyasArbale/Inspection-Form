package com.example.inspectionapp.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.inspectionapp.R
import com.example.inspectionapp.ui.tabs.AdditionalInfoTab
import com.example.inspectionapp.ui.tabs.ConditionDetailsTab
import com.example.inspectionapp.ui.tabs.PropertyDetailsTab
import com.example.inspectionapp.viewmodel.InspectionViewModel
import com.example.inspectionapp.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabScreen(viewModel: MainViewModel, inspectionViewModel: InspectionViewModel, navController: NavController, onBack: () -> Unit) {
    val selectedTabIndex by viewModel.selectedTabIndex.collectAsState()
    val progress by animateFloatAsState(targetValue = (selectedTabIndex + 1) / 3f, label = "")
    val context = LocalContext.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Inspection Form",
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        onBack()
                        viewModel.setSelectedTabIndex(0)
                    }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = colorResource(id = R.color.primary_color)
                )
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(vertical = 8.dp, horizontal = 16.dp)
        ) {
            SpacedProgressIndicator(progress)

            when (selectedTabIndex) {
                0 -> PropertyDetailsTab("Property Details", viewModel, inspectionViewModel)
                1 -> ConditionDetailsTab("Condition Report", viewModel, inspectionViewModel)
                2 -> AdditionalInfoTab("Additional Information", viewModel, inspectionViewModel, navController, context)
            }
        }
    }
}

@Composable
fun SpacedProgressIndicator(progress: Float) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(3) { index ->
            LinearProgressIndicator(
                progress = { if (index < (progress * 3).toInt()) 1f else 0f },
                modifier = Modifier
                    .weight(1f)
                    .height(4.dp),
                color = colorResource(id = R.color.green),
                trackColor = Color.LightGray.copy(alpha = 0.5f)
            )
        }
    }
}