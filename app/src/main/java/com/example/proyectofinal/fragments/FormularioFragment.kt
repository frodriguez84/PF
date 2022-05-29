package com.example.proyectofinal.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.proyectofinal.R
import com.example.proyectofinal.viewmodels.FormularioViewModel

class FormularioFragment : Fragment() {

    private lateinit var v: View
    private val vm: FormularioViewModel by viewModels()
    private lateinit var bSave : Button
    private lateinit var bBack : Button
    private lateinit var userLog : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v =  inflater.inflate(R.layout.formulario_fragment, container, false)

        bSave = v.findViewById(R.id.btnGuardar)
        bBack = v.findViewById(R.id.btnAtras)
        userLog = v.findViewById(R.id.userLog)

        return v
    }

    override fun onStart() {
        super.onStart()

        bBack.setOnClickListener {
            activity?.onBackPressed()
        }

        bSave.setOnClickListener {
            Toast.makeText(requireContext(),"Los datos se han guardado", Toast.LENGTH_SHORT)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}