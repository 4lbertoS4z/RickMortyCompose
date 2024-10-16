package com.example.rickandmortycompose.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.example.rickandmortycompose.data.RickRepository
import com.example.rickandmortycompose.presentation.model.CharacterModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class RickListViewModel @Inject constructor(rickRepository: RickRepository) : ViewModel() {
    private val searchQuery = MutableStateFlow("")

    // Emite la lista de personajes paginados, aplicando el filtro de búsqueda
    val characters: Flow<PagingData<CharacterModel>> = searchQuery
        .debounce(300) // Para evitar búsquedas innecesarias mientras el usuario escribe
        .flatMapLatest { query ->
            rickRepository.getAllCharacters().map { pagingData ->
                pagingData.filter { it.name.contains(query, ignoreCase = true) }
            }
        }
        .cachedIn(viewModelScope) // Mantiene la lista paginada en el ViewModel

    // Actualizar el texto de búsqueda
    fun updateSearchQuery(query: String) {
        searchQuery.value = query
    }
}