package com.example.firebasechat.domain.chatMessage

import androidx.lifecycle.LiveData
import com.example.firebasechat.ChatMessage

internal interface ChatMessageListRepository {

    fun getMessageFromFirebase(uid: String) : LiveData<List<ChatMessage>>

    fun addMessageInFirebaseDatabase(uid: String, textMessage: String)
}