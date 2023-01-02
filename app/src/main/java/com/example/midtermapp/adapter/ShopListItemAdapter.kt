package com.example.midtermapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.example.midtermapp.databinding.ListItemBinding
import com.example.midtermapp.datanetwork.Shoes
import java.text.NumberFormat

class ShopListItemAdapter(val onClick: (Shoes) -> Unit) : ListAdapter<Shoes, ShopListItemAdapter.ItemViewHolder>(DiffCallBack) {



    class ItemViewHolder(private val item: ListItemBinding) : ViewHolder(item.root) {
        fun binding(shoes : Shoes) {
            item.thumbnailImg.load(shoes.imgUrl.toUri().buildUpon().scheme("https").build())
            item.nameTv.text = shoes.name
            item.priceTv.text = NumberFormat.getCurrencyInstance().format(shoes.price)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(
            parent.context,
        ),
            parent,
            false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding(item)
        holder.itemView.setOnClickListener {
            onClick(item)
            Log.d("Firebase","id is ${item.id}")
        }
    }

    companion object DiffCallBack : DiffUtil.ItemCallback<Shoes>() {
        override fun areItemsTheSame(oldItem: Shoes, newItem: Shoes): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Shoes, newItem: Shoes): Boolean {
            return oldItem.id == newItem.id
        }
    }
}