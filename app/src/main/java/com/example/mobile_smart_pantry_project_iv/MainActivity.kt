package com.example.mobile_smart_pantry_project_iv

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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

        // Load manifest from JSON
        inventoryList = StorageManager.loadInventory(this).toMutableList()

        adapter = InventoryAdapter(this, inventoryList)
        binding.listViewInventory.adapter = adapter

        // Add new cargo to the manifest
        binding.btnAdd.setOnClickListener {
            addNewProductToCargo()
        }

        // Long click to remove cargo (Safety Protocol: Confirm deletion)
        binding.listViewInventory.setOnItemLongClickListener { _, _, position, _ ->
            showDeleteConfirmation(position)
            true
        }
    }

    private fun addNewProductToCargo() {
        val name = binding.etName.text.toString().trim()
        val quantityStr = binding.etQuantity.text.toString().trim()
        val category = binding.etCategory.text.toString().trim()
        val imageRef = binding.etImageRef.text.toString().trim()

        if (name.isEmpty() || quantityStr.isEmpty() || category.isEmpty() || imageRef.isEmpty()) {
            Toast.makeText(this, "🚨 BŁĄD: Wypełnij wszystkie dane manifestu!", Toast.LENGTH_SHORT).show()
            return
        }

        val quantity = quantityStr.toIntOrNull()
        if (quantity == null) {
            Toast.makeText(this, "🚨 BŁĄD: Ilość musi być liczbą!", Toast.LENGTH_SHORT).show()
            return
        }

        val newCargo = Product(
            name = name,
            quantity = quantity,
            category = category,
            imageRef = imageRef
        )

        inventoryList.add(newCargo)
        updateInventory()
        
        // Clear inputs for next entry
        binding.etName.text.clear()
        binding.etQuantity.text.clear()
        binding.etCategory.text.clear()
        binding.etImageRef.text.clear()

        Toast.makeText(this, "✅ Ładunek zabezpieczony w magazynie!", Toast.LENGTH_SHORT).show()
    }

    private fun showDeleteConfirmation(position: Int) {
        val product = inventoryList[position]
        AlertDialog.Builder(this)
            .setTitle("Usuwanie zasobów")
            .setMessage("Czy na pewno chcesz usunąć '${product.name}' z manifestu?")
            .setPositiveButton("USUŃ") { _, _ ->
                inventoryList.removeAt(position)
                updateInventory()
                Toast.makeText(this, "🗑️ Zasób usunięty.", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("ANULUJ", null)
            .show()
    }

    private fun updateInventory() {
        adapter.notifyDataSetChanged()
        StorageManager.saveInventory(this, inventoryList)
    }
}
