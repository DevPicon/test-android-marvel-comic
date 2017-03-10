package pe.devpicon.android.marvelcomic.entities

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Armando on 28/2/2017.
 */
data class Creator(val name: String, val role: String) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Creator> = object : Parcelable.Creator<Creator> {
            override fun createFromParcel(source: Parcel): Creator = Creator(source)
            override fun newArray(size: Int): Array<Creator?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readString(), source.readString())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(name)
        dest?.writeString(role)
    }
}