package cz.test.damirsovic.bscnotes.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cz.test.damirsovic.bscnotes.model.DataApiService
import cz.test.damirsovic.bscnotes.model.Note
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotesListViewModel : ViewModel() {
    private val notesData = MutableLiveData<List<Note>>()

    init {
        getNotes()
    }

    fun getData(): List<Note>?{
        return notesData.value
    }

    private fun getNotes() {
        val service = DataApiService.create()
        val call = service.getUsers()
        call.enqueue(object : Callback<List<Note>> {
            override fun onResponse(call: Call<List<Note>>, response: Response<List<Note>>) {
                notesData.value = (response.body() as List<Note>?)!!
                System.out.println(response.body())
            }

            override fun onFailure(call: Call<List<Note>>, t: Throwable) {
                System.out.println(t.localizedMessage)
            }
        })
    }
}
