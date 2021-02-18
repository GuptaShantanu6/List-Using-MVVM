package com.example.list_using_mvvm

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.bold
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class userInformationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_information)

        supportActionBar?.hide()

        val pref = baseContext.getSharedPreferences("PREFS",Context.MODE_PRIVATE)
        val userId = pref.getString("id","none").toString()

        val name : TextView = findViewById(R.id.infoName)
        val age : TextView = findViewById(R.id.infoAge)
        val city : TextView = findViewById(R.id.infoCity)
        val gender : TextView = findViewById(R.id.infoGender)
        val email : TextView = findViewById(R.id.infoEmail)

        FirebaseDatabase.getInstance().reference.child("Users").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                name.text = SpannableStringBuilder().bold { append("Name: ") }.append(snapshot.child(userId).child("name").value.toString())
                age.text = SpannableStringBuilder().bold { append("Age: ") }.append(snapshot.child(userId).child("age").value.toString())
                city.text = SpannableStringBuilder().bold { append("City: ") }.append(snapshot.child(userId).child("city").value.toString())
                gender.text = SpannableStringBuilder().bold { append("Gender: ") }.append(snapshot.child(userId).child("gender").value.toString())
                email.text = SpannableStringBuilder().bold { append("Email: ") }.append(snapshot.child(userId).child("email").value.toString())
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        val closeBtn : ImageView = findViewById(R.id.backBtn)
        closeBtn.setOnClickListener {
            startActivity(Intent(baseContext,MainActivity::class.java))
        }
    }
}