package com.example.firebasechat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LogInRecyclerAdapter(
    private val userList: List<User>,
    private val listener: Listener
) : RecyclerView.Adapter<LogInRecyclerAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.users_recycler_item, parent, false)
        return UserViewHolder(itemView)
    }

    override fun getItemCount() = userList.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.textView.text = userList[position].userEmail.toString()
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textView: TextView = itemView.findViewById(R.id.user_contact)

        init {
            textView.setOnClickListener {
                listener.onClick(layoutPosition)
            }
        }
    }

    interface Listener {
        fun onClick(position: Int)
    }
}
