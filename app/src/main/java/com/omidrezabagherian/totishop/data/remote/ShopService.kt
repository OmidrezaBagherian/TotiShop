package com.omidrezabagherian.totishop.data.remote

import com.omidrezabagherian.totishop.domain.model.category.Category
import com.omidrezabagherian.totishop.domain.model.product.Product
import retrofit2.Response
import retrofit2.http.*

interface ShopService {
    @GET("products")
    suspend fun getProductList(
        @Query("consumer_key") consumerKey:String,
        @Query("consumer_secret") consumerSecret:String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @QueryMap filter: Map<String, String>
    ): Response<List<Product>>

    @GET("products/categories")
    suspend fun getCategoryList(
        @Query("consumer_key") consumerKey:String,
        @Query("consumer_secret") consumerSecret:String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ):Response<List<Category>>
}