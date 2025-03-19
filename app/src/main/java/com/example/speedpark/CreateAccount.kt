// Author: Boden Kahn
// Course: CSCI 403 Capstone
// Description: This activity allows users to create an account in our users
// database with their first and last name, email, and password.
package com.example.speedpark

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CreateAccount : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_create_account)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Make the create account button
        val createButton = findViewById<Button>(R.id.createButton)
        createButton.setOnClickListener{
            // Make sure passwords match

            // Enter data in the user database

            // Log in

            // Send them back to the home page, where they can log in with their new account
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}