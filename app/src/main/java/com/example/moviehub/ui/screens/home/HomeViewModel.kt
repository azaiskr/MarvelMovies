package com.example.moviehub.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviehub.data.MovieRepo
import com.example.moviehub.model.MovieItem
import com.example.moviehub.ui.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel (
    private val movieRepository: MovieRepo
): ViewModel(){

    private val _homeUiState =
        MutableStateFlow<UiState<List<MovieItem>>>(UiState.Loading)
    val homeUiState = _homeUiState.asStateFlow()

//            StateFlow<UiState<List<MovieItem>>>
//        get() = _homeUiState

    fun getMovieList(){
        viewModelScope.launch {
            movieRepository.getMovies()
                .catch {
                    _homeUiState.value = UiState.Error
                }
                .collect{ movieItem->
                    _homeUiState.value = UiState.Success(movieItem)
                }
        }
    }

    fun updateMovieState(movieId: String, isBookmarked: Boolean){
        viewModelScope.launch {
            movieRepository.updateMovieState(movieId, isBookmarked).collect{isUpdated ->
                if(isUpdated){
                    getMovieList()
                }
            }
        }
    }
}