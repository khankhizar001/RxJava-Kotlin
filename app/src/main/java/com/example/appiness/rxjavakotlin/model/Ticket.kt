package com.example.appiness.rxjavakotlin.model

import com.google.gson.annotations.SerializedName

/**
 * Created by appiness on 27/4/18.
 */

class Ticket {

    var from: String? = null
        internal set
    var to: String? = null
        internal set

    @SerializedName("flight_number")
    var flightNumber: String? = null
        internal set

    var departure: String? = null
        internal set
    var arrival: String? = null
        internal set
    var duration: String? = null
        internal set
    var instructions: String? = null
        internal set

    @SerializedName("stops")
    var numberOfStops: Int = 0
        internal set

    var airline: Airline? = null
        internal set

   // lateinit var price: Price
    var price: Price?= null
    internal set



}
