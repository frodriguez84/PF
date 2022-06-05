package com.example.proyectofinal.fragments


import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.proyectofinal.R
import com.example.proyectofinal.activities.MainActivity
import com.example.proyectofinal.entities.*
import com.example.proyectofinal.entities.UserRepository.ListDti
import com.example.proyectofinal.entities.UserRepository.ListDtiNombres
import com.example.proyectofinal.entities.UserRepository.userBeachSelect
import com.example.proyectofinal.entities.UserRepository.userLatitud
import com.example.proyectofinal.entities.UserRepository.userLongitud
import com.example.proyectofinal.viewmodels.HomeViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("DEPRECATION")
class HomeFragment : Fragment() {

    private lateinit var v: View
    private val vm: HomeViewModel by viewModels()

    private lateinit var bOut: Button
    private lateinit var bContacto: Button
    private lateinit var goBeachButton: Button

    var myFragment = MyFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) { vm.populateFavs() }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        v = inflater.inflate(R.layout.fragment_home, container, false)

        bContacto = v.findViewById(R.id.btnContacto)
        bOut = v.findViewById(R.id.btnOut)
        goBeachButton = v.findViewById(R.id.goBeachBtn)

        return v
    }

    override fun onStart() {
        super.onStart()
        val c = requireContext()



        if(userLatitud.isNotBlank() && userLongitud.isNotBlank()){
            vm.dtiCercano(v)
        }else{
            Toast.makeText(c, "Error: Active la geolocalizacion para ver el Dti mas cercano", Toast.LENGTH_SHORT).show()
        }

        vm.showDti(v, c)

        goBeachButton.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToBeachFragment(vm.getDtiDocument())
            v.findNavController().navigate(action)

        }

        //LOGOUT
        bOut.setOnClickListener {
            myFragment.show(requireActivity().supportFragmentManager, "hola")
        }

        bContacto.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToContactoFragment()
            v.findNavController().navigate(action)
        }

        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed(){
                myFragment.show(requireActivity().supportFragmentManager, "hola")
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)


    }

}




