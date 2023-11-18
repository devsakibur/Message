package com.example.message.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.message.R
import com.example.message.databinding.FragmentRegBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegFragment : Fragment() {
private lateinit var binding: FragmentRegBinding
    private val auth = FirebaseAuth.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegBinding.inflate(layoutInflater)


        binding.regBtn.setOnClickListener {

            val email = binding.userEmail.text.toString()
            val password = binding.password.text.toString()
            if (password == binding.ConformPassword.text.toString()){
                val username = binding.username.text.toString()
                registerUserAndStoreData(email , password  , username )
            }else{
                Toast.makeText(requireContext() , "password didn't match" , Toast.LENGTH_SHORT).show()
            }

        }

        binding.goToLoginBtn.setOnClickListener {
            findNavController().navigate(R.id.action_regFragment_to_loginFragment)
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)
        return binding.root
    }


    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            findNavController().navigateUp()
        }
    }


    private fun registerUserAndStoreData(email: String, password: String, username: String) {

        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("users")

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                val userId = user?.uid
                if (userId != null) {
                    val userData = mapOf(
                        "username" to username,
                        "email" to email,
                        "uid" to userId
                    )
                    reference.child(userId).setValue(userData)

                    findNavController().navigate(R.id.action_regFragment_to_userFragment)
                }

            } else {
                // Handle registration failure
            }
        }
    }






}