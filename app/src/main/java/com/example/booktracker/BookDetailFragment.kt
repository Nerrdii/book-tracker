package com.example.booktracker

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
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

        val coverImageView: ImageView = view.findViewById(R.id.detailCoverImageView)
        val titleTextView: TextView = view.findViewById(R.id.detailTitleTextView)
        val authorTextView: TextView = view.findViewById(R.id.detailAuthorTextView)
        val publishedDateTextView: TextView = view.findViewById(R.id.detailPublishedDateTextView)
        val editButton: Button = view.findViewById(R.id.detailEditButton)
        val deleteButton: Button = view.findViewById(R.id.detailDeleteButton)

        viewModel.book.observe(viewLifecycleOwner) { book ->
            val imageUrl = if (book.imageUrl?.isEmpty() == true) null else book.imageUrl
            Picasso.get().load(imageUrl).placeholder(R.mipmap.ic_default_book).error(R.mipmap.ic_default_book).into(coverImageView)
            titleTextView.text = book.title
            authorTextView.text = book.author
            publishedDateTextView.text = book.publishedDate.toString()
        }

        editButton.setOnClickListener {
            val bookId = arguments?.getInt("bookId")
            val bundle = bundleOf("bookId" to bookId)
            findNavController().navigate(R.id.action_bookDetailFragment_to_editBookFragment, bundle)
        }

        deleteButton.setOnClickListener {
            val alert = AlertDialog.Builder(requireContext())
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
