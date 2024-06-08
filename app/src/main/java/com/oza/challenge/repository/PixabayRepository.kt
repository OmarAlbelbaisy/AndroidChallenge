package com.oza.challenge.repository

import com.oza.challenge.data.remote.PixabayService
import com.oza.challenge.repository.base.BaseRepository
import javax.inject.Inject

class PixabayRepository @Inject constructor(private val service: PixabayService) :
    BaseRepository() {

    suspend fun search(searchQuery: String) = apiCall { service.searchImages(searchQuery) }

}