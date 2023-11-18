package com.example.message.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.message.R
import com.example.message.databinding.FragmentLoginBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val auth = FirebaseAuth.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)

        FirebaseApp.initializeApp(requireContext())













        binding.loginBtn.setOnClickListener {
            if (binding.username.text.isNotEmpty() && binding.password.text.isNotEmpty()) {
                val email = binding.username.text.toString()
                val password = binding.password.text.toString()
                login(email, password)
            }else{
                Toast.makeText(requireContext() , "Provide Necessary Information" , Toast.LENGTH_SHORT).show()
            }


        }
        binding.goToRegBtn.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_regFragment)

        }



        return binding.root
    }

    private fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    findNavController().navigate(R.id.action_loginFragment_to_userFragment)
                } else {
                    Toast.makeText(requireContext() , "Invalid Credentials" , Toast.LENGTH_SHORT).show()
                }
            }
    }



}