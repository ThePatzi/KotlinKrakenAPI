package com.pichler.krakenapi

import com.pichler.krakenapi.util.tryBody
import org.funktionale.either.Either

/**
 * Created by Patrick Pichler on 7/7/2017.
 */
fun main(args: Array<String>) {
  val api = KrakenAPI.getAPI("https://api.kraken.com/0").public

  val response = api.getRecentTrades("etheur").execute()

  println(response)

  val body = response.tryBody()

  when (body) {
    is Either.Right -> println(body.r.errorOrResult)
    is Either.Left -> println(body.l)
  }
}