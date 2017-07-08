package com.pichler.krakenapi

import com.pichler.krakenapi.response.APIResponse
import com.pichler.krakenapi.result.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Patrick Pichler on 7/7/2017.
 */
interface KrakenAPI : KrakenAPIRaw {
  companion object {

    fun getAPI(apiURL: String): KrakenAPI = KrakenAPIImpl(Retrofit.Builder()
        .baseUrl(apiURL)
        .addConverterFactory(GsonConverterFactory.create(APIGsonCreator.buildGson()))
        .build()
        .create(KrakenAPIRaw::class.java))
  }

  fun getAssets(vararg assets: String): Call<APIResponse<Map<String, AssetsResult>>> = getAssets(assets.joinToString(",") as String?)

  fun getAssetsPairs(vararg pair: String, info: AssetPairInfo? = null) =
      getAssetsPairs(info = info, pair = if (pair.isEmpty()) null else pair.joinToString(","))

  fun getTicker(vararg pair: String) =
      if (pair.isEmpty()) throw IllegalArgumentException("No value pairs given!")
      else getTicker(pair.joinToString(",") as String?)
}

interface KrakenAPIRaw {
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
}

private class KrakenAPIImpl(private val krakenAPI: KrakenAPIRaw) : KrakenAPIRaw by krakenAPI, KrakenAPI

enum class AssetPairInfo(val value: String) {
  INFO("info"),
  LEVERAGE("leverage"),
  FEES("fees"),
  MARGIN("margin");

  override fun toString(): String {
    return value
  }
}

private val validOHLCIntervals = listOf<Int>(1, 5, 15, 30, 60, 240, 1440, 10080, 21600)