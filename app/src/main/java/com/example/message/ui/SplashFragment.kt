package com.example.message.ui

import android.animation.Animator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.message.R
import com.example.message.databinding.FragmentSplashBinding
import com.google.firebase.auth.FirebaseAuth

class SplashFragment : Fragment() {
private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(layoutInflater)
        val auth = FirebaseAuth.getInstance()


binding.animationView.playAnimation()
        binding.animationView.addAnimatorListener(object :Animator.AnimatorListener{
            override fun onAnimationStart(animation: Animator) {

            }

            override fun onAnimationEnd(animation: Animator) {
                if (auth.currentUser != null){
                    findNavController().navigate(R.id.action_splashFragment_to_userFragment)
                }else{
                    findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
                }
            }

            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationRepeat(animation: Animator) {

            }
        })






        return binding.root
    }

}