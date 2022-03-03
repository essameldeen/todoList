package com.demo.todolist.presentation.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.demo.todolist.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}