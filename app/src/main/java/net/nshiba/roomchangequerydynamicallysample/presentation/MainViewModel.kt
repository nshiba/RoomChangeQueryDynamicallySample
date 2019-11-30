package net.nshiba.roomchangequerydynamicallysample.presentation

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import net.nshiba.roomchangequerydynamicallysample.data.db.Article
import net.nshiba.roomchangequerydynamicallysample.repository.ArticleRepository
import net.nshiba.roomchangequerydynamicallysample.utils.ZonedDateTimeUtils
import net.nshiba.roomchangequerydynamicallysample.utils.formatString
import org.threeten.bp.ZonedDateTime
import timber.log.Timber
import java.lang.Exception
import java.util.*
import kotlin.concurrent.timer

class MainViewModel(private val articleRepository: ArticleRepository) : ViewModel() {

    private var timer: Timer? = null

    private val queryTime = MutableLiveData<ZonedDateTime>(ZonedDateTimeUtils.now())

    val availableArticles: LiveData<List<Article>> = Transformations.switchMap(queryTime) {
        try {
            articleRepository.fetchAvailableArticles(it)
        } catch (e: Exception) {
            Timber.e(e)
            null
        }
    }

    fun startObserveTimer() {
        timer = timer(period = 500) {
            queryTime.postValue(ZonedDateTimeUtils.now())
        }
    }

    fun stopObserveTimer() {
        timer?.cancel()
        timer = null
    }

    fun insertDummyArticles() {
        val now = ZonedDateTimeUtils.now()
        val articles = listOf(
            Article(
                id = 1,
                title = "article 1",
                description = "description 1",
                startsAt = now.formatString(),
                endsAt = now.plusSeconds(10L).formatString()
            ),
            Article(
                id = 2,
                title = "article 2",
                description = "description 2",
                startsAt = now.plusSeconds(3L).formatString(),
                endsAt = now.plusSeconds(5L).formatString()
            ),
            Article(
                id = 3,
                title = "article 3",
                description = "description 3",
                startsAt = now.plusSeconds(6L).formatString(),
                endsAt = now.plusSeconds(16L).formatString()
            ),
            Article(
                id = 4,
                title = "article 4",
                description = "description 4",
                startsAt = now.plusSeconds(9L).formatString(),
                endsAt = now.plusSeconds(12L).formatString()
            ),
            Article(
                id = 5,
                title = "article 5",
                description = "description 5",
                startsAt = now.plusSeconds(13L).formatString(),
                endsAt = now.plusSeconds(18L).formatString()
            )
        )

        viewModelScope.launch {
            try {
                articleRepository.replace(articles)
            } catch (e: Exception) {
                // do nothing
                Timber.e(e)
            }
        }
    }
}
