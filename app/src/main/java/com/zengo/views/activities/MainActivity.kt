package com.zengo.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.zengo.views.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myNavHostFragment: NavHostFragment = activity_fragment_container as NavHostFragment
        val inflater = myNavHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.navigation_main)
        myNavHostFragment.navController.graph = graph
    }
}
