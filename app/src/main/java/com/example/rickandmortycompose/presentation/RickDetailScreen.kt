package com.example.rickandmortycompose.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.rickandmortycompose.R

@Composable
fun RickDetailScreen(
    characterId: String,
    rickDetailViewModel: RickDetailViewModel = hiltViewModel()
) {
    rickDetailViewModel.fetchCharacterById(characterId)
    val character = rickDetailViewModel.character.collectAsState().value

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        character?.let {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                AsyncImage(
                    model = it.image,
                    contentDescription = "character image",
                    modifier = Modifier
                        .size(250.dp)
                        .align(Alignment.CenterHorizontally)
                        .clip(RoundedCornerShape(15))
                        .border(4.dp, Color.Red, shape = RoundedCornerShape(0, 15, 0, 24)),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .padding(top = 50.dp)
                        .clip(RoundedCornerShape(6))
                        .background(Color(0x80000000)) // Fondo semitransparente para mejor lectura
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)

                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(text = "Nombre: ${it.name}", color = Color.White)
                        Text(
                            text = "Estado: ${if (it.isAlive) "Vivo" else "Muerto"}",
                            color = Color.White
                        )
                        Text(text = "Género: ${it.gender}", color = Color.White)
                        Text(text = "Especie: ${it.species}", color = Color.White)
                        Text(text = "Origen: ${it.origin}", color = Color.White)

                    }
                }
            }

            // Muestra otros datos del personaje aquí
        } ?: run {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "No se encontró el personaje", color = Color.White)
            }
        }
    }
}