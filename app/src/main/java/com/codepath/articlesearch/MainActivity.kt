package com.codepath.articlesearch

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.articlesearch.databinding.ActivityMainBinding
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import kotlinx.serialization.json.Json
import okhttp3.Headers
import org.json.JSONException

fun createJson() = Json {
    isLenient = true
    ignoreUnknownKeys = true
    useAlternativeNames = false
}

private const val TAG = "MainActivity/"
private const val MOVIE_API_KEY = BuildConfig.API_KEY
//private const val ARTICLE_SEARCH_URL = "https://api.nytimes.com/svc/search/v2/articlesearch.json?api-key=${SEARCH_API_KEY}"
private const val POPULAR_MOVIES = "https://api.themoviedb.org/3/movie/popular?&language=en-US&api_key=${MOVIE_API_KEY}"

class MainActivity : AppCompatActivity() {
    private val movies = mutableListOf<Movie>()
    private lateinit var movieRecyclerView: RecyclerView
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        movieRecyclerView = findViewById(R.id.articles)

        movieRecyclerView.layoutManager = LinearLayoutManager(this).also {

            val dividerItemDecoration = DividerItemDecoration(this, it.orientation)
            movieRecyclerView.addItemDecoration(dividerItemDecoration)
        }

        val movieAdapter = MovieAdapter(this, movies)
        movieRecyclerView.adapter = movieAdapter


        val client = AsyncHttpClient()
        client.get(POPULAR_MOVIES, object : JsonHttpResponseHandler() {
            override fun onFailure( statusCode: Int, headers: Headers?, response: String?, throwable: Throwable?
            ) {
                Log.e(TAG, "Failed to fetch articles: $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.v(TAG, "Successfully fetched articles: ${json.jsonObject} ")
                try {
                    // TODO: Create the parsedJSON
                    val parsedJson = createJson().decodeFromString(
                        MovieResults.serializer(),
                        json.jsonObject.toString()
                    )
                    Log.v(TAG, "Parsed Json: ${parsedJson} ")

                    // TODO: Save the articles
                    parsedJson.results?.let { list ->
                        movies.addAll(list)

                        // TODO: Reload the screen
                        movieAdapter.notifyDataSetChanged()
                    }

                } catch (e: JSONException) {
                    Log.e(TAG, "Exception: $e")
                }
            }

})

    }
}