package com.example.project_mandiri

object barangManager {

    private val barangList = ArrayList<Barang>()

    fun addBarang(barang: Barang) {
        barangList.add(barang)
    }

    fun getBarangList(): ArrayList<Barang> {
        return barangList
    }

    fun getBarangById(id: String?): Barang? {
        return barangList.find { it.id == id }
    }
}
