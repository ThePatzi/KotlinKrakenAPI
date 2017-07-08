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
          .create()

}