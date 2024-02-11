package com.example.firebasechat.data

import android.util.Patterns
import com.example.firebasechat.domain.signUp.SignUpRepository

private const val PASSWORD_PATTERN =
    "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@\$%^&*-]).{8,}\$"

internal object SignUpRepositoryImpl : SignUpRepository {

    override fun isEmptyEmail(email: String): Boolean {
        if (email.isEmpty()) {
            return false
        }
        return true
    }

    override fun isValidEmail(email: String): Boolean {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return false
        }
        return true
    }

    override fun isValidPassword(password: String): Boolean {
        if (!password.matches(Regex(PASSWORD_PATTERN))) {
            return false
        }
        return true
    }

    override fun isPasswordValidConfirmPassword(
        password: String,
        confirmPassword: String
    ): Boolean {
        if (password != confirmPassword) {
            return false
        }
        return true
    }
}