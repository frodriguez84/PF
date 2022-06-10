package com.example.proyectofinal.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView

import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.proyectofinal.R
import com.example.proyectofinal.entities.Geo
import com.example.proyectofinal.entities.UserRepository.ListDti
import com.example.proyectofinal.viewmodels.BeachViewModel


class BeachFragment : Fragment() {

    private lateinit var v: View

    private val vm: BeachViewModel by viewModels()

    private lateinit var idPlaya: String
    private lateinit var btnMap: ImageView
    private lateinit var bAddToFav: Button
    private lateinit var bRemoveFav: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        v = inflater.inflate(R.layout.fragment_beach, container, false)

        btnMap = v.findViewById(R.id.btnMap)
        bAddToFav = v.findViewById(R.id.btnAddFavoritos)
        bRemoveFav = v.findViewById(R.id.btnRemoveFavoritos)

        return v
    }

    override fun onStart() {
        super.onStart()

        idPlaya = BeachFragmentArgs.fromBundle(requireArguments()).dti

        val playa = ListDti[idPlaya.toInt()]
        val lat = playa.location.coordinates[0]
        val long = playa.location.coordinates[1]
        //val geo = Geopoint(lat, long)
        val geo = Geo(lat.toString(), long.toString())
        val nombre = playa.name


        vm.showDataBeach(idPlaya, v)
        vm.showButtons(v, idPlaya)

        bAddToFav.setOnClickListener {

            if (!vm.esFavorito(idPlaya)) {
                //AGREGAR DTI A FAV
                vm.addFavotite(idPlaya)
                activity?.onBackPressed()
                vm.favAdded(v, requireContext())
            } else {
                vm.favInList(v, requireContext())
            }
        }

        bRemoveFav.setOnClickListener {

            if (vm.esFavorito(idPlaya)) {
                vm.removeFavorite(idPlaya)
                vm.favRemoved(v, requireContext())
                activity?.onBackPressed()
            } else {
                vm.dtiNotInList(v, requireContext())
            }
        }

        btnMap.setOnClickListener {

            var action = BeachFragmentDirections.actionBeachFragmentToMapsFragment(geo, nombre)
            v.findNavController().navigate(action)
        }

    }
}



