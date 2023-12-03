package com.example.moviehub.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviehub.data.MovieRepo
import com.example.moviehub.ui.screens.bookmark.BookmarkViewModel
import com.example.moviehub.ui.screens.detail.DetailViewModel
import com.example.moviehub.ui.screens.home.HomeViewModel
import com.example.moviehub.ui.screens.search.SearchViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory (private val repo: MovieRepo): ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return HomeViewModel(repo) as T
        } else if(modelClass.isAssignableFrom(DetailViewModel::class.java)){
            return DetailViewModel(repo) as T
        } else if(modelClass.isAssignableFrom(SearchViewModel::class.java)){
            return SearchViewModel(repo) as T
        } else if (modelClass.isAssignableFrom(BookmarkViewModel::class.java)){
            return BookmarkViewModel(repo) as T
        }
        throw IllegalAccessException("Unknown ViewModel class" + modelClass.name)
    }
}