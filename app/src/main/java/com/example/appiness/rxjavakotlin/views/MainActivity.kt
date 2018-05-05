package com.example.appiness.rxjavakotlin.views

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.widget.TextView

import com.example.appiness.rxjavakotlin.R
import com.example.appiness.rxjavakotlin.model.Ticket
import com.example.appiness.rxjavakotlin.service.APIHelper
import com.example.appiness.rxjavakotlin.service.RetrofitService

import java.util.ArrayList

import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

/**
 * Created by appiness on 27/4/18.
 */

class MainActivity : AppCompatActivity(), FlightAdapter.FlightAdapterListener {
    private val disposable = CompositeDisposable()
    private var unbinder: Unbinder? = null

    private var apiHelper: APIHelper? = null
    private var flightAdapter: FlightAdapter? = null


    private val flightList = ArrayList<Ticket>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val toolbar:Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle(from + " > " + to)

        apiHelper = RetrofitService.client.create(APIHelper::class.java)

        flightAdapter = FlightAdapter(this, flightList, this)

        val mLayoutManager = GridLayoutManager(this, 1)
        recycler?.layoutManager = mLayoutManager
        recycler?.adapter = flightAdapter

        val flightObservable = getFlight(from, to).replay()

        disposable.add(flightObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<List<Ticket>>() {

                    override fun onNext(ticketList: List<Ticket>) {
                        flightList.clear()
                        flightList.addAll(ticketList)
                        flightAdapter!!.notifyDataSetChanged()
                        Log.d("flight",ticketList.toString())
                    }

                    override fun onError(e: Throwable) {
                        showError(e)
                    }

                    override fun onComplete() {

                    }
                }))

        disposable.add(flightObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap { ticketList -> Observable.fromIterable(ticketList) }
                .flatMap { ticket -> getPriceObservable(ticket) }

                .subscribeWith(object : DisposableObserver<Ticket>() {

                    override fun onNext(ticket: Ticket) {
                        val position = flightList.indexOf(ticket)


                        if (position == -1) {
                            // TODO - take action
                            // Ticket not found in the list
                            // This shouldn't happen
                            Log.d("exception","exception")
                            return
                        }

                        flightList[position] = ticket
                        Log.e("on next",ticket.price.toString())
                        flightAdapter!!.notifyItemChanged(position)
                       // Log.d("position", flightList[position].price!!.price.toString())

                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {

                    }

                }))

        flightObservable.connect()

    }

    private fun getFlight(from: String, to: String): Observable<List<Ticket>> {
        return apiHelper!!.getTickets(from, to)
                //.toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

    }

    private fun getPriceObservable(ticket: Ticket): Observable<Ticket> {
        return apiHelper!!
                .getPrice(ticket.flightNumber, ticket.from, ticket.to)
                //.toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { price ->
                    ticket.price
                    ticket
                }

    }


    override fun onFlightSelected(ticket: Ticket) {

    }

    private fun showError(e: Throwable) {
        Log.e(TAG, "showError: " + e.message)

        val snackbar = Snackbar
                .make(coordinator_layout!!, e.message.toString(), Snackbar.LENGTH_LONG)
        val sbView = snackbar.view
        val textView = sbView.findViewById<TextView>(android.support.design.R.id.snackbar_text)
        textView.setTextColor(Color.YELLOW)
        snackbar.show()
    }

    companion object {
        private val TAG = MainActivity::class.java.name

        private val from = "DEL"
        private val to = "CHE"
    }
}

