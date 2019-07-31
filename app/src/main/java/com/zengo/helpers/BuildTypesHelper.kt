package com.zengo.helpers

import com.zengo.views.BuildConfig

object BuildTypesHelper
{
    fun isDebug() : Boolean
    {
        return BuildConfig.DEBUG
    }
}