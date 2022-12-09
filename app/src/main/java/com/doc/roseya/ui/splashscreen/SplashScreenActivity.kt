package com.doc.roseya.ui.splashscreen

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.doc.roseya.R
import com.doc.roseya.ui.login.LoginActivity

class SplashScreenActivity : AppCompatActivity() {
    private val waktu_loading = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({ //setelah loading maka akan langsung berpindah ke home activity

            val home = Intent(this@SplashScreenActivity, LoginActivity::class.java)
            startActivity(home)
            finish()
        }, waktu_loading.toLong())


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ) {
            // When permission is granted
            // Call method
//            getCurrentLocation();
        } else {

            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION),
                100)
//            Toast.makeText(requireActivity(), "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }


}