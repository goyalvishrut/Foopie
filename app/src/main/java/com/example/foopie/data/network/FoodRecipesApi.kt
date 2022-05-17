package com.example.foopie.data.network

import com.example.foopie.models.FoodJoke
import com.example.foopie.models.FoodRecipe
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface FoodRecipesApi {

    @GET("/recipes/complexSearch")
    suspend fun getRecipes(
        @QueryMap queries: Map<String, String>
    ): Response<FoodRecipe>


    @GET("/recipes/complexSearch")
    suspend fun searchRecipes(
        @QueryMap searchQuery: Map<String, String>
    ): Response<FoodRecipe>

    @GET("/food/jokes/random")
    suspend fun getFoodjoke(
        @Query("apiKey") apiKey: String
    ): Response<FoodJoke>
}