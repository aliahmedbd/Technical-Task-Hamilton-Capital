package com.hamiltoncapital.hamiltonpay.viewmodel

import android.content.Context
import android.os.SystemClock
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.hamiltoncapital.hamiltonpay.model.ConfigurationModel
import org.koin.java.KoinJavaComponent.inject
import java.util.*


private const val THIRTY_SECOND = 30

class CalculateAmountViewModel : ViewModel() {
    val context: Context by inject(Context::class.java)
    private val ONE_SECOND = 1000L
    private val THIRTY_SECOND = 30
    private val mElapsedTime = MutableLiveData<Long>()
    private var mInitialTime: Long = 0
    private var timer: Timer? = null

    init {
        mInitialTime = SystemClock.elapsedRealtime()
        timer = Timer()

        val myModel: ConfigurationModel =
            Gson().fromJson(readAssetsFile(), ConfigurationModel::class.java)
        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                val newValue =
                    myModel.timeout - (SystemClock.elapsedRealtime() - mInitialTime) / 1000
                mElapsedTime.postValue(newValue)
            }
        }, ONE_SECOND, ONE_SECOND)
    }

    fun getElapsedTime(): LiveData<Long?>? {
        return mElapsedTime
    }

    fun cancelTimer() {
        timer?.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

    private fun readAssetsFile(): String =
        context.assets.open("config.json").bufferedReader().use { it.readText() }
}
