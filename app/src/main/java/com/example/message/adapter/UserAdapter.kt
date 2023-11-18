package com.example.message.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.message.R
import com.example.message.model.User

class UserAdapter(private val userList : ArrayList<User>):RecyclerView.Adapter<UserAdapter.UserViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_users, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.userName.text = user.username

        holder.itemView.setOnClickListener{
            val bundle = Bundle()
            bundle.putSerializable("username" , user.username )
            bundle.putSerializable("uid" , user.uid )
            it.findNavController().navigate(R.id.action_userFragment_to_chatFragment , bundle)
        }

    }
    override fun getItemCount(): Int {
        return userList.size
    }


    class UserViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val userName: TextView = itemView.findViewById(R.id.userName)

    }



}


