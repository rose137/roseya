package com.doc.roseya

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.doc.roseya.session.PrefManager
import com.doc.roseya.ui.like.LikeFragment
import com.doc.roseya.ui.login.LoginActivity
import com.doc.roseya.ui.profile.HomeFragment
import com.doc.roseya.ui.profile.ProfileFragment
import com.doc.roseya.ui.profile.SearchFragment
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){
    private lateinit var prefManager: PrefManager

    private val container by lazy { findViewById<View>(R.id.container) }
    private val menu by lazy { findViewById<ChipNavigationBar>(R.id.bottom_nav_bar) }
    var fragment = Fragment()
//    private val container by lazy { findViewById<View>(R.id.container) }
    private val title by lazy { findViewById<TextView>(R.id.title) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        prefManager = PrefManager(this)
//        private var lastColor: Int = 0
        openMainFragment()
        supportActionBar?.hide()
        val logout = findViewById<TextView>(R.id.logout)
        logout.setOnClickListener {
            clickLogout()
        }

        menu.setOnItemSelectedListener { id ->
            val option = when (id) {

                R.id.home ->{
                    openMainFragment()
//                    R.color.home to "Home"

                }
                R.id.like -> {
                    if (savedInstanceState == null) {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.container, LikeFragment.newInstance())
                            .commitNow()
                    } else {

                    }
//                    val likeFragment = LikeFragment()
//                     supportFragmentManager.beginTransaction()
//                        .replace(R.id.likeFragment, likeFragment).commit()
                }
                R.id.search ->{
                    if (savedInstanceState == null) {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.container, SearchFragment.newInstance())
                            .commitNow()
                    } else {

                    }

                }
                R.id.profile ->{
                    if (savedInstanceState == null) {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.container, ProfileFragment.newInstance())
                            .commitNow()
                    } else {

                    }

                }
                else -> R.color.white to ""
            }
//            val color = ContextCompat.getColor(this@MainActivity, option.first)
//            container.colorAnimation(lastColor, color)
//            lastColor = color

//            title.text = option.second
        }
        if (savedInstanceState == null) {
            menu.showBadge(R.id.home)
            menu.showBadge(R.id.profile, 32)
        }
    }

    private fun openMainFragment() {

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, HomeFragment.newInstance())
            .commitNow()
    }

    private fun clickLogout() {
        prefManager.removeData()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }



}