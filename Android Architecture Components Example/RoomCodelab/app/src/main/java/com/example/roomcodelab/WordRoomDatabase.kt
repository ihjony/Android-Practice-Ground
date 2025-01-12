package com.example.roomcodelab

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Database(entities = [Word::class], version = 1)
abstract class WordRoomDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao

    companion object {
        @Volatile
        private var INSTANCE : WordRoomDatabase? =null

        fun getDatabase(context : Context,scope: CoroutineScope): WordRoomDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordRoomDatabase::class.java,

                    "Word_Database"
                ).addCallback(WordDatabaseCallback(scope = scope)).build()
                INSTANCE = instance
                return  instance
            }
        }
    }

    private class WordDatabaseCallback(private val scope:CoroutineScope): RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { wordRoomDatabase ->
                scope.launch( Dispatchers.IO ) {
                    populateDatabase(wordRoomDatabase.wordDao())
                }
            }
        }

        private suspend fun populateDatabase(wordDao: WordDao) {
            wordDao.deleteAll()
            var word = Word("Hello")
            wordDao.insert(word)
            word = Word("World!")
            wordDao.insert(word)

        }
    }

}
