package com.hamiltoncapital.hamiltonpay.viewmodel

import android.os.SystemClock
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

private const val THIRTY_SECOND = 30

class CalculateAmountViewModel : ViewModel() {
    private val ONE_SECOND = 1000L
    private val THIRTY_SECOND = 30
    private val mElapsedTime = MutableLiveData<Long>()
    private var mInitialTime: Long = 0
    private var timer: Timer? = null

    init {
        mInitialTime = SystemClock.elapsedRealtime()
        timer = Timer()

        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                val newValue = THIRTY_SECOND - (SystemClock.elapsedRealtime() - mInitialTime) / 1000
                mElapsedTime.postValue(newValue)
            }
        }, ONE_SECOND, ONE_SECOND)
    }

    fun getElapsedTime(): LiveData<Long?>? {
        return mElapsedTime
    }

    fun cancelTimer(){
        timer?.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }
}
