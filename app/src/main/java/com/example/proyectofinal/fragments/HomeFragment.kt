package com.example.proyectofinal.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.proyectofinal.R
import com.example.proyectofinal.entities.*
import com.example.proyectofinal.entities.UserRepository.userLatitud
import com.example.proyectofinal.entities.UserRepository.userLongitud
import com.example.proyectofinal.viewmodels.HomeViewModel
import kotlinx.coroutines.*

@Suppress("DEPRECATION")
class HomeFragment : Fragment() {

    private lateinit var v: View
    private val vm: HomeViewModel by viewModels()

    private lateinit var bOut: Button
    private lateinit var bContacto: Button
    private lateinit var goBeachButton: Button
    private val MSG_GEO : String = "Error: Active la geolocalizacion para ver el destino mas cercano"


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
            Toast.makeText(c, MSG_GEO, Toast.LENGTH_SHORT).show()
        }

        vm.showDti(v, c)

        //Mostrar Destino
        goBeachButton.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToBeachFragment(vm.getDtiDocument())
            v.findNavController().navigate(action)

        }

        //Logout
        bOut.setOnClickListener {
            myFragment.show(requireActivity().supportFragmentManager, "hola")
        }

        //Boton Contacto
        bContacto.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToContactoFragment()
            v.findNavController().navigate(action)
        }

        //Back Pressed
        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed(){
                myFragment.show(requireActivity().supportFragmentManager, "hola")
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

    }

}




