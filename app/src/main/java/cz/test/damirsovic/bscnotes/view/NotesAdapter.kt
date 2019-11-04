package cz.test.damirsovic.bscnotes.view

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import cz.test.damirsovic.bscnotes.R
import cz.test.damirsovic.bscnotes.model.Note
import kotlinx.android.synthetic.main.note_item.view.*
import com.google.android.material.snackbar.Snackbar

class NotesAdapter(private val items: ArrayList<Note>): RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {
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

    fun removeItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, items.size)
    }

    fun restoreItem(note: Note, position: Int) {
        items.add(position, note)
        // notify item added by position
        notifyItemInserted(position)
    }

    val simpleItemTouchCallback =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            val paint = Paint()
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition

                if (direction == ItemTouchHelper.LEFT) {
                    val deletedModel = items!![position]
                    removeItem(position)
                    // showing snack bar with Undo option
                    val snackbar = Snackbar.make(
                        viewHolder.itemView.rootView,
                        " removed from Recyclerview!",
                        Snackbar.LENGTH_LONG
                    )
                    snackbar.setAction("UNDO") {
                        // undo is selected, restore the deleted item
                        restoreItem(deletedModel, position)
                    }
                    snackbar.setActionTextColor(Color.YELLOW)
                    snackbar.show()
                } else {
                    val deletedModel = items!![position]
                    removeItem(position)
                    // showing snack bar with Undo option
                    val snackbar = Snackbar.make(
                        viewHolder.itemView.rootView,
                        " removed from Recyclerview!",
                        Snackbar.LENGTH_LONG
                    )
                    snackbar.setAction("UNDO") {
                        // undo is selected, restore the deleted item
                        restoreItem(deletedModel, position)
                    }
                    snackbar.setActionTextColor(Color.YELLOW)
                    snackbar.show()
                }
            }

            override fun onChildDraw(
                canvas: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {

                val icon: Bitmap
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    val itemView = viewHolder.itemView
                    val height = itemView.bottom.toFloat() - itemView.top.toFloat()
                    val width = height / 3
                    icon = BitmapFactory.decodeResource(recyclerView.rootView.resources, R.drawable.delete)

                    if (dX > 0) {
                        paint.color = Color.parseColor("#388E3C")
                        val background =
                            RectF(
                                itemView.left.toFloat(),
                                itemView.top.toFloat(),
                                dX,
                                itemView.bottom.toFloat()
                            )
                        canvas.drawRect(background, paint)
                        val icon_dest = RectF(
                            itemView.left.toFloat() + width,
                            itemView.top.toFloat() + width,
                            itemView.left.toFloat() + 2 * width,
                            itemView.bottom.toFloat() - width
                        )
                        canvas.drawBitmap(icon, null, icon_dest, paint)
                    } else {
                        paint.color = Color.parseColor("#D32F2F")
                        val background = RectF(
                            itemView.right.toFloat() + dX,
                            itemView.top.toFloat(),
                            itemView.right.toFloat(),
                            itemView.bottom.toFloat()
                        )
                        canvas.drawRect(background, paint)
                        val icon_dest = RectF(
                            itemView.right.toFloat() - 2 * width,
                            itemView.top.toFloat() + width,
                            itemView.right.toFloat() - width,
                            itemView.bottom.toFloat() - width
                        )
                        canvas.drawBitmap(icon, null, icon_dest, paint)
                    }
                }
                super.onChildDraw(
                    canvas,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
        }

    class NotesViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val titleItem = view.txtTitle

        fun bind(item: Note) {
            titleItem.text = item.title
        }
    }
}