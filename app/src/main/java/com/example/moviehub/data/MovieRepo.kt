package com.example.moviehub.data

import com.example.moviehub.model.MovieItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

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

    fun updateMovieState(movieId: String, isBookmarked: Boolean): Flow<Boolean>{
        val index = movies.indexOfFirst {
            it.movieData.id == movieId
        }
        val result = if (index >= 0) {
            movies[index] = movies[index].copy(isBookmarked = isBookmarked)
            true
        } else {
            false
        }
        return flowOf(result)
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