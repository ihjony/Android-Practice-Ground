package com.example.roomcodelab

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class WordRepository (private  val wordDao: WordDao){
    val allWords : LiveData<List<Word>> =wordDao.getAllWord()

    @WorkerThread
    suspend fun insert(word : Word){
        wordDao.insert(word)
    }
}