package com.example.mcleancode.mobile.database.criteria

data class LocationCriteria(
        val title: String,
        val lat: Float,
        val long: Float,
        val radius: Int,
        val entry: String,
        val status: Int
)