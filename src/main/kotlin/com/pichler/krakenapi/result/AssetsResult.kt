package com.pichler.krakenapi.result

import com.google.gson.annotations.SerializedName

/**
 * Created by Patrick Pichler on 7/7/2017.
 */
data class AssetsResult(val aclass: String,
                        val altname: String,
                        val decimals: Int,
                        @SerializedName("display_decimals") val displayDecimals: Int)