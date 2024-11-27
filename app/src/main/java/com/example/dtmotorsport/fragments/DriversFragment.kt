package com.example.dtmotorsport.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.gridlayout.widget.GridLayout
import com.example.dtmotorsport.R
import com.bumptech.glide.Glide

class DriversFragment : Fragment(R.layout.fragment_drivers) {

    private lateinit var driversGridLayout: GridLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the GridLayout
        driversGridLayout = view.findViewById(R.id.driversGridLayout)

        // Add driver cards to the GridLayout
        addDriverCards(getDtmDrivers())
    }

    // Static list of DTM drivers
    private fun getDtmDrivers(): List<Driver> {
        return listOf(
            Driver(
                "Kelvin van der Linde",
                "https://cdn-7.motorsport.com/images/mgl/0a98OZ40/s300/kelvin-van-der-linde-team-abt-.webp",
                "Team ABT",
                "https://cdn-1.motorsport.com/images/mgl/254WJOk0/s300/kelvin-van-der-linde-team-abt-.webp"
            ),
            Driver(
                "Luca Stolz",
                "https://cdn-5.motorsport.com/images/mgl/6gp975b0/s300/luca-stolz-mercedes-amg-team-h.webp",
                "Haupt Racing Team",
                "https://cdn-6.motorsport.com/images/mgl/6gp97bl0/s300/luca-stolz-mercedes-amg-team-h.webp"
            )
        )
    }

    // Add driver cards to the GridLayout
    private fun addDriverCards(drivers: List<Driver>) {
        for (driver in drivers) {
            val driverCardView = LayoutInflater.from(requireContext()).inflate(R.layout.item_driver_card, driversGridLayout, false)

            // Bind data to the card
            val driverPhoto: ImageView = driverCardView.findViewById(R.id.driverPhoto)
            val driverName: TextView = driverCardView.findViewById(R.id.driverName)
            val teamName: TextView = driverCardView.findViewById(R.id.teamName)

            driverName.text = driver.name
            teamName.text = driver.teamName

            // Use Glide to load the images (or you can load them from storage)
            Glide.with(driverPhoto.context).load(driver.photoUrl).into(driverPhoto)

            // Add the card to the GridLayout
            driversGridLayout.addView(driverCardView)
        }
    }

    // Driver data class
    data class Driver(
        val name: String,
        val photoUrl: String,
        val teamName: String,
        val teamLogoUrl: String
    )
}
