package com.example.booktracker

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.*
import java.util.concurrent.Executors

class AddBookFragment : Fragment() {
    private val viewModel: AddBookViewModel by viewModels {
        AddBookViewModelFactory((requireActivity().application as BookApplication).bookRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_book, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val titleEditText: EditText = view.findViewById(R.id.titleEditText)
        val authorEditText: EditText = view.findViewById(R.id.publisherEditText)
        val publishedDateEditText: EditText = view.findViewById(R.id.publishedDateEditText)
        val addBookButton: Button = view.findViewById(R.id.addBookButton)

        addBookButton.setOnClickListener {
            val book = Book(titleEditText.text.toString(), authorEditText.text.toString(), LocalDate.parse(publishedDateEditText.text.toString()), ReadingList.WANT_TO_READ, "http://books.google.com/books/content?id=owkKhVCq6f0C&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api")
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