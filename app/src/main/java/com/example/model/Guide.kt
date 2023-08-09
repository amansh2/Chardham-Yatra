package com.example.model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class Guide(
    var id:String="",
    var name:String="",
    var email:String="",
    var image:String="",
    var phone:Long=0,
    var pricePerDay:Int=0,
    var Rating:Float=0F,
    var users:HashMap<String,Float> = HashMap(),
    var description:String=""
): Serializable