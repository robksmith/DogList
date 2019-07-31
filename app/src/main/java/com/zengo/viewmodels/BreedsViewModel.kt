package com.zengo.viewmodels

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.zengo.models.api.dog.BreedListResponse
import com.zengo.models.api.dog.ImagesListResponse
import com.zengo.networking.ApiService
import com.zengo.views.DogListApplication
import com.zengo.models.livedata.ILiveDataResult
import com.zengo.models.livedata.LiveDataResultModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

open class BreedsViewModel constructor(app: Application) : AndroidViewModel(app)
{
    @Inject
    public lateinit var sp: SharedPreferences

    @Inject
    lateinit var api: ApiService

    var breedsLive: MutableLiveData<LiveDataResultModel<ILiveDataResult>?>? = MutableLiveData()
        private set

    var imagesLive: MutableLiveData<LiveDataResultModel<ILiveDataResult>?>? = MutableLiveData()
        private set

    init
    {
        // field injection
        (app as DogListApplication).appComponent.injection(this)
    }

    fun getBreeds()
    {
        breedsLive?.value = LiveDataResultModel.loading(null, 0)

        val resultObs = api.breedsAllGET()
        val disposable = resultObs.subscribeOn(Schedulers.newThread())?.
            observeOn(AndroidSchedulers.mainThread())?.
            subscribe({ model -> successBreeds(model) }, { error -> failureBreeds(error) })
    }

    private fun successBreeds(model: BreedListResponse) {
        breedsLive?.value = LiveDataResultModel.success(model, 0)
    }

    private fun failureBreeds(error:Throwable)
    {
        breedsLive?.value = LiveDataResultModel.error("", null, 0)
    }


    fun getImages(breed:String)
    {
        imagesLive?.value = LiveDataResultModel.loading(null, 0)

        val resultObs = api.imagesGET(breed)
        val disposable = resultObs.subscribeOn(Schedulers.newThread())?.
            observeOn(AndroidSchedulers.mainThread())?.
            subscribe({ model -> successImages(model) }, { error -> failureImages(error) })
    }

    private fun successImages(model: ImagesListResponse) {
        imagesLive?.value = LiveDataResultModel.success(model, 0)
    }

    private fun failureImages(error:Throwable)
    {
        imagesLive?.value = LiveDataResultModel.error("", null, 0)
    }
}