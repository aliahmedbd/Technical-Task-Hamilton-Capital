package com.hamiltoncapital.hamiltonpay.repository

import com.hamiltoncapital.hamiltonpay.di.NetworkModule
import com.hamiltoncapital.hamiltonpay.model.BaseResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class DataRepository(var networkModule: NetworkModule) {

    suspend fun getNewsFromNetwork(): Flow<Response<BaseResponse>> {
        return flow<Response<BaseResponse>> {
            val response = networkModule.sourceOfNetwork().getLatestCurrencies()
            emit(response)
        }
    }
}