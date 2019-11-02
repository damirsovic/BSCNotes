package cz.test.damirsovic.bscnotes.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cz.test.damirsovic.bscnotes.model.Note

class NotesAdapter(private val myDataset: List<Note>): RecyclerView.Adapter<NotesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : RecyclerViewAdapter.ViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.data_item,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerViewAdapter.ViewHolder, position: Int) {
        val item : DataModel = items[position]
        holder.descriptionItem.text = String.format("%s %d", item.name, item.number)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(private val view : View): RecyclerView.ViewHolder(view){
        val descriptionItem = view.itemDescription

    }
}