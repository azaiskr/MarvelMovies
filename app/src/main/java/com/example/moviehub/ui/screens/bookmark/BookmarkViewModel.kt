package com.example.moviehub.ui.screens.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviehub.data.MovieRepo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BookmarkViewModel(
    private val repo: MovieRepo,
) : ViewModel() {

   val movieItem = repo.getMovies().map { movies ->
        movies.filter { it.isBookmarked }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5),
        initialValue = listOf()
    )

    fun updateMovieState(movieId: String, isBookmarked: Boolean) {
        viewModelScope.launch {
            repo.updateMovieState(movieId, isBookmarked)
        }
    }

}