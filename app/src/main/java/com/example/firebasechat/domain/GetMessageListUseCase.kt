package com.example.firebasechat.domain

import androidx.lifecycle.LiveData
import com.example.firebasechat.ChatMessage

class GetMessageListUseCase(private val chatMessageListRepository: ChatMessageListRepository) {

    fun getMessageList(uid: String): LiveData<List<ChatMessage>> {
        return chatMessageListRepository.getMessageFromFirebase(uid)
    }
}