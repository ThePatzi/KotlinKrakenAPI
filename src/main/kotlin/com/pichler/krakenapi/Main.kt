package com.pichler.krakenapi

import com.pichler.krakenapi.util.tryBody
import org.funktionale.either.Either
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

/**
 * Created by Patrick Pichler on 7/7/2017.
 */
fun main(args: Array<String>) {
  val properties = Properties()
  properties.load(Files.newInputStream(Paths.get(".", "local.properties")))
  val apiKey = properties.getProperty("APIKey")
  val apiSecret = properties.getProperty("APISecret")

  val api = KrakenAPI.getAPI("https://api.kraken.com/0", apiKey, apiSecret).private

  val response = api.getBalance().execute()

  println(response)

  val body = response.tryBody()

  when (body) {
    is Either.Right -> println(body.r.errorOrResult)
    is Either.Left -> println(body.l)
  }
}