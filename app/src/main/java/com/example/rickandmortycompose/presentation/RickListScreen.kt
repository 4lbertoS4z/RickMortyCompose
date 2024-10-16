package com.example.rickandmortycompose.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.rickandmortycompose.R
import com.example.rickandmortycompose.presentation.model.CharacterModel
import com.example.rickandmortycompose.presentation.viewModel.RickListViewModel
import kotlinx.coroutines.launch

@Composable
fun RickListScreen(
    navController: NavHostController,
    rickListViewModel: RickListViewModel = hiltViewModel()
) {
    val characters = rickListViewModel.characters.collectAsLazyPagingItems()
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        when {
            //Carga inicial
            characters.loadState.refresh is LoadState.Loading && characters.itemCount == 0 -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(64.dp), color = Color.White
                    )
                }
            }
            //Estado vacio
            characters.loadState.refresh is LoadState.NotLoading && characters.itemCount == 0 -> {
                Text(text = "Todavía no hay personajes")
            }

            characters.loadState.hasError -> {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(Color.Red), contentAlignment = Alignment.Center
                ) {
                    Text(text = "Ha ocurrido un error")
                }
            }

            else -> {
                CharactersList(characters, listState,navController)
                if (characters.loadState.append is LoadState.Loading) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(64.dp), color = Color.White
                        )
                    }
                }
            }
        }

        // FloatingActionButton
        FloatingActionButton(
            onClick = {
                coroutineScope.launch {
                    listState.scrollToItem(0)
                }
            },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd)
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowUp,
                contentDescription = "Scroll to top"
            )
        }
    }
}

@Composable
fun CharactersList(characters: LazyPagingItems<CharacterModel>, listState: LazyListState, navController: NavHostController) {
    LazyColumn(state = listState) {
        items(characters.itemCount) {
            characters[it]?.let { characterModel ->
                ItemList(characterModel,navController)
            }
        }
    }
}

@Composable
fun ItemList(characterModel: CharacterModel, navController: NavHostController) {
    Box(
        modifier = Modifier
            .padding(34.dp)
            .clip(RoundedCornerShape(15))
            .border(4.dp, Color.Red, shape = RoundedCornerShape(0, 15, 0, 24))
            .fillMaxWidth()
            .height(250.dp)
            .clickable { navController.navigate("rickDetail/${characterModel.id}") },
        contentAlignment = Alignment.BottomCenter
    ) {
        AsyncImage(
            model = characterModel.image,
            contentDescription = "character image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.Black.copy(alpha = 0f),
                            Color.Black.copy(alpha = 0.5f),
                            Color.Black.copy(alpha = 0.7f)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(text = characterModel.name, color = Color.White, fontSize = 18.sp)
        }

    }
}