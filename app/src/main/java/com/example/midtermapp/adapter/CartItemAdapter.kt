package com.example.midtermapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.midtermapp.database.UserShoes

import com.example.midtermapp.databinding.CartItemBinding
import java.text.NumberFormat


class CartItemAdapter(private val delete: (UserShoes)-> Unit, private val update: (UserShoes) -> Unit) : ListAdapter<UserShoes, CartItemAdapter.CartViewHolder>(DiffCallBack) {
    class CartViewHolder(val binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun binding(shoes: UserShoes) {
            binding.apply {
                thumbnailImg.load(shoes.imgUrl.toUri().buildUpon().scheme("https").build())
                nameTv.text = shoes.itemName
                priceTv.text= NumberFormat.getCurrencyInstance().format(shoes.itemPrice)
                sizeTv.text = shoes.size.toString()
                isBuyCheckbox.isChecked = shoes.isBuy
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(CartItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding(item)
        holder.binding.deleteBtn.setOnClickListener {
            delete(item)
        }
        holder.binding.isBuyCheckbox.setOnClickListener {
            val newUpdateItem = item.copy(isBuy = !item.isBuy)
            update(newUpdateItem)
        }
    }

    companion object DiffCallBack :DiffUtil.ItemCallback<UserShoes>() {
        override fun areItemsTheSame(oldItem: UserShoes, newItem: UserShoes): Boolean {
            return  oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: UserShoes, newItem: UserShoes): Boolean {
            return newItem.id == oldItem.id
        }
    }
}