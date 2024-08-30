package com.example.trialquotesapp.room

class QuoteRepository(private val quoteDao: QuoteDao) {

     fun insertQuote(quote: Quote) {
        quoteDao.insertQuote(quote)
    }
     fun getAllQuotes(): List<Quote> {
        return quoteDao.getAllQuotes()
    }
}

