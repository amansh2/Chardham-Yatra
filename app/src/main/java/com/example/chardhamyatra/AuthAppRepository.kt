package com.example.eventmanagement

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AuthAppRepository(application: Application) {
    private val application:Application
    private val firebaseAuth: FirebaseAuth
    private val userLiveData: MutableLiveData<FirebaseUser>
     private val loggedOutLiveData: MutableLiveData<Boolean>
    init{
        this.application=application
        firebaseAuth= FirebaseAuth.getInstance()
        userLiveData= MutableLiveData<FirebaseUser>()
        loggedOutLiveData= MutableLiveData<Boolean>()

        if(firebaseAuth.currentUser!=null){
            userLiveData.postValue(firebaseAuth.currentUser)
            loggedOutLiveData.postValue(false)
        }


    }

    fun register(email:String,password:String){

//             val task: Task<AuthResult> =
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(
                    application.applicationContext,
                    "Registration Successful " ,
                    Toast.LENGTH_SHORT
                ).show()
                userLiveData.postValue(firebaseAuth.currentUser)
            } else {
                Toast.makeText(
                    application.applicationContext,
                    "Registration Failed: " + it.exception?.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


    }


    fun login(email:String,password: String){


        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(
                    application.applicationContext,
                    "Login Successful " ,
                    Toast.LENGTH_SHORT
                ).show()
                userLiveData.postValue(firebaseAuth.currentUser)
            } else {
                Toast.makeText(
                    application.applicationContext,
                    "Login Failed: " + it.exception?.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }



    }

    fun logOut(){
        firebaseAuth.signOut()
        loggedOutLiveData.postValue(true)
    }

    fun getUserLiveData():MutableLiveData<FirebaseUser>{
        return userLiveData
    }

    fun getLoggedOutLiveData():MutableLiveData<Boolean>{
        return loggedOutLiveData
    }
}