package eu.funkysoft.personalnotes.Api

import eu.funkysoft.personalnotes.BuildConfig
import eu.funkysoft.personalnotes.Dialogs.ProgressDialog
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ApiClient {

    private val URL = BuildConfig.API_URL

    val noteApi by lazy {
        ApiClient.create()
    }

    fun create():NoteApi{
        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(URL)
            .build()
        return retrofit.create(NoteApi::class.java)
    }

    fun showError(error: Throwable,progressDialog:ProgressDialog) {
        progressDialog.finishFail(error.toString())
        when (error) {
            is SocketTimeoutException -> {

            }
            is UnknownHostException -> {

            }
            is HttpException -> {

            }
        }
    }
}