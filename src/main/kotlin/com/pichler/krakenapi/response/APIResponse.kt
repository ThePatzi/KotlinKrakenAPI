package com.pichler.krakenapi.response

import com.google.gson.annotations.SerializedName
import org.funktionale.either.Either
import org.funktionale.option.Option
import org.funktionale.option.toOption

/**
 * Created by Patrick Pichler on 7/7/2017.
 */
data class APIResponse<out T>(val error: List<String>,
                              @SerializedName("result") private val requestResult: T? = null) {
  val result: Option<T>
    get() = requestResult.toOption()

  val errorOrResult: Either<List<String>, T>
    get() = if (requestResult == null) Either.left(error) else Either.right(requestResult)
}