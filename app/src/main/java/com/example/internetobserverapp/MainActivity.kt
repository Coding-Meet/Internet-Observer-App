package com.example.internetobserverapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    val networkConnectivityObserver : NetworkConnectivityObserver by lazy {
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
        ).setAction("Setting"){
            startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
        }

        lifecycleScope.launch{
            networkConnectivityObserver.observe().collect{
                when(it){
                    ConnectivityObserver.Status.Available -> {
                        internetImg.setImageResource(R.drawable.ava_signal)
                        internetTxt.text = "Internet is Available"
                        if (snackbar.isShown){
                            snackbar.dismiss()
                        }
                    }
                    ConnectivityObserver.Status.Unavailable ->{
                        internetImg.setImageResource(R.drawable.no_signal)
                        internetTxt.text = "No Internet Connection"
                        snackbar.show()
                    }
                }
            }
        }

    }
}