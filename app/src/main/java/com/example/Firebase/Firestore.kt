package com.example.Firebase

import android.widget.Toast
import com.example.TouristGuide.GuideDetailsActivity
import com.example.TouristGuide.GuideRegistrationActivity
import com.example.TouristGuide.TouristGuideActivity
import com.example.model.Guide
import com.example.utils.Constants
import com.example.utils.Constants.guides
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase

class Firestore {
    private val mFirestore = FirebaseFirestore.getInstance()

//    fun findUser(activity: GuideRegistrationActivity) {
//           mFirestore.collection(Constants.guides).document(getCurrentUserId()).get().addOnSuccessListener {
//               activity.dismissDialog()
//               if(it.exists()){
//                   activity.dismissDialog()
//                   Toast.makeText(activity,"Guide Already Exists",Toast.LENGTH_SHORT).show()
//               }else{
//                   activity.uploadGuidePic()
//               }
//           }.addOnFailureListener {
//               activity.dismissDialog()
//               Toast.makeText(activity,"${it.message}",Toast.LENGTH_SHORT).show()
//           }
//    }

        private fun getCurrentUserId(): String {
                return FirebaseAuth.getInstance().currentUser!!.uid
        }

    fun createGuide(activity: GuideRegistrationActivity, guide: Guide) {
        mFirestore.collection(Constants.guides).document(getCurrentUserId()).set(guide).addOnSuccessListener {
            activity.guideCreationSuccessfull()
        }.addOnFailureListener {
            activity.dismissDialog()
            it.printStackTrace()
        }
    }

    fun getAllGuides(activity: TouristGuideActivity) {
        mFirestore.collection(Constants.guides).get().addOnSuccessListener {
            val guidesList=ArrayList<Guide>()
            for(i in it){
                val guide=i.toObject(Guide::class.java)
                guidesList.add(guide)
            }
            activity.setUpRecyclerView(guidesList)
        }.addOnFailureListener{
            activity.dismissDialog()
            it.printStackTrace()
        }
    }

    fun updateGuideDetails(activity: GuideDetailsActivity, hashMap: HashMap<String, Any>, id: String) {
        mFirestore.collection(Constants.guides).document(id).update(hashMap).addOnSuccessListener {
            activity.onPostUpdatesuccess()
        }.addOnFailureListener {
            activity.dismissDialog()
            it.printStackTrace()
        }
    }

    fun getGuideDetails(activity:GuideDetailsActivity,id: String?) {
        if (id != null) {
            mFirestore.collection(Constants.guides).document(id).get().addOnSuccessListener {
                val guide=it.toObject(Guide::class.java)!!
                activity.setupUI(guide)
            }.addOnFailureListener {
                activity.dismissDialog()
                it.printStackTrace()
            }
        }
    }


}