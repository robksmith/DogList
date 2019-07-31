package com.zengo.views.fragments.dog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zengo.views.R
import com.zengo.adapters.GeneralPageAdapter
import com.zengo.models.api.dog.ImagesListResponse
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

class DetailFragment : BaseFragment()
{
    @Inject @field:Named("detail")
    lateinit var adapter: GeneralPageAdapter

    private lateinit var vm: BreedsViewModel

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        vm = ViewModelProviders.of(activity!!).get(BreedsViewModel::class.java)

        vm.imagesLive?.observe(this, Observer(::observeImages))

        // inject dependencies into the fields
        val component = DaggerGenComponent.create()
        component.injection(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val view1 = inflater.inflate(R.layout.list_fragment, container, false)

        val recyclerView = view1.findViewById<RecyclerView>(R.id.common_recycler_recyclerview)
        recyclerView.layoutManager = GridLayoutManager(this.activity!!, 2)
        recyclerView.adapter = adapter

        return view1
    }

    private fun observeImages(response: LiveDataResultModel<ILiveDataResult>?) {

        when (response?.status)
        {
            LiveDataResultStatus.LOADING ->
            {
                adapter.clearList()

                breeds_textview_count?.visibility = View.VISIBLE
                breeds_textview_count?.text = "Loading images"
                common_recycler_recyclerview.visibility = View.INVISIBLE
            }

            LiveDataResultStatus.SUCCESS ->
            {
                breeds_textview_count?.visibility = View.GONE
                common_recycler_recyclerview.visibility = View.VISIBLE

                val dogs = response.data as ImagesListResponse

                val items = ArrayList<GeneralPageAdapter.ViewItemBase>()

                dogs.images?.forEach{
                    items.add(GeneralPageAdapter.ViewItemDogImage(it))
                }

                items.let { adapter.update(it, replace = true) }
            }

            LiveDataResultStatus.ERROR ->
            {
                adapter.clearList()

                breeds_textview_count?.visibility = View.VISIBLE
                breeds_textview_count?.text = "Error loading images"
                common_recycler_recyclerview.visibility = View.INVISIBLE
            }
        }
    }
}
