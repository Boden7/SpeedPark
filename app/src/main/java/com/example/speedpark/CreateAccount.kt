// Author: Boden Kahn
// Course: CSCI 403 Capstone
/* Description: This activity allows users to create an account in our users
 * database with their first and last name, email, and password.
*/
package com.example.speedpark

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
//import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateAccount : AppCompatActivity() {
    private lateinit var usernameInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var confirmPasswordInput: EditText
    //private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_create_account)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        usernameInput = findViewById(R.id.createUsername)
        emailInput = findViewById(R.id.createEmail)
        passwordInput = findViewById(R.id.createPassword)
        confirmPasswordInput = findViewById(R.id.createPassword2)

        //auth = FirebaseAuth.getInstance()

        // Make the create account button
        val createButton = findViewById<Button>(R.id.createButton)
        createButton.setOnClickListener{
            val username = usernameInput.text.toString()
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()
            val confirmPassword = confirmPasswordInput.text.toString()
            // Make sure fields are not empty
            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            }
            // Make sure passwords match
            if (password != confirmPassword){
                confirmPasswordInput.error = "Passwords do not match"
            }
            // Enter data in the user database
            else{
                confirmPasswordInput.error = null
                val request = RegisterRequest(username, email, password)
                RetrofitClient.api.registerUser(request).enqueue(object : Callback<RegisterResponse> {
                    override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                        if (response.isSuccessful) {
                            // Send them back to the home page, where they can log in with their new account
                            Toast.makeText(baseContext, "Sign up successful", Toast.LENGTH_SHORT).show()
                            val intent = Intent(baseContext, MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            Log.e("REGISTER", "Error: ${response.errorBody()?.string()}")
                            Toast.makeText(baseContext, "Sign up failed", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                        Log.e("REGISTER", "Failed: ${t.message}")
                        Toast.makeText(baseContext, "Sign up failed", Toast.LENGTH_SHORT).show()
                    }
                })
                // Firebase login
                /*auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this){task ->
                    if (task.isSuccessful) {
                        // Send them back to the home page, where they can log in with their new account
                        Toast.makeText(this, "Sign up successful", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(this, "Sign up failed: ${task.exception?.message}",
                            Toast.LENGTH_SHORT).show()
                    }
                }*/
            }
            // Send them back to the home page
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}