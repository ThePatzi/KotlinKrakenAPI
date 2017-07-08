package com.pichler.krakenapi

import com.github.salomonbrys.kotson.registerTypeAdapter
import com.google.gson.GsonBuilder
import com.pichler.krakenapi.result.*

/**
 * Created by Patrick Pichler on 7/7/2017.
 */
object APIGsonCreator {
  fun buildGson() =
      GsonBuilder()
          .registerTypeAdapter<PriceDetail>(priceDetailTypeAdapter)
          .registerTypeAdapter<TradeCloseDetail>(tradeCloseDetailAdapter)
          .registerTypeAdapter<TimePairDetail>(volumeDetailAdapter)
          .registerTypeAdapter<OHLCResult>(OHLCResultTypeAdapter)
          .registerTypeAdapter<OHLCEntry>(OHLCEntryTypeAdapter)
          .registerTypeAdapter<OrderBookResult>(orderBookResultTypeAdapter)
          .registerTypeAdapter<OrderBookEntry>(orderBookEntryTypeAdapter)
          .registerTypeAdapter<RecentTradesResult>(recentTradeResultTypeAdapter)
          .registerTypeAdapter<RecentTradesEntry>(recentTradeEntryTypeAdapter)
          .registerTypeAdapter<SpreadDataResult>(spreadDataResultTypeAdapter)
          .registerTypeAdapter<SpreadDataEntry>(spreadDataEntryTypeAdapter)
          .create()

}