package com.example.rickandmortycompose.presentation.model

data class CharacterModel(
    val id: Int,
    val name: String,
    val isAlive: Boolean,
    val image: String,
    val gender: String,
    val species: String,
    val origin: String?,
    val created: String


)