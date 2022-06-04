package com.example.proyectofinal.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.proyectofinal.R
import com.example.proyectofinal.viewmodels.ContactoViewModel
import com.example.proyectofinal.viewmodels.HomeViewModel


class ContactoFragment : Fragment() {

    private val vm: ContactoViewModel by viewModels()
    private lateinit var btnCiudades : Button
    private lateinit var btnReddit : Button
    private lateinit var btnMail : Button

    private lateinit var consulta : TextView
    private lateinit var v : View

    private val url_ciudades = "https://ciudadesdelfuturo.org.ar/"
    private val url_reddti = "https://www.reddti-ar.com.ar/"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.contacto_fragment, container, false)

        btnCiudades = v.findViewById(R.id.btnCiudades)
        btnReddit = v.findViewById(R.id.btnReddit)
        btnMail = v.findViewById(R.id.btnSendMail)
        consulta = v.findViewById(R.id.consTextView)
        return v
    }

    override fun onStart() {
        super.onStart()

        btnCiudades.setOnClickListener {

            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url_ciudades)
            startActivity(i)

        }

        btnReddit.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url_reddti)
            startActivity(i)

        }

        btnMail.setOnClickListener {

            val mailto = "mailto:fernando.rodriguez84@yahoo.com.ar"

            val emailIntent = Intent(Intent.ACTION_SENDTO)
            emailIntent.data = Uri.parse(mailto)
            emailIntent.putExtra(Intent.EXTRA_SUBJECT ,"Consulta")
            emailIntent.putExtra(Intent.EXTRA_TEXT ,consulta.text.toString())

            startActivity(emailIntent)

        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}