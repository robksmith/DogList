package com.zengo.models.dagger2.modules

import com.zengo.adapters.GeneralPageAdapter
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class AdapterModule constructor() {

    @Provides
    @Named("menu")
    fun provideMenuAdapter() : GeneralPageAdapter
    {
        return GeneralPageAdapter()
    }

    @Provides
    @Named("breeds")
    fun provideBreedsAdapter() : GeneralPageAdapter
    {
        return GeneralPageAdapter()
    }

    @Provides
    @Named("detail")
    fun provideDetailsAdapter() : GeneralPageAdapter
    {
        return GeneralPageAdapter()
    }
}