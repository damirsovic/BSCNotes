package cz.test.damirsovic.bscnotes.model

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface DataApiService {
    @GET("notes")
    fun getNotes(): Call<ArrayList<Note>>

    @GET("notes/{id}")
    fun getNote(@Path("id") id : Int): Call<Note>

    @POST("notes")
    fun postNote(@Body note: Note): Call<ResponseBody>

    @PUT("notes/{id}")
    fun putNote(@Path("id") id : Int): Call<ResponseBody>

    @DELETE("notes/{id}")
    fun deleteNote(@Path("id") id : Int): Call<ResponseBody>

    companion object {
        fun create(): DataApiService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://private-9aad-note10.apiary-mock.com")
                .build()

            return retrofit.create(DataApiService::class.java)
        }
    }
}