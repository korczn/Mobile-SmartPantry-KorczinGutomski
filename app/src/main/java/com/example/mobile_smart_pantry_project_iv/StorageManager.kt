package com.example.mobile_smart_pantry_project_iv

import android.content.Context
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

object StorageManager {
    private const val FILE_NAME = "pantry_inventory.json"

    fun saveInventory(context: Context, inventory: List<Product>) {
        val json = Json.encodeToString(inventory)
        context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE).use {
            it.write(json.toByteArray())
        }
    }

    fun loadInventory(context: Context): List<Product> {
        val file = File(context.filesDir, FILE_NAME)
        if (!file.exists()) return mutableListOf()
        return try {
            val json = file.readText()
            Json.decodeFromString<List<Product>>(json)
        } catch (e: Exception) {
            mutableListOf()
        }
    }
}
