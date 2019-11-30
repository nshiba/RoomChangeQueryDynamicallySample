package net.nshiba.roomchangequerydynamicallysample.repository

import androidx.lifecycle.LiveData
import net.nshiba.roomchangequerydynamicallysample.data.db.Article
import net.nshiba.roomchangequerydynamicallysample.data.db.ArticleDao
import net.nshiba.roomchangequerydynamicallysample.utils.formatString
import org.threeten.bp.ZonedDateTime

class ArticleRepository(private val dao: ArticleDao) {

    fun fetchAvailableArticles(date: ZonedDateTime): LiveData<List<Article>> {
        return dao.selectAvailableArticles(date.formatString())
    }

    suspend fun replace(articles: List<Article>) {
        dao.replaceArticles(articles)
    }
}
