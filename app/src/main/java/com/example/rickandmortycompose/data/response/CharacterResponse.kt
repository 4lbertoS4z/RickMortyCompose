package com.example.rickandmortycompose.data.response

import com.example.rickandmortycompose.presentation.model.CharacterModel
import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    @SerializedName("id") val id:Int,
    @SerializedName("name") val name: String,
    @SerializedName("status") val status:String,
    @SerializedName("image") val image:String,
    @SerializedName("gender") val gender:String,
    @SerializedName("species") val species:String
){
    fun toPresentation(): CharacterModel {
        return CharacterModel(id=id,name=name,image=image,isAlive= status == "Alive",gender=gender,species=species)

    }
}