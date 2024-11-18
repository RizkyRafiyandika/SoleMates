package com.example.project_mandiri


object cartManager {
    private val cartList = ArrayList<cart>()

    fun addBarang(cart: cart){
        cartList.add(cart)
    }

    fun getCartList (): ArrayList<cart> {
        return cartList
    }

    fun getCartByUserId (user : String): ArrayList<cart> {
        return cartList.filter { it.namacart == user } as ArrayList<cart>
    }

    fun update(barangID : String, userID: String, jumlahBaru: Int): Boolean {
        val barang = cartList.find { it.namacart == userID && it.idBarang == barangID }

        return if(barang != null){
            barang.quantitycart = jumlahBaru
            true
        } else {
            false
        }
    }

    fun updateJumlahBarang(barangID: String, userID: String, jumlahBaru: Int): Boolean {
        val barang = cartList.find { it.namacart == userID && it.idBarang == barangID }
        return if (barang != null) {
            barang.quantitycart += jumlahBaru
            true // Successfully updated
        } else {
            val newCart = cart()
            newCart.addCart(userID, jumlahBaru, barangID)
            addBarang(newCart)
            true // New item added to cart
        }
    }

    fun delete(cartID: Int): Boolean {
        val cartItem = cartList.find { it.id == cartID }
        return if(cartItem != null){
            cartList.remove(cartItem)
            true
        } else {
            false
        }
    }
}
