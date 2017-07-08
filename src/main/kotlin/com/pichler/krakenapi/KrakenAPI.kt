package com.pichler.krakenapi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Patrick Pichler on 08-Jul-17.
 */
interface KrakenAPI {
  companion object {
    private fun buildPublicAPI(apiURL: String) =
        PublicKrakenAPIImpl(Retrofit.Builder()
            .baseUrl("$apiURL/public/")
            .addConverterFactory(GsonConverterFactory.create(APIGsonCreator.buildGson()))
            .build()
            .create(PublicKrakenAPIRaw::class.java))

    fun getAPI(apiURL: String): KrakenAPI = KrakenAPIImpl(buildPublicAPI(apiURL))
  }

  val public: PublicKrakenAPI
}

private class KrakenAPIImpl(override val public: PublicKrakenAPI) : KrakenAPI

private class PublicKrakenAPIImpl(private val publicKrakenAPI: PublicKrakenAPIRaw) : PublicKrakenAPIRaw by publicKrakenAPI, PublicKrakenAPI