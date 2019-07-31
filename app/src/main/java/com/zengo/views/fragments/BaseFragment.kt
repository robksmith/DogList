package com.zengo.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

abstract class BaseFragment : Fragment()
{
    protected lateinit var nc : NavController

    override fun onStart()
    {
        super.onStart()

        nc = NavHostFragment.findNavController(this)
    }
}