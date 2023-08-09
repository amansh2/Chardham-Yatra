package com.example.Authentication

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks.await
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthAppRepository(application: Application) {
    private val application: Application
    private val firebaseAuth: FirebaseAuth
    private val userLiveData: MutableLiveData<FirebaseUser>
    private val loggedOutLiveData: MutableLiveData<Boolean>
    private val loginStatus = MutableLiveData<Boolean>()
    private val registerStatus = MutableLiveData<Boolean>()

    init {
        this.application = application
        firebaseAuth = FirebaseAuth.getInstance()
        userLiveData = MutableLiveData<FirebaseUser>()
        loggedOutLiveData = MutableLiveData<Boolean>()

        if (firebaseAuth.currentUser != null) {
            userLiveData.postValue(firebaseAuth.currentUser)
            loggedOutLiveData.postValue(false)
        }
        loginStatus.postValue(false)
        registerStatus.postValue(false)
    }

    suspend fun register(email: String, password: String) {

        withContext(Dispatchers.IO) {
            try {
                firebaseAuth.createUserWithEmailAndPassword(email, password).await()
                registerStatus.postValue(true)
                userLiveData.postValue(firebaseAuth.currentUser)
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(application, "${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }





       suspend fun login(email: String, password: String) {

           withContext(Dispatchers.IO) {
               try {
                   firebaseAuth.signInWithEmailAndPassword(email, password).await()
                   loginStatus.postValue(true)
                   userLiveData.postValue(firebaseAuth.currentUser)
               } catch (e: Exception) {
                   withContext(Dispatchers.Main) {
                       Toast.makeText(application, "${e.message}", Toast.LENGTH_SHORT).show()
                   }
               }
           }
       }



        fun logOut() {
            firebaseAuth.signOut()
            loggedOutLiveData.postValue(true)
        }

        fun getUserLiveData(): MutableLiveData<FirebaseUser> {
            return userLiveData
        }

        fun getLoggedOutLiveData(): MutableLiveData<Boolean> {
            return loggedOutLiveData
        }

    fun getloginState(): MutableLiveData<Boolean> {
       return loginStatus
    }

    fun getRegisteredState():MutableLiveData<Boolean> {
       return registerStatus
    }

}




