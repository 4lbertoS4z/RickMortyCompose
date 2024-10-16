package com.example.rickandmortycompose.data.response

import com.example.rickandmortycompose.presentation.model.CharacterModel
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.Locale

data class CharacterResponse(
    @SerializedName("id") val id:Int,
    @SerializedName("name") val name: String,
    @SerializedName("status") val status:String,
    @SerializedName("image") val image:String,
    @SerializedName("gender") val gender:String,
    @SerializedName("species") val species:String,
    @SerializedName("origin") val origin:Origin,
    @SerializedName("created") val created:String

){
    fun toPresentation(): CharacterModel {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val date = dateFormat.parse(created)
        val formattedDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(date)

        return CharacterModel(id=id,name=name,image=image,isAlive= status == "Alive",gender=gender,species=species, origin = origin.name, created = formattedDate)

    }
}
data class Origin(
    @SerializedName("name") val name: String
)