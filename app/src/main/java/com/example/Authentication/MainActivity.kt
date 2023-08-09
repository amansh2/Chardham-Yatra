package com.example.Authentication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.chardhamyatra.NavigationHostActivity
import com.example.chardhamyatra.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private lateinit var repository: AuthAppRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = AuthAppRepository(application)
        repository.getUserLiveData().observe(this, {
            if (it != null) {
                val intent = Intent(this, NavigationHostActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
        repository.getloginState().observe(this,{
            if(it==true){
                binding.progress.visibility=View.GONE
            }
        })


        binding.apply {

            login.setOnClickListener {

                val e: String = email.text.toString()
                val p: String = password.text.toString()


                if (e.isNotEmpty() && p.isNotEmpty()) {
                    lifecycleScope.launch {
                        withContext(Dispatchers.Main) {
                            progress.visibility = View.VISIBLE
                        }
                        repository.login(e, p)

                    }
                }else{
                    Toast.makeText(this@MainActivity,"email & password must be entered",Toast.LENGTH_SHORT).show()
                }
            }
            register.setOnClickListener {
                startActivity(Intent(this@MainActivity, RegisterActivity::class.java))
                finish()
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
