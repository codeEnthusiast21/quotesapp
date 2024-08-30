package com.example.trialquotesapp

import android.content.Intent
import android.os.Bundle
import android.provider.Settings.Global
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.trialquotesapp.databinding.ActivityMainBinding
import com.example.trialquotesapp.room.Quote
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val quoteViewModel: QuoteViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getQuote()
        binding.savedquotes.setOnClickListener{
            startActivity(Intent(this,savedquotes::class.java))
        }
        binding.savebtn.setOnClickListener {
            saveQuote()
        }
        binding.sharebtn.setOnClickListener{
            val quote= binding.quoteTv.text
            val author=binding.authorTv.text
            val txt= "$quote \n \t\t~$author"
            shareText(txt)
        }


    }

    private fun shareText(txt: String) {
        val shareIntent= Intent().apply {
            action=Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT,txt)
            type="text/plain"
        }
        startActivity(Intent.createChooser(shareIntent,"Share quote via"))

    }

    private fun getQuote(){
        setInProgress(true)

        GlobalScope.launch {
            try{
                val response = RetrofitInstance.quoteApi.getRandomQuote()
                runOnUiThread{
                    setInProgress(false)
                    response.body()?.first()?.let {
                        setinUI(it)
                    }
                }

            }catch (e:Exception){
                runOnUiThread{
                    setInProgress(false)
                    Toast.makeText(applicationContext,"Something went wrong",Toast.LENGTH_LONG).show()

                }

            }
        }


    }
    private fun setinUI(quote:QuoteModel){
        binding.quoteTv.text=quote.q
        binding.authorTv.text=quote.a

    }
    private fun setInProgress(inProgress:Boolean){
        if(inProgress){
            binding.pgbar.visibility=View.VISIBLE
            binding.savebtn.visibility=View.GONE
        }
        else{
            binding.pgbar.visibility=View.GONE
            binding.savebtn.visibility=View.VISIBLE
        }
    }
    private fun saveQuote() {
        val quoteText = binding.quoteTv.text.toString()
        val author = binding.authorTv.text.toString()

        val quote = Quote(
            text = quoteText,
            author = author
        )

        // Save the quote to the database
        GlobalScope.launch {
            try {
                quoteViewModel.insertQuote(quote)
                runOnUiThread {
                    Toast.makeText(this@MainActivity, "Quote saved!", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this@MainActivity, "Failed to save quote", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}