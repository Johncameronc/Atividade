package com.example.atividade

import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("users")
    suspend fun getUsers(): List<Usuario>

    @GET("users/{id}/todos")
    suspend fun getUserTodos(@Path("id") userId : Int) : List<Tarefa>

}