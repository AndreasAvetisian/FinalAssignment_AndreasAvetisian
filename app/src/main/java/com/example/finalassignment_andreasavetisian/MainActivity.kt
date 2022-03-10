package com.example.finalassignment_andreasavetisian

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import com.example.finalassignment_andreasavetisian.ui.theme.FinalAssignment_AndreasAvetisianTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FinalAssignment_AndreasAvetisianTheme {
                Surface{
                    MainView()
                }
            }
        }
    }
}