package com.example.booktracker

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.util.*

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

        val spinner: Spinner = view.findViewById(R.id.readingListSpinner)
        spinner.adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_dropdown_item, ReadingList.values())

        val titleEditText: EditText = view.findViewById(R.id.titleEditText)
        val authorEditText: EditText = view.findViewById(R.id.authorEditText)
        val publishedDateEditText: EditText = view.findViewById(R.id.publishedDateEditText)
        val imageUrlEditText: EditText = view.findViewById(R.id.imageUrlEditText)
        val startDateEditText: EditText = view.findViewById(R.id.startDateEditText)
        val finishDateEditText: EditText = view.findViewById(R.id.finishDateEditText)
        val ratingBar: RatingBar = view.findViewById(R.id.ratingBar)
        val addBookButton: Button = view.findViewById(R.id.addBookButton)

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
                        finishDateEditText.visibility = View.INVISIBLE
                        ratingBar.visibility = View.INVISIBLE
                    }
                    ReadingList.READING -> {
                        startDateEditText.visibility = View.VISIBLE
                        finishDateEditText.visibility = View.INVISIBLE
                        ratingBar.visibility = View.INVISIBLE
                    }
                    ReadingList.READ -> {
                        startDateEditText.visibility = View.VISIBLE
                        finishDateEditText.visibility = View.VISIBLE
                        ratingBar.visibility = View.VISIBLE
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        addBookButton.setOnClickListener {
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
                null)
            viewModel.insert(book)
            findNavController().navigate(R.id.action_addBookFragment_to_readingListFragment)
            Toast.makeText(requireActivity(), "Book added", Toast.LENGTH_SHORT).show()
        }

//        val publishedDateEditText: EditText = view.findViewById(R.id.publishedDateEditText)
//        val publishedDateCalendar: Calendar = Calendar.getInstance()
//
//        val date = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
//            with (publishedDateCalendar) {
//                set(Calendar.YEAR, year)
//                set(Calendar.MONTH, month)
//                set(Calendar.DAY_OF_MONTH, dayOfMonth)
//                updateLabel(view)
//            }
//        }
//
//        publishedDateEditText.setOnClickListener {
//            DatePickerDialog(requireActivity()).setOnDateSetListener(date)
//        }
    }

//    private fun updateLabel(view: View) {
//        view.text
//    }
}