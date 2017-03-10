package pe.devpicon.android.marvelcomic.utils

import android.os.Parcel
import android.os.Parcelable
import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * Created by Armando on 23/2/2017.
 */
fun ImageView.loadImage(url: String?){
    if(url != null){
        Glide.with(this.context)
                .load(url)
                .fitCenter()
                .into(this)
    }
}

inline fun <reified T : Parcelable> createParcel(
        crossinline createFromParcel: (Parcel) -> T?): Parcelable.Creator<T> =
        object : Parcelable.Creator<T> {
            override fun newArray(size: Int): Array<out T?> = arrayOfNulls(size)
            override fun createFromParcel(source: Parcel?): T = createFromParcel(source)
        }