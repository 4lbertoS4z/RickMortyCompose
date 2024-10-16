package com.example.rickandmortycompose.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortycompose.data.RickRepository
import com.example.rickandmortycompose.presentation.model.CharacterModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RickDetailViewModel @Inject constructor(private val repository: RickRepository) : ViewModel() {
    private val _character = MutableStateFlow<CharacterModel?>(null)
    val character: StateFlow<CharacterModel?> = _character.asStateFlow()

    fun fetchCharacterById(characterId: String) {
        viewModelScope.launch {
            val result = repository.getCharacterById(characterId)
            _character.value = result
        }
    }
}