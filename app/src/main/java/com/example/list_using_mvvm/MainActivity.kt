package com.example.list_using_mvvm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.internal.Objects
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    private var recyclerView : RecyclerView? = null
    private var mAdapter :myAdapter? = null
    private var mUser :MutableList<User>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView?.setHasFixedSize(true)

        val linerLayoutManager = LinearLayoutManager(baseContext)
        recyclerView!!.layoutManager = linerLayoutManager

        mUser = ArrayList()
        mAdapter = baseContext?.let { myAdapter(it,false,mUser as ArrayList<User>) }
        recyclerView?.adapter = mAdapter

        initiator()

        val signOut : TextView = findViewById(R.id.signOutBtn)
        signOut.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(baseContext,SignInActivity::class.java))
        }


    }

    private fun initiator() {
        val db = FirebaseDatabase.getInstance().reference.child("Users")
        db.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (x in snapshot.children){
                    val u = x.getValue(User::class.java)
                    if (u != null){
                        mUser?.add(u)
                    }
                }
                mAdapter?.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
}