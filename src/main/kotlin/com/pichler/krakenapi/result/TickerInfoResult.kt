package com.pichler.krakenapi.result

import com.github.salomonbrys.kotson.typeAdapter
import com.google.gson.annotations.SerializedName

/**
 * Created by Patrick Pichler on 7/7/2017.
 */
data class TickerInfoResult(
    @SerializedName("a") val ask: PriceDetail,
    @SerializedName("b") val bid: PriceDetail,
    @SerializedName("c") val lastTradeClosed: TradeCloseDetail,
    @SerializedName("v") val timePair: TimePairDetail,
    @SerializedName("p") val timePairWeightedAveragePrice: TimePairDetail,
    @SerializedName("t") val numberOfTrades: TimePairDetail,
    @SerializedName("l") val low: TimePairDetail,
    @SerializedName("h") val high: TimePairDetail,
    @SerializedName("o") val openingPrice: Double
)

data class PriceDetail(
    val price: Double,
    val wholeLotVolume: Int,
    val lotVolume: Double
)

data class TradeCloseDetail(
    val price: Double,
    val lotVolume: Double
)

data class TimePairDetail(
    val today: Double,
    val last24h: Double
)

val priceDetailTypeAdapter = typeAdapter<PriceDetail> {
  write {
    beginArray()

    value("${it.price}")
    value("${it.wholeLotVolume}")
    value("${it.lotVolume}")

    endArray()
  }

  read {
    beginArray()

    val price = nextDouble()
    val wholeLotVolume = nextInt()
    val lotVolume = nextDouble()

    endArray()

    PriceDetail(price, wholeLotVolume, lotVolume)
  }
}

val tradeCloseDetailAdapter = typeAdapter<TradeCloseDetail> {
  write {
    beginArray()

    value("${it.price}")
    value("${it.lotVolume}")

    endArray()
  }

  read {
    beginArray()

    val price = nextDouble()
    val lotVolume = nextDouble()

    endArray()

    TradeCloseDetail(price, lotVolume)
  }
}

val volumeDetailAdapter = typeAdapter<TimePairDetail> {
  write {
    beginArray()

    value("${it.today}")
    value("${it.last24h}")

    endArray()
  }

  read {
    beginArray()

    val today = nextDouble()
    val last24h = nextDouble()

    endArray()

    TimePairDetail(today, last24h)
  }
}
