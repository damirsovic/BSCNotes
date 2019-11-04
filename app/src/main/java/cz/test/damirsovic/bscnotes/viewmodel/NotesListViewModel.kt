package cz.test.damirsovic.bscnotes.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cz.test.damirsovic.bscnotes.model.DataApiService
import cz.test.damirsovic.bscnotes.model.Note
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotesListViewModel : ViewModel() {
    private val notesData = MutableLiveData<ArrayList<Note>>()

    init {
        getNotes()
    }

    fun getData(): MutableLiveData<ArrayList<Note>>{
        return notesData
    }

    private fun getNotes() {
        val service = DataApiService.create()
        val call = service.getUsers()
        call.enqueue(object : Callback<ArrayList<Note>> {
            override fun onResponse(call: Call<ArrayList<Note>>, response: Response<ArrayList<Note>>) {
                notesData.value = (response.body() as ArrayList<Note>?)!!
                System.out.println(response.body())
            }

            override fun onFailure(call: Call<ArrayList<Note>>, t: Throwable) {
                System.out.println(t.localizedMessage)
            }
        })
    }
}
