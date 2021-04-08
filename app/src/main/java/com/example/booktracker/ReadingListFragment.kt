package com.example.booktracker

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ReadingListFragment : Fragment() {
    private val viewModel: ReadingListViewModel by viewModels {
        ReadingListViewModelFactory((requireActivity().application as BookApplication).bookRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reading_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spinner: Spinner = view.findViewById(R.id.spinner)
        spinner.adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_dropdown_item, ReadingList.values())

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Log.d("DVS", ReadingList.values()[position].toString())
                viewModel.updateReadingList(ReadingList.values()[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        val adapter = ReadingListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        viewModel.books.observe(viewLifecycleOwner) { books ->
            books.let { adapter.submitList(it) }
        }
    }
}