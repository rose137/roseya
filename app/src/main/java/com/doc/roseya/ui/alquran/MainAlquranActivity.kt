package com.doc.roseya.ui.alquran

import android.Manifest
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.text.format.DateFormat
import android.view.KeyEvent
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.doc.roseya.MainActivity
import com.doc.roseya.R
import com.doc.roseya.adapter.MainAdapter
import com.doc.roseya.fragment.FragmentJadwalSholat.Companion.newInstance
import com.doc.roseya.model.ModelSurah
import com.doc.roseya.ui.login.LoginActivity
import com.doc.roseya.ui.profile.HomeFragment
import com.doc.roseya.viewModel.SurahViewModel
import im.delight.android.location.SimpleLocation
import kotlinx.android.synthetic.main.activity_main_alquran.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.io.IOException
import java.util.*

class MainAlquranActivity : AppCompatActivity() {
    var REQ_PERMISSION = 100
    var strCurrentLatitude = 0.0
    var strCurrentLongitude = 0.0
    lateinit var strCurrentLatLong: String
    lateinit var strDate: String
    lateinit var strDateNow: String
    lateinit var simpleLocation: SimpleLocation
    lateinit var mainAdapter: MainAdapter
    lateinit var progressDialog: ProgressDialog
    lateinit var surahViewModel: SurahViewModel
    lateinit var progressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_alquran)
        progressBar = findViewById(R.id.pbLoading)
        setInitLayout()
        setPermission()
        setLocation()
        setCurrentLocation()
        setViewModel()
    }
    private fun setInitLayout() {
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Mohon Tunggu")
        progressDialog.setCancelable(false)
        progressDialog.setMessage("Sedang menampilkan data...")

        val dateNow = Calendar.getInstance().time
        strDate = DateFormat.format("EEEE", dateNow) as String
        strDateNow = DateFormat.format("d MMMM yyyy", dateNow) as String

        tvToday.setText("$strDate,")
        tvDate.setText(strDateNow)

        mainAdapter = MainAdapter(this)
        rvSurah.setHasFixedSize(true)
        rvSurah.setLayoutManager(LinearLayoutManager(this))
        rvSurah.setAdapter(mainAdapter)

//        val jadwalSholat = newInstance("Jadwal Sholat")
//        layoutTime.setOnClickListener {
//            jadwalSholat.show(
//                supportFragmentManager, jadwalSholat.tag
//            )
//        }

//        layoutMosque.setOnClickListener {
//            startActivity(
//                Intent(
//                    this@MainAlquranActivity,
//                    MasjidActivity::class.java
//                )
//            )
//        }
    }

    private fun setLocation() {
        simpleLocation = SimpleLocation(this)
        if (!simpleLocation.hasLocationEnabled()) {
            SimpleLocation.openSettings(this)
        }

        //get location
        strCurrentLatitude = simpleLocation.latitude
        strCurrentLongitude = simpleLocation.longitude

        //set location lat long
        strCurrentLatLong = "$strCurrentLatitude,$strCurrentLongitude"
    }

    private fun setCurrentLocation() {
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            val addressList = geocoder.getFromLocation(strCurrentLatitude, strCurrentLongitude, 1)
            if (addressList != null && addressList.size > 0) {
                val strCurrentLocation = addressList[0].locality
                tvLocation.text = strCurrentLocation
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun setViewModel() {
        progressBar.visibility = View.VISIBLE
        progressDialog.show()
        surahViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(SurahViewModel::class.java)
        surahViewModel.setSurah()
        surahViewModel.getSurah()
            .observe(this) { modelSurah: ArrayList<ModelSurah> ->
                if (modelSurah.size != 0) {
                    mainAdapter.setAdapter(modelSurah)
                    progressDialog.dismiss()
                } else {
                    Toast.makeText(this, "Data Tidak Ditemukan!", Toast.LENGTH_SHORT).show()
                    progressDialog.dismiss()
                }
                progressDialog.dismiss()
                progressBar.visibility = View.GONE
            }
    }

    private fun setPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQ_PERMISSION)
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        for (grantResult in grantResults) {
            if (grantResult == PackageManager.PERMISSION_GRANTED) {
                val intent = intent
                finish()
                startActivity(intent)
            }
        }
    }
    override fun onKeyDown(key_code: Int, key_event: KeyEvent?): Boolean {
        if (key_code == KeyEvent.KEYCODE_BACK) {
            super.onKeyDown(key_code, key_event)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
//             supportFragmentManager.beginTransaction()
//                .replace(R.id.container, HomeFragment.newInstance())
//                .commitNow()
//            return true
        }
        return false
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_PERMISSION && resultCode == RESULT_OK) {

            //load data
            setViewModel()
        }
    }
}