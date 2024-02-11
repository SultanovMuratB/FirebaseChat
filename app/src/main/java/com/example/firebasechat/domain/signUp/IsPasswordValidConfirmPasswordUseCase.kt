package com.example.firebasechat.domain.signUp

import com.example.firebasechat.data.SignUpRepositoryImpl

internal class IsPasswordValidConfirmPasswordUseCase(
    private val signUpRepositoryImpl: SignUpRepositoryImpl
) {

    fun isPasswordValidConfirmPassword(password: String, confirmPassword: String) : Boolean {
        return signUpRepositoryImpl.isPasswordValidConfirmPassword(password, confirmPassword)
    }
}