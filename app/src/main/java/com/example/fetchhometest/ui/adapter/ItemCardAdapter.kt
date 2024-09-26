package com.example.fetchhometest.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.fetchhometest.ui.entity.HiringItem
import androidx.recyclerview.widget.RecyclerView
import com.example.fetchhometest.databinding.ItemCardBinding


class ItemCardAdapter(private var items: List<HiringItem>) :
    RecyclerView.Adapter<ItemCardAdapter.ItemCardViewHolder>(){
    class ItemCardViewHolder(private val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HiringItem) {
            binding.idTextView.text = "ID: ${item.id}"
            binding.listIdTextView.text = "List ID: ${item.listId}"
            binding.nameTextView.text = "Name: ${item.name ?: "N/A"}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemCardViewHolder {
        val binding = ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemCardViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ItemCardViewHolder, position: Int) {
        holder.bind(items[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(newItems: List<HiringItem>){
        items = newItems
        notifyDataSetChanged()
    }

}