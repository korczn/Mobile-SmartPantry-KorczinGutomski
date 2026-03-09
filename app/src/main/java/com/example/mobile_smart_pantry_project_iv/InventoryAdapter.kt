package com.example.mobile_smart_pantry_project_iv

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.BaseAdapter
import androidx.core.content.ContextCompat
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

        // Red Alert logic
        if (product.quantity < 5) {
            // Background color alert
            binding.root.setBackgroundColor(ContextCompat.getColor(context, R.color.warning_red))
            
            // Pulsing animation alert
            val pulseAnimation = AnimationUtils.loadAnimation(context, R.anim.pulse)
            binding.ivProductIcon.startAnimation(pulseAnimation)
        } else {
            binding.root.setBackgroundColor(ContextCompat.getColor(context, R.color.default_item_bg))
            binding.ivProductIcon.clearAnimation()
        }

        // Image loading logic
        val resourceId = context.resources.getIdentifier(product.imageRef, "drawable", context.packageName)
        if (resourceId != 0) {
            binding.ivProductIcon.setImageResource(resourceId)
        } else {
            binding.ivProductIcon.setImageResource(android.R.drawable.ic_menu_help)
        }

        return view
    }
}
