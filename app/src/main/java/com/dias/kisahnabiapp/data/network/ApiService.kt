package com.dias.kisahnabiapp.data.network

import com.dias.kisahnabiapp.data.KisahResponse
import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.GET

interface ApiService {
    @GET("kisahnabi")
    fun getKisahnabi(): Flowable<List<KisahResponse>>
}
