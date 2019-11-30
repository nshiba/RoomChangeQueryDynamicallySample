package net.nshiba.roomchangequerydynamicallysample.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Article::class],
    version = 1,
    exportSchema = true
)
abstract class ArticleDatabse : RoomDatabase() {

    abstract fun articleDao(): ArticleDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: ArticleDatabse? = null

        fun getDatabase(context: Context): ArticleDatabse {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ArticleDatabse::class.java,
                    "article_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
