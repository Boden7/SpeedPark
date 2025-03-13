package com.example.speedpark

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // temporary
        val userButton = findViewById<Button>(R.id.userButton)
        userButton.setOnClickListener {
            val intent = Intent(this, UserView::class.java)
            startActivity(intent);
        }
        val adminButton = findViewById<Button>(R.id.adminButton)
        adminButton.setOnClickListener {
            val intent = Intent(this, AdminView::class.java)
            startActivity(intent);
        }
    }
}