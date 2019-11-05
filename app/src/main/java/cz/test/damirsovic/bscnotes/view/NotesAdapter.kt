package cz.test.damirsovic.bscnotes.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cz.test.damirsovic.bscnotes.model.Note
import kotlinx.android.synthetic.main.note_item.view.*
import android.widget.AdapterView
import cz.test.damirsovic.bscnotes.events.OnItemClickListener


class NotesAdapter(val items: ArrayList<Note>, val onClickListener: OnItemClickListener): RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : NotesViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(cz.test.damirsovic.bscnotes.R.layout.note_item, parent, false)
        return NotesViewHolder(v)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val note: Note = items[position]
        holder.bind(note)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class NotesViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        val titleItem = view.txtTitle

        fun bind(note: Note){

            titleItem.text = note.title
            titleItem.setOnClickListener { onClickListener.onClick(note)}
        }
    }
}