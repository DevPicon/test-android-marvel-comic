package pe.devpicon.android.marvelcomic.entities

import android.os.Parcel
import android.os.Parcelable
import pe.devpicon.android.marvelcomic.utils.createParcel

data class Characters(val name: String): Parcelable {

    companion object {
        @JvmField @Suppress("unused")
        val CREATOR = createParcel {Characters(it)}
    }

    protected constructor(parcelIn: Parcel) : this (
            parcelIn.readString()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
    }

    override fun describeContents(): Int = 0
}

