package com.example.projeto.db

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.projeto.dao.TitleDao
import com.example.projeto.entities.Title


class TitleRepository(private val titleDao: TitleDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allTitles: LiveData<List<Title>> = titleDao.getAlphabetizedTitles()

    fun getNotesByTitle(title: String): LiveData<List<Title>> {
        return titleDao.getNotesByTitle(title)
    }

    suspend fun insert(title: Title) {
        titleDao.insert(title)
    }

    suspend fun deleteAll(){
        titleDao.deleteAll()
    }

    suspend fun deleteByTitle(title: String){
        titleDao.deleteByTitle(title)
    }

    fun delete(title: Title){
        titleDao.delete(title)
    }

}