package com.example.completefeature.network

import com.example.completefeature.model.Post
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface ApiService {

    @GET("posts")
    suspend fun getPosts(): List<Post>

    // Add more API endpoints as needed

}