package com.example.chardhamyatra

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.chardhamyatra.databinding.ActivityNavigationHostBinding
import com.example.eventmanagement.AuthAppRepository

class NavigationHostActivity : AppCompatActivity() {
    private lateinit var navController:NavController
    private  lateinit var drawerLayout:DrawerLayout
    private lateinit var repository: AuthAppRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding=ActivityNavigationHostBinding.inflate(layoutInflater)
        repository= AuthAppRepository(application)
        repository.getLoggedOutLiveData().observe(this,{
            if(it==true){
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
        })
        setContentView(binding.root)
        buildDrawerLayout(binding)

    }

    private fun buildDrawerLayout(binding: ActivityNavigationHostBinding) {
            navController = this.findNavController(R.id.host_fragment)
            drawerLayout = binding.drawerLayout
            NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
            NavigationUI.setupWithNavController(binding.navigationView, navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController,drawerLayout)
    }
    fun logout(item:MenuItem){
        repository.logOut()
    }
    fun website(item:MenuItem){
        val uri= Uri.parse("https://prodigy22.ccbp.tech")
        startActivity(Intent(Intent.ACTION_VIEW,uri))
    }
    fun medication(item:MenuItem){
        startActivity(Intent(this,Medi::class.java))
        Toast.makeText(this,"hello",Toast.LENGTH_SHORT).show()
    }
    fun contact(item:MenuItem){
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "*/*"
            putExtra(Intent.EXTRA_EMAIL, arrayOf("amanshdeep2@gmail.com"))
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }
    fun pdf(item: MenuItem){
        val uri= Uri.parse("https://iato.in/uploads/circulars/ROUTES_OF_CHARDHAM_YATRA_2019_(1).pdf")
        startActivity(Intent(Intent.ACTION_VIEW,uri))
    }

}