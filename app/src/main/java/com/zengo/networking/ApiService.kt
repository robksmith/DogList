package com.zengo.networking

import com.zengo.models.api.dog.BreedListResponse
import com.zengo.models.api.dog.ImagesListResponse
import io.reactivex.Observable
import retrofit2.http.*
import java.util.HashMap

interface ApiService
{
    @GET("breeds/list/all")
    fun breedsAllGET(@HeaderMap headers: Map<String, String> = buildApiHeaders()): Observable<BreedListResponse>

    @GET("breed/{path}/images")
    fun imagesGET(@Path("path", encoded = true) path:String, @HeaderMap headers: Map<String, String> = buildApiHeaders()): Observable<ImagesListResponse>

    companion object ServiceGenerator {

        private fun buildApiHeaders(): HashMap<String, String>
        {
            val map = HashMap<String, String>()

            map["Content-Type"] = "application/json"
            map["Accept"] = "application/json"

            return map
        }
    }
}
