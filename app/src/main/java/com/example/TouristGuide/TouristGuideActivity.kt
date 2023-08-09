package com.example.TouristGuide

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.BaseActivity
import com.example.Firebase.Firestore
import com.example.adapters.GuidesListAdapter
import com.example.adapters.OnItemClickedListener
import com.example.chardhamyatra.R
import com.example.chardhamyatra.databinding.ActivityTouristGuideBinding
import com.example.model.Guide
import com.example.utils.Constants

class TouristGuideActivity : BaseActivity() {
    lateinit var binding:ActivityTouristGuideBinding
    private val GuideDetailsActivityLauncher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode== RESULT_OK){
            showProgressDialog(getString(R.string.please_wait))
            Firestore().getAllGuides(this@TouristGuideActivity)
        }
    }
    private val GuideRegistrationActivityLauncher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode== RESULT_OK){
            showProgressDialog(getString(R.string.please_wait))
            Firestore().getAllGuides(this@TouristGuideActivity)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityTouristGuideBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        showProgressDialog(getString(R.string.please_wait))
        Firestore().getAllGuides(this@TouristGuideActivity)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.tourist_guide_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.guide ->GuideRegistrationActivityLauncher.launch(Intent(this,GuideRegistrationActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    fun setUpRecyclerView(guidesList: ArrayList<Guide>) {
        dismissDialog()
        binding.rvTouristGuide.layoutManager=LinearLayoutManager(this)
        binding.rvTouristGuide.adapter=GuidesListAdapter(guidesList,this,object:OnItemClickedListener{
            override fun onItemClicked(model: Guide) {
                val intent=Intent(this@TouristGuideActivity,GuideDetailsActivity::class.java)
                intent.putExtra(Constants.model,model)
                GuideDetailsActivityLauncher.launch(intent)
            }
        })
    }
}