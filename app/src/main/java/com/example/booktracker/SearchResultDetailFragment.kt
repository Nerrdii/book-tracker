package com.example.booktracker

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.booktracker.utils.DateUtils
import com.example.booktracker.viewmodels.SearchResultDetailViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchResultDetailFragment : Fragment() {
    private val viewModel: SearchResultDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_result_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val coverImageView: ImageView = view.findViewById(R.id.searchResultCoverImageView)
        val titleTextView: TextView = view.findViewById(R.id.searchResultTitleTextView)
        val authorTextView: TextView = view.findViewById(R.id.searchResultAuthorTextView)
        val publishedDateTextView: TextView = view.findViewById(R.id.searchResultPublishedDateTextView)
        val descriptionTextView: TextView = view.findViewById(R.id.searchResultDescriptionTextView)
        val genreTextView: TextView = view.findViewById(R.id.searchResultGenreTextView)
        val addButton: Button = view.findViewById(R.id.searchResultAddButton)

        viewModel.book.observe(viewLifecycleOwner) { book ->
            val imageUrl = if (book.imageUrl?.isEmpty() == true) null else book.imageUrl
            Picasso.get().load(imageUrl).placeholder(R.mipmap.ic_default_book).error(R.mipmap.ic_default_book).into(coverImageView)
            titleTextView.text = book.title
            authorTextView.text = book.author
            publishedDateTextView.text = DateUtils.dateToString(book.publishedDate!!)
            if (book.description != null) {
                descriptionTextView.visibility = View.VISIBLE
                descriptionTextView.text = Html.fromHtml(book.description, Html.FROM_HTML_MODE_COMPACT)
            }
            if (book.genre != null) {
                genreTextView.visibility = View.VISIBLE
                genreTextView.text = book.genre
            }
        }

        addButton.setOnClickListener {
            val bundle = bundleOf("book" to viewModel.book.value)
            findNavController().navigate(R.id.action_searchResultDetailFragment_to_searchResultAddFragment, bundle)
        }
    }

}
