package com.example.moviehub.data

import com.example.moviehub.model.MovieItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class MovieRepo {
    private val movies = mutableListOf<MovieItem>()

    init {
        if (movies.isEmpty()) {
            FakeMoviesDataSource.dummyMovieData.forEach {
                movies.add(MovieItem(it, false))
            }
        }
    }

    fun getMovies(): Flow<List<MovieItem>> {
        return flowOf(movies)
    }

    fun getDetailMovie(id: String): MovieItem {
        return movies.first {
            it.movieData.id == id
        }
    }

    fun getBookmarkedMovies(): Flow<List<MovieItem>> {
        return getMovies().map { movies ->
            movies.filter { it.isBookmarked }
        }
    }

    fun setBookmark(id: String) {
        movies.find { it.movieData.id == id }?.let {
            it.isBookmarked = !it.isBookmarked
        }
    }

    companion object {
        @Volatile
        private var instance: MovieRepo? = null
        fun getInstance(): MovieRepo =
            instance ?: synchronized(this) {
                instance ?: MovieRepo().also { instance = it }
            }
    }

}