package com.example.firebasechat

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasechat.databinding.ActivityAuthenticationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

private const val PASSWORD_PATTERN =
    "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@\$%^&*-]).{8,}\$"

class SingUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityAuthenticationBinding
    private lateinit var databaseReference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        binding.button.setOnClickListener {
            if (checkEmailAndPassword()) {
                val email = binding.etEmail.text.toString()
                val password = binding.etPassword.text.toString()
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val user = auth.currentUser
                            val userUid = user!!.uid
                            val userEmail = user.email.toString()
                            databaseReference =
                                FirebaseDatabase.getInstance().getReference("users").child(userUid)
                            val hashMap = hashMapOf<String, String>()
                            hashMap["userUid"] = userUid
                            hashMap["userEmail"] = userEmail
                            databaseReference.setValue(hashMap)
                                .addOnCompleteListener(this) { task ->
                                    if (task.isSuccessful) {
                                        val intent = Intent(this, LogInActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    }
                                }
                        }
                        Toast.makeText(this, "Is successfully", Toast.LENGTH_SHORT).show()
                    }
            }
        }
        binding.registerButton.setOnClickListener {
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun checkEmailAndPassword(): Boolean {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val confirmPassword = binding.confirmPassword.text.toString()
        if (email == "") {
            binding.textInputLayoutEmail.error = "Is empty email"
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.textInputLayoutEmail.error = "Is not valid email"
            return false
        }
        if (!password.matches(Regex(PASSWORD_PATTERN))) {
            binding.textInputLayoutPassword.error = "Is not valid password"
            binding.textInputLayoutPassword.errorIconDrawable = null
            return false
        }
        if (password != confirmPassword) {
            binding.textInputConfirmPassword.error = "different passwords"
            binding.textInputLayoutPassword.errorIconDrawable = null
            return false
        }
        return true
    }
}