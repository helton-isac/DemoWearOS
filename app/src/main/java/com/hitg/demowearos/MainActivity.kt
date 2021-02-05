package com.hitg.demowearos

import MainMenuAdapter
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.wear.activity.ConfirmationActivity
import androidx.wear.widget.WearableLinearLayoutManager
import androidx.wear.widget.WearableRecyclerView
import com.google.android.gms.location.LocationServices

class MainActivity : WearableActivity() {

    private lateinit var wearableRecyclerView: WearableRecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Enables Always-on
        setAmbientEnabled()


        wearableRecyclerView = findViewById(R.id.wearableRecyclerView)

        wearableRecyclerView.apply {
            layoutManager = WearableLinearLayoutManager(this@MainActivity)

            val menuItems: ArrayList<MenuItem> = ArrayList()
            menuItems.add(MenuItem("Timer"))
            menuItems.add(MenuItem("Confirmação de Sucesso"))
            menuItems.add(MenuItem("Confirmação de Falha"))
            menuItems.add(MenuItem("Confirmação On Phone"))
            menuItems.add(MenuItem("Minha Posição"))
            menuItems.add(MenuItem("Chamada HTTP"))

            adapter = MainMenuAdapter(this@MainActivity, menuItems, object : MainMenuAdapter.AdapterCallback {
                override fun onItemClicked(menuPosition: Int?) {
                    when (menuPosition) {
                        0 -> timer()
                        1 -> successConfirmation()
                        2 -> failureConfirmation()
                        3 -> openOnPhoneConfirmation()
                        4 -> myPosition()
                    }
                }
            })
        }
    }

    private fun timer() {
        val intent = Intent(this, ConfirmScreenActivity::class.java)
        startActivity(intent)
    }

    fun showConfirmation(extra: String = "Mensagem Extra", type: Int) {
        val intent = Intent(this, ConfirmationActivity::class.java).apply {
            putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE, type)
            putExtra(ConfirmationActivity.EXTRA_MESSAGE, extra)
        }
        startActivity(intent)
    }

    fun successConfirmation(extra: String = "Mensagem Extra") {
        showConfirmation(extra, ConfirmationActivity.SUCCESS_ANIMATION)
    }

    fun failureConfirmation(extra: String = "Mensagem Extra") {
        showConfirmation(extra, ConfirmationActivity.FAILURE_ANIMATION)
    }

    fun openOnPhoneConfirmation(extra: String = "Mensagem Extra") {
        showConfirmation(extra, ConfirmationActivity.OPEN_ON_PHONE_ANIMATION)
    }

    fun myPosition() {
        if (hasGPS()) {
            if (ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED) {

                val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

                fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                    location?.let { location ->
                        val address =
                                Geocoder(this).getFromLocation(location.latitude, location.longitude, 1)[0]
                        Toast.makeText(this, address.getAddressLine(0), Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                requestPermissions(arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION
                ), 10)
            }
        } else {
            Toast.makeText(this, "GPS não encontrado", Toast.LENGTH_LONG).show()
        }
    }

    fun hasGPS(): Boolean = packageManager.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS)
}