package com.doc.roseya.ui.ilmuwan

import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.doc.roseya.R
import com.doc.roseya.model.IlmuwanModel

class DetailIlmuwanActivity : AppCompatActivity() {
     var EXTRA_KEY = "EXTRA_KEY"
//    var tvName: TextView? = null
//    var tvYear: TextView? = null
//    var tvDesc: TextView? = null
//    var imgPhoto: ImageView? = null
    private lateinit var tvName: TextView
    private lateinit var tvYear: TextView
    private lateinit var tvDesc: TextView
    private lateinit var imgPhoto: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_ilmuwan)


        val dataMain: IlmuwanModel? =
            intent.getParcelableExtra("EXTRA_KEY")
        val tbNotif = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(tbNotif)
        assert(supportActionBar != null)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Detail Ilmuan"
        tvName = findViewById(R.id.tv_name)
        tvYear = findViewById(R.id.tv_year)
        tvDesc = findViewById(R.id.tv_desc)
        imgPhoto = findViewById(R.id.img_photo)


        tvName.setText(dataMain?.getName().toString())
        tvYear.setText(dataMain?.getYear().toString())
        tvDesc.setText(dataMain?.getDesc().toString())


        Glide.with(this)
            .load(dataMain!!.getPhoto())
            .into(imgPhoto)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}