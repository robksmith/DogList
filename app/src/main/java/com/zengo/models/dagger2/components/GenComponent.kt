package com.zengo.models.dagger2.components

import com.zengo.models.dagger2.modules.AdapterModule
import com.zengo.views.fragments.MenuFragment
import com.zengo.views.fragments.dog.BreedsFragment
import com.zengo.views.fragments.dog.DetailFragment
import dagger.Component

@Component(modules = [AdapterModule::class])
interface GenComponent{

    fun injection(frag: MenuFragment)
    fun injection(frag: BreedsFragment)
    fun injection(frag: DetailFragment)
}
