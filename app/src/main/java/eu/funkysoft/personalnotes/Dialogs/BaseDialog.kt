package eu.funkysoft.personalnotes.Dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window

open class BaseDialog(context: Context): Dialog(context){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window!!.setBackgroundDrawableResource(android.R.color.transparent)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
    }
}