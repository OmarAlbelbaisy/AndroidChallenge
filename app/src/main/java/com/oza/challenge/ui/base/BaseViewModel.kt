package com.oza.challenge.ui.base

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oza.challenge.App
import com.oza.challenge.BuildConfig
import com.oza.challenge.common.Resource
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 *  It is a base ViewModel for other ViewModels in the app. It extends the Android ViewModel class
 *  and utilizes the Kotlin Coroutines library for background tasks.
 *
 *  It contain common functionalities that are reused across different ViewModels.
 *  And the following properties:
 *  - tokenExpired: a SingleLiveEvent<Boolean> instance that is used to notify the app when the
 *      user's token has expired
 *  - isError: a SingleLiveEvent<Int?> instance that is used to notify the app of any errors that
 *      occur during a network call
 *  - isProgress: a SingleLiveEvent<Boolean> instance that is used to show or hide a progress dialog
 *      when a network call is in progress
 */
open class BaseViewModel : ViewModel() {

    val tokenExpired = MutableLiveData<Boolean>()
    val progressLiveData = MutableLiveData<Boolean>()

    /**
     * method that is used to handle network requests in a safe and consistent way.
     * This method takes a suspend function that returns a Resource<T> object as its parameter.
     * It also takes two optional SingleLiveEvent objects that are used to post the result of
     * the network request to the UI thread and a service name string that is used to display
     * an error toast if the network request fails.
     *
     * If the network request is successful, the handleResource() method posts the result to
     * the liveData object.
     * If the network request fails, the method posts an error code to the errorLiveData object
     * and displays an error toast if the error code is not a 403 (which indicates an expired token).
     * If the error code is a 403, the method sets the tokenExpired property to true.
     */
    fun <T> handleResource(
        resource: suspend () -> Resource<T>,
        responseLiveData: MutableLiveData<T>? = null,
        errorLiveData: MutableLiveData<String>? = null,
        service: String? = null
    ) {
        progressLiveData.postValue(true)
        viewModelScope.launch {
            when (val result = resource.invoke()) {
                is Resource.Success -> {
                    progressLiveData.postValue(false)
                    responseLiveData?.postValue(result.data)
                }
                else -> {
                    progressLiveData.postValue(false)
                    val error = (result as Resource.Failure)
                    errorLiveData?.postValue(result.errorMessage)
                    Timber.e("ERROR:: " + result.errorCode.toString() + " " + result.errorMessage)
                    if (error.errorCode == 403) {
                        tokenExpired.postValue(true)
                    }
                    if (BuildConfig.DEBUG) {
                        val errorCode = result.errorCode
                        service?.let {
                            Toast.makeText(
                                App.getAppContext().applicationContext,
                                "ERROR:: $service , $errorCode " + result.errorMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

}