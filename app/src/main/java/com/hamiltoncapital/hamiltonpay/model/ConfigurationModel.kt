package com.hamiltoncapital.hamiltonpay.model

import com.google.gson.annotations.SerializedName

data class ConfigurationModel(@SerializedName("timeout") val timeout: Int)
