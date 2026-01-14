package com.example.newspulse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.newspulse.ui.navigation.BottomBar
import com.example.newspulse.ui.navigation.NavGraph
import com.example.newspulse.ui.navigation.NavRoutes
import com.example.newspulse.ui.theme.NewsPulseTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NewsPulseTheme {
                NewsPulseApp()
            }
        }
    }
}

@Composable
fun NewsPulseApp() {

    val navController = rememberNavController()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            val currentRoute =
                navController.currentBackStackEntry
                    ?.destination
                    ?.route

            if (currentRoute != NavRoutes.Detail.route) {
                BottomBar(navController = navController)
            }
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                NavGraph(navController = navController)
            }
        }
    )

}

