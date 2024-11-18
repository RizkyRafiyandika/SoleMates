package com.example.project_mandiri.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.project_mandiri.MainActivity
import com.example.project_mandiri.R
import com.example.project_mandiri.databinding.FragmentAboutUsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class AboutUs : Fragment() {
    private var _binding: FragmentAboutUsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAboutUsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setting onClickListener to navigate to LoginPage
        binding.textLogin.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }

        // Initializing map fragment and setting up the map
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync { googleMap ->
            val position = LatLng(-6.20201, 106.78208)
            val markerOptions = MarkerOptions()
                .position(position)
                .title("JollyCat’s Store")

            googleMap.addMarker(markerOptions)
            val cameraUpdate = CameraUpdateFactory.newLatLng(position)
            googleMap.moveCamera(cameraUpdate)
            googleMap.moveCamera(CameraUpdateFactory.zoomTo(15.0f))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
