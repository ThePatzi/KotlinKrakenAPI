package com.pichler.krakenapi.result

import com.github.salomonbrys.kotson.typeAdapter
import com.google.gson.stream.JsonToken

/**
 * Created by Patrick Pichler on 7/7/2017.
 */
data class OHLCResult(
    val name: String,
    val entries: List<OHLCEntry>,
    val last: Long
)

data class OHLCEntry(
    val time: Long,
    val open: Double,
    val high: Double,
    val low: Double,
    val close: Double,
    val vwap: Double,
    val volume: Double,
    val count: Int
)

val OHLCResultTypeAdapter = typeAdapter<OHLCResult> {
  write {
    beginObject()

    name(it.name)
    beginArray()


    it.entries.forEach {
      OHLCEntryTypeAdapter.write(this, it)
    }

    endArray()

    name("last")
    value(it.last)

    endObject()
  }

  read {
    beginObject()

    val name = nextName()
    beginArray()

    val entries =
        generateSequence {
          if (peek() == JsonToken.END_ARRAY) null
          else OHLCEntryTypeAdapter.read(this)
        }.toList()

    endArray()

    skipValue()
    val last = nextLong()

    endObject()

    OHLCResult(name, entries, last)
  }
}

val OHLCEntryTypeAdapter = typeAdapter<OHLCEntry> {
  write {
    beginArray()

    value(it.time)
    value(it.open)
    value(it.high)
    value(it.low)
    value(it.close)
    value(it.vwap)
    value(it.volume)
    value(it.count)

    endArray()
  }

  read {
    beginArray()

    val time = nextLong()
    val open = nextDouble()
    val high = nextDouble()
    val low = nextDouble()
    val close = nextDouble()
    val vwap = nextDouble()
    val volume = nextDouble()
    val count = nextInt()

    endArray()

    OHLCEntry(time, open, high, low, close, vwap, volume, count)
  }
}

