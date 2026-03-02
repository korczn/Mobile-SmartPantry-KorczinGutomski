package com.example.mobile_smart_pantry_project_iv

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.mobile_smart_pantry_project_iv.databinding.ItemProductBinding

class InventoryAdapter(private val context: Context, private val dataSource: List<Product>) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int = dataSource.size

    override fun getItem(position: Int): Any = dataSource[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: ItemProductBinding
        val view: View

        if (convertView == null) {
            binding = ItemProductBinding.inflate(inflater, parent, false)
            view = binding.root
            view.tag = binding
        } else {
            view = convertView
            binding = view.tag as ItemProductBinding
        }

        val product = getItem(position) as Product
        binding.tvProductName.text = product.name
        binding.tvProductCategory.text = product.category
        binding.tvProductQuantity.text = product.quantity.toString()

        val resourceId = context.resources.getIdentifier(product.imageRef, "drawable", context.packageName)
        if (resourceId != 0) {
            binding.ivProductIcon.setImageResource(resourceId)
        } else {
            binding.ivProductIcon.setImageResource(android.R.drawable.ic_menu_help)
        }

        return view
    }
}
