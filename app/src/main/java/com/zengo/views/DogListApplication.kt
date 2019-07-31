package com.zengo.views

import android.app.Application
import com.zengo.models.dagger2.components.ApplicationComponent
import com.zengo.models.dagger2.components.DaggerApplicationComponent
import com.zengo.models.dagger2.modules.ApplicationModule
import com.zengo.models.dagger2.modules.NetworkModule

class DogListApplication : Application()
{
    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerApplicationComponent.builder()
            .networkModule(NetworkModule(this))
            .applicationModule(ApplicationModule(this))
            .build()
        appComponent.injection(this)
    }
}