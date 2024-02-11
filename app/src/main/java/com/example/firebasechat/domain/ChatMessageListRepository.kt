package com.example.firebasechat.domain

import androidx.lifecycle.LiveData
import com.example.firebasechat.ChatMessage

interface ChatMessageListRepository {

    fun getMessageFromFirebase(uid: String) : LiveData<List<ChatMessage>>

    fun addMessageInFirebaseDatabase(uid: String, textMessage: String)
}