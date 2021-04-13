package com.example.booktracker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.booktracker.R
import com.example.booktracker.data.GoogleBook
import com.squareup.picasso.Picasso

class SearchResultsAdapter : ListAdapter<GoogleBook, SearchResultsAdapter.SearchResultsViewHolder>(SearchResultsDiffCallback()) {
    override fun onBindViewHolder(holder: SearchResultsViewHolder, position: Int) {
        val current = getItem(position)
        Picasso.get().load(current.imageUrl).error(R.mipmap.ic_default_book).into(holder.coverImageView)
        holder.titleTextView.text = current.title
        holder.authorTextView.text = current.author
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultsViewHolder {
        return SearchResultsViewHolder.create(parent)
    }

    class SearchResultsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val coverImageView: ImageView = itemView.findViewById(R.id.searchResultsCoverImageView)
        val titleTextView: TextView = itemView.findViewById(R.id.searchResultsTitleTextView)
        val authorTextView: TextView = itemView.findViewById(R.id.searchResultsAuthorTextView)

        companion object {
            fun create(parent: ViewGroup): SearchResultsViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.search_results_recycler_view, parent, false)
                return SearchResultsViewHolder(view)
            }
        }
    }
}

class SearchResultsDiffCallback : DiffUtil.ItemCallback<GoogleBook>() {
    override fun areContentsTheSame(oldItem: GoogleBook, newItem: GoogleBook): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areItemsTheSame(oldItem: GoogleBook, newItem: GoogleBook): Boolean {
        return oldItem == newItem
    }
}
