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
import com.example.booktracker.data.ReadingList
import com.example.booktracker.viewmodels.AddBookViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

// Annotation allows activities/fragments to use dependency injection for getting associated view models
@AndroidEntryPoint
class AddBookFragment : Fragment() {
    private val viewModel: AddBookViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_book, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val calendar = Calendar.getInstance()

        // Populate spinner with reading list options
        val spinner: Spinner = view.findViewById(R.id.readingListSpinner)
        spinner.adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_dropdown_item, ReadingList.values())

        // View references
        val titleEditText: EditText = view.findViewById(R.id.titleEditText)
        val authorEditText: EditText = view.findViewById(R.id.authorEditText)
        val publishedDateEditText: EditText = view.findViewById(R.id.publishedDateEditText)
        val imageUrlEditText: EditText = view.findViewById(R.id.imageUrlEditText)
        val startDateEditText: EditText = view.findViewById(R.id.startDateEditText)
        val finishDateEditText: EditText = view.findViewById(R.id.finishDateEditText)
        val ratingBar: RatingBar = view.findViewById(R.id.ratingBar)
        val reviewEditText: EditText = view.findViewById(R.id.reviewEditText)
        val addBookButton: Button = view.findViewById(R.id.addBookButton)

        // Listener for reading list spinner to update which views are visible based on selection
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

        // Listener for date picker dialog to set input on correct text view
        val date = DatePickerDialog.OnDateSetListener { datePickerView, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            view.findViewById<EditText>(datePickerView.tag as Int).setText(sdf.format(calendar.time))
        }

        // Show date picker when clicking on edit text
        startDateEditText.setOnClickListener {
            val picker = DatePickerDialog(requireContext(), date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
            picker.datePicker.tag = R.id.startDateEditText
            picker.datePicker.maxDate = System.currentTimeMillis()
            picker.show()
        }

        finishDateEditText.setOnClickListener {
            val picker = DatePickerDialog(requireContext(), date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
            picker.datePicker.tag = R.id.finishDateEditText
            picker.datePicker.maxDate = System.currentTimeMillis()
            picker.show()
        }

        publishedDateEditText.setOnClickListener {
            val picker = DatePickerDialog(requireContext(), date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
            picker.datePicker.tag = R.id.publishedDateEditText
            picker.datePicker.maxDate = System.currentTimeMillis()
            picker.show()
        }

        addBookButton.setOnClickListener {
            val startDate = if (startDateEditText.text.isEmpty()) null else LocalDate.parse(startDateEditText.text.toString())
            val finishDate = if (finishDateEditText.text.isEmpty()) null else LocalDate.parse(finishDateEditText.text.toString())

            // Validation
            when {
                titleEditText.text.isEmpty() -> {
                    titleEditText.error = "Please enter a title"
                }
                authorEditText.text.isEmpty() -> {
                    authorEditText.error = "Please enter an author"
                }
                publishedDateEditText.text.isEmpty() -> {
                    publishedDateEditText.error = "Please enter a published date"
                }
                else -> {
                    // Add book using view model
                    val book = Book(
                        titleEditText.text.toString(),
                        authorEditText.text.toString(),
                        LocalDate.parse(publishedDateEditText.text.toString()),
                        imageUrlEditText.text.toString(),
                        spinner.selectedItem as ReadingList,
                        startDate,
                        finishDate,
                        ratingBar.rating.toInt(),
                        reviewEditText.text.toString())
                    viewModel.insert(book)
                    // Using navigation component, go to reading list fragment
                    findNavController().navigate(R.id.action_addBookFragment_to_readingListFragment)
                    Toast.makeText(requireActivity(), "Book added", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
