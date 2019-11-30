package net.nshiba.roomchangequerydynamicallysample.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.nshiba.roomchangequerydynamicallysample.repository.ArticleRepository

class MainViewModelFactory(
    private val articleRepository: ArticleRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass
            .getConstructor(ArticleRepository::class.java)
            .newInstance(articleRepository)
    }
}
