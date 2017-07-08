package com.pichler.krakenapi.util

import org.funktionale.option.Option

/**
 * Created by Patrick Pichler on 7/7/2017.
 */
fun <T, R> Option<T>.mapNullToNone(f: (T) -> R?): Option<R> =
    if (isEmpty())
      Option.None
    else {
      val mapped = f(get())

      if (mapped == null) {
        Option.None
      } else {
        Option.Some(mapped)
      }
    }