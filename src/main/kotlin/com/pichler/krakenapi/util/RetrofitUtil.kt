package com.pichler.krakenapi.util

import okhttp3.FormBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import okio.Buffer
import org.funktionale.either.Either
import retrofit2.Response

/**
 * Created by Patrick Pichler on 7/7/2017.
 */
fun <T> Response<T>.tryBody(): Either<ResponseBody, T> = if (this.isSuccessful) Either.right(this.body()!!) else Either.left(this.errorBody()!!)

fun RequestBody.asString(): String {
  val buffer = Buffer()

  writeTo(buffer)

  return buffer.readUtf8()
}

fun FormBody.toBuilder(): FormBody.Builder {
  val builder = FormBody.Builder()

  (0 until size())
      .map { name(it) to value(it) }
      .forEach { builder.addEncoded(it.first, it.second) }

  return builder
}