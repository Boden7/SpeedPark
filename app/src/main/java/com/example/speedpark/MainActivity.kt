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
        setContentView(R.layout.activity_home_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val logInButton = findViewById<Button>(R.id.logInButton)
        logInButton.setOnClickListener {
            val intent = Intent(this, LoginPage::class.java)
            startActivity(intent);
        }
        val createAccountButton = findViewById<Button>(R.id.createAccountButton)
        createAccountButton.setOnClickListener {
            val intent = Intent(this, CreateAccount::class.java)
            startActivity(intent);
        }
    }
}