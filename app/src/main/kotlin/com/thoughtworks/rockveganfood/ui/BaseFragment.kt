package com.thoughtworks.rockveganfood.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import org.koin.core.KoinComponent

abstract class BaseFragment(@LayoutRes val layoutRes: Int) : Fragment(), KoinComponent {

    private var innerView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (innerView == null) {
            innerView = inflater.inflate(layoutRes, container, false)
        }

        return innerView
    }
}