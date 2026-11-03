package com.dam.libraryapp

import com.google.gson.annotations.SerializedName

data class AuthorsResponse(
    var numFound: Int,
    @SerializedName("docs") var autores: List<Autores>
)

data class Autores(
    var key: String,
    var birth_date: String?,
    var name: String
)
