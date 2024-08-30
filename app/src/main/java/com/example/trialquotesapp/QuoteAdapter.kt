package com.example.trialquotesapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.trialquotesapp.room.Quote

class QuoteAdapter(private val quotes: List<Quote>) : RecyclerView.Adapter<QuoteAdapter.QuoteViewHolder>() {

    class QuoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val quotetv: TextView = itemView.findViewById(R.id.quote_text)
        val authortv: TextView = itemView.findViewById(R.id.author_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_quote, parent, false)
        return QuoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val currentQuote = quotes[position]
        holder.quotetv.text = currentQuote.text
        holder.authortv.text = currentQuote.author
    }

    override fun getItemCount(): Int {
        return quotes.size
    }
}
