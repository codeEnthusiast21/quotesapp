package com.example.trialquotesapp.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface QuoteDao {

    @Insert
     fun insertQuote(quote: Quote)

    @Query("SELECT * FROM quotes")
     fun getAllQuotes(): List<Quote>
}