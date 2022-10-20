package me.shakhriyor.mustplayer.data.model

import android.os.Parcel
import android.os.Parcelable


data class MusicModel(
    val path: String? = null,
    val title: String? = null,
    val duration: Int? = null,
    val artistName: String? = null
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString()) {
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<MusicModel> {
        override fun createFromParcel(parcel: Parcel): MusicModel {
            return MusicModel(parcel)
        }

        override fun newArray(size: Int): Array<MusicModel?> {
            return arrayOfNulls(size)
        }
    }
}