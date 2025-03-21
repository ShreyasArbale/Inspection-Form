package com.example.inspectionapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.inspectionapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController, function: () -> Unit) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Inspection Form",
                        color = Color.White,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = colorResource(id = R.color.primary_color)
                )
            )
        },
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(bottom = 16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = "Vehicle Inspection",
                    style = MaterialTheme.typography.headlineLarge,
                    color = colorResource(id = R.color.primary_color)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Vehicle inspection is a routine checkup to assess the safety, functionality, and condition of essential components like brakes, tires, engine, lights, and fluids to ensure compliance, efficiency, and roadworthiness.",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.DarkGray
                )

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = { navController.navigate("tabs") },
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.primary_color)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text("Get Started")
                }
            }
        }
    }
}