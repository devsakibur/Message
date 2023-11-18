package com.example.message.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.example.message.R
import com.example.message.adapter.ChatAdapter
import com.example.message.databinding.FragmentChatBinding
import com.example.message.model.Message
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class ChatFragment : Fragment() {
private lateinit var binding: FragmentChatBinding
    lateinit var adapter: ChatAdapter
    var chatList = ArrayList<Message>()



    var senderRoom :String? = null
    var receiverRoom :String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(layoutInflater)

        val username = requireArguments().getString("username")
        val receiverUid = requireArguments().getString("uid")
        val senderUid = FirebaseAuth.getInstance().currentUser?.uid
        val databaseReference = FirebaseDatabase.getInstance().reference

        binding.chatName.text = username.toString()

        senderRoom = receiverUid + senderUid
        receiverRoom = senderUid + receiverUid


        adapter = ChatAdapter(chatList)
        binding.chatRCV.adapter = adapter


        //show msg
        databaseReference.child("chats").child(senderRoom!!).child("messages").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                    chatList.clear()
                for (snap in snapshot.children){
                    val chats = snap.getValue(Message::class.java)
                    if (chats != null) {
                        chatList.add(chats)
                    }
                }
                adapter.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })





        binding.mgsSendBtn.setOnClickListener{

            val message = binding.textInputEdt.text.toString()
            val messageObject = Message(message , senderUid)


            databaseReference.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    databaseReference.child("chats").child(receiverRoom!!).child("messages").push()
                        .setValue(messageObject)
                }
            binding.textInputEdt.text.clear()
        }





        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)

        return binding.root
    }


    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            findNavController().navigateUp()
        }
    }

}