package cz.test.damirsovic.bscnotes.view

import android.app.AlertDialog
import android.graphics.*
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
import com.google.android.material.snackbar.Snackbar

import cz.test.damirsovic.bscnotes.R
import cz.test.damirsovic.bscnotes.events.OnItemClickListener
import cz.test.damirsovic.bscnotes.model.Note
import cz.test.damirsovic.bscnotes.viewmodel.NotesListViewModel
import kotlinx.android.synthetic.main.add_note.view.*
import kotlinx.android.synthetic.main.notes_list_fragment.*

class NotesListFragment : Fragment() {

    lateinit var notesAdapter : NotesAdapter

    companion object {
        fun newInstance() = NotesListFragment()
    }

    private val viewModel: NotesListViewModel by lazy{
         ViewModelProviders.of(this).get(NotesListViewModel::class.java)
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

                if (direction == ItemTouchHelper.LEFT || direction == ItemTouchHelper.RIGHT) {
                    val deletedNote = removeItem(viewHolder.adapterPosition)
                    // showing snack bar with Undo option
                    val snackbar = Snackbar.make(
                        notesRecyclerView,
                        " removed from Recyclerview!",
                        Snackbar.LENGTH_LONG
                    )
                    snackbar.setAction("UNDO") {
                        // undo is selected, restore the deleted item
                        restoreItem(deletedNote, position)
                    }
                    snackbar.setActionTextColor(Color.YELLOW)

                    snackbar.addCallback(object : Snackbar.Callback() {

                        override fun onDismissed(snackbar : Snackbar, event : Int) {
                            if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT) {
                                viewModel.removeDataReal(deletedNote)
                            }
                        }
                    })
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
                        paint.color = Color.parseColor("#D32F2F")
                        //paint.color = Color.parseColor("#388E3C")
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
                        //paint.color = Color.parseColor("#D32F2F")
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.notes_list_fragment, container, false)
    }

    var onItemClick :OnItemClickListener = object : OnItemClickListener{
        override fun onClick(note: Note) {
            showEditDialog(note)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.dataList.observe(this, Observer { items ->
            notesRecyclerView.layoutManager = LinearLayoutManager(context)
            notesAdapter = NotesAdapter(items, onItemClick)
            notesRecyclerView.adapter = notesAdapter
            notesRecyclerView.addItemDecoration(DividerItemDecoration(context!!, DividerItemDecoration.VERTICAL_LIST))
            val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
            itemTouchHelper.attachToRecyclerView(notesRecyclerView)
            addNoteButton.setOnClickListener { view ->
                val newId = viewModel.getLastId() + 1
                //Inflate the dialog with custom view
                val mDialogView = LayoutInflater.from(context!!).inflate(R.layout.add_note, null)
                mDialogView.txtId.text = newId.toString()
                //AlertDialogBuilder
                val mBuilder = AlertDialog.Builder(context!!)
                    .setView(mDialogView)
                    .setTitle("Add new note")
                //show dialog
                val  mAlertDialog = mBuilder.show()
                //Save button click
                mDialogView.btnSave.setOnClickListener {
                    //dismiss dialog
                    mAlertDialog.dismiss()
                    //get title from EditText
                    val newTitle = mDialogView.txtTitle.text.toString()
                    val newNote = Note(newId, newTitle)
                    addItem(newNote)
                }
                //cancel button click
                mDialogView.btnCancel.setOnClickListener {
                    //dismiss dialog
                    mAlertDialog.dismiss()
                }
            }
        })

    }

    fun showEditDialog(note : Note){
        //Inflate the dialog with custom view
        val mDialogView = LayoutInflater.from(context!!).inflate(R.layout.add_note, null)
        mDialogView.txtId.text = note.id.toString()
        mDialogView.txtTitle.setText(note.title)
        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(context!!)
            .setView(mDialogView)
            .setTitle("Edit note")
        //show dialog
        val  mAlertDialog = mBuilder.show()
        //Save button click
        mDialogView.btnSave.setOnClickListener {
            //dismiss dialog
            mAlertDialog.dismiss()
            //get title from EditText
            val newTitle = mDialogView.txtTitle.text.toString()
            val newNote = Note(note.id, newTitle)
            System.out.println("Old title: ${note.title}")
            System.out.println("New title: ${newTitle}")
            replaceItem(note, newNote)
        }
        //cancel button click
        mDialogView.btnCancel.setOnClickListener {
            //dismiss dialog
            mAlertDialog.dismiss()
        }
    }

    fun removeItem(position: Int): Note {
        val note = viewModel.getItem(position)
        viewModel.removeData(note)
        notesAdapter.notifyItemRemoved(position)
        //notesAdapter.notifyItemRangeChanged(position, viewModel.getDataSize())
        return note
    }

    fun restoreItem(note: Note, position: Int) {
        if(position < viewModel.getDataSize()) {
            viewModel.addData(note, position)
        }else{
            viewModel.addData(note)
        }
        // notify item added by position
        notesAdapter.notifyItemInserted(position)
    }

    fun addItem(note : Note){
        viewModel.saveNotes(note)
    }

    fun replaceItem(oldNote : Note, newNote: Note){
        val position = viewModel.replaceNote(oldNote, newNote)
        notesAdapter.notifyItemChanged(position)
    }
}
