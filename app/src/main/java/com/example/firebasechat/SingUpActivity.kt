package com.example.firebasechat

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasechat.data.SignUpRepositoryImpl
import com.example.firebasechat.databinding.ActivityAuthenticationBinding
import com.example.firebasechat.domain.signUp.IsEmptyEmailUseCase
import com.example.firebasechat.domain.signUp.IsPasswordValidConfirmPasswordUseCase
import com.example.firebasechat.domain.signUp.IsValidEmailUseCase
import com.example.firebasechat.domain.signUp.IsValidPasswordUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SingUpActivity : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()
    private lateinit var binding: ActivityAuthenticationBinding
    private lateinit var databaseReference: DatabaseReference

    private val repository = SignUpRepositoryImpl
    private val isEmptyEmailUseCase = IsEmptyEmailUseCase(repository)
    private val isValidEmailUseCase = IsValidEmailUseCase(repository)
    private val isValidPasswordUseCase = IsValidPasswordUseCase(repository)
    private val isPasswordValidConfirmPasswordUseCase =
        IsPasswordValidConfirmPasswordUseCase(repository)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener {
            binding.textInputConfirmPassword.error = ""
            binding.textInputLayoutEmail.error = ""
            binding.textInputLayoutPassword.error = ""
            binding.textInputLayoutEmail.error = ""
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val confirmPassword = binding.confirmPassword.text.toString()
            if (isEmptyEmailUseCase.isEmptyEmail(email)) {
                if (isValidPasswordUseCase.isValidPassword(password)) {
                    if (isValidEmailUseCase.isValidEmail(email)) {
                        if (isPasswordValidConfirmPasswordUseCase.isPasswordValidConfirmPassword(
                                password,
                                confirmPassword
                            )
                        ) {
                            auth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener {
                                    if (it.isSuccessful) {
                                        val user = auth.currentUser
                                        user?.let {
                                            val userUid = user.uid
                                            val userEmail = user.email.toString()
                                            databaseReference =
                                                FirebaseDatabase.getInstance().getReference("users")
                                                    .child(userUid)
                                            val hashMap = hashMapOf<String, String>()
                                            hashMap["userUid"] = userUid
                                            hashMap["userEmail"] = userEmail
                                            databaseReference.setValue(hashMap)
                                                .addOnCompleteListener(this) { task ->
                                                    if (task.isSuccessful) {
                                                        val intent =
                                                            Intent(this, LogInActivity::class.java)
                                                        startActivity(intent)
                                                        finish()
                                                    }
                                                }
                                        }
                                    }
                                }
                        } else {
                            binding.textInputConfirmPassword.error = "different passwords"
                            binding.textInputLayoutPassword.errorIconDrawable = null
                        }
                    } else {
                        binding.textInputLayoutEmail.error = "Is not valid email"
                    }
                } else {
                    binding.textInputLayoutPassword.error = "Is not valid password"
                    binding.textInputLayoutPassword.errorIconDrawable = null
                }
            } else {
                binding.textInputLayoutEmail.error = "Is empty email"
            }
        }
        binding.registerButton.setOnClickListener {
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}