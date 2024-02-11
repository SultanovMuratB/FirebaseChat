package com.example.firebasechat

data class ChatMessage(
    var receiver: String? = null,
    var sender: String? = null,
    var message: String? = null,
    var send: Boolean = false
)
