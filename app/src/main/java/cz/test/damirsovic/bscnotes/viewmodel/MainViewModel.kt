package cz.test.damirsovic.bscnotes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cz.test.damirsovic.bscnotes.model.Note

class MainViewModel: ViewModel() {
    private val notesData: MutableLiveData<List<Note>> by lazy {
        MutableLiveData<List<Note>>().also {
            loadNotes()
        }
    }

    fun getNotes(): LiveData<List<Note>> {
        return notesData
    }

    fun saveNote(note: Note){

    }

    fun deleteNote(note: Note){

    }

    private fun loadNotes() {

    }

    override fun onCleared() {

        super.onCleared()
    }
}