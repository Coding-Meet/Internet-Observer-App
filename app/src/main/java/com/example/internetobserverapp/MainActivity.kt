package com.example.internetobserverapp

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private val networkConnectivityObserver: NetworkConnectivityObserver by lazy {
        NetworkConnectivityObserver(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val internetTxt = findViewById<TextView>(R.id.internetTxt)
        val internetImg = findViewById<ImageView>(R.id.internetImg)
        val rootView = findViewById<LinearLayout>(R.id.rootView)

        val snackbar = Snackbar.make(
            rootView,
            "No Internet Connection",
            Snackbar.LENGTH_INDEFINITE
        ).setAction("Wifi") {
            startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
        }

        networkConnectivityObserver.observe(this) {
            when (it) {
                Status.Available -> {
                    internetImg.setImageResource(R.drawable.ava_signal_cellular)
                    internetTxt.text = "Internet is Available"
                    if (snackbar.isShown) {
                        snackbar.dismiss()
                    }
                }
                else -> {
                    internetImg.setImageResource(R.drawable.no_signal_cellular)
                    internetTxt.text = "No Internet Connection"
                    snackbar.show()
                }
            }
        }

    }
}