package com.example.project_mandiri


import android.app.AlertDialog
import android.content.Context
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class CartAdapter(
    private val cartItems: ArrayList<cart>,
    private val context: Context,
    private val user: String
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private var totalHarga: Double = 0.0
    private var totalQuantity: Int = 0

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemNameTextView: TextView = itemView.findViewById(R.id.text_item_name)
        val itemPriceTextView: TextView = itemView.findViewById(R.id.text_item_price)
        val itemQuantityTextView: TextView = itemView.findViewById(R.id.text_item_quantity)
        val itemDelete: TextView = itemView.findViewById(R.id.button_delete)
        val itemQuantity: TextView = itemView.findViewById(R.id.button_quantity)
        val imageItem: ImageView = itemView.findViewById(R.id.image_cart)
        val totalPriceTextView: TextView = itemView.findViewById(R.id.total_price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
        return CartViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val currentItem = cartItems[position]
        val barang = barangManager.getBarangById(currentItem.idBarang)

        if (barang != null) {
            holder.itemNameTextView.text = barang.nama
            holder.itemPriceTextView.text = formatHarga(barang.price)
            holder.itemQuantityTextView.text = currentItem.quantitycart.toString()
            Glide.with(context)
                .load(barang?.gambar) // Sumber gambar, misalnya URL atau path lokal
                .into(holder.imageItem)


            val subTotal = currentItem.quantitycart * barang.price
            holder.totalPriceTextView.text = formatHarga(subTotal)
        }

        holder.itemDelete.setOnClickListener {
            cartManager.delete(currentItem.id)
            notifyDataSetChanged()
        }

        holder.itemQuantity.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Masukkan Jumlah Barang")

            val input = EditText(context)
            input.inputType = InputType.TYPE_CLASS_NUMBER
            builder.setView(input)

            builder.setPositiveButton("Ok") { dialog, _ ->
                val inputText = input.text.toString()
                if (inputText.isNotEmpty()) {
                    val quantity = inputText.toIntOrNull()
                    if (quantity != null && quantity > 0) {
                        cartManager.update(currentItem.idBarang, user, quantity)
                        updateTotal()
                        notifyItemChanged(holder.absoluteAdapterPosition)
                        Toast.makeText(context, "Anda memilih $quantity barang", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Masukkan jumlah barang yang valid (angka lebih besar dari 0)", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Masukkan jumlah barang", Toast.LENGTH_SHORT).show()
                }
                dialog.dismiss()
            }
            builder.setNegativeButton("Batal") { dialog, _ -> dialog.cancel() }
            builder.show()
        }
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }

    private fun formatHarga(price: Double): String {
        val localeID = Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        numberFormat.maximumFractionDigits = 2
        val formattedPrice = numberFormat.format(price)
        return formattedPrice.replace("Rp", "Rp ")
    }

    private fun updateTotal() {
        totalHarga = 0.0
        totalQuantity = 0
        for (item in cartItems) {
            val barang = barangManager.getBarangById(item.idBarang)
            if (barang != null) {
                totalHarga += barang.price * item.quantitycart
                totalQuantity += item.quantitycart
            }
        }
        notifyDataSetChanged()
    }
}
