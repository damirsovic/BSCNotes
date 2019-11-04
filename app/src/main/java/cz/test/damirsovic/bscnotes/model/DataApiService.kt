package cz.test.damirsovic.bscnotes.model

import android.content.res.Resources
import cz.test.damirsovic.bscnotes.R
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface DataApiService {
    @GET("notes")
    fun getUsers(): Call<ArrayList<Note>>

    @GET("notes/{id}")
    fun getUser(id : Int): Call<Note>

    @POST("notes")
    fun postUsers(): Call<ArrayList<Note>>

    @PUT("notes/{id}")
    fun putUser(id : Int): Call<Note>

    @DELETE("notes/{id}")
    fun deleteUser(id : Int): Call<Note>

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