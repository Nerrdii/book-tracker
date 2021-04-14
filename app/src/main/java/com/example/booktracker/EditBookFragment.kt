package com.example.booktracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.booktracker.data.Book
import com.example.booktracker.data.ReadingList
import com.example.booktracker.viewmodels.EditBookViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

@AndroidEntryPoint
class EditBookFragment : Fragment() {
    private val viewModel: EditBookViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_book, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spinner: Spinner = view.findViewById(R.id.readingListSpinner)
        spinner.adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_dropdown_item, ReadingList.values())

        val titleEditText: EditText = view.findViewById(R.id.titleEditText)
        val authorEditText: EditText = view.findViewById(R.id.authorEditText)
        val publishedDateEditText: EditText = view.findViewById(R.id.publishedDateEditText)
        val imageUrlEditText: EditText = view.findViewById(R.id.imageUrlEditText)
        val startDateEditText: EditText = view.findViewById(R.id.startDateEditText)
        val finishDateEditText: EditText = view.findViewById(R.id.finishDateEditText)
        val ratingBar: RatingBar = view.findViewById(R.id.ratingBar)
        val reviewEditText: EditText = view.findViewById(R.id.reviewEditText)
        val editBookButton: Button = view.findViewById(R.id.editBookButton)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (ReadingList.values()[position]) {
                    ReadingList.WANT_TO_READ -> {
                        startDateEditText.visibility = View.INVISIBLE
                        startDateEditText.text.clear()
                        finishDateEditText.visibility = View.INVISIBLE
                        finishDateEditText.text.clear()
                        ratingBar.visibility = View.INVISIBLE
                        ratingBar.rating = 0F
                        reviewEditText.visibility = View.INVISIBLE
                        reviewEditText.text.clear()
                    }
                    ReadingList.READING -> {
                        startDateEditText.visibility = View.VISIBLE
                        startDateEditText.text.clear()
                        finishDateEditText.visibility = View.INVISIBLE
                        finishDateEditText.text.clear()
                        ratingBar.visibility = View.INVISIBLE
                        ratingBar.rating = 0F
                        reviewEditText.visibility = View.INVISIBLE
                        reviewEditText.text.clear()
                    }
                    ReadingList.READ -> {
                        startDateEditText.visibility = View.VISIBLE
                        startDateEditText.text.clear()
                        finishDateEditText.visibility = View.VISIBLE
                        finishDateEditText.text.clear()
                        ratingBar.visibility = View.VISIBLE
                        ratingBar.rating = 0F
                        reviewEditText.visibility = View.VISIBLE
                        reviewEditText.text.clear()
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        editBookButton.setOnClickListener {
            val startDate = if (startDateEditText.text.isEmpty()) null else LocalDate.parse(startDateEditText.text.toString())
            val finishDate = if (finishDateEditText.text.isEmpty()) null else LocalDate.parse(finishDateEditText.text.toString())

            val book = Book(
                titleEditText.text.toString(),
                authorEditText.text.toString(),
                LocalDate.parse(publishedDateEditText.text.toString()),
                imageUrlEditText.text.toString(),
                spinner.selectedItem as ReadingList,
                startDate,
                finishDate,
                ratingBar.rating.toInt(),
                reviewEditText.text.toString(),
                requireArguments().getInt("bookId"))
            viewModel.update(book)
            findNavController().navigate(R.id.action_editBookFragment_to_readingListFragment)
            Toast.makeText(requireActivity(), "Book updated", Toast.LENGTH_SHORT).show()
        }

        viewModel.book.observe(viewLifecycleOwner) { book ->
            val rating = if (book.rating == null) 0F else book.rating.toFloat()
            val startDate = if (book.startDate == null) "" else book.startDate.toString()
            val finishDate = if (book.finishDate == null) "" else book.finishDate.toString()
            titleEditText.setText(book.title)
            authorEditText.setText(book.author)
            publishedDateEditText.setText(book.publishedDate.toString())
            imageUrlEditText.setText(book.imageUrl)
            startDateEditText.setText(startDate)
            finishDateEditText.setText(finishDate)
            ratingBar.rating = rating
            reviewEditText.setText(book.review)
            spinner.setSelection(book.readingList.ordinal)
        }
    }
}
