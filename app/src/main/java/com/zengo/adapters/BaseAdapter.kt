package com.zengo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

abstract class BaseAdapter : RecyclerView.Adapter<BaseViewHolder>() {
    val list = ArrayList<Any>()

    interface IBaseAdapterClickable {
        fun adapterItemClicked(item: Any, view: View? = null)
    }

    open fun viewHolder(i: Int, view: View): BaseViewHolder {
        throw IllegalStateException("Invalid layout")
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): BaseViewHolder {
        return viewHolder(i, inflateView(viewGroup, i))
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bindItems(list[position], position)
    }

    override fun getItemCount(): Int {
        return this.list.size
    }

    fun clearList() {
        this.list.clear()
        notifyDataSetChanged()
    }

    override fun onViewRecycled(baseViewHolder: BaseViewHolder) {
        super.onViewRecycled(baseViewHolder)
        baseViewHolder.destroy()
    }

    private fun inflateView(viewGroup: ViewGroup, i: Int): View {
        return LayoutInflater.from(viewGroup.context).inflate(i, viewGroup, false)
    }

    // Given a list, append or replace current list
    open fun update(listToAdd: Any, replace: Boolean = false) {
        if (replace)
            this.list.clear()

        this.list.addAll(listToAdd as List<Any>)

        notifyDataSetChanged()
    }

}
