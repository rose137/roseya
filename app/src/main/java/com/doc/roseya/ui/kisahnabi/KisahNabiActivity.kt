package com.doc.roseya.ui.kisahnabi

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.doc.roseya.MainActivity
import com.doc.roseya.R
import com.doc.roseya.adapter.kisahnabi.KisahNabiMainAdapter
import com.doc.roseya.model.kisahnabi.KisahNabiModelMain
import org.json.JSONArray
import org.json.JSONException
import java.io.IOException
import java.nio.charset.StandardCharsets


class KisahNabiActivity : AppCompatActivity(), KisahNabiMainAdapter.onSelectData {
    lateinit var rvName: RecyclerView
    var KisahNabiMainAdapter: KisahNabiMainAdapter? = null

//    var modelMain: List<KisahNabiModelMain> = ArrayList<KisahNabiModelMain>()
private var kisahNabiModelMain = ArrayList<KisahNabiModelMain>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kisah_nabi)


        rvName = findViewById(R.id.rvList)
        rvName.setHasFixedSize(true)
        rvName.layoutManager = LinearLayoutManager(this)
        setupToolbar()

        loadJSON()


    }

    private fun loadJSON() {
        try {
            val stream = assets.open("kisahnabi.json")
            val size = stream.available()
            val buffer = ByteArray(size)
            stream.read(buffer)
            stream.close()
            val tContents = String(buffer, StandardCharsets.UTF_8)
            try {
                val obj = JSONArray(tContents)
                for (i in 0 until obj.length()) {
                    val temp = obj.getJSONObject(i)
                    val dataApi = KisahNabiModelMain()
                    dataApi.setName(temp.getString("name"))
                    dataApi.setThn_kelahiran(temp.getString("thn_kelahiran"))
                    dataApi.setUsia(temp.getString("usia"))
                    dataApi.setDescription(temp.getString("description"))
                    dataApi.setTmp(temp.getString("tmp"))
                    dataApi.setImage_url(temp.getString("image_url"))
                    kisahNabiModelMain.add(dataApi)
                    KisahNabiMainAdapter = KisahNabiMainAdapter(this@KisahNabiActivity, kisahNabiModelMain, this)
                    rvName.adapter = KisahNabiMainAdapter
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        } catch (ignored: IOException) {
        }
    }

    private fun setupToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.tbMain)
        toolbar.title = "Kisah Nabi"
        setSupportActionBar(toolbar)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
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
    override fun onSelected(kisahNabiModelMain: KisahNabiModelMain) {
        val intnt = Intent(this@KisahNabiActivity, DetailKisahNabiActivity::class.java)
        intnt.putExtra("paramDtl", kisahNabiModelMain)
        startActivity(intnt)
    }
}