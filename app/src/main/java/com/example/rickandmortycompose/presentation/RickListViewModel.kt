package com.example.rickandmortycompose.presentation

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.rickandmortycompose.data.RickRepository
import com.example.rickandmortycompose.presentation.model.CharacterModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class RickListViewModel @Inject constructor(rickRepository: RickRepository) : ViewModel() {

    val characters: Flow<PagingData<CharacterModel>> = rickRepository.getAllCharacters()

}