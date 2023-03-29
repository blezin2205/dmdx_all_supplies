package com.example.retrofit_test

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofit_test.adapter.ProductAdapter
import com.example.retrofit_test.databinding.ActivityMainBinding
import com.example.retrofit_test.retrofit.MainApi
import com.example.retrofit_test.retrofit.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import android.widget.SearchView.OnQueryTextListener
import com.example.retrofit_test.retrofit.Supply

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: ProductAdapter
    lateinit var binding: ActivityMainBinding
    lateinit var supplies: List<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ProductAdapter()
        binding.rcView.layoutManager = LinearLayoutManager(this)
        binding.rcView.adapter = adapter


        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://dmdxstorage.herokuapp.com/api/").client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
        val mainApi = retrofit.create(MainApi::class.java)

        CoroutineScope(Dispatchers.IO).launch {

            supplies = mainApi.getAllProducts().filter { it.name != null }.sortedBy { it.name }
            runOnUiThread {
                binding.apply {
                    adapter.submitList(supplies)
                    binding.progressBar.setVisibility(View.GONE)
                }
            }
        }

        binding.sv.setOnQueryTextListener(object : OnQueryTextListener{
            override fun onQueryTextSubmit(text: String?): Boolean {
                    if (text != null) {
                        val list = supplies.filter { it.name.contains(text, ignoreCase = true) }.sortedBy { it.name }
                        binding.apply {
                            adapter.submitList(list)
                        }
                    } else {
                        binding.apply {
                            adapter.submitList(supplies)
                        }
                    }
                return true
            }

            override fun onQueryTextChange(text: String?): Boolean {
                if (text != null) {
                        val list = supplies.filter { it.name.contains(text, ignoreCase = true) }.sortedBy { it.name }
                        binding.apply {
                            adapter.submitList(list)
                        }
                    } else {
                        binding.apply {
                            adapter.submitList(supplies)
                        }
                    }
                return true
            }
        })
    }
}





