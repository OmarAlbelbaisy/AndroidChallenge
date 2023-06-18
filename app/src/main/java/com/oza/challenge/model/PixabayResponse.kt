package com.oza.challenge.model

import com.google.gson.annotations.SerializedName

data class PixabayResponse(

    @SerializedName("total") var total: Int,
    @SerializedName("totalHits") var totalHits: Int,
    @SerializedName("hits") var hits: ArrayList<ImageData>

)