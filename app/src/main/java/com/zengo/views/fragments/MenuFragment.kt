package com.zengo.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zengo.adapters.BaseAdapter
import com.zengo.views.R
import com.zengo.adapters.GeneralPageAdapter
import com.zengo.models.dagger2.components.DaggerGenComponent
import com.zengo.viewmodels.MenuViewModel
import javax.inject.Inject
import javax.inject.Named

class MenuFragment : BaseFragment(), BaseAdapter.IBaseAdapterClickable {

    @Inject @field:Named("menu")
    lateinit var adapter: GeneralPageAdapter

    private lateinit var vm: MenuViewModel

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        vm = ViewModelProviders.of(activity!!).get(MenuViewModel::class.java)

        vm.buttonActionsLive?.observe(this, Observer(::observeButtons))

        // inject dependencies into the fields
        val component = DaggerGenComponent.builder()
            .build()
        component.injection(this)

        adapter.clickable = this

        // now load the buttons
        vm.loadButtons()
    }

    private fun observeButtons(list: List<GeneralPageAdapter.ViewItemBase>?)
    {
        list?.let { adapter.update(it) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val view1 = inflater.inflate(R.layout.list_fragment, container, false)

        val recyclerView = view1.findViewById<RecyclerView>(R.id.common_recycler_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this.activity!!, RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter

        return view1
    }

    override fun adapterItemClicked(item: Any, view: View?)
    {
        if ( nc.currentDestination?.id == R.id.menuFragment)
        {
            when (item)
            {
                is GeneralPageAdapter.ViewItemButton ->
                {
                        val action = MenuFragmentDirections.actionMenuFragmentToBreedsFragment()
                        nc.navigate(action)
                }
            }
        }
    }
}
