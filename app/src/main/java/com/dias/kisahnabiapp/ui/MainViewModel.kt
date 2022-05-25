package com.dias.kisahnabiapp.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dias.kisahnabiapp.data.KisahResponse
import com.dias.kisahnabiapp.data.network.ApiClient
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

// Business logic for the app
class MainViewModel : ViewModel() {

    val kisahResponse = MutableLiveData<List<KisahResponse>>()
    val isLoading = MutableLiveData(true)
    val isError = MutableLiveData<Throwable>()

    /**
     * The example of higher order function in kotlin
     * so this function takes two function as parameters
     * and then passed to a function that needed it
     */
    private fun getKisahNabi(
        responseHandler: (List<KisahResponse>) -> Unit,
        errorHandler: (Throwable) -> Unit,
    ) {
        ApiClient.getApiService().getKisahnabi()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                responseHandler(it)
            }, {
                errorHandler(it)
            })
    }

    fun loadDataForView() {
        getKisahNabi({
            isLoading.value = false
            kisahResponse.value = it
        }, {
            Log.i("Error", it.message.toString())
            isLoading.value = false
            isError.value = it
        })
    }
}