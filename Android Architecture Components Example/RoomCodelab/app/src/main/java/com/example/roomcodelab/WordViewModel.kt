package com.example.roomcodelab

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WordViewModel(application : Application) : AndroidViewModel(application)  {

    private val repository: WordRepository
    val allWords : LiveData<List<Word>>

    init {
        val wordDao = WordRoomDatabase.getDatabase(application, viewModelScope).wordDao()
        repository = WordRepository(wordDao)
        allWords = repository.allWords
    }

    fun insert (word: Word) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(word)
    }
}