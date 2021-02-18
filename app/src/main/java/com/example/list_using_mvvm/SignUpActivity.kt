package com.example.list_using_mvvm

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val alreadyAct : TextView = findViewById(R.id.alreadyActBtn)
        alreadyAct.setOnClickListener {
            startActivity(Intent(this@SignUpActivity,SignInActivity::class.java))
        }

        val newName : EditText = findViewById(R.id.newName)
        val newEmail : EditText = findViewById(R.id.newEmail)
        val newPass : EditText = findViewById(R.id.newPassword)
        val newAge : EditText = findViewById(R.id.newAge)
        val newCity : EditText = findViewById(R.id.newCity)
        val newGender : EditText = findViewById(R.id.newGender)



        val signUpBtn : TextView = findViewById(R.id.submitBtn)
        signUpBtn.setOnClickListener {
            val nameText = newName.text.toString()
            val emailText = newEmail.text.toString()
            val passText = newPass.text.toString()
            val ageText = newAge.text.toString()
            val cityText = newCity.text.toString()
            val genderText = newGender.text.toString()
            when{
                TextUtils.isEmpty(nameText) -> newName.error = "Enter your name"
                TextUtils.isEmpty(emailText) -> newName.error = "Enter your email"
                TextUtils.isEmpty(passText) -> newName.error = "Enter your password"
                TextUtils.isEmpty(ageText) -> newName.error = "Enter your age"
                TextUtils.isEmpty(cityText) -> newName.error = "Enter your city"
                TextUtils.isEmpty(genderText) -> newName.error = "Enter your gender"

                else -> {
                    val progressDialog = ProgressDialog(this@SignUpActivity)
                    progressDialog.setTitle("Signup")
                    progressDialog.setTitle("Please Wait, this may take a while")
                    progressDialog.setCanceledOnTouchOutside(false)
                    progressDialog.show()

                    val mAuth = FirebaseAuth.getInstance()

                    mAuth.createUserWithEmailAndPassword(emailText,passText).addOnCompleteListener {
                        if (it.isSuccessful){
                            saveUserInfo(nameText,emailText,ageText,cityText,genderText,progressDialog)
                        }
                        else{
                            val message = it.result.toString()
                            Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
                            mAuth.signOut()
                            progressDialog.dismiss()
                        }
                    }
                }
            }
        }

    }

    private fun saveUserInfo(nameText: String, emailText: String, ageText: String, cityText: String, genderText: String, progressDialog: ProgressDialog) {
        val currentUserId = FirebaseAuth.getInstance().currentUser

        val userMap = HashMap<String,Any>()
        userMap["name"] = nameText
        userMap["email"] = emailText
        userMap["age"] = ageText
        userMap["city"] = cityText
        userMap["gender"] = genderText
        userMap["Id"] = currentUserId!!.uid

        val db = FirebaseDatabase.getInstance().reference

        db.child("Users").child(currentUserId.uid).setValue(userMap)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    progressDialog.dismiss()
                    Toast.makeText(this,"User Created",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@SignUpActivity,MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
                else{
                    val message = it.exception.toString()
                    Toast.makeText(this,message,Toast.LENGTH_LONG).show()
                    FirebaseAuth.getInstance().signOut()
                    progressDialog.dismiss()
                }
            }
    }
}