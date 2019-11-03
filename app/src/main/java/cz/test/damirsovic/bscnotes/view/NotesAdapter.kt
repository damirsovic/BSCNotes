package cz.test.damirsovic.bscnotes.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cz.test.damirsovic.bscnotes.R
import cz.test.damirsovic.bscnotes.model.Note
import kotlinx.android.synthetic.main.note_item.view.*

class NotesAdapter(private val items: List<Note>): RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : NotesViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_item, parent, false)
        return NotesViewHolder(v)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val item : Note = items[position]
        holder.idItem.text = item.id.toString()
        holder.titleItem.text = item.title
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class NotesViewHolder(private val view : View): RecyclerView.ViewHolder(view){
        val idItem = view.txtId
        val titleItem = view.txtTitle

    }
}