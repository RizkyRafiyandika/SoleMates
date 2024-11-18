
package com.example.project_mandiri
class cart {
    var id: Int = 0
    var namacart: String = ""
    var quantitycart: Int = 0
    var idBarang: String = ""

    fun addCart(namacart: String, quantitycart: Int, idBarang: String) {
        this.namacart = namacart
        this.quantitycart = quantitycart
        this.idBarang = idBarang
    }
}