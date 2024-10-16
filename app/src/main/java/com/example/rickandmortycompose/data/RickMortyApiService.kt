package com.example.rickandmortycompose.data

import com.example.rickandmortycompose.data.response.CharacterResponse
import com.example.rickandmortycompose.data.response.ResponseWrapper
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickMortyApiService {

    @GET("/api/character/")
    suspend fun getCharacters(@Query("page") page: Int): ResponseWrapper

    @GET("/api/character/{id}")
    suspend fun getCharacterById(@Path("id") characterId: String): CharacterResponse

}