package net.nshiba.roomchangequerydynamicallysample.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import net.nshiba.roomchangequerydynamicallysample.R
import net.nshiba.roomchangequerydynamicallysample.data.db.ArticleDao
import net.nshiba.roomchangequerydynamicallysample.data.db.ArticleDatabse
import net.nshiba.roomchangequerydynamicallysample.databinding.ActivityMainBinding
import net.nshiba.roomchangequerydynamicallysample.repository.ArticleRepository
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val articleAdapter = ArticleAdapter()

    private val articleDao: ArticleDao by lazy {
        ArticleDatabse.getDatabase(baseContext).articleDao()
    }

    private val viewModelFactory: ViewModelProvider.Factory by lazy {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val articleRepository = ArticleRepository(articleDao)
                return MainViewModel(articleRepository) as T
            }
        }
    }

    private val viewModel by viewModels<MainViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.articleList.layoutManager = LinearLayoutManager(this)
        binding.articleList.adapter = articleAdapter
        viewModel.insertDummyArticles()
        viewModel.availableArticles.observe(this, Observer {
            articleAdapter.submitList(it)
        })
        viewModel.startObserveTimer()
    }
}
