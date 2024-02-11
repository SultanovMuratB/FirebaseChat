package com.example.firebasechat.domain.signUp

import com.example.firebasechat.data.SignUpRepositoryImpl

internal class IsEmptyEmailUseCase(private val signUpRepositoryImpl: SignUpRepositoryImpl) {

    fun isEmptyEmail(email: String): Boolean {
        return signUpRepositoryImpl.isEmptyEmail(email)
    }
}