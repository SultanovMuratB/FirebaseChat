package com.example.firebasechat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebasechat.data.ChatMessageListRepositoryImpl
import com.example.firebasechat.domain.GetMessageListUseCase
import com.example.firebasechat.databinding.ActivityChatBinding
import com.example.firebasechat.domain.AddMessageInFirebaseDatabase

class ChatActivity : AppCompatActivity() {

    private val repository = ChatMessageListRepositoryImpl
    private val getShopListUseCase = GetMessageListUseCase(repository)
    private val addMessageInFirebaseDatabase = AddMessageInFirebaseDatabase(repository)

    private lateinit var binding: ActivityChatBinding
    private var chatList: LiveData<List<ChatMessage>>? = MutableLiveData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val uid = intent.getStringExtra("userUid")
        if (uid != null) {
            chatList = getShopListUseCase.getMessageList(uid)
        }
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        val chatRecyclerAdapter = ChatRecyclerAdapter()
        chatList?.observe(this) {
            chatRecyclerAdapter.submitList(it)
        }
        val sendButton = binding.sendButton
        sendButton.setOnClickListener {
            val textMessage = binding.messageInput.text.toString()
            if (uid != null) {
                addMessageInFirebaseDatabase.addMessageInFirebase(uid, textMessage)
            }
            binding.messageInput.text.clear()
        }
    }
}