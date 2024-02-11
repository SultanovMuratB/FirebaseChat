package com.example.firebasechat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import java.lang.RuntimeException

class ChatRecyclerAdapter :
    ListAdapter<ChatMessage, ChatRecyclerAdapter.ChatViewHolder>(MessageItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val layout = when (viewType) {
            VIEW_TYPE_SEND_MESSAGE -> R.layout.send_user_message
            VIEW_TYPE_RECEIVED_MESSAGE -> R.layout.receiver_user_message
            else -> throw RuntimeException("unknown view type $viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ChatViewHolder(view)
    }


    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.textView.text = getItem(position).toString()
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item.send) {
            VIEW_TYPE_SEND_MESSAGE
        } else {
            VIEW_TYPE_RECEIVED_MESSAGE
        }
    }

    inner class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textView: TextView = itemView.findViewById(R.id.user_message)
    }

    companion object {
        private const val VIEW_TYPE_SEND_MESSAGE = 0
        private const val VIEW_TYPE_RECEIVED_MESSAGE = 1
    }
}