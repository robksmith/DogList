package com.zengo.models.api.dog

import com.google.gson.annotations.SerializedName
import com.zengo.models.livedata.ILiveDataResult

class BreedListResponse: ILiveDataResult {

    @SerializedName("status")
    var status: String? = null

    @SerializedName("message")
    var breeds: Map<String, Array<String>>? = null
}

class ImagesListResponse: ILiveDataResult {

    @SerializedName("status")
    var status: String? = null

    @SerializedName("message")
    var images: List<String>? = null
}