package com.example.appiness.rxjavakotlin.service

import com.example.appiness.rxjavakotlin.model.Price
import com.example.appiness.rxjavakotlin.model.Ticket

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by appiness on 27/4/18.
 */

interface APIHelper {

    @GET("airline-tickets.php")
    fun getTickets(@Query("from") from: String, @Query("to") to: String): Observable<List<Ticket>>

    @GET("airline-tickets-price.php")
    fun getPrice(@Query("flight_number") flightNumber: String?, @Query("from") from: String?, @Query("to") to: String?): Observable<Price>

}
