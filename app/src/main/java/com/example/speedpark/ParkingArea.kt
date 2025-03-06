//Author: Boden Kahn
//Course: Capstone CSCI 403
//Due: 3/6/25
package com.example.speedpark

import android.os.Parcel
import android.os.Parcelable

class ParkingArea(val id: Int, val name: String, var isChecked: Boolean = false)  : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readBoolean()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeBoolean(isChecked)
    }

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