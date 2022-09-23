package com.demoalbum

import android.app.Dialog
import android.content.Context
import android.os.Bundle

class CustomProgressDialog constructor(context: Context?) : Dialog(context!!) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_progress)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
    }
}