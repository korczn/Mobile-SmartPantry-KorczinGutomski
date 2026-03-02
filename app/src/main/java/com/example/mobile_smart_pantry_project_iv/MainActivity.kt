package com.example.mobile_smart_pantry_project_iv

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mobile_smart_pantry_project_iv.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var inventoryList: MutableList<Product>
    private lateinit var adapter: InventoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inventoryList = StorageManager.loadInventory(this).toMutableList()

        adapter = InventoryAdapter(this, inventoryList)
        binding.listViewInventory.adapter = adapter

        binding.btnAdd.setOnClickListener {
            addNewProductToCargo()
        }
    }

    private fun addNewProductToCargo() {
        val name = binding.etName.text.toString().trim()
        val quantityStr = binding.etQuantity.text.toString().trim()
        val category = binding.etCategory.text.toString().trim()
        val imageRef = binding.etImageRef.text.toString().trim()

        if (name.isEmpty() || quantityStr.isEmpty() || category.isEmpty() || imageRef.isEmpty()) {
            Toast.makeText(this, "Błąd: Wypełnij wszystkie dane ładunku!", Toast.LENGTH_SHORT).show()
            return
        }

        val quantity = quantityStr.toIntOrNull()
        if (quantity == null) {
            Toast.makeText(this, "Błąd: Ilość musi być liczbą!", Toast.LENGTH_SHORT).show()
            return
        }

        val newCargo = Product(
            name = name,
            quantity = quantity,
            category = category,
            imageRef = imageRef
        )

        inventoryList.add(newCargo)
        adapter.notifyDataSetChanged()
        StorageManager.saveInventory(this, inventoryList)

        binding.etName.text.clear()
        binding.etQuantity.text.clear()
        binding.etCategory.text.clear()
        binding.etImageRef.text.clear()

        Toast.makeText(this, "Ładunek zabezpieczony w magazynie!", Toast.LENGTH_SHORT).show()
    }
}
