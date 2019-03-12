package eu.funkysoft.personalnotes.Api

import eu.funkysoft.personalnotes.Api.Models.Note
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*

interface NoteApi {
    @GET("notes")
    fun getNotes(): Observable<ArrayList<Note>>

    @POST("notes")
    fun addNote(@Body note:Note): Observable<Note>

    @GET("notes/{id}")
    fun getNoteById(@Path("id") id:Int): Observable<Note>

    @PUT("notes/{id}")
    fun editNoteById(@Path("id") id:Int,@Body note:Note): Observable<Note>

    @DELETE("notes/{id}")
    fun deleteNoteById(@Path("id") id:Int): Observable<Response<Void>>
}