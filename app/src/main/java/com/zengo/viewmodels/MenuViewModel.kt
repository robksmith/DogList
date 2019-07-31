package com.zengo.viewmodels

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.zengo.networking.ApiService
import com.zengo.views.DogListApplication
import com.zengo.adapters.GeneralPageAdapter
import java.util.ArrayList
import javax.inject.Inject

open class MenuViewModel constructor(app: Application) : AndroidViewModel(app)
{
    @Inject
    public lateinit var sp: SharedPreferences

    @Inject
    lateinit var api: ApiService

    var buttonActionsLive: MutableLiveData<List<GeneralPageAdapter.ViewItemBase>>? = MutableLiveData()
        private set

    init
    {
        // field injection
        (app as DogListApplication).appComponent.injection(this)
    }

    fun loadButtons()
    {
        val items = ArrayList<GeneralPageAdapter.ViewItemBase>()

        items.add(GeneralPageAdapter.ViewItemButton("Load list of dogs"))
        items.add(GeneralPageAdapter.ViewItemButton("Test button"))

        buttonActionsLive!!.value = items
    }
}