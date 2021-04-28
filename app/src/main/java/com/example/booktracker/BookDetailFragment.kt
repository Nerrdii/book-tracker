package com.example.booktracker

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.booktracker.utils.DateUtils
import com.example.booktracker.viewmodels.BookDetailViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookDetailFragment : Fragment() {
    private val viewModel: BookDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // View references
        val coverImageView: ImageView = view.findViewById(R.id.detailCoverImageView)
        val titleTextView: TextView = view.findViewById(R.id.detailTitleTextView)
        val authorTextView: TextView = view.findViewById(R.id.detailAuthorTextView)
        val publishedDateTextView: TextView = view.findViewById(R.id.detailPublishedDateTextView)
        val readingListTextView: TextView = view.findViewById(R.id.detailReadingListTextView)
        val startDateTextView: TextView = view.findViewById(R.id.detailStartDateTextView)
        val finishDateTextView: TextView = view.findViewById(R.id.detailFinishDateTextView)
        val ratingBar: RatingBar = view.findViewById(R.id.detailRatingBar)
        val reviewTextView: TextView = view.findViewById(R.id.detailReviewTextView)
        val editButton: Button = view.findViewById(R.id.detailEditButton)
        val deleteButton: Button = view.findViewById(R.id.detailDeleteButton)
        val shareReviewButton: Button = view.findViewById(R.id.shareReviewButton)

        // Using book from view model, populate layout with data
        viewModel.book.observe(viewLifecycleOwner) { book ->
            val imageUrl = if (book.imageUrl?.isEmpty() == true) null else book.imageUrl
            Picasso.get().load(imageUrl).placeholder(R.mipmap.ic_default_book).error(R.mipmap.ic_default_book).into(coverImageView)
            titleTextView.text = book.title
            authorTextView.text = book.author
            publishedDateTextView.text = DateUtils.dateToString(book.publishedDate)
            readingListTextView.text = book.readingList.toString()
            // Show certain views only if relevant
            if (book.startDate != null) {
                startDateTextView.visibility = View.VISIBLE
                startDateTextView.text = "Started reading ${DateUtils.dateToString(book.startDate)}"
            }
            if (book.finishDate != null) {
                finishDateTextView.visibility = View.VISIBLE
                finishDateTextView.text = "Finished reading ${DateUtils.dateToString(book.finishDate)}"
            }
            if (book.rating != null && book.rating > 0) {
                ratingBar.visibility = View.VISIBLE
                ratingBar.rating = book.rating.toFloat()
            }
            if (book.review != null && book.review.isNotEmpty()) {
                reviewTextView.visibility = View.VISIBLE
                reviewTextView.text = book.review
                shareReviewButton.visibility = View.VISIBLE
            }

            shareReviewButton.setOnClickListener {
                val intent = Intent(Intent.ACTION_SEND)
                intent.putExtra(Intent.EXTRA_TEXT, "I rated ${book.title} ${book.rating} stars. Review: ${book.review}")
                intent.type = "text/plain"

                startActivity(intent)
            }
        }

        editButton.setOnClickListener {
            // Get book ID from arguments
            val bookId = arguments?.getInt("bookId")
            val bundle = bundleOf("bookId" to bookId)
            // Navigate to edit book fragment with ID
            findNavController().navigate(R.id.action_bookDetailFragment_to_editBookFragment, bundle)
        }

        deleteButton.setOnClickListener {
            val alert = AlertDialog.Builder(requireContext())
            // Show alert for delete book confirmation
            with (alert) {
                setTitle("Delete")
                setMessage("Are you sure you want to delete this book?")
                setPositiveButton("Yes") { _, _ ->
                    viewModel.delete()
                    findNavController().navigateUp()
                }
                setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
                show()
            }
        }
    }
}
