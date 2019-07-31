package com.zengo.views.fragments.dog

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
import com.zengo.models.api.dog.BreedListResponse
import com.zengo.models.dagger2.components.DaggerGenComponent
import com.zengo.models.livedata.ILiveDataResult
import com.zengo.models.livedata.LiveDataResultModel
import com.zengo.models.livedata.LiveDataResultStatus
import com.zengo.views.fragments.BaseFragment
import com.zengo.viewmodels.BreedsViewModel
import kotlinx.android.synthetic.main.list_fragment.*
import java.util.ArrayList
import javax.inject.Inject
import javax.inject.Named

class BreedsFragment : BaseFragment(), BaseAdapter.IBaseAdapterClickable
{
    @Inject @field:Named("breeds")
    public lateinit var adapter: GeneralPageAdapter

    private lateinit var vm: BreedsViewModel

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        vm = ViewModelProviders.of(activity!!).get(BreedsViewModel::class.java)

        vm.breedsLive?.observe(this, Observer(::observeBreeds))

        // inject dependencies into the fields
        val component = DaggerGenComponent.create()
        component.injection(this)

        adapter.clickable = this

        // now load the breeds
        vm.getBreeds()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val view1 = inflater.inflate(R.layout.list_fragment, container, false)

        val recyclerView = view1.findViewById<RecyclerView>(R.id.common_recycler_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this.activity!!, RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter

        return view1
    }

    private fun observeBreeds(response: LiveDataResultModel<ILiveDataResult>?) {

        when (response?.status)
        {
            LiveDataResultStatus.LOADING ->
            {
                breeds_textview_count?.visibility = View.VISIBLE
                breeds_textview_count?.text = "Loading breeds"
            }

            LiveDataResultStatus.SUCCESS ->
            {
                breeds_textview_count?.visibility = View.GONE

                val dogs = response.data as BreedListResponse

                val items = ArrayList<GeneralPageAdapter.ViewItemBase>()
                dogs.breeds?.forEach{
                    val selected = it.key
                    val separator = "/"

                    items.add(GeneralPageAdapter.ViewItemDogBreed(it.key, it.value.size, path = selected))

                    it.value.forEach {
                        items.add(GeneralPageAdapter.ViewItemDogSubBreed("$it", path ="$selected$separator$it" ))
                    }
                }

                items.let { adapter.update(it, replace = true) }
            }

            LiveDataResultStatus.ERROR ->
            {
                breeds_textview_count?.visibility = View.VISIBLE
                breeds_textview_count?.text = "Error loading breeds"
            }
        }
    }

    override fun adapterItemClicked(item: Any, view: View?)
    {
        if ( nc.currentDestination?.id == R.id.breedsFragment)
        {
            when (item)
            {
                is GeneralPageAdapter.ViewItemDogBreed ->
                {
                    // start loading image before we even move fragment
                    vm.getImages(item.path)

                    val action = BreedsFragmentDirections.actionBreedsFragmentToDetailFragment()
                    nc.navigate(action)
                }

                is GeneralPageAdapter.ViewItemDogSubBreed -> {
                    // start loading image before we even move fragment
                    vm.getImages(item.path)

                    val action = BreedsFragmentDirections.actionBreedsFragmentToDetailFragment()
                    nc.navigate(action)
                }
            }
        }
    }
}
