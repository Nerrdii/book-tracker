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
import com.example.booktracker.data.Activity
import com.example.booktracker.data.ActivityType
import com.example.booktracker.utils.DateUtils

class HomeListAdapter : ListAdapter<Activity, HomeListAdapter.HomeListViewHolder>(
    HomeListComparator()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeListViewHolder {
        return HomeListViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: HomeListViewHolder, position: Int) {
        val current = getItem(position)
        holder.activityTypeImageView.setImageResource(getIcon(current.type))
        holder.descriptionTextView.text = current.description
        holder.createdAtTextView.text = DateUtils.dateTimeToString(current.createdAt)
    }

    private fun getIcon(type: ActivityType): Int {
        return when (type) {
            ActivityType.INSERT -> R.drawable.ic_baseline_bookmark_add_24
            ActivityType.UPDATE -> R.drawable.ic_baseline_menu_book_24
            ActivityType.DELETE -> R.drawable.ic_baseline_bookmark_remove_24
        }
    }

    class HomeListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val activityTypeImageView: ImageView = itemView.findViewById(R.id.activityTypeImageView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        val createdAtTextView: TextView = itemView.findViewById(R.id.createdAtTextView)

        companion object {
            fun create(parent: ViewGroup): HomeListViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.home_list_recycler_view, parent, false)
                return HomeListViewHolder(view)
            }
        }
    }

    class HomeListComparator : DiffUtil.ItemCallback<Activity>() {
        override fun areItemsTheSame(oldItem: Activity, newItem: Activity): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Activity, newItem: Activity): Boolean {
            return oldItem.id == newItem.id
        }
    }
}
