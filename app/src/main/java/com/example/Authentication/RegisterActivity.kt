package com.example.Authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.chardhamyatra.databinding.ActivityRegisterBinding
import com.example.chardhamyatra.NavigationHostActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterActivity : AppCompatActivity() {
    lateinit var repository: AuthAppRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding=ActivityRegisterBinding.inflate(layoutInflater)
        repository= AuthAppRepository(application)

        repository.getUserLiveData().observe(this,{
            if(it!=null){
                val intent= Intent(this, NavigationHostActivity::class.java)
                startActivity(intent)
                finish()
            }
        })

        repository.getRegisteredState().observe(this,{
            if(it==true) binding.progress.visibility=View.GONE
        })

        binding.register.setOnClickListener {
            val email=binding.email.text.toString()
            val password1=binding.password.text.toString()
            val password2=binding.confirmPassword.text.toString()
            if(password1==password2 && password1.isNotEmpty() && email.isNotEmpty()){
                binding.register.setOnClickListener {
                    lifecycleScope.launch {
                        withContext(Dispatchers.Main){
                            binding.progress.visibility= View.VISIBLE
                        }
                        repository.register(email, password1)
                    }
                }
            }else if(password1!=password2) Toast.makeText(this,"passwords fields don't match",Toast.LENGTH_SHORT).show()
            else   Toast.makeText(this,"Email Address And Password Must Be Entered",
                Toast.LENGTH_SHORT).show()
        }
        setContentView(binding.root)
    }
}