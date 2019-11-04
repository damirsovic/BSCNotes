package cz.test.damirsovic.bscnotes.view

import android.graphics.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import cz.test.damirsovic.bscnotes.R
import cz.test.damirsovic.bscnotes.model.Note
import kotlinx.android.synthetic.main.note_item.view.*
import com.google.android.material.snackbar.Snackbar

class NotesAdapter(val items: ArrayList<Note>): RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : NotesViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_item, parent, false)
        return NotesViewHolder(v)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val note: Note = items[position]
        holder.bind(note)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class NotesViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val titleItem = view.txtTitle

        fun bind(item: Note) {
            titleItem.text = item.title
            System.out.println(item.title)
        }
    }
}