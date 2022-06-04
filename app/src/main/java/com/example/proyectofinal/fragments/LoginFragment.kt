package com.example.proyectofinal.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.proyectofinal.R
import com.example.proyectofinal.entities.Favoritos

import com.example.proyectofinal.entities.UserRepository.userMailLogin
import com.example.proyectofinal.viewmodels.LoginViewModel

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Suppress("DEPRECATION")
class LoginFragment : Fragment() {

    private val vm: LoginViewModel by viewModels()
    private lateinit var btnLog : Button
    private lateinit var btnReg : Button
    private lateinit var btnRecu : Button
    private lateinit var v : View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         v = inflater.inflate(R.layout.fragment_login, container, false)

        btnLog = v.findViewById(R.id.logBtn)
        btnReg = v.findViewById(R.id.regBtn)
        btnRecu = v.findViewById(R.id.btnRecuMail)

        return v
    }

    override fun onStart() {
        super.onStart()

        val c = requireContext()
        btnReg.setOnClickListener {vm.register(v,c) }
        btnLog.setOnClickListener { vm.login(v, c) }

        btnRecu.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRecuMailFragment()
            v.findNavController().navigate(action)
        }
    }
}