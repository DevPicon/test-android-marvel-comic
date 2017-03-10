package pe.devpicon.android.marvelcomic.entities

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by apico on 7/02/2017.
 */
data class Comic(val id: Int, val title: String, val price: Double, val imageURL: String?,
                 val description: String? = null, val onSaleDate: String? = null, val pageCount: Int? = 0,
                 val series: String? = null, val thumbnailUrl: String? = "",
                 val creators: List<Creator>? = null, val characters: List<Characters>? = null) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Comic> = object : Parcelable.Creator<Comic> {
            override fun createFromParcel(source: Parcel): Comic = Comic(source)
            override fun newArray(size: Int): Array<Comic?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            source.readInt(),
            source.readString(),
            source.readDouble(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readInt(),
            source.readString(),
            source.readString() ,
            mutableListOf<Creator>().apply { source.readTypedList(this, Creator.CREATOR) },
            source.createTypedArrayList(Characters.CREATOR))

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(id)
        dest?.writeString(title)
        dest?.writeDouble(price)
        imageURL?.let { dest?.writeString(imageURL) }
        description?.let { dest?.writeString(description) }
        onSaleDate?.let { dest?.writeString(onSaleDate) }
        pageCount?.let { dest?.writeInt(pageCount ?: 0) }
        series?.let { dest?.writeString(series) }
        thumbnailUrl?.let { dest?.writeString(thumbnailUrl) }
        creators?.let { dest?.writeTypedList(creators) }
        characters?.let { dest?.writeTypedList(characters) }
    }
}



