package com.example.moviehub.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviehub.data.MovieRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class SearchViewModel(
    private val movieRepo: MovieRepo,
) : ViewModel() {
    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()


    val movieItem = query
        .combine(movieRepo.getMovies()) { query, movieList ->
            if (query.isEmpty()) {
                listOf()
            } else {
                movieList.filter { movieItem ->
                    movieItem.movieData.title.contains(query, ignoreCase = true)
                }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = listOf()
        )

    fun onQueryChange(query: String) {
        _query.value = query
    }

    fun updateMovieState(movieId: String, isBookmarked: Boolean) {
        movieRepo.updateMovieState(movieId, isBookmarked)
    }

//    fun setBookmark(movieId: String) {
//        movieRepo.setBookmark(movieId)
//    }

}