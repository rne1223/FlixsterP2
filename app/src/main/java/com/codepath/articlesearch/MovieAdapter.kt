package com.codepath.articlesearch

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

const val ARTICLE_EXTRA = "ARTICLE_EXTRA"
private const val TAG = "ArticleAdapter"

class MovieAdapter(private val context: Context, private val movies: List<Movie>) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_article, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // TODO: Get the individual article and bind to holder
        val article = movies[position]
        holder.bind(article)
    }


    override fun getItemCount() = movies.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
            private val mediaImageView = itemView.findViewById<ImageView>(R.id.mediaImage)
            private val titleTextView = itemView.findViewById<TextView>(R.id.mediaTitle)
            private val overviewTextView = itemView.findViewById<TextView>(R.id.mediaAbstract)

            init {
                itemView.setOnClickListener(this)
            }

            // TODO: Write a helper method to help set up the onBindViewHolder method
            fun bind(movie: Movie) {
                titleTextView.text = movie.title
                overviewTextView.text = movie.overview

                Glide.with(context)
                    .load(movie.mediaImageUrl)
                    .into(mediaImageView)
            }

            override fun onClick(v: View?) {
                // TODO: Get selected article
                val movie = movies[absoluteAdapterPosition]

                // TODO: Navigate to Details screen and pass selected article
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra(ARTICLE_EXTRA, movie)
                context.startActivity(intent)
            }
    }

}