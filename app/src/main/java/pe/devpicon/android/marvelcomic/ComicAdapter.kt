package pe.devpicon.android.marvelcomic

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.comic_item.view.*

/**
 * Created by apico on 7/02/2017.
 */
class ComicAdapter(var items: List<Comic>) : RecyclerView.Adapter<ComicAdapter.ComicViewHolder>() {

    override fun onBindViewHolder(holder: ComicViewHolder, position: Int) {
        holder.bindComic(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comic_item, parent, false)
        return ComicViewHolder(view)
    }

    override fun getItemCount(): Int = items.size


    class ComicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindComic(comic: Comic) {
            with(comic) {
                Log.d(javaClass.simpleName, comic.toString())

                itemView.txt_comic_name.text = name
                itemView.txt_comic_price.text = if (price > 0) price.toString() else "Agotado"
            }
        }

    }
}