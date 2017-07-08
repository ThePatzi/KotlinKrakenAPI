package com.pichler.krakenapi.result

import com.github.salomonbrys.kotson.typeAdapter
import com.google.gson.stream.JsonToken
import org.funktionale.option.Option
import org.funktionale.option.toOption

/**
 * Created by Patrick Pichler on 08-Jul-17.
 */
data class RecentTradesResult(
    val pairName: String,
    val recentTradesEntries: List<RecentTradesEntry>,
    val last: Long
)

data class RecentTradesEntry(
    val price: Double,
    val volume: Double,
    val time: Double,
    val action: TradeAction,
    val type: TradeType,
    val misc: String
)

enum class TradeAction(val value: String) {
  BUY("b"),
  SELL("s");

  companion object {
    fun valueFor(value: String): Option<TradeAction> = values().find { it.value == value }.toOption()
  }
}

enum class TradeType(val value: String) {
  MARKET("m"),
  LIMIT("l");

  companion object {
    fun valueFor(value: String): Option<TradeType> = TradeType.values().find { it.value == value }.toOption()
  }
}

val recentTradeEntryTypeAdapter = typeAdapter<RecentTradesEntry> {
  write {
    beginArray()

    value("${it.price}")
    value("${it.volume}")
    value(it.time)
    value(it.action.value)
    value(it.type.value)
    value(it.misc)

    endArray()
  }

  read {
    beginArray()

    val price = nextDouble()
    val volume = nextDouble()
    val time = nextDouble()
    val action = TradeAction.valueFor(nextString()).get()
    val type = TradeType.valueFor(nextString()).get()
    val misc = nextString()

    endArray()

    RecentTradesEntry(price, volume, time, action, type, misc)
  }
}

val recentTradeResultTypeAdapter = typeAdapter<RecentTradesResult> {
  write {
    beginObject()

    name(it.pairName)
    beginArray()

    it.recentTradesEntries.forEach { recentTradeEntryTypeAdapter.write(this, it) }

    endArray()

    name("last")
    value(it.last)

    endObject()
  }

  read {
    beginObject()

    val pairName = nextName()

    beginArray()

    val entries =
        generateSequence {
          if (peek() == JsonToken.END_ARRAY) null
          else recentTradeEntryTypeAdapter.read(this)
        }.toList()

    endArray()

    skipValue()
    val last = nextLong()

    endObject()

    RecentTradesResult(pairName, entries, last)
  }
}