package com.example.appiness.rxjavakotlin.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by appiness on 27/4/18.
 */

class Airline {
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("logo")
    @Expose
    var logo: String? = null

}
