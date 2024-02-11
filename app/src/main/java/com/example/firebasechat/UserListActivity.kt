package com.example.firebasechat

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebasechat.databinding.ActivityUserListBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UserListActivity : AppCompatActivity(), LogInRecyclerAdapter.Listener {

    private lateinit var binding: ActivityUserListBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: FirebaseDatabase
    private val list = mutableListOf<User>()
    private val mAdapter = LogInRecyclerAdapter(list, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        val firebaseUser: FirebaseUser = FirebaseAuth.getInstance().currentUser!!
        databaseReference = FirebaseDatabase.getInstance()
        val db = databaseReference.getReference("users")

        db.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                if (snapshot.exists()) {
                    for (it in snapshot.children) {
                        val user = it.getValue(User::class.java)
                        if (!user?.userEmail.equals(firebaseUser.email)) {
                            list.add(user!!)
                        }
                    }
                    recyclerView.adapter = mAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@UserListActivity, error.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onClick(position: Int) {
        val intent = Intent(this, ChatActivity::class.java)
        intent.putExtra("userEmail", list[position].userEmail!!)
        intent.putExtra("userUid", list[position].userUid!!)
        startActivity(intent)
        finish()
    }
}