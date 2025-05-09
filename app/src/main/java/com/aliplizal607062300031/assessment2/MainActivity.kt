package com.aliplizal607062300031.assessment2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.aliplizal607062300031.assessment2.navigation.SetupNavGraph
import com.aliplizal607062300031.assessment2.ui.theme.Assessment2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Assessment2Theme {
                SetupNavGraph(navController = rememberNavController())
            }
        }
    }
}
