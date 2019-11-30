package net.nshiba.roomchangequerydynamicallysample.data.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
abstract class ArticleDao {

    @Query(
        """
        SELECT
            *
        FROM
            articles
        WHERE
            starts_at <= :date
            AND :date <= ends_at
    """
    )
    abstract fun selectAvailableArticles(date: String): LiveData<List<Article>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertArticles(articles: List<Article>)

    @Query("DELETE FROM articles")
    abstract suspend fun deleteArticles()

    @Transaction
    open suspend fun replaceArticles(articles: List<Article>) {
        deleteArticles()
        insertArticles(articles)
    }
}
