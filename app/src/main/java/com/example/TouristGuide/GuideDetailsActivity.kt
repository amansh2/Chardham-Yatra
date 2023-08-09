package com.example.TouristGuide

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.bumptech.glide.Glide
import com.example.BaseActivity
import com.example.Firebase.Firestore
import com.example.chardhamyatra.R
import com.example.chardhamyatra.databinding.ActivityGuideDetailsBinding
import com.example.model.Guide
import com.example.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlin.math.roundToInt

class GuideDetailsActivity : BaseActivity() {
    lateinit var binding: ActivityGuideDetailsBinding
    private var uid: String = ""
    private var guideDetails: Guide? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuideDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if (intent.hasExtra(Constants.model)) {
            guideDetails = intent.getSerializableExtra(Constants.model) as Guide
        }
        uid = FirebaseAuth.getInstance().currentUser!!.uid
        if (guideDetails != null) setupUI(guideDetails!!)
        binding.rate.setOnClickListener {
            rateGuide()
        }
    }

    private fun rateGuide() {
        binding.ratingBar2.visibility = View.VISIBLE
        binding.submitRating.visibility = View.VISIBLE
        binding.rate.visibility = View.GONE
        binding.submitRating.setOnClickListener {
            val currRating = binding.ratingBar2.rating
            val totalUsers = guideDetails!!.users.size.toFloat()
            val prevRating =
                if (guideDetails!!.users[uid] != null) guideDetails!!.users[uid]!!.toFloat() else 0F
            val netRating =
                (guideDetails!!.Rating * totalUsers - prevRating + currRating) / (if (guideDetails!!.users[uid] != null) totalUsers else totalUsers + 1)

            val hashMap = HashMap<String, Any>()
            guideDetails!!.users[uid] = currRating
            hashMap[Constants.users] = guideDetails!!.users
            hashMap[Constants.rating] = netRating
            showProgressDialog(getString(R.string.please_wait))
            Firestore().updateGuideDetails(this, hashMap, guideDetails!!.id)
        }
    }

    fun setupUI(model: Guide) {
        dismissDialog()
        guideDetails = model
        binding.apply {
            nameDetails.text = model!!.name
            emailDetails.text = model!!.email
            priceDetails.text = "₹" + model!!.pricePerDay.toString() + "/day"
            descriptionDetails.text = model!!.description
            ratingBar1.rating = model!!.Rating
            phoneDetails.text = model!!.phone.toString()
            var rating = (model!!.Rating * 10.0).roundToInt() / 10.0
            numRating.text = "$rating/5"
            Glide.with(this@GuideDetailsActivity).load(model!!.image)
                .placeholder(R.drawable.ic_board_placeholder).centerCrop().into(imageView)
            ratingBar2.visibility = View.GONE
            submitRating.visibility = View.GONE
            rate.visibility = View.VISIBLE
            if (guideDetails!!.users[uid] != null) {
                rate.text = "Edit Rating"
            } else {
                rate.text = "Rate"
            }
            buttonPhone.setOnClickListener {
                requestPermissions()
            }
            buttonEmail.setOnClickListener {
                val intent=Intent(Intent.ACTION_SEND)
                intent.type = "message/rfc822";
                var arr= arrayOf(guideDetails!!.email)
                intent.putExtra(Intent.EXTRA_EMAIL,arr)
                if(intent.resolveActivity(packageManager)!=null) startActivity(Intent.createChooser(intent,"Send Email via"))
                else Toast.makeText(this@GuideDetailsActivity, "not resolved", Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun requestPermissions() {
        Dexter.withContext(this)
            .withPermissions(android.Manifest.permission.CALL_PHONE)
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    if (report!!.areAllPermissionsGranted()) {
                        val intent=Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+"${guideDetails!!.phone}"))
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this@GuideDetailsActivity,
                            "You have denied Media permission. Please allow it is mandatory.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<com.karumi.dexter.listener.PermissionRequest>?,
                    p1: PermissionToken?
                ) {
                    showRationalDialog(this@GuideDetailsActivity)
                }
            }).onSameThread().check()
    }

    private fun showRationalDialog(activity: Activity) {
        val builder = AlertDialog.Builder(activity)
        builder.setMessage("It Looks like you have turned off permissions required for this feature. It can be enabled under Application Settings")
        builder.setPositiveButton("Go To Settings") { _, _ ->
            try {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", activity.packageName, null)
                intent.data = uri
                ContextCompat.startActivity(activity, intent, null)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
            }
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    fun onPostUpdatesuccess() {
        dismissDialog()
        Toast.makeText(this, "Thank you for rating the guide", Toast.LENGTH_SHORT).show()
        setResult(RESULT_OK)
        showProgressDialog(getString(R.string.please_wait))
        Firestore().getGuideDetails(this, guideDetails?.id)
    }
}