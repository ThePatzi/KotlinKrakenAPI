package com.pichler.krakenapi.result

import com.github.salomonbrys.kotson.typeAdapter
import com.google.gson.stream.JsonToken

/**
 * Created by Patrick Pichler on 08-Jul-17.
 */
data class SpreadDataResult(
    val pairName: String,
    val entries: List<SpreadDataEntry>,
    val last: Long
)

data class SpreadDataEntry(
    val time: Long,
    val bid: Double,
    val ask: Double
)

val spreadDataEntryTypeAdapter = typeAdapter<SpreadDataEntry> {
  write {
    beginArray()

    value(it.time)
    value("${it.bid}")
    value("${it.ask}")

    endArray()
  }

  read {
    beginArray()

    val time = nextLong()
    val bid = nextDouble()
    val ask = nextDouble()

    endArray()

    SpreadDataEntry(time, bid, ask)
  }
}

val spreadDataResultTypeAdapter = typeAdapter<SpreadDataResult> {
  write {
    beginObject()

    name(it.pairName)
    beginObject()

    beginArray()
    it.entries.forEach { spreadDataEntryTypeAdapter.write(this, it) }
    endArray()

    endObject()

    name("last")
    value(it.last)

    endObject()
  }

  read {
    beginObject()

    val pairName = nextName()
    beginArray()
    val entries = generateSequence {
      if (peek() == JsonToken.END_ARRAY) null
      else spreadDataEntryTypeAdapter.read(this)
    }.toList()
    endArray()

    skipValue()
    val last = nextLong()

    endObject()

    SpreadDataResult(pairName, entries, last)
  }
}