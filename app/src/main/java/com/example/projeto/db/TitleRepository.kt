package com.example.projeto.db

import androidx.lifecycle.LiveData
import com.example.projeto.dao.TitleDao
import com.example.projeto.entities.Title

class TitleRepository(private val titleDao: TitleDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allTitles: LiveData<List<Title>> = titleDao.getAlphabetizedTitles()

    suspend fun insert(title: Title) {
        titleDao.insert(title)

    }
}