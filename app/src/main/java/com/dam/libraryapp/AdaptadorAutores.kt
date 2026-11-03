package com.dam.libraryapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class AdaptadorAutores : RecyclerView.Adapter<AutoresViewHolder>(){
    private var aut: List<Autores> =mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            AutoresViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return AutoresViewHolder(layoutInflater.inflate(R.layout.fila_autores,
            parent, false))
    }
    override fun getItemCount(): Int = aut.size
    override fun onBindViewHolder(holder: AutoresViewHolder, position: Int) {
        val item = aut[position]
        holder.nombre.text = item.name
    }

    fun changelist(autores: List<Autores>) {
        aut=autores
    }
}