package com.example.TouristGuide

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.BaseActivity
import com.example.Firebase.Firestore
import com.example.chardhamyatra.R
import com.example.chardhamyatra.databinding.ActivityGuideRegistrationBinding
import com.example.model.Guide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

class GuideRegistrationActivity : BaseActivity() {
    private var fGuidePicLink: String = ""
    lateinit var binding: ActivityGuideRegistrationBinding
    private var galleryUri: Uri? = null
    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        try {
            galleryUri = it
            Glide.with(this).load(galleryUri).centerCrop()
                .placeholder(R.drawable.ic_board_placeholder).into(binding.ivGuideImage)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuideRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.etEmail.setText(FirebaseAuth.getInstance().currentUser?.email)
        binding.ivGuideImage.setOnClickListener {
            requestPermissions()
        }
        binding.btnCreate.setOnClickListener {
            if (galleryUri != null) uploadGuidePic()
            else  Toast.makeText(
                this@GuideRegistrationActivity,
                "No Image Selected",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun requestPermissions() {
        Dexter.withContext(this)
            .withPermissions(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    if (report!!.areAllPermissionsGranted()) {
                        galleryLauncher.launch("image/*")
                    } else {
                        Toast.makeText(
                            this@GuideRegistrationActivity,
                            "You have denied Media permission. Please allow it is mandatory.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<com.karumi.dexter.listener.PermissionRequest>?,
                    p1: PermissionToken?
                ) {
                    showRationalDialog(this@GuideRegistrationActivity)
                }


            }).onSameThread().check()
    }


//    private fun registerGuide() {
//        showProgressDialog(getString(R.string.please_wait))
//        Firestore().findUser(this)
//    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    fun uploadGuidePic() {
        showProgressDialog(getString(R.string.please_wait))
        val sRef = FirebaseStorage.getInstance().reference.child(
            "GUIDE_IMAGE" + System.currentTimeMillis() + "." + getfileExtension(galleryUri)
        )
        sRef.putFile(galleryUri!!).addOnSuccessListener {
            it.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
                fGuidePicLink = it.toString()
                dismissDialog()
                createGuide()
            }
        }
    }

    private fun createGuide() {
        binding.apply {
            val name = etName.text.toString()
            val email = etEmail.text.toString()
            val phone = etPhone?.text.toString()
            val price = etPrice.text.toString()
            val description = etDescription.text.toString()
            val image = fGuidePicLink
            val id = FirebaseAuth.getInstance().currentUser!!.uid
            if (name.isNotEmpty() && phone.isNotEmpty() && image.isNotEmpty() && price.isNotEmpty() && description.isNotEmpty()) {
                val guide = Guide(
                    id,
                    name,
                    email,
                    image,
                    phone.toLong(),
                    price.toInt(),
                    0F,
                    HashMap(),
                    description
                )
                showProgressDialog(getString(R.string.please_wait))
                Firestore().createGuide(this@GuideRegistrationActivity, guide)
            } else {
                Toast.makeText(
                    this@GuideRegistrationActivity,
                    "Please Fill all The Details",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    private fun getfileExtension(galleryUri: Uri?): Any? {
        return MimeTypeMap.getSingleton()
            .getExtensionFromMimeType(contentResolver.getType(galleryUri!!))
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

    fun guideCreationSuccessfull() {
        dismissDialog()
        setResult(RESULT_OK)
        Toast.makeText(
            this@GuideRegistrationActivity,
            "Guide registered successfully",
            Toast.LENGTH_SHORT
        ).show()
        finish()
    }

}