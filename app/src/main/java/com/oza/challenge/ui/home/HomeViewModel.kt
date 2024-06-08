package com.oza.challenge.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.oza.challenge.model.PixabayResponse
import com.oza.challenge.repository.PixabayRepository
import com.oza.challenge.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val pixabayRepository: PixabayRepository) :
    BaseViewModel() {

    private val _pixabayResponseLiveData = MutableLiveData<PixabayResponse>()
    val pixabayResponseLiveData: LiveData<PixabayResponse> = _pixabayResponseLiveData

    private val _pixabayResponseErrorLiveData = MutableLiveData<String>()
    val pixabayResponseErrorLiveData: LiveData<String> = _pixabayResponseErrorLiveData

    val searchQuery = MutableLiveData<String>()
    val isEmpty = MutableLiveData<Boolean>()

    fun search() {
        handleResource(
            { pixabayRepository.search(searchQuery.value ?: "") },
            _pixabayResponseLiveData,
            _pixabayResponseErrorLiveData,
            "search"
        )
    }

}
