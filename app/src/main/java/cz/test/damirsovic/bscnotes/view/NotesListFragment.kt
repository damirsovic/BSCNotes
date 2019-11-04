package cz.test.damirsovic.bscnotes.view

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper

import cz.test.damirsovic.bscnotes.R
import cz.test.damirsovic.bscnotes.viewmodel.NotesListViewModel
import kotlinx.android.synthetic.main.notes_list_fragment.*

class NotesListFragment : Fragment() {

    companion object {
        fun newInstance() = NotesListFragment()
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var viewModel: NotesListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.notes_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NotesListViewModel::class.java)
        notesRecyclerView.layoutManager = LinearLayoutManager(context)
        viewModel.getData().observe(this, Observer { items ->
            notesRecyclerView.addItemDecoration(DividerItemDecoration(context!!, DividerItemDecoration.VERTICAL_LIST))
            val notesAdapter = NotesAdapter(items)
            notesRecyclerView.adapter = notesAdapter
            val itemTouchHelper = ItemTouchHelper(notesAdapter.simpleItemTouchCallback)
            itemTouchHelper.attachToRecyclerView(notesRecyclerView)
        })
    }


}
