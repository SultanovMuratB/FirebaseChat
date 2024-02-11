package com.example.firebasechat.domain.chatMessage

import androidx.lifecycle.LiveData
import com.example.firebasechat.ChatMessage

internal class GetMessageListUseCase(private val chatMessageListRepository: ChatMessageListRepository) {

    fun getMessageList(uid: String): LiveData<List<ChatMessage>> {
        return chatMessageListRepository.getMessageFromFirebase(uid)
    }
}