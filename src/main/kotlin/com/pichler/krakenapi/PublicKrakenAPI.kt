package com.pichler.krakenapi

import com.pichler.krakenapi.response.APIResponse
import com.pichler.krakenapi.result.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PublicKrakenAPI : PublicKrakenAPIRaw {
  fun getAssets(vararg assets: String): Call<APIResponse<Map<String, AssetsResult>>> = getAssets(assets.joinToString(",") as String?)

  fun getAssetsPairs(vararg pair: String, info: AssetPairInfo? = null) =
      getAssetsPairs(info = info, pair = if (pair.isEmpty()) null else pair.joinToString(","))

  fun getTicker(vararg pair: String) =
      if (pair.isEmpty()) throw IllegalArgumentException("No value pairs given!")
      else getTicker(pair.joinToString(",") as String?)
}

interface PublicKrakenAPIRaw {
  @GET("Time")
  fun getTime(): Call<APIResponse<TimeResult>>

  @GET("Assets")
  fun getAssets(@Query("asset") asset: String? = null): Call<APIResponse<Map<String, AssetsResult>>>

  @GET("AssetPairs")
  fun getAssetsPairs(@Query("pair") pair: String? = null, @Query("info") info: AssetPairInfo? = null): Call<APIResponse<Map<String, AssetPairsResult>>>

  @GET("Ticker")
  fun getTicker(@Query("pair") pair: String?): Call<APIResponse<Map<String, TickerInfoResult>>>

  @GET("OHLC")
  fun getOHLC(@Query("pair") pair: String, @Query("interval") interval: Int = 1, @Query("since") since: Int? = null): Call<APIResponse<OHLCResult>>

  @GET("Depth")
  fun getOrderBook(@Query("pair") pair: String, @Query("count") count: Int? = null): Call<APIResponse<OrderBookResult>>

  @GET("Trades")
  fun getRecentTrades(@Query("pair") pair: String, @Query("since") since: Int? = null): Call<APIResponse<RecentTradesResult>>

  @GET("Spread")
  fun getRecentSpreadData(@Query("pair") pair: String, @Query("since") since: Int? = null): Call<APIResponse<SpreadDataResult>>
}

enum class AssetPairInfo(val value: String) {
  INFO("info"),
  LEVERAGE("leverage"),
  FEES("fees"),
  MARGIN("margin");

  override fun toString(): String {
    return value
  }
}