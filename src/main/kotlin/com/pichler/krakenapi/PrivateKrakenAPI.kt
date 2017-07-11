package com.pichler.krakenapi

import com.pichler.krakenapi.response.APIResponse
import com.pichler.krakenapi.result.TradeBalanceResult
import com.pichler.krakenapi.util.asString
import com.pichler.krakenapi.util.toBuilder
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.Response
import org.omg.CORBA.Object
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.security.MessageDigest
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

/**
 * Created by Patrick Pichler on 08-Jul-17.
 */
interface PrivateKrakenAPI : PrivateKrakenAPIRaw {

}

interface PrivateKrakenAPIRaw {
  @POST("Balance")
  fun getBalance(): Call<APIResponse<Map<String, Double>>>

  @FormUrlEncoded
  @POST("TradeBalance")
  fun getTradeBalance(@Field("asset") asset: String? = null): Call<APIResponse<TradeBalanceResult>>

  @POST("OpenOrders")
  fun getOpenOrders(): Call<APIResponse<Map<String, Object>>>
}

class PrivateAPIInterceptor(val apiKey: String, val secret: String) : Interceptor {
  override fun intercept(chain: Interceptor.Chain?): Response {
    if (chain == null) throw NullPointerException()

    val originalRequest = chain.request()

    val body = originalRequest.body()

    if (body is FormBody) {
      val nonce = generateNonce()

      val requestBody =
          body.toBuilder()
              .add("nonce", "$nonce")
              .build()

      val apiSign = generateMessageSignature(originalRequest.url().encodedPath(), requestBody.asString(), nonce)

      val updatedRequest =
          originalRequest.newBuilder()
              .addHeader("API-Key", apiKey)
              .addHeader("API-Sign", apiSign)
              .post(requestBody)
              .build()

      return chain.proceed(updatedRequest)
    }

    return chain.proceed(originalRequest)
  }

  fun generateMessageSignature(path: String, body: String, nonce: Long): String {
    val md = MessageDigest.getInstance("SHA-256")
    md.update("$nonce$body".toByteArray())

    val HMAC_SHA512 = "HmacSHA512"

    val secretKeySpec = SecretKeySpec(Base64.getDecoder().decode(secret), HMAC_SHA512)
    val mac = Mac.getInstance(HMAC_SHA512)

    mac.init(secretKeySpec)

    mac.update(path.toByteArray())

    return Base64.getEncoder().encodeToString(mac.doFinal(md.digest()))
  }

  fun generateNonce() = System.currentTimeMillis()

  fun generatePostMessage(message: String, nonce: Long) =
      if (message.trim().isEmpty()) "nonce=$nonce"
      else "$message\nnonce=$nonce"
}