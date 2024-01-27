package com.hocheol.newsapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.hocheol.newsapp.databinding.ActivityMainBinding
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var newsAdapter: NewsAdapter
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://news.google.com/")
        .addConverterFactory(
            TikXmlConverterFactory.create(
                TikXml.Builder()
                    .exceptionOnUnreadXml(false)
                    .build()
            )
        )
        .build()

    private val newsService = retrofit.create(NewsService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        setupChips()
        setupSearch()
        loadMainFeed()
    }

    private fun setupViews() {
        newsAdapter = NewsAdapter { url ->
            startActivity(
                Intent(this, WebViewActivity::class.java).apply {
                    putExtra("url", url)
                }
            )
        }
        binding.newsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = newsAdapter
        }
    }

    private fun setupChips() {
        val chipClickListener: (Chip) -> Unit = { chip ->
            binding.chipGroup.clearCheck()
            chip.isChecked = true
            when (chip.id) {
                R.id.feedChip -> loadMainFeed()
                R.id.politicsChip -> loadNews(newsService.politicsNews())
                R.id.economyChip -> loadNews(newsService.economyNews())
                R.id.societyChip -> loadNews(newsService.societyNews())
                R.id.itChip -> loadNews(newsService.itNews())
                R.id.sportChip -> loadNews(newsService.sportNews())
            }
        }
        binding.chipGroup.children.forEach { chip ->
            chip.setOnClickListener { chipClickListener(chip as Chip) }
        }
    }

    private fun setupSearch() {
        binding.searchTextInputEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                binding.chipGroup.clearCheck()
                hideKeyboard(v)

                val query = binding.searchTextInputEditText.text.toString()
                loadNews(newsService.search(query))

                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    private fun loadMainFeed() {
        loadNews(newsService.mainFeed())
    }

    private fun loadNews(call: Call<NewsRss>) {
        call.enqueue(object : Callback<NewsRss> {
            override fun onResponse(call: Call<NewsRss>, response: Response<NewsRss>) {
                val newsItems = response.body()?.channel?.items.orEmpty()
                val newsModels = newsItems.transform()

                newsAdapter.submitList(newsModels)
                binding.notFoundView.isVisible = newsModels.isEmpty()

                fetchImageUrls(newsModels)
            }

            override fun onFailure(call: Call<NewsRss>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun fetchImageUrls(newsModels: List<NewsModel>) {
        newsModels.forEachIndexed { index, newsModel ->
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val jsoup = Jsoup.connect(newsModel.link).get()
                    val elements = jsoup.select("meta[property^=og:]")
                    val ogImageNode = elements.find { node ->
                        node.attr("property") == "og:image"
                    }
                    newsModel.imageUrl = ogImageNode?.attr("content")
                    withContext(Dispatchers.Main) {
                        newsAdapter.notifyItemChanged(index)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun hideKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}