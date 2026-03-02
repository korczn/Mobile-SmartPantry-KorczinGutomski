package com.example.mobile_smart_pantry_project_iv

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val name: String,
    val quantity: Int,
    val category: String,
    val imageRef: String
)
