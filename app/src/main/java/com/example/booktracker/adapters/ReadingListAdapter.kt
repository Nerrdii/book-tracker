package com.example.booktracker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.booktracker.R
import com.example.booktracker.data.Book
import com.squareup.picasso.Picasso

class ReadingListAdapter(private val listener: (id: Int) -> Unit) :
    ListAdapter<Book, ReadingListAdapter.ReadingListViewHolder>(
        ReadingListComparator()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReadingListViewHolder {
        return ReadingListViewHolder.create(parent)
    }

    // Bind activity data to view holder
    override fun onBindViewHolder(holder: ReadingListViewHolder, position: Int) {
        val current = getItem(position)
        // Load image url into image view
        val imageUrl = if (current.imageUrl?.isEmpty() == true) null else current.imageUrl
        Picasso.get().load(imageUrl).placeholder(R.mipmap.ic_default_book)
            .error(R.mipmap.ic_default_book).into(holder.coverImageView)
        holder.titleTextView.text = current.title
        holder.authorTextView.text = current.author

        // Listener for recycler view item
        holder.itemView.setOnClickListener {
            listener(current.id)
        }
    }

    class ReadingListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // View references
        val coverImageView: ImageView = itemView.findViewById(R.id.coverImageView)
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val authorTextView: TextView = itemView.findViewById(R.id.authorTextView)

        // Static method to create view holder for recycler view
        companion object {
            fun create(parent: ViewGroup): ReadingListViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.reading_list_recycler_view, parent, false)
                return ReadingListViewHolder(view)
            }
        }
    }

    // Used to efficiently update recycler view items
    class ReadingListComparator : DiffUtil.ItemCallback<Book>() {
        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem.id == newItem.id
        }
    }
}