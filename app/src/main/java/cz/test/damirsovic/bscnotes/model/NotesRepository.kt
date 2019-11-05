package cz.test.damirsovic.bscnotes.model

import androidx.lifecycle.MutableLiveData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotesRepository {
    private val notesData = MutableLiveData<ArrayList<Note>>()
    val service = DataApiService.create()

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

    fun replaceData(oldNote: Note, newNote: Note): Int{
        //putNote(note)
        val position = notesData.value!!.indexOf(oldNote)
        System.out.println("Replacing at position $position")
        notesData.value!!.remove(oldNote)
        notesData.value!!.add(position, newNote)
        return position
    }

    fun getDataSize() = notesData.value!!.size

    fun getLastId() = notesData.value!!.get(notesData.value!!.size-1).id

    private fun getNotes() {

        val call = service.getNotes()
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

    fun removeDataReal(note:Note){
        val call = service.deleteNote(note.id)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                System.out.println(response.message())
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                System.out.println(t.localizedMessage)
            }
        })
    }

    fun saveNotes(note : Note)  {
        val call = service.postNote(note)
        call.enqueue(object : Callback<ResponseBody > {
            override fun onResponse(call: Call<ResponseBody >, response: Response<ResponseBody >) {
                System.out.println(response.message())
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                System.out.println(t.localizedMessage)
                removeData(note)
            }
        })
    }

    fun putNote(note : Note) {
        val call = service.putNote(note.id)
        call.enqueue(object : Callback<ResponseBody > {
            override fun onResponse(call: Call<ResponseBody >, response: Response<ResponseBody >) {
                System.out.println(response.message())
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                System.out.println(t.localizedMessage)
                removeData(note)
            }
        })
    }
}