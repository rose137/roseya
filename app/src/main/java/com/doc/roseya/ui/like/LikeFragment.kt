package com.doc.roseya.ui.like

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.doc.roseya.R

class LikeFragment : Fragment() {

    companion object {
        fun newInstance() = LikeFragment()
    }

    private lateinit var viewModel: LikeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LikeViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return inflater.inflate(R.layout.fragment_like_layout, container, false)
    }

}