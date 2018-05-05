package com.example.appiness.rxjavakotlin.views

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.appiness.rxjavakotlin.R
import com.example.appiness.rxjavakotlin.model.Ticket

import butterknife.BindView
import butterknife.ButterKnife
import com.example.appiness.rxjavakotlin.R.id.*
import com.example.appiness.rxjavakotlin.model.Price
import kotlinx.android.synthetic.main.ticket_list.view.*

/**
 * Created by appiness on 27/4/18.
 */
 class FlightAdapter(private val context: Context, private val flightList: List<Ticket>, private val listener: FlightAdapterListener) : RecyclerView.Adapter<FlightAdapter.MyViewHolder>() {


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

       /* @BindView(R.id.tvAirlineName)
        internal var airlineName: TextView? = null

        @BindView(R.id.ivLogo)
        internal var logo: ImageView? = null

        @BindView(R.id.tvDepTime)
        internal var departureTime: TextView? = null

        @BindView(R.id.tvArrTime)
        internal var arrivalTime: TextView? = null

        @BindView(R.id.tvDuration)
        internal var duration: TextView? = null

        @BindView(R.id.tvStops)
        internal var stops: TextView? = null

        @BindView(R.id.tvPrice)
        internal var price: TextView? = null

        @BindView(R.id.tvSeats)
        internal var seats: TextView? = null

        init {
            ButterKnife.bind(this, itemView)

            itemView.setOnClickListener {
                // send selected contact in callback
                listener.onFlightSelected(flightList[adapterPosition])
            }
        }
*/
       fun bind(ticket: Ticket) {
           val tvAirlineName = itemView.findViewById<TextView>(R.id.tvAirlineName)
           val tvDepTime = itemView.findViewById<TextView>(R.id.tvDepTime)
           val tvArrTime = itemView.findViewById<TextView>(R.id.tvArrTime)
           val tvDuration = itemView.findViewById<TextView>(R.id.tvDuration)
           val tvStops = itemView.findViewById<TextView>(R.id.tvStops)
           val tvPrice = itemView.findViewById<TextView>(R.id.tvPrice)
           val tvSeats = itemView.findViewById<TextView>(R.id.tvSeats)
           val ivLogo = itemView.findViewById<ImageView>(R.id.ivLogo)
           Glide.with(context)
                   .load(ticket.airline?.logo)
                   .apply(RequestOptions.centerCropTransform())
                   .into(ivLogo)
           tvAirlineName.text = ticket.airline?.name
           tvDepTime.text = ticket.departure
           tvArrTime.text = ticket.arrival
           tvDuration.text = ticket.duration
           tvStops.text = ticket.numberOfStops.toString() + " stops"
           /*Log.d("priceflight",ticket.price?.price.toString())
           if (ticket.price != null) {
               tvPrice.text = "₹" + String.format("%.0f", ticket.price!!.price)
               tvSeats.text = ticket.price!!.seats + " seats"
           }else{
               tvPrice.text = "₹"+ " Wait"
               tvSeats.text = "PLease Wait"
           }*/
       }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ticket_list, parent, false)
        return MyViewHolder(view)
    }

   /* override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val ticket = flightList[position]

        Glide.with(context)
                .load(ticket.airline?.logo)
                .apply(RequestOptions.centerCropTransform())
                .into(holder?.logo)
        val airline = ticket.airline?.name
        Log.d("name:", airline)

        holder.airlineName!!.text = ticket.airline!!.name
        holder.departureTime!!.text = ticket.departure
        holder.arrivalTime!!.text = ticket.arrival
        holder.duration!!.text = ticket.duration
        holder.stops!!.text = ticket.numberOfStops.toString() + " stops "

        if (ticket.price != null) {
            holder.price!!.text = "₹" + String.format("%.0f", ticket.price.price)
            holder.seats!!.text = ticket.price.seats!! + " Seats"
        } else {

        }
    }*/

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(flightList[position])

       }

    override fun getItemCount(): Int {
        return flightList.size
    }

    interface FlightAdapterListener {
        fun onFlightSelected(ticket: Ticket)


    }
}

