package com.example.chardhamyatra

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.chardhamyatra.databinding.ActivityMainBinding
import com.example.eventmanagement.AuthAppRepository

class MainActivity : AppCompatActivity() {
    private lateinit var repository:AuthAppRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository= AuthAppRepository(application)
        repository.getUserLiveData().observe(this,{
            if(it!=null){
                val intent= Intent(this,NavigationHostActivity::class.java)
                startActivity(intent)
                finish()
            }
        })


        binding.apply {

            login.setOnClickListener {
                val e:String=email.text.toString()
                val p:String=password.text.toString()
                if(e.isNotEmpty() && p.isNotEmpty()){
                    repository.login(e,p)
                }
                else{
                    Toast.makeText(application.applicationContext,"Email Address And Password Must Be Entered",
                        Toast.LENGTH_SHORT).show()
                }
            }
            register.setOnClickListener {
               startActivity(Intent(this@MainActivity,RegisterActivity::class.java))
            }

        }





//
//
//        val uri =
//            Uri.parse("geo:29.941465620499056, 76.81746399686475?q=hotels")
//        val intent= Intent(Intent.ACTION_VIEW,uri)
//        intent.setPackage("com.google.android.apps.maps")
//        startActivity(intent)
    }


}
