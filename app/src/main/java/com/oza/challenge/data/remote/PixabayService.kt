package com.oza.challenge.data.remote

import com.oza.challenge.BuildConfig
import com.oza.challenge.model.PixabayResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface PixabayService {

    @GET("api/")
    suspend fun searchImages(
        @Query("q") query: String?,
        @Query("image_type") imageType: String = "photo",
        @Query("key") apiKey: String = BuildConfig.API_KEY,
        ): PixabayResponse

}
