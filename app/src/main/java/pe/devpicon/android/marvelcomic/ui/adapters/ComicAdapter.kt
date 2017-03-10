package pe.devpicon.android.marvelcomic.ui.adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.comic_item.view.*
import pe.devpicon.android.marvelcomic.entities.Comic
import pe.devpicon.android.marvelcomic.R
import pe.devpicon.android.marvelcomic.utils.loadImage

/**
 * Created by apico on 7/02/2017.
 */
class ComicAdapter(var items: List<Comic>?, val itemClick: OnItemClickListener) : RecyclerView.Adapter<ComicAdapter.ComicViewHolder>() {

    override fun onBindViewHolder(holder: ComicViewHolder, position: Int) {
        holder.bindComic(items?.get(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comic_item, parent, false)
        return ComicViewHolder(view, itemClick)
    }

    fun getItem(position: Int): Comic? {
        if(items != null && items!!.isNotEmpty()){
            items!!.get(position)
        }

        return null
    }

    override fun getItemCount(): Int = items?.size ?: 0

    override fun getItemId(position: Int): Long  = items?.get(position)?.id as Long? ?: 0

    class ComicViewHolder(itemView: View, val itemClick: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {

        fun bindComic(comic: Comic?) {

            var title: String = "Este es un titulo"
            if(comic != null){
                with(comic) {
                    Log.d(javaClass.simpleName, comic.toString())
                    itemView.img_comic_cover.loadImage(thumbnailUrl)
                    itemView.txt_comic_name.text = title
                    itemView.txt_comic_price.text = if (price > 0) price.toString() else itemView.context.getString(R.string.message_not_available)
                    itemView.setOnClickListener { itemClick(this) }
                }
            }
        }

    }

    interface OnItemClickListener {
        operator fun invoke(comic: Comic)
    }
}