package com.zengo.adapters

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder(private val view: View) : RecyclerView.ViewHolder(view), View.OnClickListener
{
    @Throws(Exception::class)
    abstract fun bindItems(item: Any, position: Int)

    override fun onClick(view: View) {}

    init
    {
        view.setOnClickListener(this)
    }

    fun destroy() {
    }

    protected fun view(): View {
        return this.view
    }

    protected fun context(): Context {
        return this.view.context
    }
}