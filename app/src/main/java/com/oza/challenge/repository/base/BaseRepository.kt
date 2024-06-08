package com.oza.challenge.repository.base

import com.oza.challenge.App
import com.oza.challenge.R
import com.oza.challenge.common.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONException
import retrofit2.HttpException
import timber.log.Timber
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

open class BaseRepository {

    suspend fun <T> apiCall(apiCall: suspend () -> T): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        Timber.w(throwable.message())
                        Resource.Failure(throwable.code(), "" + throwable.message)
                    }

                    is UnknownHostException -> {
                        Resource.Failure(
                            0,
                            App.getAppContext().getString(R.string.error_no_internet)
                        )
                    }

                    is SocketTimeoutException -> {
                        Resource.Failure(
                            0,
                            App.getAppContext().getString(R.string.error_socket_timeout)
                        )
                    }

                    is SSLHandshakeException -> {
                        Resource.Failure(
                            0,
                            App.getAppContext().getString(R.string.error_ssl_handshake)
                        )
                    }

                    is JSONException -> {
                        Resource.Failure(
                            0,
                            App.getAppContext().getString(R.string.error_json_parsing)
                        )
                    }

                    else -> {
                        Timber.w("Error:: " + throwable.message)
                        Resource.Failure(-1, "Unknown Error")
                    }
                }
            }
        }
    }
}
