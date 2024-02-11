package com.example.firebasechat.domain.signUp

import com.example.firebasechat.data.SignUpRepositoryImpl

internal class IsValidPasswordUseCase(private val signUpRepositoryImpl: SignUpRepositoryImpl) {

    fun isValidPassword(password: String) : Boolean {
        return signUpRepositoryImpl.isValidPassword(password)
    }
}