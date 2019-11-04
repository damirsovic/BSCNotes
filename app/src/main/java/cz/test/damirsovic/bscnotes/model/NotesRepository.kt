package cz.test.damirsovic.bscnotes.model

import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotesRepository {
    private val notesData = MutableLiveData<ArrayList<Note>>()

    init{
        getNotes()
    }

    fun getItems(): MutableLiveData<ArrayList<Note>>{
        return notesData
    }

    fun getItem(position : Int) = notesData.value!!.get(position)

    fun addData(note:Note){
        notesData.value!!.add(note)
    }

    fun addData(note:Note, position: Int){
        notesData.value!!.add(position, note)
    }

    fun removeData(note:Note){
        notesData.value!!.remove(note)
    }

    fun getDataSize() = notesData.value!!.size

    fun getLastId() = notesData.value!!.get(notesData.value!!.size-1).id

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