package com.example.furnitureshopchallenge.domain.model

data class Warehouse(
    val code : String = "",
    val name: String = "",
    val address : String ="",
    val state : String ="",
    val county: String? = "",
    val zip:String? = "",
    var priceList : String? = ""
)

