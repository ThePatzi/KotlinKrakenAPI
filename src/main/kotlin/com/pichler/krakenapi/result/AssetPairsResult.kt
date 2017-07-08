package com.pichler.krakenapi.result

import com.google.gson.annotations.SerializedName

/**
 * Created by Patrick Pichler on 7/7/2017.
 */
@Suppress("ArrayInDataClass")
data class AssetPairsResult(val altname: String,
                            @SerializedName("aclass_base") val aclassBase: String,
                            val base: String,
                            @SerializedName("aclass_quote") val aclassQuote: String,
                            val quote: String,
                            val lot: String,
                            @SerializedName("pair_decimals") val pairDecimals: Int,
                            @SerializedName("lot_decimals") val lotDecimals: Int,
                            @SerializedName("lot_multiplier") val lotMultiplier: Int,
                            @SerializedName("leverage_buy") val leverageBuy: List<Int>,
                            @SerializedName("leverage_sell") val leverageSell: List<Int>,
                            val fees: List<List<Float>>,
                            @SerializedName("fee_maker") val feeMaker: List<List<Float>>,
                            @SerializedName("fee_volume_currency") val feeVolumeCurrency: String,
                            @SerializedName("margin_call") val marginCall: Int,
                            @SerializedName("margin_stop") val marginStop: Int
)