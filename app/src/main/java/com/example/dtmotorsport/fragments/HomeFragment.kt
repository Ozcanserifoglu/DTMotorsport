package com.example.dtmotorsport.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dtmotorsport.R
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize RecyclerView and Adapter
        recyclerView = view.findViewById(R.id.recyclerView)
        adapter = NewsAdapter(emptyList()) { newsItem ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(newsItem.url))
            startActivity(intent)
        }
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // Fetch news
        lifecycleScope.launch {
            fetchNews()
        }
    }

    // News Adapter for RecyclerView
    class NewsAdapter(
        private var newsList: List<NewsItem>,
        private val onItemClick: (NewsItem) -> Unit
    ) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

        inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val title: TextView = itemView.findViewById(R.id.newsTitle)
            val description: TextView = itemView.findViewById(R.id.newsDescription)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_news_card, parent, false)
            return NewsViewHolder(view)
        }

        override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
            val newsItem = newsList[position]
            holder.title.text = newsItem.title
            holder.description.text = newsItem.description
            holder.itemView.setOnClickListener { onItemClick(newsItem) }
        }

        override fun getItemCount(): Int = newsList.size

        fun updateNews(newList: List<NewsItem>) {
            newsList = newList
            notifyDataSetChanged()
        }
    }

    // Data model for the news item
    data class NewsItem(
        val title: String,
        val description: String,
        val url: String,
        val publishedAt: String
    )

    // Retrofit service interface for API calls
    interface NewsApiService {
        @GET("news")
        suspend fun getNews(
            @Query("access_key") accessKey: String,
            @Query("keywords") keywords: String,
            @Query("countries") countries: String,
            @Query("category") category: String,
            @Query("languages") languages: String
        ): ApiResponse
    }

    data class ApiResponse(
        val data: List<NewsItem>,
        val error: String? = null
    )

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.mediastack.com/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(NewsApiService::class.java)

    // Fetch news from API
    private suspend fun fetchNews() {
        try {
            val response = service.getNews(
                accessKey = "c140f36cb97b50ba92a481b2087fa2f5",
                keywords = "DTM,",
                countries = "gb,de",
                category = "sports",
                languages = "en,de"
            )

            if (response.data.isEmpty()) {
                println("No news found.")
            } else {
                println("News fetched successfully: ${response.data.size} items")
                adapter.updateNews(response.data)
            }
        } catch (e: Exception) {
            println("Error fetching news: ${e.message}")
            e.printStackTrace()
        }
    }
}

