package com.example.proyectofinal.fragments


import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.proyectofinal.R
import com.example.proyectofinal.entities.*
import com.example.proyectofinal.entities.UserRepository.dtiDocument
import com.example.proyectofinal.entities.UserRepository.userLatitud
import com.example.proyectofinal.entities.UserRepository.userLongitud
import com.example.proyectofinal.viewmodels.HomeViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.*

@Suppress("DEPRECATION")
class HomeFragment : Fragment() {

    private lateinit var v: View
    private val vm: HomeViewModel by viewModels()

    private lateinit var bOut: ImageView
    private lateinit var bContacto: Button
    private lateinit var goBeachButton: Button
    private val MSG_GEO : String = "Error: Active la geolocalizacion para ver el destino mas cercano"
    private val TIME_OUT : String = "No se ha podido acceder al servidor , Intentelo mas tarde"

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
        bOut = v.findViewById(R.id.btnOut)
        goBeachButton = v.findViewById(R.id.goBeachBtn)

        return v
    }

    override fun onStart() {
        super.onStart()
        if(UserRepository.ListDti.isNotEmpty()) {
            if (!userLatitud.isBlank() && !userLongitud.isBlank()) {
                //Nos va a mostrar el DTI que se encuentra mas cerca a nuestra posicion por Geolocalizacion
                vm.dtiCercano(v)
            } else {
                vm.showData(dtiDocument.toInt(), v)
                Toast.makeText(context, MSG_GEO, Toast.LENGTH_SHORT)
                    .show()
            }

            vm.showDti(v, requireContext())

        } else {

            AlertDialog.Builder(requireContext())
                .setMessage(TIME_OUT)
                .setCancelable(false)
                .setPositiveButton("Aceptar") { dialog, whichButton ->
                    FirebaseAuth.getInstance().signOut()
                    vm.cleanLogUser()
                    activity?.finish()

                }
                .setNegativeButton("Contacto") { dialog, whichButton ->
                    val action = HomeFragmentDirections.actionHomeFragmentToContactoFragment()
                    v.findNavController().navigate(action)
                }
                .show()
        }

        goBeachButton.setOnClickListener {

            val action = HomeFragmentDirections.actionHomeFragmentToBeachFragment(dtiDocument)
            v.findNavController().navigate(action)
        }

        bOut.setOnClickListener {

            vm.dialog(requireContext(),requireActivity())
        }

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            vm.dialog(requireContext() , requireActivity())
        }

    }

}




