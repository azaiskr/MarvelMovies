package com.example.moviehub.ui.screens.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviehub.data.MovieRepo
import com.example.moviehub.model.MovieItem
import com.example.moviehub.ui.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class BookmarkViewModel(
    private val repo: MovieRepo
): ViewModel() {
    private val _bookmarkUiState = MutableStateFlow<UiState<List<MovieItem>>>(UiState.Loading)
    val bookmarkUiState: StateFlow<UiState<List<MovieItem>>>
        get() = _bookmarkUiState

    fun showBookmarkedMovies() {
        viewModelScope.launch {
            repo.getBookmarkedMovies()
                .catch{
                    _bookmarkUiState.value = UiState.Error
                }
                .collect{movieItem ->
                    _bookmarkUiState.value = UiState.Success(movieItem)
                }
        }
    }

    fun updateMovieState(movieId: String, isBookmarked: Boolean) {
        viewModelScope.launch {
            repo.updateMovieState(movieId, isBookmarked)
        }
    }

//    fun setBookmark(id: String) {
//        viewModelScope.launch {
//            repo.setBookmark(id)
//        }
//    }

}