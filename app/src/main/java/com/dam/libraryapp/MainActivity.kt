package com.dam.libraryapp

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private lateinit var recy: RecyclerView
    val adaptadorRecyclerView = AdaptadorAutores()
    private lateinit var buscador: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        buscador=findViewById(R.id.svAutores)
        buscador.setOnQueryTextListener(this)
        recy = findViewById(R.id.rvAutores)
        recy.layoutManager = LinearLayoutManager(
            applicationContext,
            LinearLayoutManager.VERTICAL, false
        )
        recy.adapter = adaptadorRecyclerView
    }

    private fun searchAutors(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = RetrofitClient.api.getAutores("/search/authors.json?q=$query")

            val autoresAPI = call.body()

            if (call.isSuccessful){
                val autores = autoresAPI?.autores ?:emptyList()
                withContext(Dispatchers.Main) {
                    adaptadorRecyclerView.changelist(autores)
                    adaptadorRecyclerView.notifyDataSetChanged()
                }
            }else{
                Toast.makeText(applicationContext, "error API",
                Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(!query.isNullOrEmpty()){
            searchAutors(query.lowercase())
        }
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(this.buscador.windowToken, 0)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        buscador.setOnQueryTextListener(this)
        return true
    }
}