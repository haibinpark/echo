package com.sctuopuyi.echo.util

import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.DialogInterface.BUTTON_NEGATIVE

class ProgressUtil {

    private var context: Context
    private lateinit var progressDialog: ProgressDialog

    constructor(context: Context) {
        this.context = context
        this.progressDialog = ProgressDialog(this.context)
    }

    fun getInstance(): ProgressDialog {
        return this.progressDialog
    }

    fun setTitle(titleName: String?): ProgressDialog {
        if (!titleName.isNullOrBlank())
            this.progressDialog.setTitle(titleName)
        return this.progressDialog
    }

    fun setMessage(messageContent: String?): ProgressDialog {
        if (!messageContent.isNullOrBlank())
            this.progressDialog.setMessage(messageContent)
        return this.progressDialog
    }

    fun setProgress(progress: Int): ProgressDialog {
        this.progressDialog.progress = progress
        return this.progressDialog
    }

    fun cancancel(cancel: Boolean): ProgressDialog {
        this.progressDialog.setCancelable(cancel)
        return this.progressDialog
    }

    fun setIndeterminate(indeterminate: Boolean): ProgressDialog {
        this.progressDialog.isIndeterminate = indeterminate
        return this.progressDialog
    }

    fun setMaxNum(maxNumber: Int): ProgressDialog {
        this.progressDialog.max = maxNumber
        return this.progressDialog
    }

    fun setCancelButton(buttonLabel:String,callback:DialogInterface.OnClickListener):ProgressDialog{
        this.progressDialog.setButton(BUTTON_NEGATIVE,buttonLabel,callback)
        return this.progressDialog
    }

    fun show(): ProgressDialog {
        this.progressDialog.show()
        return this.progressDialog
    }


}