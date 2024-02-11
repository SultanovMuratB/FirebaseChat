package com.example.firebasechat.domain.chatMessage

internal class AddMessageInFirebaseDatabase(
    private val chatMessageListRepository: ChatMessageListRepository
) {

    fun addMessageInFirebase(uid: String, textMessage: String) {
        chatMessageListRepository.addMessageInFirebaseDatabase(uid, textMessage)
    }
}