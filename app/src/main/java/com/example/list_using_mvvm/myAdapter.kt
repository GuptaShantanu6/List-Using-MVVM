package com.example.list_using_mvvm

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.core.text.bold
import androidx.recyclerview.widget.RecyclerView
import com.example.list_using_mvvm.myAdapter.ViewHolder
import com.google.android.material.internal.ContextUtils.getActivity
import org.w3c.dom.Text

class myAdapter(private var mContext : Context, private var isFragment : Boolean = false, private var mUser : List<User>)
    :RecyclerView.Adapter<ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_layout,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = mUser[position]
        holder.nameTextView.text = SpannableStringBuilder().append("Name: ").bold { append(user.getName()) }
        holder.ageTextView.text = SpannableStringBuilder().append("Age: ").bold { append(user.getAge()) }

        holder.nameTextView.setOnClickListener {
            val pref = mContext.getSharedPreferences("PREFS",Context.MODE_PRIVATE).edit()
            pref.apply{
                putString("id",user.getId())
                apply()
            }
            val intent = Intent(mContext,userInformationActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            mContext.startActivity(intent)

        }


    }

    override fun getItemCount(): Int {
        return mUser.size
    }

    class ViewHolder(@NonNull itemView : View) : RecyclerView.ViewHolder(itemView){
        val nameTextView : TextView = itemView.findViewById(R.id.name)
        val ageTextView : TextView = itemView.findViewById(R.id.age)
    }
}