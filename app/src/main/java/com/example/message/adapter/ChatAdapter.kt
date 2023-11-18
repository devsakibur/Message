package com.example.message.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.message.R
import com.example.message.model.Message
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class ChatAdapter(private val messageList:ArrayList<Message> )
    :RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM_SENT = 1
    val ITEM_RCV = 2


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == 1){
            val view = LayoutInflater.from(parent.context).inflate(R.layout.right_chat_ui , parent , false)
            return SendViewHolder(view)
        }else{
            val view = LayoutInflater.from(parent.context).inflate(R.layout.left_chat_ui , parent , false)
            return ReceiveViewHolder(view)
        }


    }



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMsg = messageList[position]


        if (holder.javaClass == SendViewHolder::class.java){
            val viewHolder = holder as SendViewHolder
            holder.sentMsg.text = currentMsg.msg

        }else{
            val viewHolder = holder as ReceiveViewHolder
            holder.rcvMgs.text = currentMsg.msg
        }



    }

    override fun getItemViewType(position: Int): Int {
        val currentMsg  = messageList[position]

        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMsg.senderId)){
            return ITEM_SENT
        }else{
            return ITEM_RCV
        }
    }


    override fun getItemCount(): Int {
        return messageList.size
    }



    class SendViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){

        val sentMsg = itemView.findViewById<TextView>(R.id.messagetxt)


    }
    class ReceiveViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val rcvMgs = itemView.findViewById<TextView>(R.id.messagetxt)
    }

}