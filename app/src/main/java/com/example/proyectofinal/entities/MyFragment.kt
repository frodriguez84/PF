package com.example.proyectofinal.entities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.proyectofinal.R
import com.example.proyectofinal.entities.UserRepository.userMailLogin
import com.example.proyectofinal.fragments.HomeFragment
import com.example.proyectofinal.fragments.HomeFragmentDirections
import com.example.proyectofinal.viewmodels.HomeViewModel
import com.google.firebase.auth.FirebaseAuth

class MyFragment: DialogFragment() {

    private val vm: HomeViewModel by viewModels()
    private lateinit var usuario : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView: View = inflater.inflate(R.layout.fragment_dialog, container, false)
        var cancelButton = rootView.findViewById<Button>(R.id.cancelBtn)
        var aceptButton = rootView.findViewById<Button>(R.id.aceptBtn)
        var action = HomeFragmentDirections.actionHomeFragmentToLoginFragment()
        usuario = rootView.findViewById(R.id.mailUs)
        usuario.text = userMailLogin

        cancelButton.setOnClickListener {
            dismiss()
        }
        aceptButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            cleanLogUser()
            //dismiss()
            onDestroyView()
            findNavController().navigate(action)
            //activity?.onBackPressed()



        }

        return rootView
    }

    private fun cleanLogUser(){
        userMailLogin = ""
    }

}