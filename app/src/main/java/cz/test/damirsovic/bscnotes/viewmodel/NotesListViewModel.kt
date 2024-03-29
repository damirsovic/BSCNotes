package cz.test.damirsovic.bscnotes.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cz.test.damirsovic.bscnotes.model.DataApiService
import cz.test.damirsovic.bscnotes.model.Note
import cz.test.damirsovic.bscnotes.model.NotesRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotesListViewModel : ViewModel() {
    val repo = NotesRepository()
    val dataList = repo.getItems()

    fun addData(note: Note) {
        repo.addData(note)
    }

    fun addData(note: Note, position: Int) {
        repo.addData(note, position)
    }

    fun saveNotes(note: Note) {
        repo.saveNotes(note)
        addData(note)
    }

    fun removeData(note: Note) {
        repo.removeData(note)
    }

    fun removeDataReal(note: Note) {
        repo.removeDataReal(note)
    }

    fun replaceNote(oldNote: Note, newNote: Note): Int{
        return repo.replaceData(oldNote, newNote)

    }

    fun getItem(position: Int) : Note = repo.getItem(position)

    fun getDataSize() : Int = repo.getDataSize()

    fun getLastId() : Int = repo.getLastId()
}
