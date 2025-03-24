/*
 * Author: Boden Kahn
 * Course: CSCI 403 Capstone
 * Description: This class holds data of a parking area.
*/
package com.example.speedpark

import android.os.Parcel
import android.os.Parcelable

class ParkingArea(val id: Int, val name: String, val url: String)  : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!
    )

    // Set the information for the object
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(url)
    }

    // Stub method
    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ParkingArea> {
        override fun createFromParcel(parcel: Parcel): ParkingArea {
            return ParkingArea(parcel)
        }

        override fun newArray(size: Int): Array<ParkingArea?> {
            return arrayOfNulls(size)
        }
    }
}