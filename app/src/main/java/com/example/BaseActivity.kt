package com.example

import android.app.Dialog
import android.content.Context
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.chardhamyatra.R
import com.example.chardhamyatra.databinding.ActivityBaseBinding
import com.example.utils.Constants
import com.example.utils.Constants.mprogressDialog

open class BaseActivity : AppCompatActivity() {
    lateinit var mProgressDialog: Dialog
    lateinit var mbinding:ActivityBaseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding=ActivityBaseBinding.inflate(layoutInflater)
        setContentView(mbinding.root)
    }
    fun showProgressDialog(text:String){
        mprogressDialog = Dialog(this)
        mprogressDialog.setContentView(R.layout.dialog_progress)
        mprogressDialog.findViewById<TextView>(R.id.tv_progress_text).text=text
        mprogressDialog.setCanceledOnTouchOutside(false)
        mprogressDialog.show()
    }
    fun dismissDialog(){
        mprogressDialog.dismiss()
    }
}

