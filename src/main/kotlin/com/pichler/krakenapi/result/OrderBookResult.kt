package com.pichler.krakenapi.result

import com.github.salomonbrys.kotson.typeAdapter
import com.google.gson.stream.JsonToken

/**
 * Created by Patrick Pichler on 08-Jul-17.
 */
data class OrderBookResult(
    val pairName: String,
    val asks: List<OrderBookEntry>,
    val bids: List<OrderBookEntry>
)

data class OrderBookEntry(
    val price: Double,
    val volume: Double,
    val timestamp: Long
)

val orderBookResultTypeAdapter = typeAdapter<OrderBookResult> {
  write {
    name(it.pairName)

    beginObject()

    name("asks")
    beginArray()
    it.asks.forEach { orderBookEntryTypeAdapter.write(this, it) }
    endArray()

    name("bids")
    beginArray()
    it.bids.forEach { orderBookEntryTypeAdapter.write(this, it) }
    endArray()


    endObject()
  }

  read {
    beginObject()

    val pairName = nextName()

    beginObject()

    skipValue()
    beginArray()

    val asks = generateSequence {
      if (peek() == JsonToken.END_ARRAY) null
      else orderBookEntryTypeAdapter.read(this)
    }.toList()

    endArray()

    skipValue()
    beginArray()

    val bids = generateSequence {
      if (peek() == JsonToken.END_ARRAY) null
      else orderBookEntryTypeAdapter.read(this)
    }.toList()

    endArray()

    endObject()
    endObject()

    OrderBookResult(pairName, asks, bids)
  }
}

val orderBookEntryTypeAdapter = typeAdapter<OrderBookEntry> {
  write {
    beginArray()

    value("${it.price}")
    value("${it.volume}")
    value(it.timestamp)

    endArray()
  }

  read {
    beginArray()

    val price = nextDouble()
    val volume = nextDouble()
    val timestamp = nextLong()

    endArray()

    OrderBookEntry(price, volume, timestamp)
  }
}