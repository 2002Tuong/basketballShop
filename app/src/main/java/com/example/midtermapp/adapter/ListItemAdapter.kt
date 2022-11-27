package com.example.midtermapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.midtermapp.R
import com.example.midtermapp.datanetwork.Shoes
import com.example.midtermapp.databinding.ListItemBinding
import java.text.NumberFormat
import java.util.*

class ListItemAdapter(private val context: Context, private val dataSet:List<Shoes>, val onClick: (Shoes)->Unit) : RecyclerView.Adapter<ListItemAdapter.ItemViewHolder>() {


    class ItemViewHolder(private val binding: ListItemBinding, private val context: Context) : RecyclerView.ViewHolder(binding.root) {
        fun binding(shoes: Shoes) {
            binding.nameTv.text = shoes.name
            binding.priceTv.text =NumberFormat.getCurrencyInstance().format(shoes.price)
            binding.thumbnailImg.load(shoes.imgUrl.toUri().buildUpon().scheme("https").build()){
                placeholder(R.drawable.loading_animation)
                error(R.drawable.broken_image_24)
            }
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return ItemViewHolder(binding,context)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataSet[position]
        holder.binding(item)
        holder.itemView.setOnClickListener {
            onClick(item)
        }
    }
}