package com.pichler.krakenapi.util

import okhttp3.ResponseBody
import org.funktionale.either.Either
import retrofit2.Response

/**
 * Created by Patrick Pichler on 7/7/2017.
 */
fun <T> Response<T>.tryBody(): Either<ResponseBody, T> = if (this.isSuccessful) Either.right(this.body()!!) else Either.left(this.errorBody()!!)