package com.example.firebasechat.domain.signUp

internal interface SignUpRepository {

    fun isEmptyEmail(email: String) : Boolean

    fun isValidEmail(email: String) : Boolean

    fun isValidPassword(password: String) : Boolean

    fun isPasswordValidConfirmPassword(password: String, confirmPassword: String) : Boolean
}