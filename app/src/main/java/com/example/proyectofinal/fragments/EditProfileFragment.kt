@file:Suppress("DEPRECATION")

package com.example.proyectofinal.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import com.example.proyectofinal.R
import com.example.proyectofinal.viewmodels.EditProfileViewModel

class EditProfileFragment : Fragment() {

    private lateinit var v: View
    private val vm: EditProfileViewModel by viewModels()
    private lateinit var saveBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_edit_profile, container, false)

        saveBtn = v.findViewById(R.id.editBtn)

        return v
    }

    override fun onStart() {
        super.onStart()

        vm.showData(v)

        saveBtn.setOnClickListener {
            if (vm.saveData(v)) {
                vm.saveDataMessage(v, requireContext())
                activity?.onBackPressed()
            } else {
                vm.failSaveDataMessage(v, requireContext())
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
}