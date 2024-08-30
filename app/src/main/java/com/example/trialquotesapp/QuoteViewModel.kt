package com.example.trialquotesapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.trialquotesapp.room.Quote
import com.example.trialquotesapp.room.QuoteDatabase
import com.example.trialquotesapp.room.QuoteRepository
import kotlin.concurrent.thread

class QuoteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: QuoteRepository
    private val allQuotes: MutableLiveData<List<Quote>> = MutableLiveData()

    init {
        val quoteDao = QuoteDatabase.getDatabase(application).quoteDao()
        repository = QuoteRepository(quoteDao)
        loadQuotes()
    }

    private fun loadQuotes() {
        thread {
            val quotes = repository.getAllQuotes()
            allQuotes.postValue(quotes)
        }
    }

    fun insertQuote(quote: Quote) {
        thread {
            repository.insertQuote(quote)
            loadQuotes()
        }
    }

    fun getAllQuotes(): LiveData<List<Quote>> {
        return allQuotes
    }
}
