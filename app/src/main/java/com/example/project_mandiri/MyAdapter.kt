package com.example.project_mandiri

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class MyAdapter(private var catalogList: ArrayList<Barang>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    var onItemClick: ((Barang) -> Unit)? = null

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemName: TextView = itemView.findViewById(R.id.textView_item_name)
        val itemPrice: TextView = itemView.findViewById(R.id.textView_item_price)
        val itemImage: ImageView = itemView.findViewById(R.id.imageView_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_name, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return catalogList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = catalogList[position]
        holder.itemName.text = currentItem.nama
        val localeID = Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        numberFormat.maximumFractionDigits = 2
        val formattedPrice = numberFormat.format(currentItem.price)

        // Remove the currency symbol (Rp)
        val formattedPriceWithoutSymbol = formattedPrice.replace("Rp", "").trim()

        holder.itemPrice.text = "Rp$formattedPriceWithoutSymbol"

        // Use Glide to load the image from URL
        Glide.with(holder.itemView.context)
            .load(currentItem.gambar)
            .into(holder.itemImage)

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(currentItem)
        }
    }

    fun updateData(newCatalogList: ArrayList<Barang>) {
        catalogList = newCatalogList
        notifyDataSetChanged()
    }
}
