package com.example.proyectofinal.entities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.example.proyectofinal.R

class MyFragment: DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView: View = inflater.inflate(R.layout.fragment_dialog, container, false)
        var cancelButton = rootView.findViewById<Button>(R.id.cancelBtn)
        var aceptButton = rootView.findViewById<Button>(R.id.aceptBtn)

        cancelButton.setOnClickListener {
            dismiss()
        }
        aceptButton.setOnClickListener {
            activity?.onBackPressed()
        }

        return rootView
    }
}