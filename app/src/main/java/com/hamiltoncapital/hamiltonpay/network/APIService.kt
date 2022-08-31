package com.hamiltoncapital.hamiltonpay.network

import com.hamiltoncapital.hamiltonpay.model.BaseResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface APIService {
    @GET(URL.GET_LATEST_CURRENCIES)
    suspend fun getLatestCurrencies(): Response<BaseResponse>

}