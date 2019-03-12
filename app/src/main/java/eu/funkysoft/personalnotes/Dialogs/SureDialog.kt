package eu.funkysoft.personalnotes.Dialogs

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import eu.funkysoft.personalnotes.R

class SureDialog(context: Context,var listener:OnSureDialogClick):BaseDialog(context),View.OnClickListener {

    lateinit var buttonYes:Button
    lateinit var buttonNo:Button

    override fun onClick(p0: View?) {
        listener.onButtonClick(p0 == buttonYes)
        dismiss()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_sure)
        setCancelable(false)

        buttonYes = findViewById(R.id.button_sure_dialog_yes)
        buttonNo = findViewById(R.id.button_sure_dialog_no)

        buttonYes.setOnClickListener(this)
        buttonNo.setOnClickListener(this)

    }

    interface OnSureDialogClick{
        fun onButtonClick(isAccept:Boolean)
    }
}