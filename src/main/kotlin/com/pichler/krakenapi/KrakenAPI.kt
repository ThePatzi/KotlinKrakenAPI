package com.pichler.krakenapi

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.Charset

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

    private fun buildPrivateAPI(apiURL: String, apiKey: String, secret: String) =
        PrivateKrakenAPIImpl(Retrofit.Builder()
            .baseUrl("$apiURL/private/")
            .addConverterFactory(GsonConverterFactory.create(APIGsonCreator.buildGson()))
            .client(OkHttpClient.Builder()
                .addInterceptor(buildLogInterceptor())
                .addInterceptor(PrivateAPIInterceptor(apiKey, secret))
                .build())
            .build()
            .create(PrivateKrakenAPIRaw::class.java))

    private fun buildLogInterceptor(): (Interceptor.Chain) -> Response {
      return { chain ->
        val response = chain.proceed(chain.request())

        val source = response.body()?.source()!!
        source.request(Long.MAX_VALUE)

        val buffer = source.buffer()

        println(buffer.clone().readString(Charset.forName("UTF-8")))

        response
      }
    }

    fun getAPI(apiURL: String, apiKey: String, secret: String): KrakenAPI =
        KrakenAPIImpl(buildPublicAPI(apiURL), buildPrivateAPI(apiURL, apiKey, secret))

  }

  val public: PublicKrakenAPI
  val private: PrivateKrakenAPI
}

private class KrakenAPIImpl(override val public: PublicKrakenAPI,
                            override val private: PrivateKrakenAPI) : KrakenAPI

private class PublicKrakenAPIImpl(private val publicKrakenAPI: PublicKrakenAPIRaw) : PublicKrakenAPIRaw by publicKrakenAPI, PublicKrakenAPI
private class PrivateKrakenAPIImpl(private val privateKrakenAPI: PrivateKrakenAPIRaw) : PrivateKrakenAPIRaw by privateKrakenAPI, PrivateKrakenAPI