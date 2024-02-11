package com.example.firebasechat

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasechat.databinding.ActivityLogInBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

private const val PASSWORD_PATTERN =
    "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@\$%^&*-]).{8,}\$"

class LogInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogInBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        binding.registerButton.setOnClickListener {
            if (checkLogIn()) {
                val email = binding.etEmailLogIn.text.toString()
                val password = binding.etPasswordLogIn.text.toString()
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val user = auth.currentUser
                        Toast.makeText(
                            this,
                            "Is successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        binding.userUid.text = user?.uid.toString()
                        val intent = Intent(this, UserListActivity::class.java)
                        intent.putExtra("userUid", user?.uid.toString())
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(
                            this,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

    }

    private fun checkLogIn(): Boolean {
        val email = binding.etEmailLogIn.text.toString()
        val password = binding.etPasswordLogIn.text.toString()
        if (email == "") {
            binding.textInputLayoutEmailLogIn.error = "Is empty email"
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.textInputLayoutPasswordLogIn.error = "Is not valid email"
            return false
        }
        if (!password.matches(Regex(PASSWORD_PATTERN))) {
            binding.textInputLayoutPasswordLogIn.error = "Is not valid password"
            binding.textInputLayoutPasswordLogIn.errorIconDrawable = null
            return false
        }
        return true
    }
}