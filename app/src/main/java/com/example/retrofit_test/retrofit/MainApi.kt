package com.example.retrofit_test.retrofit
import retrofit2.http.*

interface MainApi {
    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): Product

//    @POST("auth/login")
//    suspend fun auth(@Body authRequest: AuthRequest): User

    @GET("supplies")
    suspend fun getAllProducts(): List<Product>

    @GET("supplies_add_from_scan")
    suspend fun getProductsByName(@Query("searchText") name: String): List<Product>

    @GET("supplies_add_from_scan")
    suspend fun getSuppliesByName(@Query("q") name: String): List<Supply>
}


//mainUrl = https://dmdxstorage.herokuapp.com/api/supplies_add_from_scan