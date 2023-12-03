package com.example.moviehub.injection

import com.example.moviehub.data.MovieRepo

object Injection {
    fun provideRepository(): MovieRepo {
        return MovieRepo.getInstance()
    }
}