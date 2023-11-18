package com.example.message.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.message.R
import com.example.message.adapter.UserAdapter
import com.example.message.databinding.FragmentUserBinding
import com.example.message.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UserFragment : Fragment() {
private lateinit var binding: FragmentUserBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserBinding.inflate(layoutInflater)

        val auth = FirebaseAuth.getInstance()
        val database = FirebaseDatabase.getInstance().reference

        val users = ArrayList<User>()

        val adapter = UserAdapter(users)
        binding.userRecyclerView.adapter = adapter


        database.child("users").addValueEventListener( object : ValueEventListener{
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {

                users.clear()
                for (snap in snapshot.children){
                    val currentUser = snap.getValue(User::class.java)
                    if(currentUser?.uid != auth.currentUser?.uid ){
                        users.add(currentUser!!)
                    }

                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {}
        })


        binding.setting.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Alert!")
            builder.setMessage("Do you want to Logout")
            builder.setIcon(android.R.drawable.ic_dialog_alert)
            builder.setPositiveButton("Yes"){dialogInterface, which ->
                auth.signOut()
                findNavController().navigate(R.id.action_userFragment_to_loginFragment)
            }
            builder.setNegativeButton("No"){dialogInterface, which ->
            }
            // Create the AlertDialog
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()

        }


        return binding.root
    }

}