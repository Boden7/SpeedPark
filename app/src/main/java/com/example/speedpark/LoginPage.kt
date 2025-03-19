// Author: Boden Kahn
// Course: CSCI 403 Capstone
// Description: This activity allows users to log in with their credentials and
// determines if they are an administrator or a regular user.
package com.example.speedpark

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginPage : AppCompatActivity() {
    private lateinit var buttonLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Create the login button
        buttonLogin = findViewById(R.id.buttonLogin)

        // Set listener to check for button clicks
        buttonLogin.setOnClickListener{
            // Validate login credentials

            // Determine if the user is an admin or not
            val isAdmin = true // temporary
            // Go to the admin page
            if(isAdmin) {
                val intent = Intent(this, AdminView::class.java)
                startActivity(intent)
            }
            // Go to the user page
            else{
                val intent = Intent(this, UserView::class.java)
                startActivity(intent)
            }
        }
    }
}