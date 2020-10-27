package com.example.projeto.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.projeto.dao.TitleDao
import com.example.projeto.entities.Title
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = arrayOf(Title::class), version = 4, exportSchema = false)
public abstract class TitleDB : RoomDatabase() {

    abstract fun titleDao(): TitleDao

    private class WordDataBaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var titleDao = database.titleDao()
                }
            }
        }
    }

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: TitleDB? = null

        fun getDatabase(context: Context, scope: CoroutineScope): TitleDB {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TitleDB::class.java,
                    "titles_database",
                )
                    //estratégia de destrução
                    //.fallbacktoDestructiveMigration()
                    .addCallback(WordDataBaseCallback(scope))
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}