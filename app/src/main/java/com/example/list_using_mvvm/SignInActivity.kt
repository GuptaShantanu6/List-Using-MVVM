package com.example.list_using_mvvm

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val newUserBtn : TextView = findViewById(R.id.newUserBtn)
        newUserBtn.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
        }

        val email : TextView = findViewById(R.id.email)
        val password : TextView = findViewById(R.id.password)

        val signInBtn : Button = findViewById(R.id.logInBtn)
        signInBtn.setOnClickListener {
            val emailText : String = email.text.toString()
            val passwordText : String = password.text.toString()
            if (emailText.isBlank() || passwordText.isBlank()){
                Toast.makeText(this,"Please enter all the fields",Toast.LENGTH_SHORT).show()
            }
            else{
                val progressDialog = ProgressDialog(this@SignInActivity)
                progressDialog.setTitle("Sign In")
                progressDialog.setTitle("Hang on a Moment")
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()

                val mAuth : FirebaseAuth = FirebaseAuth.getInstance()

                mAuth.signInWithEmailAndPassword(emailText,passwordText).addOnCompleteListener {
                    if (it.isSuccessful){
                        progressDialog.dismiss()
                        val intent = Intent(this@SignInActivity,MainActivity::class.java)
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

    }

    override fun onStart() {
        super.onStart()
        if (FirebaseAuth.getInstance().currentUser != null){
            val intent = Intent(this@SignInActivity,MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

    }
}