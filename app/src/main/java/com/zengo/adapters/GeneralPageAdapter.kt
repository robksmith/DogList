package com.zengo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.zengo.views.R
import kotlinx.android.synthetic.main.view_dog_breed.view.*
import kotlinx.android.synthetic.main.view_dog_image.view.*
import kotlinx.android.synthetic.main.view_dog_sub_breed.view.*
import kotlinx.android.synthetic.main.view_selection_button.view.*
import javax.inject.Inject

class GeneralPageAdapter @Inject constructor() : BaseAdapter()
{
    var clickable: IBaseAdapterClickable? = null

    abstract class ViewItemBase(val type: ViewType)
    {
        enum class ViewType
        {
            SELECTION_BUTTON,
            DOG_BREED,
            DOG_SUBBREED,
            DOG_IMAGE
        }
    }

    data class ViewItemButton
        (val text: String?) : ViewItemBase(ViewType.SELECTION_BUTTON)

    data class ViewItemDogBreed
        (val text: String, val subBreedCount:Int, val path:String) : ViewItemBase(ViewType.DOG_BREED)

    data class ViewItemDogSubBreed
        (val text: String, val path:String) : ViewItemBase(ViewType.DOG_SUBBREED)

    data class ViewItemDogImage
        (val path:String) : ViewItemBase(ViewType.DOG_IMAGE)

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): BaseViewHolder
    {
        val v: View

        when (ViewItemBase.ViewType.values()[i])
        {
            ViewItemBase.ViewType.SELECTION_BUTTON -> v = LayoutInflater.from(viewGroup.context).inflate(R.layout.view_selection_button, viewGroup, false)
            ViewItemBase.ViewType.DOG_BREED -> v = LayoutInflater.from(viewGroup.context).inflate(R.layout.view_dog_breed, viewGroup, false)
            ViewItemBase.ViewType.DOG_SUBBREED -> v = LayoutInflater.from(viewGroup.context).inflate(R.layout.view_dog_sub_breed, viewGroup, false)
            ViewItemBase.ViewType.DOG_IMAGE -> v = LayoutInflater.from(viewGroup.context).inflate(R.layout.view_dog_image, viewGroup, false)
        }

        return ViewHolder(v)
    }

    override fun getItemViewType(position: Int): Int
    {
        val viewItemBaseList = list as List<ViewItemBase>
        return viewItemBaseList[position].type.ordinal
    }

    inner class ViewHolder(view: View) : BaseViewHolder(view) {

        override fun bindItems(item: Any, position: Int): Unit = with(itemView)
        {

            when (item)
            {
                is ViewItemButton ->
                {
                    constraint_button?.setOnClickListener { clickable?.adapterItemClicked(item) }
                    question_textview.text = item.text
                }

                is ViewItemDogBreed ->
                {
                    constraint_breed_button?.setOnClickListener { clickable?.adapterItemClicked(item) }
                    textview_breed.text = item.text
                    textview_count.text = "(${item.subBreedCount} sub breeds)"
                    textview_count.visibility = if ( item.subBreedCount > 0 ) View.VISIBLE else View.GONE
                }

                is ViewItemDogSubBreed ->
                {
                    constraint_subbreed_button?.setOnClickListener { clickable?.adapterItemClicked(item) }
                    textview_subbreed.text = item.text
                }

                is ViewItemDogImage ->
                {
                    Picasso.with(context).load(item.path).into(imageview_dog)
                }

                else ->
                {
                }
            }
        }
    }
}

