package com.example.firebasechat.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.firebasechat.ChatMessage
import com.example.firebasechat.domain.chatMessage.ChatMessageListRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

internal object ChatMessageListRepositoryImpl : ChatMessageListRepository {

    private val chatMessageLD = MutableLiveData<List<ChatMessage>>()
    private val chatMessageList = mutableListOf<ChatMessage>()
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance()
    override fun getMessageFromFirebase(uid: String): LiveData<List<ChatMessage>> {
        db.getReference("Chat").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                chatMessageList.clear()
                if (snapshot.exists()) {
                    for (snap in snapshot.children) {
                        val chatMessage = snap.getValue(ChatMessage::class.java)!!
                        if ((uid == chatMessage.receiver) && (auth.uid == chatMessage.sender)) {
                            chatMessage.send = true
                            chatMessageList.add(chatMessage)
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                throw RuntimeException("Unknown error $error")
            }
        })
        chatMessageLD.value = chatMessageList
        return chatMessageLD
    }

    override fun addMessageInFirebaseDatabase(uid: String, textMessage: String) {
        val hashMap = HashMap<String, Any>()
        hashMap["receiver"] = uid
        hashMap["sender"] = auth.uid ?: ""
        hashMap["message"] = textMessage
        hashMap["send"] = false
        val reference = db.reference
        reference.child("Chat").push().setValue(hashMap)
    }
}