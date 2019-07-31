package com.zengo.models.dagger2.components

import com.zengo.models.dagger2.modules.ApplicationModule
import com.zengo.models.dagger2.modules.NetworkModule
import com.zengo.views.DogListApplication
import com.zengo.viewmodels.BreedsViewModel
import com.zengo.viewmodels.MenuViewModel
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [ApplicationModule::class, NetworkModule::class])
interface ApplicationComponent {

    fun injection(target: DogListApplication)
    fun injection(target: MenuViewModel)
    fun injection(target: BreedsViewModel)
}
