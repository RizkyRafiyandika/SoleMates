package com.example.project_mandiri

import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import java.text.NumberFormat
import java.util.*

class DetailActivity : AppCompatActivity() {
    private lateinit var buyBTN: Button
    private lateinit var backBTN: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val barangID = intent.getStringExtra("ShoeID")
        val userID = intent.getStringExtra("userEmail")

        buyBTN = findViewById(R.id.button_buy)
        backBTN = findViewById(R.id.backBTN)

        buyBTN.setOnClickListener {
            val editText = EditText(this)
            editText.inputType = InputType.TYPE_CLASS_NUMBER
            val alertDialog = AlertDialog.Builder(this)
                .setTitle("Masukkan jumlah barang")
                .setMessage("Silakan masukkan jumlah Anda:")
                .setView(editText)
                .setPositiveButton("OK") { dialog, which ->
                    val inputString = editText.text.toString()
                    if (inputString.isEmpty()) {
                        Toast.makeText(this, "Mohon masukkan jumlah barang", Toast.LENGTH_SHORT).show()
                    } else {
                        val jumlahBarang = inputString.toIntOrNull()
                        if (jumlahBarang == null || jumlahBarang <= 0) {
                            Toast.makeText(this, "Jumlah barang harus lebih dari 0", Toast.LENGTH_SHORT).show()
                        } else {
                            if (barangID != null && userID != null) {
                                cartManager.updateJumlahBarang(barangID, userID, jumlahBarang)
                                Toast.makeText(this, "Barang berhasil ditambahkan ke keranjang", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(this, "Gagal menambahkan barang ke keranjang", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
                .setNegativeButton("Batal") { dialog, which ->
                    dialog.cancel()
                }
                .create()

            alertDialog.show()
        }

        backBTN.setOnClickListener {
            finish()
        }

        if (barangID != null) {
            val barang = barangManager.getBarangById(barangID)
            if (barang != null) {
                val catalogName: TextView = findViewById(R.id.tv_detail_title)
                val catalogImage: ImageView = findViewById(R.id.detail_image)
                val dataDesk: TextView = findViewById(R.id.tv_detail_desc)
                val catalogPrice: TextView = findViewById(R.id.tv_detail_price)

                catalogName.text = barang.nama
                Glide.with(this).load(barang.gambar).into(catalogImage)
                dataDesk.text = barang.desk
                val localeID = Locale("in", "ID")
                val numberFormat = NumberFormat.getCurrencyInstance(localeID)
                numberFormat.maximumFractionDigits = 2
                val formattedPrice = numberFormat.format(barang.price)
                val formattedPriceWithoutSymbol = formattedPrice.replace("Rp", "").trim()
                catalogPrice.text = "Rp${formattedPriceWithoutSymbol}"
            }
        }
    }
}
