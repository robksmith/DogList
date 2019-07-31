package com.zengo.models.dagger2.modules

import android.content.SharedPreferences
import com.zengo.helpers.Constants
import com.zengo.views.DogListApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/*
    ApplicationModule provides an Application with various singletons
*/

@Module
class ApplicationModule(private val app: DogListApplication)
{
    @Provides
    @Singleton
    fun provideApp(): DogListApplication
    {
        return app
    }

    @Provides
    @Singleton
    internal fun provideSharedPreferences(): SharedPreferences
    {
        return app.getSharedPreferences(Constants.FilesAndDirs.FILENAME_STANDARD, android.content.Context.MODE_PRIVATE)
    }
}