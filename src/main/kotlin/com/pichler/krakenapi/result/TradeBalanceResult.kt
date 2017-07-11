package com.pichler.krakenapi.result

import com.google.gson.annotations.SerializedName

/**
 * Created by Patrick Pichler on 09-Jul-17.
 */
data class TradeBalanceResult(
    @SerializedName("eb") val equivalentBalance: Double,
    @SerializedName("tb") val tradeBalance: Double,
    @SerializedName("m") val marginAmountOpenPositions: Double,
    @SerializedName("n") val unrealizedNetProfitLossOfOpenPositions: Double,
    @SerializedName("c") val costBasisOpenPositions: Double,
    @SerializedName("v") val currentFloatingValuationOfOpenPositions: Double,
    @SerializedName("e") val equity: Double,
    @SerializedName("mf") val freeMargin: Double,
    @SerializedName("ml") val marginLevel: Double
)