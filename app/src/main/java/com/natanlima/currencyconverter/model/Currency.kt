package com.natanlima.currencyconverter.model

import androidx.annotation.DrawableRes

data class Currency(@DrawableRes val image: Int, val currency: String, val value: Double)
