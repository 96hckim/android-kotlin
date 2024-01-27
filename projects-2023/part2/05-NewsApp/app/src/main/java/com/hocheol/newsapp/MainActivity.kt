package com.hocheol.newsapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.hocheol.newsapp.databinding.ActivityMainBinding
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        newsAdapter = NewsAdapter()

        binding.newsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = newsAdapter
        }

        val newsService = retrofit.create(NewsService::class.java)
        newsService.mainFeed().enqueue(object : Callback<NewsRss> {
            override fun onResponse(call: Call<NewsRss>, response: Response<NewsRss>) {
                Log.e(TAG, "items: ${response.body()?.channel?.items}")

                val newsItems = response.body()?.channel?.items.orEmpty()
                val newsModels = newsItems.transform()
                newsAdapter.submitList(newsModels)

                newsModels.forEachIndexed { index, newsModel ->
                    Thread {
                        try {
                            val jsoup = Jsoup.connect(newsModel.link).get()

                            val elements = jsoup.select("meta[property^=og:]")
                            val ogImageNode = elements.find { node ->
                                node.attr("property") == "og:image"
                            }

                            newsModel.imageUrl = ogImageNode?.attr("content")
                            Log.e(TAG, "link: ${newsModel.link}")
                            Log.e(TAG, "imageUrl: ${newsModel.imageUrl}")
                        } catch (e: Exception) {
                            e.printStackTrace()
                        } finally {
                            runOnUiThread {
                                newsAdapter.notifyItemChanged(index)
                            }
                        }
                    }.start()
                }
            }

            override fun onFailure(call: Call<NewsRss>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}