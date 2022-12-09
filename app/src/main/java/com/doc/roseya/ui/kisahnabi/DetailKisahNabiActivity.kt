package com.doc.roseya.ui.kisahnabi

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.doc.roseya.R
import com.doc.roseya.model.kisahnabi.KisahNabiModelMain
import com.google.android.material.appbar.CollapsingToolbarLayout

class DetailKisahNabiActivity : AppCompatActivity() {
     lateinit var kisahNabiModelMain : KisahNabiModelMain
    var tbDetail: Toolbar? = null
    lateinit var dtlName : String
    lateinit var dtlThn : String
    lateinit var dtlUsia : String
    lateinit var dtlDesc : String
    lateinit var dtlTmp : String
    lateinit var tvThn : TextView
    lateinit var tvUsia : TextView
    lateinit var tvDesc : TextView
    lateinit var tvTmp : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kisahnabi_detail)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        //make fully Android Transparent Status bar
        //make fully Android Transparent Status bar
        if (Build.VERSION.SDK_INT >= 21) {
         setWindowFlag(this,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                false)
            window.statusBarColor = Color.TRANSPARENT
        }
        tbDetail = findViewById(R.id.tbDetail)
        setSupportActionBar(tbDetail)
        assert(supportActionBar != null)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        kisahNabiModelMain = intent.getSerializableExtra("paramDtl") as KisahNabiModelMain
        val progressBar = findViewById<ProgressBar>(R.id.progress)
        val imgHeader = findViewById<ImageView>(R.id.cover)

        Glide.with(this)
            .load(kisahNabiModelMain.image_url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .listener(object : RequestListener<Drawable> {
                @SuppressLint("CheckResult")
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean,
                ): Boolean {
                    progressBar.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean,
                ): Boolean {
                    progressBar.visibility = View.GONE
                    return false
                }

            })
            .into(imgHeader)

        val collapsingToolbarLayout = findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout)

        if(kisahNabiModelMain != null){
            dtlName = kisahNabiModelMain.name
            dtlThn = kisahNabiModelMain.thn_kelahiran
            dtlUsia = kisahNabiModelMain.usia
            dtlDesc = kisahNabiModelMain.description
            dtlTmp = kisahNabiModelMain.tmp

            collapsingToolbarLayout.title = kisahNabiModelMain.name

            tvTmp = findViewById(R.id.tmp)
            tvTmp.text = dtlTmp

            tvThn = findViewById(R.id.thn)
            tvThn.text = dtlThn

            tvUsia = findViewById(R.id.usia)
            tvUsia.text = dtlUsia

            tvDesc = findViewById(R.id.description)
            tvDesc.text = dtlDesc
        }
    }

    private fun setWindowFlag(activity: Activity, bits: Int, on: Boolean) {
        val win = activity.window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}