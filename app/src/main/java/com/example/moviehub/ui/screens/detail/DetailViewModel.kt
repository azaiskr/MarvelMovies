package com.example.moviehub.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviehub.data.MovieRepo
import com.example.moviehub.model.MovieData
import com.example.moviehub.model.MovieItem
import com.example.moviehub.ui.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel (
    private val movieRepository: MovieRepo
): ViewModel(){

    private val _detailUiState: MutableStateFlow<UiState<MovieItem>> =
        MutableStateFlow(UiState.Loading)
    val detailUiState: StateFlow<UiState<MovieItem>>
        get() = _detailUiState


    fun getMovieDetail(id: String){
        viewModelScope.launch {
            _detailUiState.value = UiState.Loading
            _detailUiState.value = UiState.Success(movieRepository.getDetailMovie(id))
        }
    }

    fun updateMovieState(movie: String, isBookmark: Boolean){
        viewModelScope.launch {
            movieRepository.updateMovieState(movie, isBookmark)
        }
    }

//    fun setBookmark(movie: MovieData, isBookmark: Boolean){
//        viewModelScope.launch {
//            movieRepository.updateMovieState(movie.id, isBookmark)
//        }
//    }

}