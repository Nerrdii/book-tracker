package com.example.booktracker

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.booktracker.data.Book
import com.example.booktracker.data.GoogleBook
import com.example.booktracker.data.ReadingList
import com.example.booktracker.viewmodels.SearchResultAddViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

@AndroidEntryPoint
class SearchResultAddFragment : Fragment() {
    private val viewModel: SearchResultAddViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_result_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val calendar = Calendar.getInstance()

        // Get book from arguments
        val googleBook = requireArguments().getSerializable("book")!! as GoogleBook

        // Populate spinner
        val spinner: Spinner = view.findViewById(R.id.searchResultAddReadingListSpinner)
        spinner.adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_dropdown_item, ReadingList.values())

        // View references
        val startDateEditText: EditText = view.findViewById(R.id.searchResultAddStartDateEditText)
        val finishDateEditText: EditText = view.findViewById(R.id.searchResultAddFinishDateEditText)
        val ratingBar: RatingBar = view.findViewById(R.id.searchResultAddRatingBar)
        val reviewEditText: EditText = view.findViewById(R.id.searchResultAddReviewEditText)
        val saveButton: Button = view.findViewById(R.id.searchResultAddSaveButton)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (ReadingList.values()[position]) {
                    ReadingList.WANT_TO_READ -> {
                        startDateEditText.visibility = View.GONE
                        finishDateEditText.visibility = View.GONE
                        ratingBar.visibility = View.GONE
                        reviewEditText.visibility = View.GONE
                    }
                    ReadingList.READING -> {
                        startDateEditText.visibility = View.VISIBLE
                        finishDateEditText.visibility = View.GONE
                        ratingBar.visibility = View.GONE
                        reviewEditText.visibility = View.GONE
                    }
                    ReadingList.READ -> {
                        startDateEditText.visibility = View.VISIBLE
                        finishDateEditText.visibility = View.VISIBLE
                        ratingBar.visibility = View.VISIBLE
                        reviewEditText.visibility = View.VISIBLE
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // Date picker dialog for date edit texts
        val date = DatePickerDialog.OnDateSetListener { datePickerView, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            view.findViewById<EditText>(datePickerView.tag as Int).setText(sdf.format(calendar.time))
        }

        // Listeners for date edit texts
        startDateEditText.setOnClickListener {
            val picker = DatePickerDialog(requireContext(), date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
            picker.datePicker.tag = R.id.searchResultAddStartDateEditText
            picker.datePicker.maxDate = System.currentTimeMillis()
            picker.show()
        }

        finishDateEditText.setOnClickListener {
            val picker = DatePickerDialog(requireContext(), date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
            picker.datePicker.tag = R.id.searchResultAddFinishDateEditText
            picker.datePicker.maxDate = System.currentTimeMillis()
            picker.show()
        }

        saveButton.setOnClickListener {
            val startDate = if (startDateEditText.text.isEmpty()) null else LocalDate.parse(startDateEditText.text.toString())
            val finishDate = if (finishDateEditText.text.isEmpty()) null else LocalDate.parse(finishDateEditText.text.toString())
            val author = googleBook.author ?: ""

            // Add new book to database using view model
            val book = Book(
                googleBook.title,
                author,
                LocalDate.parse(googleBook.publishedDate.toString()),
                googleBook.imageUrl,
                spinner.selectedItem as ReadingList,
                startDate,
                finishDate,
                ratingBar.rating.toInt(),
                reviewEditText.text.toString())
            viewModel.insert(book)
            findNavController().navigate(R.id.action_searchResultAddFragment_to_readingListFragment)
            Toast.makeText(requireActivity(), "Book added", Toast.LENGTH_LONG).show()
        }
    }

}
