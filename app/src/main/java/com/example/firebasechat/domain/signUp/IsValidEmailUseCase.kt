package com.example.firebasechat.domain.signUp

import com.example.firebasechat.data.SignUpRepositoryImpl

internal class IsValidEmailUseCase(private val signUpRepositoryImpl: SignUpRepositoryImpl) {

    fun isValidEmail(email: String) : Boolean {
        return signUpRepositoryImpl.isValidEmail(email)
    }
}