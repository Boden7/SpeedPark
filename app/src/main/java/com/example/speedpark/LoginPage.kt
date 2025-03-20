// Author: Boden Kahn
// Course: CSCI 403 Capstone
// Description: This activity allows users to log in with their credentials and
// determines if they are an administrator or a regular user.
package com.example.speedpark

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class LoginPage : AppCompatActivity() {
    private lateinit var buttonLogin: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        auth = FirebaseAuth.getInstance()
        emailInput = findViewById(R.id.Inemail)
        passwordInput = findViewById(R.id.InPassword)
        buttonLogin = findViewById(R.id.buttonLogin)

        // Set listener to check for button clicks
        buttonLogin.setOnClickListener{
            // Validate login credentials
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill out all fields",
                    Toast.LENGTH_SHORT).show()
            } else {
                loginUser(email, password)
            }
        }
    }
    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                val admin = false
                // determine if the user is an admin or not
                if (admin) {
                    // Proceed to the admin screen
                    val intent = Intent(this, AdminView::class.java)
                    startActivity(intent)
                } else {
                    // Proceed to the user screen
                    val intent = Intent(this, UserView::class.java)
                    startActivity(intent)
                }
            } else {
                Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}