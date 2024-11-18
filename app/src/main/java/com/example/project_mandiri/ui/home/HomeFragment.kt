package com.example.project_mandiri.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.project_mandiri.Barang
import com.example.project_mandiri.DetailActivity
import com.example.project_mandiri.MyAdapter
import com.example.project_mandiri.barangManager
import com.example.project_mandiri.databinding.FragmentHomeBinding
import org.json.JSONException
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {

    private lateinit var requestQueue: RequestQueue
    private lateinit var myAdapter: MyAdapter
    private var userEmail: String = ""
    private lateinit var searchList: ArrayList<Barang>
    private lateinit var searchView: SearchView
    private lateinit var binding: FragmentHomeBinding
    private lateinit var dataList: ArrayList<Barang>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        userEmail = activity?.intent?.getStringExtra("userEmail").orEmpty()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newRecyclerView: RecyclerView = binding.recyclerviewHome
        searchView = binding.search

        newRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        dataList = ArrayList()
        searchList = ArrayList()
        myAdapter = MyAdapter(searchList)
        newRecyclerView.adapter = myAdapter

        requestQueue = Volley.newRequestQueue(requireContext())
        fetchBarangData()

        searchView.clearFocus()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchList.clear()
                val searchText = newText?.toLowerCase(Locale.getDefault())
                if (searchText.isNullOrEmpty()) {
                    searchList.addAll(dataList)
                } else {
                    dataList.forEach {
                        if (it.nama.toLowerCase(Locale.getDefault()).contains(searchText)) {
                            searchList.add(it)
                        }
                    }
                }
                myAdapter.notifyDataSetChanged()
                return false
            }
        })
    }

    private fun fetchBarangData() {
        val url = "https://api.npoint.io/d81ab7a1516e86f4e945"
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    for (i in 0 until response.length()) {
                        val item = response.getJSONObject(i)
                        val id = item.getString("ShoeID")
                        val nama = item.getString("ShoeBrand")
                        val gambar = item.getString("ShoeImage")
                        val harga = item.getDouble("ShoePrice")
                        val deskripsi = item.getString("ShoeDescription")

                        val barang = Barang(id, nama, gambar, deskripsi, harga)
                        barangManager.addBarang(barang)
                        dataList.add(barang)
                    }

                    searchList.addAll(dataList)
                    myAdapter.notifyDataSetChanged()
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Log.e("HomeFragment", "JSON Parsing error: ${e.message}")
                }
            },
            { error ->
                error.printStackTrace()
                Log.e("HomeFragment", "Volley error: ${error.message}")
            }
        )

        myAdapter.onItemClick = { barang ->
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("ShoeID", barang.id)
            intent.putExtra("userEmail", userEmail)
            startActivity(intent)
        }

        requestQueue.add(jsonArrayRequest)
    }
}
