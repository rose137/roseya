package com.doc.roseya.ui.like

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.doc.roseya.R

class LikeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_like_layout)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, LikeFragment.newInstance())
                .commitNow()
        }
    }
}