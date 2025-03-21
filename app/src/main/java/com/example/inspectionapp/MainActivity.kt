package com.example.inspectionapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.inspectionapp.ui.screens.MainScreen
import com.example.inspectionapp.ui.screens.SplashScreen
import com.example.inspectionapp.ui.screens.TabScreen
import com.example.inspectionapp.viewmodel.InspectionViewModel
import com.example.inspectionapp.viewmodel.MainViewModel
import com.example.inspectionapp.viewmodel.SyncViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = MainViewModel()
            val inspectionViewModel = InspectionViewModel()
            val syncViewModel = SyncViewModel()
            val navController = rememberNavController()

            syncViewModel.scheduleSyncWorker(this)

            NavHost(navController = navController, startDestination = "splash") {
                composable("splash") {
                    SplashScreen {
                        navController.navigate("main")
                    }
                }
                composable("main") {
                    MainScreen(navController) {
                        navController.navigate("tabs")
                    }
                }
                composable("tabs") {
                    TabScreen(viewModel, inspectionViewModel, navController) {
                        navController.popBackStack()
                    }
                }
            }
        }
    }
}