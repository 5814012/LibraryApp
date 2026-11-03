package com.dam.libraryapp

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AutoresViewHolder (view: View): RecyclerView.ViewHolder(view) {
    var nombre: TextView
    init{
        nombre=view.findViewById(R.id.tvAutor)
    }
}