package com.example.weatherapp.ui.fragments.home

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.weatherapp.data.CurrentLocation
import com.example.weatherapp.databinding.FragmentHomeBinding
import com.example.weatherapp.ui.fragments.home.adapter.WeatherDataAdapter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val weatherDataAdapter = WeatherDataAdapter(
        onLocationClicked = { showLocationOptions() }
    )

    private val locationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {isGranted ->
        if (isGranted) {
            getCurrentLocation()
        } else {
            Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setWeatherDataAdapter()
        setWeatherData()
    }

    private fun setWeatherDataAdapter() {
        binding.weatherDataRecyclerView.adapter = weatherDataAdapter
    }

    private fun setWeatherData() {
        weatherDataAdapter.setData(data = listOf(CurrentLocation(date = getCurrentDate())))
    }

    private fun getCurrentDate(): String {
        val currentDate = Date()
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return "Today ${formatter.format(currentDate)}"
    }

    private fun getCurrentLocation() {
        Toast.makeText(requireContext(), "getCurrentLocation", Toast.LENGTH_SHORT).show()
    }

    private fun isLocationPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermission() {
        locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private fun proceedWithCurrentLocation() {
        if (isLocationPermissionGranted()) {
            getCurrentLocation()
        } else {
            requestLocationPermission()
        }
    }

    private fun showLocationOptions() {
        val options = arrayOf("Current Location", "Search Manually")
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Choose Location")
            setItems(options) { _, which ->
                when (which) {
                    0 -> proceedWithCurrentLocation()
                }
            }
            show()
        }
    }

}